package requestPackage;

import java.util.Comparator;

public class PositionComparator implements Comparator<Request> {

    @Override
    public int compare(Request o1, Request o2) {
        return o1.getPosition() - o2.getPosition();
    }
}
