package flik;
import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest {
    @Test
    public void randomTest(){
        final int N = 5000;
        for (int i = 0, j = 0; i < N; i ++, j++) {
            System.out.printf("%d  %d%n", i, j);
            assertEquals(true, Flik.isSameNumber(i, j));
        }
    }
}
