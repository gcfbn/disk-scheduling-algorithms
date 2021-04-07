package algorithms;

import requestPackage.Request;

import java.util.ArrayList;

public interface Algorithm {
    void execute(ArrayList<Request> requests, int diskSize);
}
