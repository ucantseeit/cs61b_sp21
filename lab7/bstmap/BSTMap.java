package bstmap;

import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    int size;

    BSTNode sentinel;

    boolean isFound = true;

    public BSTMap(){
        sentinel = new BSTNode(null, null);
        sentinel.rightT = null;
        sentinel.leftT = null;
    }

    private class BSTNode {
        K key;
        V value;
        BSTNode leftT = null;
        BSTNode rightT = null;

        BSTNode(K k, V v) {
            key = k;
            value = v;
        }

        V search(K k) {
            if (key == null){
                if (leftT == null) {
                    isFound = false;
                    return null;
                } else return leftT.search(k);
            }
            if (k.equals(key)) {
                return value;
            } else if (rightT != null && k.compareTo(key) > 0) {
                return rightT.search(k);
            } else if (leftT != null && k.compareTo(key) < 0 ) {
                return leftT.search(k);
            } else {
                isFound = false;
                return null;
            }
        }

        V delete(K k) {
            if (key == null){
                if (leftT == null) {
                    return null;
                } else {
                    return leftT.delete(k);
                }
            }
            V result = this.search(k);
            if (this.leftT == null) {
                sentinel.leftT = this.rightT;
                return result;
            }
            K leftMaxK = this.leftT.findMaxK();
            helper(this, k, leftMaxK);
            return result;
        }

        private K findMaxK(){
            BSTNode targetParent = this;
            if (this.rightT == null) {
                return this.key;
            }
            while (targetParent.rightT.rightT != null) {
                targetParent = targetParent.rightT;
            }
            K resultKey = targetParent.rightT.key;
            targetParent.rightT = null;
            return resultKey;
        }

        private void helper(BSTNode tree, K target, K leftMaxK){
            if (tree.key.equals(target)) {
                tree.key = leftMaxK;
            } else if (rightT != null && tree.key.compareTo(target) > 0) {
                helper(tree.leftT, target, leftMaxK);
            } else if (leftT != null && tree.key.compareTo(target) < 0 ) {
                helper(tree.rightT, target, leftMaxK);
            }
        }

        void newNode(K k, V v) {
            if (key == null){
                if (leftT == null) {
                    leftT = new BSTNode(k, v);
                } else leftT.newNode(k, v);
            } else if (k.compareTo(key) > 0) {
                if (rightT != null) {
                    rightT.newNode(k, v);
                } else {
                    rightT = new BSTNode(k, v);
                }
            } else if (k.compareTo(key) < 0) {
                if (leftT != null) {
                    leftT.newNode(k, v);
                } else {
                    leftT = new BSTNode(k, v);
                }
            }
        }
    }

    public V get(K key){
        return sentinel.search(key);
    }

    @Override
    public int size() {
        return size;
    }

    public boolean containsKey(K key) {
        this.sentinel.search(key);
        if (isFound) {
            return true;
        }
        isFound = true;
        return false;
    }

    public void put(K key, V value){
        if(containsKey(key)) {
            return;
        }
        size += 1;
        this.sentinel.newNode(key, value);
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public V remove(K key) {
        if (!this.containsKey(key)) {
            return null;
        }
        size -= 1;
        return this.sentinel.delete(key);
    }

    public V remove(K key, V value){
        return null;
    }

    public void clear(){
        size = 0;
        sentinel.leftT = null;
        return;
    }

    public Iterator<K> iterator() {
//        return new BSTMap.ULLMapIter();
        return null;
    }
}
