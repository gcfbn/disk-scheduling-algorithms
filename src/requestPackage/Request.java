package requestPackage;

public class Request {

    private final int position, arrivalTime, deadline;
    private int completionTime;

    public Request(int position, int arrivalTime, int deadline) {
        this.position = position;
        this.arrivalTime = arrivalTime;
        this.deadline = deadline;
    }

    public int getPosition() {
        return position;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getDeadline() {
        return deadline;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getWaitingTime(){
        return completionTime - arrivalTime;
    }
}
