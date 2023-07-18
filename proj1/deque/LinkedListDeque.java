package deque;

public class LinkedListDeque<Stuff> {
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

    public boolean isEmpty(){
        return sentinel.next == sentinel;
    }

    public int size(){
        return size;
    }

    public void addFirst(Stuff x){
        size += 1;
        sentinel.next = new StuffNode(x, sentinel.next);
        sentinel.next.pre = sentinel;
    }

    public void addLast(Stuff x){
        size += 1;
        sentinel.pre = new StuffNode(x, sentinel.pre, sentinel);
    }
    
    public Stuff get(int i){
        StuffNode p = sentinel;
        while(i != 0){
            p = p.next;
            i -= 1;
        }
        return p.item;
    }

    public void printDeque(){
        StuffNode p = sentinel.next;
        while(p.next != sentinel){
            System.out.print(p.item);
            System.out.print(' ');
            p = p.next;
        }
        System.out.println(p.item);
    }

    public Stuff removeFirst(){
        if(! this.isEmpty()){
            size -= 1;
        }
        Stuff result = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.pre = sentinel;
        return result;
    }

    public Stuff removeLast(){
        if(! this.isEmpty()){
            size -= 1;
        }
        Stuff result = sentinel.pre.item;
        sentinel.pre = sentinel.pre.pre;
        sentinel.pre.next = sentinel;
        return result;
    }

}
