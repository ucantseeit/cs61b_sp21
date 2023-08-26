package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    static File objects = Utils.join(GITLET_DIR, "objects");
    static File commits = Utils.join(objects, "commits");
    static File blobs = Utils.join(objects, "blobs");
    static File ref = Utils.join(GITLET_DIR, "ref");
    static File head = Utils.join(ref, "head");
    static File master = Utils.join(ref, "master");
    static File staging = Utils.join(GITLET_DIR, "staging");
    static File trie = Utils.join(commits, "tries");

    static class Blob implements Serializable {
        byte[] content;
        final String hashName;
        final String normalName;

        Blob(File f) {
            hashName = Utils.sha1(f.toString());
            content = Utils.readContents(f);
            normalName = f.getName();
        }
    }
    
    static class Staging implements Serializable {
        Map<String, String> stagingAddMap;
        Map<String, String> stagingrmMap;
        Map<String, Blob> addBlobs;
        Map<String, Blob> rmBlobs;
        
        Staging() {
            stagingAddMap = new HashMap<>();
            stagingrmMap = new HashMap<>();
            addBlobs = new HashMap<>();
            rmBlobs = new HashMap<>();
        }
    }

    /* TODO: fill in the rest of this class. */
    public static void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        } else {
            GITLET_DIR.mkdir();
            objects.mkdir();
            commits.mkdir();
            blobs.mkdir();
            ref.mkdir();

            // store the information of the initial commit into the file in commits folder
            Commit initCommit = new Commit();
            File initCommitFile = Utils.join(commits, generateHash(initCommit));
            Utils.writeObject(initCommitFile, initCommit);

            Utils.writeContents(master, initCommitFile.getPath());
            Utils.writeContents(head, master.getPath());

            Staging emptyStaging = new Staging();
            Utils.writeObject(staging, emptyStaging);


            Trie trieOfCommitHashes = new Trie();
            trieOfCommitHashes.put(generateHash(initCommit));
            Utils.writeObject(trie, trieOfCommitHashes);
        }
    }

    static File getNowBranch() {
        return new File(readContentsAsString(head));
    }

    static Commit getHeadCommit() {
        File nowBranch = getNowBranch();
        File headCommitFile = new File(readContentsAsString(nowBranch));
        return Utils.readObject(headCommitFile, Commit.class);
    }
    
    static Staging getStaging() {
        return Utils.readObject(staging, Staging.class);
    }

    static String generateHash(Commit c) {
        if (c.parentHash == null) {
            return sha1(dateToTimeStamp(c.date), c.message, c.addressMap.toString());
        }
        return sha1(dateToTimeStamp(c.date), c.message, c.parentHash, c.addressMap.toString());
    }

    private static String dateToTimeStamp(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        return dateFormat.format(date);
    }

    public static void add(String fileName) {
        File source = Utils.join(CWD, fileName);

        if (!source.exists()) {
            System.out.println("File does not exist.");
            return;
        }

        Blob blob = new Blob(source);
        Staging nowStaging;
        if (getStaging() == null) {
            nowStaging = new Staging();
        } else {
            nowStaging = getStaging();
        }
        
        // Return if the file is already added
        if (nowStaging.addBlobs.containsKey(blob.hashName)) {
            return;
        }

        //delete the file in staging if the file we now add equals to the file in head commit
        Commit headCommit = getHeadCommit();
        if (blob.hashName.equals(headCommit.addressMap.get(fileName))) {
            nowStaging.addBlobs.remove(blob.hashName);
            nowStaging.stagingAddMap.remove(fileName);
            return;
        }
        
        // Update the staging
        nowStaging.stagingAddMap.put(fileName, blob.hashName);
        nowStaging.addBlobs.put(blob.hashName, blob);
        staging.delete();
        Utils.writeObject(staging, nowStaging);
    }

    public static void remove(String filename) {
        Commit headCommit = getHeadCommit();
        Staging nowStaging = getStaging();
        File targetFile = join(CWD, filename);

        if (!headCommit.addressMap.containsKey(filename) &&
                !nowStaging.stagingAddMap.containsKey(filename)) {
            System.out.println("No reason to remove the file.");
            return;
        }

        if (nowStaging.stagingAddMap.containsKey(filename)) {
            nowStaging.addBlobs.remove(nowStaging.stagingAddMap.get(filename));
            nowStaging.stagingAddMap.remove(filename);
        }

        Blob rmBlob = new Blob(targetFile);
        nowStaging.stagingrmMap.put(filename, rmBlob.hashName);
        nowStaging.rmBlobs.put(rmBlob.hashName, rmBlob);

        targetFile.delete();
    }

    public static void commit(String message) {
        String parentHash = generateHash(getHeadCommit());
        Commit newCommit = new Commit(message, parentHash);
        Staging nowStaging = getStaging();

        // update addressMap and blobs to create new commit
        for (Blob b : nowStaging.addBlobs.values()) {
            newCommit.addressMap.put(b.normalName, b.hashName);
            File targetFile = join(blobs, b.hashName);
            Utils.writeObject(targetFile, b);
        }
        for (Blob b : nowStaging.rmBlobs.values()) {
            newCommit.addressMap.remove(b.normalName, b.hashName);
        }

        Staging emptyStaging = new Staging();
        staging.delete();
        Utils.writeObject(staging, emptyStaging);

        File newCommitFile = Utils.join(commits, generateHash(newCommit));
        Utils.writeObject(newCommitFile, newCommit);

        Utils.writeContents(getNowBranch(), newCommitFile.getPath());

        Trie trieOfCommitHashes = Utils.readObject(trie, Trie.class);
        trieOfCommitHashes.put(generateHash(newCommit));
        trie.delete();
        writeObject(trie, trieOfCommitHashes);
    }

    public static void basicCheckout(String fileName) {
        checkoutFromCommitBefore(generateHash(getHeadCommit()), fileName);
    }

    public static void checkoutFromCommitBefore(String abrOfCommitHash, String fileName) {
        Trie trieOfCommitHashes = readObject(trie, Trie.class);
        String commitHash = trieOfCommitHashes.stringsWithPrefix(abrOfCommitHash).get(0);

        if (!join(commits, commitHash).exists()) {
            System.out.println("No commit with that id exists.");
            return;
        }

        if (!join(CWD, fileName).exists()){
            System.out.println("File does not exist in that commit.");
        }
        join(CWD, fileName).delete();

        Commit targetCommit = readObject(join(commits, commitHash), Commit.class);
        replaceFromSomeCommit(targetCommit, fileName);
    }

    private static void replaceFromSomeCommit(Commit sourceCommit, String fileName) {
        String blobHash = sourceCommit.addressMap.get(fileName);
        Blob targetBlob = readObject(Utils.join(blobs, blobHash), Blob.class);

        File newFile = Utils.join(CWD, fileName);
        Utils.writeContents(newFile, (Object) targetBlob.content);
    }

    public static void branchCheckout(String branchName) {
        File branch = join(ref, branchName);
        if (!branch.exists()) {
            System.out.println("No such branch exists.");
            return;
        }
        if (branchName.equals(getNowBranch().getName())) {
            System.out.println("No need to checkout the current branch.");
            return;
        }
        head.delete();
        writeContents(head, branch.getPath());
    }

    public static void log() {
        List<Commit> allCommits = new ArrayList<>();
        Commit trackCommit = getHeadCommit();
        while (trackCommit.parentHash != null) {
            allCommits.add(trackCommit);
            trackCommit = readObject(join(commits, trackCommit.parentHash), Commit.class);
        }
        allCommits.add(trackCommit);

        for (Commit aCommit : allCommits) {
            System.out.println("===");
            System.out.println("commit " + generateHash(aCommit));
            System.out.println("Date: " + dateToTimeStamp(aCommit.date));
            System.out.println(aCommit.message);
            System.out.println();
        }
    }

    public static void find(String targetMessage) {
        boolean found = false;
        for (String fileName : Objects.requireNonNull(plainFilenamesIn(commits))) {
            Commit trackCommit = readObject(join(commits, fileName), Commit.class);
            if (trackCommit.message.equals(targetMessage)) {
                found = true;
                System.out.println(generateHash(trackCommit));
            }
        }
        if (!found) {
            System.out.println("Found no commit with that message.");
        }
    }

    public static void branch(String branchName) {
        File newBranch = join(ref, branchName);
        writeContents(newBranch, readContentsAsString(getNowBranch()));
    }

    public static void rm_branch(String branchName) {
        File targetBranch = join(ref, branchName);
        if (!targetBranch.exists()) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        if (targetBranch.equals(getNowBranch())) {
            System.out.println("Cannot remove the current branch.");
            return;
        }
        targetBranch.delete();
    }

    public static void reset(String commitHash) {
        if (!join(commits, commitHash).exists()) {
            System.out.println("No commit with that id exists.");
            return;
        }

        for (String fileName : Objects.requireNonNull(plainFilenamesIn(CWD))) {
            if (fileName.equals(".gitlet")) {
                continue;
            }
            checkoutFromCommitBefore(commitHash, fileName);
        }

        File targetCommitFile = join(commits, commitHash);
        Utils.writeContents(getNowBranch(), targetCommitFile.getPath());
    }
}
