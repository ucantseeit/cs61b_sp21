package randomizedtest;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;
import timingtest.AList;

public class testThreeAddThreeRemove {
    @Test
    public void testThreeAddLastThreeRemove(){
        AListNoResizing<Integer> ANR = new AListNoResizing<>();
        BuggyAList<Integer> BA = new BuggyAList<>();

        ANR.addLast(4);
        ANR.addLast(5);
        ANR.addLast(6);
        BA.addLast(4);
        BA.addLast(5);
        BA.addLast(6);

        assertEquals(ANR.size(), BA.size());
        assertEquals(ANR.removeLast(), BA.removeLast());
        assertEquals(ANR.removeLast(), BA.removeLast());
        assertEquals(ANR.removeLast(), BA.removeLast());
    }
}
