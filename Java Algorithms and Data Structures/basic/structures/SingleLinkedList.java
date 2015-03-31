package basic.structures;


import java.util.Iterator;


/**
 * Created by vankata on 26.03.15.
 */
public class SingleLinkedList<Value extends Comparable<Value>>implements Iterable<Value>{
    protected ListNode<Value> head, tail;

    public SingleLinkedList(){
        this.head = null;
        this.tail = null;

    }

    public void addToHead(Value value){
        if(head == null){
            head = new ListNode<>(value);
            tail = head;
        }else{
            ListNode<Value> node = new ListNode<>(value);
            node.next = head;
            head = node;
        }
    }
    public void addToTail(Value value){
        if(tail == null){
            tail = new ListNode<>(value);
            head = tail;
        }else{
            ListNode<Value> node = new ListNode<>(value);
            tail.next = node;
            tail = node;
        }
    }
    public Value deleteHead(){
        if(head != null){
            Value current = head.value;
            if(tail == head){
                head = null;
                tail = null;

            }else{
                head = head.next;
            }
            return current;
        }
        return null;
    }
    public Value deleteTail(){
        if(tail != null){
            Value current = tail.value;
            if(tail == head){
                head = null;
                tail = null;
            }else{
                ListNode<Value> node = head;
                while(node.next != tail){
                    node = node.next;
                }
                node.next = null;
                tail = node;
            }
            return current;
        }
        return null;
    }
    public void delete(Value value){
        if(head == null){
            return;
        }
        if(head.value.compareTo(value) == 0){
            deleteHead();
            return;
        }
        ListNode<Value> previous = head, node = head.next;
        while (node != null && node.value.compareTo(value) != 0){
            node = node.next;
            previous = previous.next;
        }
        if(node != null){
            previous.next = node.next;
            if(node == tail){
                tail = previous;
            }
        }
    }
    public boolean isInList(Value value){
        ListNode<Value> node = head;
        while(node != null && node.value.compareTo(value) != 0){
            node = node.next;
        }
        if(node != null){
            return true;
        }
        return false;
    }


    @Override
    public Iterator<Value> iterator() {
        return new SingleLinkedListIterator();
    }

    private class SingleLinkedListIterator implements Iterator<Value>{
        private ListNode<Value> current = head;
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Value next() {
            Value value = current.value;
            current = current.next;
            return value;
        }
    }



}
