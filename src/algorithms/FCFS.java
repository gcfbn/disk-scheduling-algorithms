package algorithms;

import requestPackage.ArrivalTimeComparator;
import requestPackage.Request;

import java.util.ArrayList;

public class FCFS implements Algorithm {

    @Override
    public int execute(ArrayList<Request> requests, int diskSize) {

        int head = 0;
        int currentTime = 0;
        int totalJumps = 0;

        // sort requests by arrival time
        requests.sort(new ArrivalTimeComparator());

        for (Request r : requests) {

            // jump in time to the next request if needed
            if (currentTime < r.getArrivalTime()) currentTime = r.getArrivalTime();

            // move the head from current position to requested position
            totalJumps += Math.abs(head - r.getPosition());
            currentTime += Math.abs(head - r.getPosition());
            head = r.getPosition();

            // set completion time for current request
            r.setCompletionTime(currentTime);
        }

        return totalJumps;
    }
}
