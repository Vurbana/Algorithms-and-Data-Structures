package structures;

/**
 * Created by vankata on 12.03.15.
 */
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;
    private class Node{
        private Key key;
        private int N;
        private Value value;
        private Node left, right;
        public Node(Key key, Value value, int N){
            this.key = key;
            this.value = value;
            this.N = N;
        }
    }
    public int size(){
        return size(root);
    }
    private int size(Node x){
        if(x == null){
            return 0;
        }else{
            return x.N;
        }
    }
    public void put(Key key, Value value){

    }
    public Value get(Key key){
        return get(key, root);
    }
    private Value get(Key key, Node node){
        if(node == null){
            return null;
        }
        if(node.key.compareTo(key)<0){
            get(key, node.right);
        }else if (node.key.compareTo(key)>0){
            get(key, node.left);
        }
        return node.value;
    }
    public boolean contains(Key key){
        return get(key) != null;
    }

}
