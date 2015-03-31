package splayTree;
import exceptions.EmptyTreeException;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;



/**
 *
 * @author vankata
 *
 */
public class SplayTree {
    
    private Node root = null;
    
    private class Node{
        private Node left, right, parent;
        private int key;
        public Node(int key){
            this.key = key;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }
    
    
    public void insert(int key){
        Node parent = null;
        Node current = root;
        while(current != null){
            parent = current;
            if(current.key == key){
                return;
            }else if(key < current.key){
                current = current.left;
            }else{
                current = current.right;
            }
        }
        Node node = new Node(key);
        node.parent = parent;
        if(parent == null){
            root = node;
        }else if(key < parent.key){
            parent.left = node;
            
        }else{
            parent.right = node;
        }
        splay(node);
    }
    private void splay(Node node){
        while(node != root){
            if(node.parent == root){
                zig(node);
            }else if (node.parent.parent.left == node.parent){
                if(node.parent.left == node){
                    zigZig(node);
                }else{
                    zigZag(node);
                }
            }else{
                if(node.parent.right == node){
                    zigZig(node);
                }else{
                    zigZag(node);
                }
            }
        }
    }
    public int find(int key){
        Node node = getNode(key);
        if(node == null){
            throw new NoSuchElementException();
        }else{
            return node.key;
        }
    }
    private void zig(Node node){
        if(node.parent.left == node){
            rightRotation(node);
        }else{
            leftRotation(node);
        }
    }
    private void zigZig(Node node){
        zig(node.parent);
        zig(node);
    }
    private void zigZag(Node node){
        zig(node);
        zig(node);
    }
    private void leftRotation(Node node) {
        Node grandParent = node.parent.parent;
        Node temp = node.left;
        Node parent = node.parent;
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
            node.parent = null;
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
    private void rightRotation(Node node){
        Node temp = node.right;
        Node grandParent = node.parent.parent;
        Node parent = node.parent;
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
            node.parent = null;
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

    private void join(Node left, Node right){
        if(left == null && right == null){
            root = null;
            return;
        }
        if(left == null){
            root = right;
            right.parent = null;
            return;
        }
        if(right == null){
            root = left;
            left.parent = null;
            return;
        }

            Node node = max(left);
            root = left;
            left.parent = null;
            splay(node);
            node.right = right;
            right.parent = node;
            node.parent = null;

    }
     private Node getNode(int key){
         Node current = root;
        Node parent = null;
        while(current != null){
            if(current.key == key){
                break;
            }else if(key < current.key){
                parent = current;
                current = current.left;
            }else{
                parent = current;
                current = current.right;
            }
        }
         if(current != null){
             splay(current);
             return current;
         }else{
             if(parent != null){
                 splay(parent);
             }
         }
        return null;
    }
     public void remove(int key){
        Node node = getNode(key);
        if(node == null){
            return;
        }
         join(node.left,node.right);
     }
     private Node min(Node node){

        while(node.left != null){
            node = node.left;
        }
        return node;
    }
    public int max(){
        if(root == null){
            throw new NoSuchElementException("The Splay Tree is empty");
        }
        Node max = max(root);

        return max.key;

    }
    public int min()  {
        if(root == null){
            throw new NoSuchElementException("The tree is empty");
        }
        Node min = min(root);

        return min.key;

    }
    public boolean isEmpty(){
        return root == null;
    }
     private Node max(Node node) {

         while(node.right != null){
             node = node.right;
         }
         return node;
     }


}
