package basic.structures;

/**
 * Created by vankata on 26.03.15.
 */
public class ListNode<Value extends Comparable<Value>> {
    public ListNode<Value> next;
    public Value value;
    public ListNode(Value value){
        this.value = value;
        this.next = null;
    }
}
