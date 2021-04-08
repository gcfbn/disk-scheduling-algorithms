package algorithms;

import requestPackage.ArrivalTimeComparator;
import requestPackage.PositionComparator;
import requestPackage.Request;

import java.util.ArrayList;

public class FDSCAN implements Algorithm {

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

            // copy queue and sort it by position
            ArrayList<Request> queueByPosition = new ArrayList<>(queue);
            queueByPosition.sort(new PositionComparator());

            // find request with smallest (earliest) deadline
            Request closestDeadlineRequest = queue.get(0);

            for (int i = 1; i < queue.size(); i++) {
                if (queue.get(i).getDeadline() < closestDeadlineRequest.getDeadline())
                    closestDeadlineRequest = queue.get(i);
            }

            // set direction, where head should move
            boolean directionRight = head < closestDeadlineRequest.getPosition();

            // move head to requested direction and service requests on the way
            int currentIndex;

            if (directionRight) {
                currentIndex = 0;
                while (currentIndex < queueByPosition.size() && queueByPosition.get(currentIndex).getPosition() <= closestDeadlineRequest.getPosition()) {

                    Request currentRequest = queueByPosition.get(currentIndex);

                    // if request under currentIndex is on the right-hand side of the head
                    if (currentRequest.getPosition() >= head) {

                        int distance = Math.abs(head - currentRequest.getPosition());
                        currentTime += distance;
                        totalJumps += distance;
                        head = currentRequest.getPosition();

                        // service request
                        currentRequest.setCompletionTime(currentTime);
                        queue.remove(currentRequest);
                        completedRequests++;
                    }

                    currentIndex++;
                }
            } // end of if (directionRight)
            else { // direction left

                currentIndex = queueByPosition.size() - 1;
                while (currentIndex >= 0 && queueByPosition.get(currentIndex).getPosition() >= closestDeadlineRequest.getPosition()) {

                    Request currentRequest = queueByPosition.get(currentIndex);

                    // if request under currentIndex is on the left-hand side of the head
                    if (currentRequest.getPosition() <= head) {

                        int distance = Math.abs(head - currentRequest.getPosition());
                        currentTime += distance;
                        totalJumps += distance;
                        head = currentRequest.getPosition();

                        // service request
                        currentRequest.setCompletionTime(currentTime);
                        queue.remove(currentRequest);
                        completedRequests++;
                    }

                    currentIndex--;
                }

            } // end of else

            queueByPosition.clear();
        } // end of while loop

        return totalJumps;
    }
}
