package deque;
//import net.sf.saxon.expr.Component;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> c = getDefaultComparator();;

    public MaxArrayDeque(Comparator<T> o) {
        c = o;
    }

    private class DefaultComparator implements Comparator<T> {
        public int compare(T o1, T o2) {
            Comparable<T> c1;
            c1 = (Comparable<T>) o1;
            return c1.compareTo(o2);
        }
    }

    private DefaultComparator getDefaultComparator() {
        return new DefaultComparator();
    }

    public T max() {
        int maxInd = 0;
        for (int i = 0; i < this.size(); i++) {
            if (c.compare(this.get(i), this.get(maxInd)) > 0) {
                maxInd = i;
            }
        }
        return this.get(maxInd);
    }

    public T max(Comparator<T> c1) {
        int maxInd = 0;
        for (int i = 0; i < this.size(); i++) {
            if (c1.compare(this.get(i), this.get(maxInd)) > 0) {
                maxInd = i;
            }
        }
        return this.get(maxInd);
    }

}
