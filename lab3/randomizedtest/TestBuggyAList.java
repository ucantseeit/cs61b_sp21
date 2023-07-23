package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
  @Test
  public void randomizedTest(){
      AListNoResizing<Integer> L = new AListNoResizing<>();
      BuggyAList<Integer> L1 = new BuggyAList<>();
      int N = 5000;
      for (int i = 0; i < N; i += 1) {
          int operationNumber = StdRandom.uniform(0, 4);
          if (operationNumber == 0) {
              // addLast
              int randVal = StdRandom.uniform(0, 100);
              L.addLast(randVal);
              L1.addLast(randVal);
              assertEquals(L.getLast(), L1.getLast());
          } else if (operationNumber == 1) {
              // size
              int size = L.size();
              assertEquals(L.size(), L1.size());
          } else if (operationNumber == 2) {
              // getLast
              if(L.size() > 0){
                  assertEquals(L.getLast(), L1.getLast());
              }
          } else if (operationNumber == 3){
              // removeLast
              if(L.size() > 0){
                  int last = L.removeLast();
                  int last1 = L1.removeLast();
                  assertEquals(last, last1);
              }
          }
      }
  }
}
