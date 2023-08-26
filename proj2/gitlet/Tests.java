package gitlet;
import org.junit.Test;
import static org.junit.Assert.*;

public class Tests {
    @Test
    public void testTriePut() {
        Trie t = new Trie();
        t.put("a13");
        t.put("ab");
        for (String s : t.stringsWithPrefix("a")) {
            System.out.println(s);
        }
        assertEquals(2, t.size);

    }
}
