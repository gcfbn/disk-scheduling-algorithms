import java.util.ArrayList;
import java.util.Random;

public class RequestGenerator {

    private static final int DISK_SIZE = 100;
    private static final int MAX_ARRIVAL_TIME = 100;
    private static final int NUMBER_OF_REQUESTS = 15;

    public static ArrayList<Request> getRandomSet(){

        ArrayList<Request> requests = new ArrayList<>();
        Random randomGenerator = new Random();

        for (int i=0; i < NUMBER_OF_REQUESTS; i++){
            int randomPosition = Math.abs(randomGenerator.nextInt() % (DISK_SIZE + 1));
            int randomArrivalTime = Math.abs(randomGenerator.nextInt() % (MAX_ARRIVAL_TIME + 1));
            int randomDeadline;
            if (randomGenerator.nextDouble() >= 0.25)
                randomDeadline = (int)(randomArrivalTime + (1 + 5 * randomGenerator.nextDouble()) * DISK_SIZE);
            else
                randomDeadline = 0;

            requests.add(new Request(randomPosition, randomArrivalTime, randomDeadline));
        }
        return requests;
    }
}
