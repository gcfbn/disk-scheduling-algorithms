package requestPackage;

import java.util.Comparator;

public class DeadlineComparator implements Comparator<Request> {

    @Override
    public int compare(Request o1, Request o2) {
        return Integer.compare(o1.getDeadline(), o2.getDeadline());
    }
}
