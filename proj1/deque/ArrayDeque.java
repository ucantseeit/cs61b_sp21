package deque;

//import afu.org.checkerframework.checker.oigj.qual.O;

import java.util.Iterator;

public class ArrayDeque<Item> implements Deque<Item> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private Object[] items;
    private int len;


    public ArrayDeque() {
        size = 0;
        nextLast = 4;
        nextFirst = 3;
        items = new Object[8];
        len = items.length;
    }

    public int next(int x, int length1) {
        if (x != length1 - 1){
            return x + 1;
        }else return 0;
    }

    public int back(int x, int length1) {
        if (x != 0) {
            return x - 1;
        }else return length1 - 1;
    }

    private void relen (int capacity, int nf, int nl) {
        Object[] newItems = new Object[capacity];
        int newLen = newItems.length;
        nextLast =  capacity / 2;
        nextFirst = nextLast - 1;

        newItems[nextLast] = items[next(nf, len)];
        items[next(nf, len)] = null;
        nextLast = next(nextLast, newLen);
        nf = next(nf, len);
        while(nf != back(nl, len)){
            newItems[nextLast] = items[next(nf, len)];
            items[next(nf, len)] = null;
            nextLast = next(nextLast, newLen);
            nf = next(nf, len);
        }

        items = newItems;
        len = newLen;
    }

    @Override
    public void addFirst(Item x) {
        if(size == len) {
            relen(2 * size, nextFirst, nextLast);
        }
        size += 1;
        items[nextFirst] = x;
        nextFirst = back(nextFirst, len);
    }

    @Override
    public void addLast(Item x) {
        if(size == len) {
            relen(2 * size, nextFirst, nextLast);
        }
        size += 1;
        items[nextLast] = x;
        nextLast = next(nextLast, len);
    }

    @Override
    public Item removeFirst() {
        if (! isEmpty()) {
            size -= 1;
            nextFirst = next(nextFirst, len);
            Item result = (Item) items[nextFirst];
            items[nextFirst] = null;
            if(size * 4 <= len && len >= 16) {
                relen(len / 2, nextFirst, nextLast);
            }
            return result;
        }else return null;
    }

    @Override
    public Item removeLast() {
        if (! isEmpty()) {
            size -= 1;
            nextLast = back(nextLast, len);
            Item result =  (Item) items[nextLast];
            items[nextLast] = null;
            if (size * 4 <= len && len >= 16) {
                relen(len / 2, nextFirst, nextLast);
            }
            return result;
        }else return null;
    }

    @Override
    public int size(){
        return size;
    }


    @Override
    public Item get(int i) {
        if (nextFirst + 1 + i < len) {
            return (Item) items[nextFirst + 1 + i];
        }else return (Item) items[i - (nextFirst + 1)];
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size - 1; i++) {
            System.out.print(this.get(i));
            System.out.print(" ");
        }
        System.out.print(this.get(size - 1));
    }

    @Override
    public Iterator<Item> iterator() {
        return new ADIterator();
    }

    private class ADIterator implements Iterator<Item> {
        int ptr;
        public ADIterator() {
            ptr = 0;
        }

        public boolean hasNext() {
            return ptr < size;
        }

        public Item next() {
            Item result;
            if (nextFirst+1+ptr < len) {
                result = (Item) items[nextFirst + 1 + ptr];
            } else result =  (Item) items[ptr - (nextFirst + 1)];
            ptr += 1;
            return result;
        }
    }

    @Override
    public boolean equal(Object o) {
        if (this == o) {
            return true;
        } else if (this.getClass() != o.getClass()) {
            return false;
        } else {
            for (int i = 0; i < size; i ++){
                if (this.get(i) != ((ArrayDeque<?>) o).get(i)){
                    return false;
                }
            }
            return true;
        }
    }
}
