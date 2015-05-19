package trie;

import java.util.ArrayList;

/**
 * Prefix Tree with alphabet consisting of lowercase ASCII characters
 *
 * @author Vurbanov
 */
public class PrefixTrie<Value> {
    private int size;
    private static final int ALPHABET = 26;
    private Node root;

    public PrefixTrie(){
        size = 0;
        root = new Node();

    }
    private static class Node{
        private Object value;
        private Node[] next;
        public Node(){
            next = new Node[ALPHABET];
        }
    }

    /**
     * Inserts the string in the prefix trie.
     *
     * @param string  the string to be inserted
     * @param value  the value coresponding to that string
     */
    public void put(String string, Value value){
        Node node = root;
        string.toLowerCase();
        int i = 0;
        int n = string.length();
        int position;
        while (i < n){
            position = (int)string.charAt(i) - 97;
            if(node.next[position] != null){
                node = node.next[position];
                i++;
            }else{
                break;
            }
        }
        while(i < n){
            position = (int)string.charAt(i) - 97;
            node.next[position] = new Node();
            node = node.next[position];
            i++;
        }
        node.value = value;
        size++;
    }

    /**
     * Given a string it returns the coresponding value
     * @param string the string to be searched fro
     * @return null if the string is not in the trie, its value otherwise
     */
    public Value get(String string){
        if(size == 0){
            return null;
        }
        int i = 0;
        string.toLowerCase();
        int n = string.length();
        Node node = root;
        int position;
        while(i < n){
            position = (int)string.charAt(i) - 97;
            if(node.next[position] == null){
                return null;
            }else{
                node = node.next[position];
                i++;
            }
        }
        return (Value)node.value;
    }

    /**
     * The size of the trie
     * @return size of trie
     */
    public int size(){
        return size;
    }

    /**
     * Checks if the trie is empty.
     *
     * @return true only if the trie is empty, false otherwise
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Given a prefix it returns all the words in the trie with that prefix
     *
     * @param prefix the prefix for which to be searched
     * @return empty list if there is no such prefix in the trie, list with all the words matching the prefix otherwise
     */
    public ArrayList<String> keysWithPrefix(String prefix){
        int i = 0;
        int n = prefix.length();
        ArrayList<String> list = new ArrayList<>();
        int position;
        Node node = root;
        while( i < n){
            position = (int)prefix.charAt(i) - 97;
            if(node.next[position] == null){
                return list;
            }else{
                node = node.next[position];
                i++;
            }
        }
        collect(node, prefix, list);
        return list;

    }

    /**
     * Helper method for <code>keysWithPrefix</code>. It fills the ArrayList with the matching words
     *
     * @param node the current node
     * @param prefix the current prefix
     * @param list the list containing all the found words
     */
    private void  collect(Node node, String prefix, ArrayList<String> list){

        if(node.value != null){
            list.add(prefix);
        }
        for(int i = 0; i < ALPHABET; i++){
            if(node.next[i] != null){
                collect(node.next[i], prefix + (char)(i+97), list);
            }
        }

    }

}
