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

        ArrayList<Request> queue = new ArrayList<>();
        requests.sort(new ArrivalTimeComparator());
        int requestIndex = 0;

        while (completedRequests < requests.size()) {

            while (requestIndex < requests.size() && requests.get(requestIndex).getArrivalTime() <= currentTime) {
                queue.add(requests.get(requestIndex));
                requestIndex++;
            }

            if (queue.isEmpty()) {
                queue.add(requests.get(requestIndex));
                currentTime = queue.get(0).getArrivalTime();
            }

            Request closestRequest = queue.get(0);
            int smallestDistance = Math.abs(closestRequest.getPosition() - head);

            for (int i = 1; i < queue.size(); i++) {
                int distance = Math.abs(queue.get(i).getPosition() - head);
                if (distance < smallestDistance) {
                    closestRequest = queue.get(i);
                    smallestDistance = distance;
                }
            }

            totalJumps += smallestDistance;
            currentTime += smallestDistance;
            head = closestRequest.getPosition();

            closestRequest.setCompletionTime(currentTime);
            queue.remove(closestRequest);
            completedRequests++;
        }

        return totalJumps;
    }
}
