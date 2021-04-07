package algorithms;

import requestPackage.ArrivalTimeComparator;
import requestPackage.PositionComparator;
import requestPackage.Request;

import java.util.ArrayList;

public class SCAN implements Algorithm {

    @Override
    public int execute(ArrayList<Request> requests, int diskSize) {

        boolean directionRight = true;   //true if head moves from 0 to diskSize and false if head moves the opposite direction

        int head = 0;
        int currentTime = 0;
        int totalJumps = 0;
        int completedRequests = 0;

        ArrayList<Request> queue = new ArrayList<>();
        requests.sort(new ArrivalTimeComparator());
        int requestIndex = 0;

        while (completedRequests < requests.size()) {

            while (requestIndex < requests.size() && requests.get(requestIndex).getArrivalTime() <= currentTime) {
                queue.add(requests.get(requestIndex++));
            }

            if (queue.isEmpty()) {
                queue.add(requests.get(requestIndex++));
                currentTime = queue.get(0).getArrivalTime();
            }

            queue.sort(new PositionComparator());

            int closestIndex;
            Request closestRequest;

            if (directionRight) {

                closestIndex = 0;
                closestRequest = queue.get(closestIndex);

                while (closestIndex < queue.size() && closestRequest.getPosition() < head)
                    closestRequest = queue.get(closestIndex++);


                if (closestRequest.getPosition() < head) {    //move head to the edge of the disk and come back to closest request
                    currentTime += diskSize - head - 1;
                    totalJumps += diskSize - head - 1;
                    head = diskSize - 1;

                    directionRight = false;

                    currentTime += head - closestRequest.getPosition();
                    totalJumps += head - closestRequest.getPosition();

                } else {

                    currentTime += closestRequest.getPosition() - head;
                    totalJumps += closestRequest.getPosition() - head;
                }

            } else {   //direction left

                closestIndex = queue.size() - 1;
                closestRequest = queue.get(closestIndex);

                while (closestIndex >= 0 && closestRequest.getPosition() > head)
                    closestRequest = queue.get(closestIndex--);


                if (closestRequest.getPosition() > head) {    //move head to the edge of the disk and come back to closest request
                    currentTime += head;
                    totalJumps += head;
                    head = 0;

                    directionRight = true;

                    currentTime += closestRequest.getPosition();
                    totalJumps += closestRequest.getPosition();

                } else {

                    currentTime += head - closestRequest.getPosition();
                    totalJumps += head - closestRequest.getPosition();
                }
            }

            head = closestRequest.getPosition();

            closestRequest.setCompletionTime(currentTime);
            completedRequests++;
            queue.remove(closestRequest);
        }

        return totalJumps;
    }


}
