package structures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by vankata on 12.03.15.
 */
public class BST<Key extends Comparable<Key>, Value> {

    private Node root = null;

    private class Node{
        private Key key;
        private Value value;
        private Node left, right;
        public Node(Key key, Value value){
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;

        }
    }

    public void put(Key key, Value value){
        Node parent = null;
        Node current = root;
        int cmp;
        while(current != null){
            parent = current;
            cmp = key.compareTo(current.key);
            if( cmp == 0){
                break;
            }else if(cmp < 0){
                current = current.left;
            }else{
                current = current.right;
            }
        }
        if(parent == null){
            root = new Node(key,value);
        }else if(key.compareTo(parent.key) == 0){
            parent.value = value;
        }else if(key.compareTo(parent.key) < 0){
            parent.left = new Node(key, value);
        }else{
            parent.right = new Node(key,value);
        }
    }

    public Value get(Key key){
        Node current  = root;
        int cmp;
        while(current != null){
            cmp = key.compareTo(current.key);
            if(cmp == 0){
                return current.value;
            }else if(cmp < 0){
                current = current.left;
            }else{
                current = current.right;
            }
        }
        return null;
    }

    public boolean contains(Key key){

        return get(key) != null;

    }
    public void print(){
        print(root);
    }
    private void print(Node node){
        if(node != null){
            print(node.left);
            System.out.print(String.format("%s %s\n", node.key.toString(), node.value.toString()));
            print(node.right);
        }
    }

    public Key min(){
      return  min(root).key;
    }
    private Node min(Node node){
        while(node.left != null){
            node = node.left;
        }
        return node;
    }
    public Key max(){
        return max(root).key;

    }
    private Node max(Node node){
        while(node.right != null){
            node = node.right;
        }
        return node;
    }


}
