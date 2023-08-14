package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!

    private int size;
    private Set<K> keySet = new HashSet<>();
    private Collection<Node>[] table;
    private int initialSize = 16;
    private double loadFactor = 0.75;


    /** Constructors */
    public MyHashMap() {
        size = 0;
        table = createTable(initialSize);
    }

    public MyHashMap(int iS) {
        size = 0;
        table = createTable(iS);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        size = 0;
        table = createTable(initialSize);
        loadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    private void resize(){
        int realSize = size;
        Collection<Node>[] temp = table;
        table = createTable(2 * table.length);
        for (Collection<Node> bucket : temp) {
            if (bucket == null) {
                continue;
            }
            for (Node n : bucket) {
                this.put(n.key, n.value);
            }
        }
        size = realSize;

    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    public int size(){
        return size;
    }

    public void put(K k, V v){
        int hashCode;
        if (k.hashCode() >= 0) {
            hashCode = k.hashCode() % table.length;
        } else {
            hashCode = (- k.hashCode()) % table.length;
        }
        Node newNode = new Node(k, v);
        if (table[hashCode] == null) {
            table[hashCode] = createBucket();
        }
        for (Node n : table[hashCode]) {
            if (n.key.equals(k)) {
                n.value = v;
                return;
            }
        }
        size += 1;
        table[hashCode].add(newNode);
        keySet.add(k);
        if ((double) this.size / table.length > loadFactor) {
            this.resize();
        }
    }

    public V get(K k){
        int hashCode;
        if (k.hashCode() >= 0) {
            hashCode = k.hashCode() % table.length;
        } else {
            hashCode = (- k.hashCode()) % table.length;
        }
        if (table[hashCode] == null) {
            table[hashCode] = createBucket();
        }
        for (Node n : table[hashCode]) {
            if (n.key.equals(k)) {
                return n.value;
            }
        }
        return null;
    }

    public void clear(){
        size = 0;
        table = createTable(initialSize);
    }

    @Override
    public boolean containsKey(K key) {
        int hashCode;
        if (key.hashCode() >= 0) {
            hashCode = key.hashCode() % table.length;
        } else {
            hashCode = (- key.hashCode()) % table.length;
        }
        if (table[hashCode] == null) {
            return false;
        }
        for (Node n : table[hashCode]) {
            if (n.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public Set<K> keySet() {
        return keySet;
    }

    public Iterator<K> iterator() {
        return keySet.iterator();
    }

    public V remove(K key) {
        return null;
    }

    public V remove(K key, V value) {
        return null;
    }



}
