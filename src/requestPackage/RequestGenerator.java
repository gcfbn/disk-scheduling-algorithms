package requestPackage;

import java.util.ArrayList;
import java.util.Random;

public class RequestGenerator {

    public static ArrayList<Request> getRandomSet(int diskSize, int maxArrivalTime, int numberOfRequests) {

        ArrayList<Request> requests = new ArrayList<>();
        Random randomGenerator = new Random();

        for (int i = 0; i < numberOfRequests; i++) {
            int randomPosition = Math.abs(randomGenerator.nextInt() % (diskSize));
            int randomArrivalTime = Math.abs(randomGenerator.nextInt() % (maxArrivalTime + 1));
            int randomDeadline;
            if (randomGenerator.nextDouble() >= 0.25)   // 1/4 of all requests will have no deadline
                randomDeadline = (int) (randomArrivalTime + (1 + 5 * randomGenerator.nextDouble()) * diskSize);
            else
                randomDeadline = Integer.MAX_VALUE;

            // create request and add it to the queue
            requests.add(new Request(randomPosition, randomArrivalTime, randomDeadline));
        }
        return requests;
    }
}
