package structures;

import javax.swing.tree.TreeNode;
import java.util.ArrayDeque;

/**
 * Created by vankata on 13.03.15.
 */
public class TreapImplementation implements Treap {
    private TreapNode root = null;

    @Override
    public void insert(int key) {
        TreapNode parent = null;
        TreapNode current = root;
        while(current != null){
            parent = current;
            if(current.key == key){
                return;
            }else if( key < current.key){

                current = current.left;
            }else{

                current = current.right;
            }
        }
        TreapNode node = new TreapNode(key);
        if(parent == null){
            root = node;
        }else if(key < parent.key){
            parent.left = node;
            node.parent = parent;
            heapify(node);
        }else {
            parent.right = node;
            node.parent = parent;
            heapify(node);
        }
    }
    private void leftRotation(TreapNode node) {
        TreapNode grandParent = node.parent.parent;
        TreapNode temp = node.left;
        TreapNode parent = node.parent;
        if (grandParent != null) {
            if (grandParent.left == parent) {
                grandParent.left = node;
                node.parent = grandParent;
            } else {
                grandParent.right = node;
                node.parent = grandParent;
            }
        }else{
            root = node;
            }
        node.left = parent;
        parent.parent = node;
        if(temp != null){
            parent.right = temp;
            temp.parent = parent;
        }else{
            parent.right = null;
        }
    }
    private void rightRotation(TreapNode node){
        TreapNode temp = node.right;
        TreapNode grandParent = node.parent.parent;
        TreapNode parent = node.parent;
        if(grandParent != null){
            if(grandParent.left == parent){
                grandParent.left = node;
                node.parent = grandParent;
            }else{
                grandParent.right = node;
                node.parent = grandParent;
            }
        }else{
            root = node;
        }
        node.right = parent;
        parent.parent = node;
        if(temp != null){
            temp.parent = parent;
            parent.left = temp;
        }else{
            parent.left = null;
        }
    }
    private void heapify(TreapNode node){
        while(node != root && node.priority < node.parent.priority){
            if(node.parent.left == node){
                rightRotation(node);
            }else{
                leftRotation(node);
            }
        }
    }
    public void display(){

            if(root == null){
                return;
            }
            System.out.printf("root: %d\n", root.key);
            ArrayDeque<TreapNode> arrayDeque = new ArrayDeque<>();
            System.out.printf("Parent: %d priority: %.2f ", root.key,root.priority);
            if(root.left != null){
                System.out.printf("left: %d ", root.left.key);
                arrayDeque.add(root.left);
            }
            if(root.right != null){
                System.out.printf("right: %d", root.right.key);
                arrayDeque.add(root.right);
            }
            System.out.println();
            while(!arrayDeque.isEmpty()){
                TreapNode tmp = arrayDeque.removeFirst();
                System.out.printf("Parent: %d priority: %.2f ", tmp.key, tmp.priority);
                if(tmp.left != null){
                    System.out.printf("left: %d ", tmp.left.key);
                    arrayDeque.add(tmp.left);
                }
                if(tmp.right != null){
                    System.out.printf("right: %d", tmp.right.key);
                    arrayDeque.add(tmp.right);
                }
                System.out.println();
            }


    }
    private TreapNode getNode(int key){
        TreapNode current = root;
        while(current != null){
            if(current.key == key){
                return current;
            }else if(key < current.key){
                current = current.left;
            }else{
                current = current.right;
            }
        }
        return null;
    }

    @Override
    public void remove(int key) {
        TreapNode node = getNode(key);
        if(node == null){
            return;
        }
        
    }

    @Override
    public boolean containsKey(int key) {

        return getNode(key) != null;
    }
}
