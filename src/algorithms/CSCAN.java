package algorithms;

import requestPackage.ArrivalTimeComparator;
import requestPackage.PositionComparator;
import requestPackage.Request;

import java.util.ArrayList;

public class CSCAN implements Algorithm{

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

        // index of request that would be added to the queue as next
        int requestIndex = 0;

        while (completedRequests < requests.size()) {

            // add to the queue requests that already arrived
            QueueHelper.addRequests(queue, requests, currentTime, requestIndex);

            // if any of requests didn't arrive, jump in time to the earliest request that is not yet completed
            if (queue.isEmpty()) {
                queue.add(requests.get(requestIndex++));
                currentTime = queue.get(0).getArrivalTime();
            }

            // sort queue by requests' positions (requested disc blocks)
            queue.sort(new PositionComparator());

            // find request closest to the head
            int closestIndex = 0;
            Request closestRequest = queue.get(closestIndex);

            while (closestIndex < queue.size() && closestRequest.getPosition() < head)
                closestRequest = queue.get(closestIndex++);

            // if there is no requests on the right-hand side of the head
            // move the head to the edge of the disk (block number diskSize - 1)
            // and then move the head back to block number 0
            if (closestRequest.getPosition() < head){

                currentTime += diskSize - 1 - head;
                totalJumps += diskSize - 1 - head;

                // move the head back to position 0
                currentTime += diskSize - 1;
                totalJumps += diskSize - 1;
                head = 0;
            }

            else{ // if closestRequest is on the right-hand side of the queue

                // add time that passed during the head's move
                currentTime += closestRequest.getPosition() - head;
                totalJumps += closestRequest.getPosition() - head;

                // move the head to requested position
                head = closestRequest.getPosition();

                // remove completed request from the queue
                closestRequest.setCompletionTime(currentTime);
                queue.remove(closestRequest);
                completedRequests++;
            }
        } // end of while loop

        return totalJumps;
    }
}
