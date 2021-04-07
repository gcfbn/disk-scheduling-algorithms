package algorithms;

import requestPackage.ArrivalTimeComparator;
import requestPackage.Request;

import java.util.ArrayList;

public class SSTF implements Algorithm {

    @Override
    public int execute(ArrayList<Request> requests, int diskSize) {

        int head = 0;
        int currentTime = 0;
        int totalJumps = 0;
        int completedRequests = 0;

        // create queue (it's not an FIFO queue but queue filled with waiting requests)
        // if current time > request arrival time, request will be added to the queue
        ArrayList<Request> queue = new ArrayList<>();

        // sort requests by arrival time
        requests.sort(new ArrivalTimeComparator());

        // index of request that will be added to the queue as next
        int requestIndex = 0;

        while (completedRequests < requests.size()) {

            // add to the queue requests that already arrived
            while (requestIndex < requests.size() && requests.get(requestIndex).getArrivalTime() <= currentTime) {
                queue.add(requests.get(requestIndex++));
            }

            // if any of requests didn't arrive, jump in time to the earliest request that is not yet completed
            if (queue.isEmpty()) {
                queue.add(requests.get(requestIndex++));
                currentTime = queue.get(0).getArrivalTime();
            }

            // request with position closest to the head
            Request closestRequest = queue.get(0);

            // smallest distance between head an request position
            int smallestDistance = Math.abs(closestRequest.getPosition() - head);

            // find closest request from the queue
            for (int i = 1; i < queue.size(); i++) {
                int distance = Math.abs(queue.get(i).getPosition() - head);
                if (distance < smallestDistance) {
                    closestRequest = queue.get(i);
                    smallestDistance = distance;
                }
            }

            // move the head to requested position
            totalJumps += smallestDistance;
            currentTime += smallestDistance;
            head = closestRequest.getPosition();

            // remove completed request from the queue
            closestRequest.setCompletionTime(currentTime);
            queue.remove(closestRequest);
            completedRequests++;

        } // end of while loop

        return totalJumps;
    }
}
