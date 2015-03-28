package heaps;

import java.util.NoSuchElementException;

/**
 * Data type that represents a min-heap implementation of Skew heap data stucture
 */
public class SkewHeap {
	/**
	 * Reference to the root for the current skew heap
	 */
	private Node root;

	/**
	 * Data type that represents a node in the Skew heap
	 */
	private static class Node {
		int value; // data value
		Node left; // left subtree
		Node right; // right subtree

		Node(int value) {
			// Implement me...
            this.value = value;
            this.left = null;
            this.right = null;
		}
	}

	public SkewHeap() {

	}

	/**
	 * Method that adds a node with data provided by @value in the current skew
	 * heap.
	 * 
	 * @param value: value to be added in the heap
	 */
	public void add(int value) {
        Node current = new Node(value);
        root = merge(root,current);

	}

	/**
	 * This method removes and returns the smallest element in the current skew
	 * heap.
	 * 
	 * @returns the removed element
	 * 
	 * @throws Exception if there are no elements, but minimum was tried to be
	 * removed
	 */
	public int removeMin() {
        if(empty()){
            throw new NoSuchElementException();
        }
        else{
            int min = root.value;
            root = merge(root.left, root.right);
            return min;
        }

	}

	/**
	 * Tests whether there are any elements in the current heap.
	 * 
	 * @returns true, if there aren't any elements and false, otherwise
	 */
	public boolean empty() {
		return root == null;
	}

	/**
	 * Method that merges the current skew heap with the given by @other. This
	 * method destructs the @other skew heap while merging it.
	 *
	 * @param other
	 *            : reference to the skew heap data structure that will be
	 *            merged with the current one
	 */
	public void merge(SkewHeap other) {
        if(other != null){
            this.root = merge(this.root, other.root);
            other.root = null;
        }


	}

	/**
	 * Method that merges two skew heap data structures referenced to their
	 * roots by @root1 and @root2
	 * 
	 * @param root1: reference to the root of the first skew heap
	 * 
	 * @param root2: reference to the root of the second skew heap
	 * 
	 * @returns a reference to the root of the merged data structure
	 */
	private Node merge(Node root1, Node root2) {
        Node firstRoot = root1;
        Node secondRoot = root2;
        if(firstRoot == null){
            return secondRoot;
        }else if(secondRoot == null){
            return firstRoot;
        }
        if(firstRoot.value <= secondRoot.value){
            Node temp = firstRoot.right;
            firstRoot.right = firstRoot.left;
            firstRoot.left = merge(secondRoot,temp);
            return firstRoot;
        }
        else{
          return merge(secondRoot,firstRoot);
        }

	}
}