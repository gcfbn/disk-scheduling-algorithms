package algorithms;

import requestPackage.DeadlineComparator;
import requestPackage.Request;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class EDF implements Algorithm{

    @Override
    public int execute(ArrayList<Request> requests, int diskSize) {

        // queue containing requests and sorted by their deadlines
        PriorityQueue<Request> queue = new PriorityQueue<>(new DeadlineComparator());

        int head = 0;
        int currentTime = 0;
        int totalJumps = 0;
        int completedRequests = 0;

        // index of request that will be added to the queue as next
        int requestIndex = 0;
        
        while (completedRequests < requests.size()){

            // add requests that already arrived
            while (requestIndex < requests.size() && requests.get(requestIndex).getArrivalTime() <= currentTime) {
                queue.add(requests.get(requestIndex++));
            }

            // if queue is empty, jump in time and add request with smallest arrival time to the queue
            if (queue.isEmpty()){
                queue.add(requests.get(requestIndex++));
                currentTime = queue.peek().getArrivalTime();
            }

            // get request with the lowest (earliest) deadline
            Request earliestDeadlineRequest = queue.poll();

            // get distance between the head and earliestDeadlineRequest
            int distance = Math.abs(earliestDeadlineRequest.getPosition() - head);

            // move the head to requested position
            currentTime += distance;
            totalJumps += distance;
            head = earliestDeadlineRequest.getPosition();

            // complete request
            earliestDeadlineRequest.setCompletionTime(currentTime);
            completedRequests++;
        } // end of while loop

        return totalJumps;
    }
}
