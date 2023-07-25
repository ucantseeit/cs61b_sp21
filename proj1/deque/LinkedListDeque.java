package deque;
import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class StuffNode {
        private final T item;
        private StuffNode next;
        private StuffNode pre;
        StuffNode() {
            item = null;
            next = this;
            pre = this;
        }
        StuffNode(T x, StuffNode n) {
            item = x;
            next = n;
            n.pre = this;
        }

        StuffNode(T x, StuffNode last, StuffNode n) {
            item = x;
            next = n;
            last.next = this;
            pre = last;
            n.pre = this;
        }
    }

    private StuffNode sentinel;
    private int size;
    public LinkedListDeque() {
        size = 0;
        sentinel = new StuffNode();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(T x) {
        size += 1;
        sentinel.next = new StuffNode(x, sentinel.next);
        sentinel.next.pre = sentinel;
    }

    @Override
    public void addLast(T x) {
        size += 1;
        sentinel.pre = new StuffNode(x, sentinel.pre, sentinel);
    }

    @Override
    public T get(int i) {
        StuffNode p = sentinel.next;
        while (i != 0) {
            p = p.next;
            i -= 1;
        }
        return p.item;
    }

    public T getRecursive(int i) {
        return helper(sentinel.next, i);
    }

    private T helper(StuffNode ptr, int i) {
        if (i == 0) {
            return ptr.item;
        }
        return helper(ptr.next, i - 1);
    }

    @Override
    public void printDeque() {
        StuffNode p = sentinel.next;
        while (p.next != sentinel) {
            System.out.print(p.item);
            System.out.print(' ');
            p = p.next;
        }
        System.out.println(p.item);
    }

    @Override
    public T removeFirst() {
        if (!this.isEmpty()) {
            size -= 1;
        }
        T result = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.pre = sentinel;
        return result;
    }

    @Override
    public T removeLast() {
        if (!this.isEmpty()) {
            size -= 1;
        }
        T result = sentinel.pre.item;
        sentinel.pre = sentinel.pre.pre;
        sentinel.pre.next = sentinel;
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new LLDIterator();
    }

    private class LLDIterator implements Iterator<T> {
        private int ptr;
        LLDIterator() {
            ptr = 0;
        }


        public boolean hasNext() {
            return ptr < size;
        }

        public T next() {
            StuffNode target = sentinel.next;
            for (int i = 0; i < ptr; i++) {
                target = target.next;
            }
            ptr += 1;
            return target.item;
        }
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Deque)) {
            return false;
        } else if (size != ((Deque<?>) o).size()) {
            return false;
        } else {
            for (int i = 0; i < size; i++) {
                if (!this.get(i).equals(((Deque<?>) o).get(i))){
                    return false;
                }
            }
            return true;
        }
    }
}
