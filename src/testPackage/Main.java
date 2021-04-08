package testPackage;

import algorithms.*;
import requestPackage.Request;

import java.util.ArrayList;

public class Main {

    public static void main (String[] args){

        final int DISK_SIZE = 101;

        ArrayList<Request> requests = new ArrayList<>();
        requests.add(new Request(90, 60, 1000));
        requests.add(new Request(80, 20, 300));
        requests.add(new Request(40, 30, 450));
        requests.add(new Request(30, 50, 200));
        requests.add(new Request(30, 0, 700));

        Algorithm algorithm = new FDSCAN();
        int totalJumps = algorithm.execute(requests, DISK_SIZE);

        System.out.println("Total jumps: " + totalJumps);
        for (Request r : requests){
            System.out.println("Position: " + r.getPosition() + ", completion time: " + r.getCompletionTime());
        }
    }

}
