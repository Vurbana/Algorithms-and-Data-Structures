package basic.structures;

import java.util.Iterator;

/**
 * Created by vankata on 27.03.15.
 */
public class StackImpl<Value extends Comparable<Value>> implements Iterable<Value>{
    protected ListNode<Value> top;
    private int size;

    public StackImpl(){
        this.top = null;
        this.size = 0;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size;
    }
    public void push(Value value){
        if(top != null){
            ListNode<Value> node = new ListNode<>(value);
            node.next = top;
            top = node;
        }else{
            top = new ListNode<>(value);
        }
        size++;
    }

    public Value pop(){
        Value current  = top.value;
        top = top.next;
        size--;
        return current;
    }

    @Override
    public Iterator<Value> iterator() {
        return new StackIterator();
    }
    private class StackIterator implements Iterator<Value>{
        private ListNode<Value> first = top;
        @Override
        public boolean hasNext() {

            return first != null;
        }

        @Override
        public Value next() {
            Value current = first.value;
            first = first.next;
            return current;
        }
    }
}
