package structures;


import java.util.ArrayDeque;

/**
 * TreeMap implemented using Binary Search Tree
 * Created by vankata on 12.03.15.
 */
public class BST<Key extends Comparable<Key>, Value> {

    private Node root = null;
    private int size = 0;

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
        public Node(){
            this.key = null;
            this.value = null;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * Implementation of Day–Stout–Warren (DSW) algorithm for efficiently balancing binary search trees.
     */
    public void balanceTree(){
        Node pseudoRoot = new Node();
        pseudoRoot.right = root;
        treeToVine(pseudoRoot);
        vineToTree(pseudoRoot, size);
        this.root = pseudoRoot.right;

    }
    private void vineToTree(Node root, int size){
        int leaves =  size + 1 - (int)(Math.pow(2,Math.floor(Math.log(size+1)/Math.log(2))));
        compress(root, leaves);
        size -= leaves;
        while(size > 1){
            compress(root, (int)Math.floor(size/2));
            size = (int)Math.floor(size/2);
        }
    }
    private void compress(Node root, int count){
        Node temp = root;
        for (int i = 0; i < count; i++) {
            Node child = temp.right;
            temp.right = child.right;
            temp = temp.right;
            child.right = temp.left;
            temp.left = child;
        }

    }
    public void display(){
        if(root == null){
            return;
        }
        System.out.printf("root: %s\n", root.key.toString());
        ArrayDeque<Node> arrayDeque = new ArrayDeque<>();
        if(root.left != null){
            System.out.printf("Parent: %s left: %s ", root.key.toString(), root.left.key.toString());
            arrayDeque.add(root.left);
        }
        if(root.right != null){
            System.out.printf("right: %s", root.right.key.toString());
            arrayDeque.add(root.right);
        }
        System.out.println();
        while(!arrayDeque.isEmpty()){
            Node tmp = arrayDeque.removeFirst();
            System.out.printf("Parent: %s ", tmp.key.toString());
            if(tmp.left != null){
                System.out.printf("left: %s ", tmp.left.key.toString());
                arrayDeque.add(tmp.left);
            }
            if(tmp.right != null){
                System.out.printf("right: %s", tmp.right.key.toString());
                arrayDeque.add(tmp.right);
            }
            System.out.println();
        }

    }
    public int getSize(){
        return size;
    }
    public boolean isEmpty(){
        return size == 0;
    }


    public void put(Key key, Value value){
        if(value == null){
            delete(key);
            return;
        }
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
            size++;
        }else if(key.compareTo(parent.key) == 0){
            parent.value = value;

        }else if(key.compareTo(parent.key) < 0){
            parent.left = new Node(key, value);
            size++;
        }else{
            parent.right = new Node(key,value);
            size++;
        }
    }

    public Value get(Key key){
        Node node = getNode(key);
        if(node != null){
            return node.value;
        }else{
            return null;
        }
    }
    private Node getNode(Key key){
        Node current  = root;
        int cmp;
        while(current != null){
            cmp = key.compareTo(current.key);
            if(cmp == 0){
                return current;
            }else if(cmp < 0){
                current = current.left;
            }else{
                current = current.right;
            }
        }
        return null;
    }

    public boolean contains(Key key){

        return getNode(key) != null;

    }
    public void print(){
        print(root);
    }
    private void print(Node node){
        if(node != null){
            print(node.left);
            System.out.print(String.format("<%s,%s>\n", node.key.toString(), node.value.toString()));
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
    private Node getParent(Key key){
        Node parent = null;
        Node current = root;
        int cmp = key.compareTo(current.key);
        if(cmp == 0){
            return null;
        }
        while(current != null){
            cmp = key.compareTo(current.key);
            if( cmp == 0){
                break;
            }else if(cmp < 0){
                parent = current;
                current = current.left;
            }else{
                parent = current;
                current = current.right;
            }
        }
        return parent;
    }
    public void delete(Key key){
        if(root == null){
            return;
        }
        Node current = getNode(key);
        if(current != null){
            Node parent = getParent(key);
            delete(current, parent);
        }
    }
    private void treeToVine(Node root){
        Node tail = root;
        Node rest = tail.right;
        while(rest != null){
            if(rest.left == null){
                tail = rest;
                rest = rest.right;
            }else{
                Node temp = rest.left;
                rest.left = temp.right;
                temp.right = rest;
                rest = temp;
                tail.right = temp;
            }
        }
    }
    private void delete( Node node, Node parent){
        if(node.left == null && node.right == null){
            if(parent == null){
                node = null;
                root = null;
            }else{
                if(parent.left == node){
                    node = null;
                    parent.left = null;
                }else{
                    node = null;
                    parent.right = null;
                }
            }
        size--;
        }else if(node.left != null && node.right != null){
            Node tmp = min(node.right);
            Node p = getParent(tmp.key);
            node.key = tmp.key;
            node.value = tmp.value;
            delete(tmp, p);

        }else{
            if(node.left == null){
                if(parent == null){
                    root = node.right;
                    node = null;
                }else{
                    if(parent.left == node){
                        parent.left = node.right;
                        node = null;
                    }
                    else{
                        parent.right = node.right;
                        node = null;
                    }
                }
            }else{
                if(parent == null){
                    root = node.left;
                    node = null;
                }else{
                    if(parent.left == node){
                        parent.left = node.left;
                        node = null;
                    }else{
                        parent.right = node.left;
                        node = null;
                    }
                }

            }
            size--;
        }

    }

    }

