package algorithms;

import requestPackage.ArrivalTimeComparator;
import requestPackage.PositionComparator;
import requestPackage.Request;

import java.util.ArrayList;

public class SCAN implements Algorithm {

    @Override
    public int execute(ArrayList<Request> requests, int diskSize) {

        boolean directionRight = true;   // true if head moves from 0 to diskSize and false if head moves the opposite direction

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
            QueueHelper.addRequests(queue, requests, currentTime, requestIndex);

            // if any of requests didn't arrive, jump in time to the earliest request that is not yet completed
            if (queue.isEmpty()) {
                queue.add(requests.get(requestIndex++));
                currentTime = queue.get(0).getArrivalTime();
            }

            // sort queue by requests' positions (requested disc blocks)
            queue.sort(new PositionComparator());

            // request closest to the head considering it's direction
            int closestIndex;
            Request closestRequest;

            if (directionRight) {   // if head is moving right

                // set closest index to first from the queue
                closestIndex = 0;
                closestRequest = queue.get(closestIndex);

                // find closest request on the right-hand side of the queue
                while (closestIndex < queue.size() && closestRequest.getPosition() < head)
                    closestRequest = queue.get(closestIndex++);

                // if there is no requests on the right-hand side of the head
                // move the head to the edge of the disk (block number diskSize - 1)
                //  and come back to the closest request
                if (closestRequest.getPosition() < head) {
                    currentTime += diskSize - head - 1;
                    totalJumps += diskSize - head - 1;
                    head = diskSize - 1;

                    // change direction - now the head moves left
                    directionRight = false;

                    // add time that passed during the head's move
                    currentTime += head - closestRequest.getPosition();
                    totalJumps += head - closestRequest.getPosition();

                } else { // if closestRequest is on the right-hand side of the queue

                    // add time that passed during the head's move
                    currentTime += closestRequest.getPosition() - head;
                    totalJumps += closestRequest.getPosition() - head;
                }

            } else {   // if head is moving left

                // set closest index to last from the queue
                closestIndex = queue.size() - 1;
                closestRequest = queue.get(closestIndex);

                // find closest request on the left-hand side of the head
                while (closestIndex >= 0 && closestRequest.getPosition() > head)
                    closestRequest = queue.get(closestIndex--);

                //  if there is no requests on the left-hand side of the head
                //  move the head to the edge of the disk (block number 0)
                //  and come back to the closest request
                if (closestRequest.getPosition() > head) {    // move head to the edge of the disk and come back to closest request
                    currentTime += head;
                    totalJumps += head;

                    // change direction - now the head moves right
                    directionRight = true;

                    // add time that passed during the head's move
                    currentTime += closestRequest.getPosition();
                    totalJumps += closestRequest.getPosition();

                } else {// if closest request is on the left-hand side of the head

                    // add time that passed during the head's move
                    currentTime += head - closestRequest.getPosition();
                    totalJumps += head - closestRequest.getPosition();
                }
            }

            // move head to closest request position
            head = closestRequest.getPosition();

            // remove completed request from the queue
            closestRequest.setCompletionTime(currentTime);
            completedRequests++;
            queue.remove(closestRequest);

        } // end of while loop

        return totalJumps;
    }
}
