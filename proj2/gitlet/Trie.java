package gitlet;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Trie implements Serializable {
    int size;
    private Node root;

    private class Node implements Serializable {
        private Map<Character, Node> next;

        public Node() {
            next = new HashMap<>();
        }
    }


    public Trie() {
        root = new Node();
        size = 0;
    }

    public void put(String commitID) {
        size += 1;
        root = helpPut(root, commitID);
    }

    public Node helpPut(Node track, String s) {
        if (s.length() == 0) {
            return track;
        }

        char c = s.charAt(0);

        if (!track.next.containsKey(c)) {
            track.next.put(c, helpPut(new Node(), s.substring(1)));

        } else {
            Node newTrack = track.next.get(c);
            track.next.put(c, helpPut(newTrack, s.substring(1)));
        }

        return track;
    }

    private List<String> collect(Node n) {
        List<String> x = new ArrayList<>();
        String s = "";
        helpCol(n, x, s);
        return x;
    }

    private void helpCol(Node track, List<String> x, String completingWord) {
        if (track.next.values().size() == 0) {
            x.add(completingWord);
            return;
        }
        for (char c : track.next.keySet()) {
            helpCol(track.next.get(c), x, completingWord + c);
        }
    }

    private Node get(Node track, String s) {
        if (s.length() == 0) {
            return track;
        }
        return get(track.next.get(s.charAt(0)), s.substring(1));
    }

    public List<String> stringsWithPrefix(String s) {
        Node target = get(root, s);
        List<String> result = new ArrayList<>();
        for (String t : collect(target)) {
            t = s + t;
            result.add(t);
        }
        return result;
    }
}









