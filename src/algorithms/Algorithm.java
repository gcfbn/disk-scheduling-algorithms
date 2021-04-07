package algorithms;

import requestPackage.Request;

import java.util.ArrayList;

public interface Algorithm {
    int execute(ArrayList<Request> requests, int diskSize);
}
