package structures;



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
        }

    }

    }

