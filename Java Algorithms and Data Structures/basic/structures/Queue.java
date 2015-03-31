package basic.structures;

import java.util.Iterator;

/**
 * Created by vankata on 27.03.15.
 */
public class Queue<Value extends Comparable<Value>> implements Iterable<Value>{
    private int size;
    protected ListNode<Value> first, last;
    public Queue(){
        this.first = null;
        this.last = null;
        this.size = 0;
    }
    
    public void enqueue(Value value){
        if(first != null){
            ListNode<Value> node = new ListNode<>(value);
            last.next = node;
            last = node;
        }
        else{
            first = new ListNode<>(value);
            last = first;
        }
        size++;
    }
    public Value dequeue(){
        if(first != null){
            size--;
            Value current = first.value;
            if(first == last){
                first = null;
                last = null;
            }else{
                first = first.next;
            }
            return current;
        }

        return null;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size;
    }


    @Override
    public Iterator<Value> iterator() {
        return new QueueIterator();
    }
    private class QueueIterator implements Iterator<Value>{
        private ListNode<Value> node = first;
        @Override
        public boolean hasNext() {

            return node != null;
        }

        @Override
        public Value next() {
            Value current = node.value;
            node = node.next;
            return current;
        }
    }
}
