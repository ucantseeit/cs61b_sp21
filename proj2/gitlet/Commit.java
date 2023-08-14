package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {

    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    String message;
    Map<String, String> addressMap;
    String parentHash;

    String author;
    Date date;

    /* TODO: fill in the rest of this class. */
    public Commit() {
        author = "Leo";
        date = new Date(0);
        message = "initial commit";
        addressMap = new TreeMap<>();
    }

    public Commit(String m, String ph) {
        author = "Leo";
        date = new Date();
        parentHash = ph;
        message = m;
        addressMap = Repository.getHeadCommit().addressMap;
    }
}
