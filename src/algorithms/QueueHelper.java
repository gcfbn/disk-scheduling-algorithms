package algorithms;

import requestPackage.PositionComparator;
import requestPackage.Request;

import java.util.ArrayList;


public class QueueHelper {

    /**
     * Adds requests that already have arrived to the queue
     *
     * @param queue current queue with requests
     * @param requests list of all requests
     * @param currentTime current time in algorithm
     * @param requestIndex index of request that would be added to the queue as next
     *
     * */
    public static void addRequests(ArrayList<Request> queue, ArrayList<Request> requests, int currentTime, int requestIndex){

        while (requestIndex < requests.size() && requests.get(requestIndex).getArrivalTime() <= currentTime) {
            queue.add(requests.get(requestIndex++));
        }
    }
}
