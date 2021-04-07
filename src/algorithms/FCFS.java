package algorithms;

import requestPackage.ArrivalTimeComparator;
import requestPackage.Request;

import java.util.ArrayList;

public class FCFS implements Algorithm{

    @Override
    public int execute(ArrayList<Request> requests, int diskSize) {

        int head = 0;
        int currentTime = 0;
        int totalJumps = 0;

        requests.sort(new ArrivalTimeComparator());

        for (Request r : requests){

            if (currentTime < r.getArrivalTime()) currentTime = r.getArrivalTime();
            totalJumps += Math.abs(head - r.getPosition());
            currentTime += Math.abs(head - r.getPosition());
            head = r.getPosition();

            r.setCompletionTime(currentTime);
        }

        return totalJumps;
    }
}
