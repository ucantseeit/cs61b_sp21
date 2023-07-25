package deque;
import java.util.Iterator;

public class LinkedListDeque<Stuff> implements Deque<Stuff>, Iterable<Stuff> {
    private class StuffNode{
        public Stuff item;
        public StuffNode next;
        public StuffNode pre;
        public StuffNode(){
            item = null;
            next = this;
            pre = this;
        }
        public StuffNode(Stuff x, StuffNode n){
            item = x;
            next = n;
            n.pre = this;
        }

        public StuffNode(Stuff x, StuffNode last, StuffNode n){
            item = x;
            next = n;
            last.next = this;
            pre = last;
            n.pre = this;
        }
    }

    public StuffNode sentinel;
    public int size;
    public LinkedListDeque(){
        size = 0;
        sentinel = new StuffNode();
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void addFirst(Stuff x){
        size += 1;
        sentinel.next = new StuffNode(x, sentinel.next);
        sentinel.next.pre = sentinel;
    }

    @Override
    public void addLast(Stuff x){
        size += 1;
        sentinel.pre = new StuffNode(x, sentinel.pre, sentinel);
    }

    @Override
    public Stuff get(int i){
        StuffNode p = sentinel.next;
        while(i != 0){
            p = p.next;
            i -= 1;
        }
        return p.item;
    }

    public Stuff getRecursive(int i){
        return helper(sentinel.next, i);
    }

    private Stuff helper(StuffNode ptr, int i){
        if (i == 0) {
            return ptr.item;
        }
        return helper(sentinel.next, i - 1);
    }

    @Override
    public void printDeque(){
        StuffNode p = sentinel.next;
        while(p.next != sentinel){
            System.out.print(p.item);
            System.out.print(' ');
            p = p.next;
        }
        System.out.println(p.item);
    }

    @Override
    public Stuff removeFirst(){
        if(! this.isEmpty()){
            size -= 1;
        }
        Stuff result = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.pre = sentinel;
        return result;
    }

    @Override
    public Stuff removeLast(){
        if(! this.isEmpty()){
            size -= 1;
        }
        Stuff result = sentinel.pre.item;
        sentinel.pre = sentinel.pre.pre;
        sentinel.pre.next = sentinel;
        return result;
    }

    @Override
    public Iterator<Stuff> iterator(){
        return new LLDIterator();
    }

    private class LLDIterator implements Iterator<Stuff> {
        public int ptr;
        public LLDIterator(){
            ptr = 0;
        }


        public boolean hasNext(){
            return ptr < size;
        }

        public Stuff next() {
            StuffNode target = sentinel.next;
            for (int i = 0; i < ptr; i++){
                target = target.next;
            }
            ptr += 1;
            return target.item;
        }
    }


    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if (! (o instanceof Deque)) {
            return false;
        } else {
            for (int i = 0; i < size; i++){
                if (this.get(i) != ((Deque<?>) o).get(i)){
                    return false;
                }
            }
            return true;
        }
    }
}
