package testPackage;

import algorithms.*;
import requestPackage.Request;
import requestPackage.RequestGenerator;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        final int DISK_SIZE = 101;
        final int MAX_ARRIVAL_TIME = 1000;
        final int NUMBER_OF_REQUESTS = 100;


//        ArrayList<Request> requests = new ArrayList<>();
//        requests.add(new Request(90, 60, 450));
//        requests.add(new Request(80, 20, 200));
//        requests.add(new Request(50, 30, 300));
//        requests.add(new Request(30, 50, 250));
//        requests.add(new Request(30, 0, 700));

        ArrayList<Request> requests = RequestGenerator.getRandomSet(DISK_SIZE, MAX_ARRIVAL_TIME, NUMBER_OF_REQUESTS);
        int totalJumps;
        double averageWaitingTime;
        Algorithm algorithm;


        algorithm = new FCFS();
        totalJumps = algorithm.execute(requests, DISK_SIZE);
        averageWaitingTime = 0;

        for (Request r : requests) averageWaitingTime += r.getWaitingTime();
        averageWaitingTime /= NUMBER_OF_REQUESTS;

        System.out.println("FCFS: ");
        System.out.println("Jumps: " + totalJumps);
        System.out.println("Average waiting time: " + averageWaitingTime);
        System.out.println();


        algorithm = new SSTF();
        totalJumps = algorithm.execute(requests, DISK_SIZE);
        averageWaitingTime = 0;

        for (Request r : requests) averageWaitingTime += r.getWaitingTime();
        averageWaitingTime /= NUMBER_OF_REQUESTS;

        System.out.println("SSTF: ");
        System.out.println("Jumps: " + totalJumps);
        System.out.println("Average waiting time: " + averageWaitingTime);
        System.out.println();



        algorithm = new SCAN();
        totalJumps = algorithm.execute(requests, DISK_SIZE);
        averageWaitingTime = 0;

        for (Request r : requests) averageWaitingTime += r.getWaitingTime();
        averageWaitingTime /= NUMBER_OF_REQUESTS;

        System.out.println("SCAN: ");
        System.out.println("Jumps: " + totalJumps);
        System.out.println("Average waiting time: " + averageWaitingTime);
        System.out.println();



        algorithm = new CSCAN();
        totalJumps = algorithm.execute(requests, DISK_SIZE);
        averageWaitingTime = 0;

        for (Request r : requests) averageWaitingTime += r.getWaitingTime();
        averageWaitingTime /= NUMBER_OF_REQUESTS;

        System.out.println("CSCAN: ");
        System.out.println("Jumps: " + totalJumps);
        System.out.println("Average waiting time: " + averageWaitingTime);
        System.out.println();



        algorithm = new EDF();
        totalJumps = algorithm.execute(requests, DISK_SIZE);
        averageWaitingTime = 0;

        for (Request r : requests) averageWaitingTime += r.getWaitingTime();
        averageWaitingTime /= NUMBER_OF_REQUESTS;

        System.out.println("EDF: ");
        System.out.println("Jumps: " + totalJumps);
        System.out.println("Average waiting time: " + averageWaitingTime);
        System.out.println();


//        System.out.println();
//        for (Request r : requests) {
//            System.out.println("Position: " + r.getPosition() + ", completion time: " + r.getCompletionTime());
//        }
//        System.out.println();


        algorithm = new FDSCAN();
        totalJumps = algorithm.execute(requests, DISK_SIZE);
        averageWaitingTime = 0;

        for (Request r : requests) averageWaitingTime += r.getWaitingTime();
        averageWaitingTime /= NUMBER_OF_REQUESTS;

        System.out.println("FDSCAN: ");
        System.out.println("Jumps: " + totalJumps);
        System.out.println("Average waiting time: " + averageWaitingTime);
        System.out.println();


//        System.out.println();
//        for (Request r : requests) {
//            System.out.println("Position: " + r.getPosition() + ", completion time: " + r.getCompletionTime());
//        }
//        System.out.println();
    }

}
