package com.fmi.ads.avl;

import java.util.ArrayDeque;
import java.util.Stack;

/**
 * Implement your solution here.
 * @author boris.strandjev
 *
 * @param <T> The type of the values to be stored in the tree.
 */
public class AVLTree<T extends Comparable<T>> extends AVLTreeInterface<T> {
	// !!! DO NOT FORGET TO UPDATE THOSE
	public static final String NAME = "Ivan Georgiev Vurbanov";
	public static final String MOODLE_NAME = "Ivan Vurbanov";
	public static final String FACULTY_NUMBER = "61569";

	// public methods
	public AVLTree() {
	}

	@Override
	public void finalize() throws Throwable {
        Stack<Node<T>> stack = new Stack<>();
        if(root != null){
            stack.push(root);
        }
        while(!stack.empty()){
            Node<T> node = stack.pop();
            if(node.leftChild != null){
                stack.push(node.leftChild);
                node.leftChild = null;
            }
            if(node.rightChild != null){
                stack.push(node.rightChild);
                node.rightChild = null;
            }
            node.parent = null;
        }
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public Node<T> findNode(T value) {
        Node<T> current = root;
        int cmp;
        while(current != null){
            cmp = value.compareTo(current.value);
            if(cmp == 0){
                return current;
            }else if(cmp < 0){
                current = current.leftChild;
            }else{
                current = current.rightChild;
            }
        }
        return null;
	}

	@Override
	public void insertNode(T value) {
        Node<T> parent = null;
        Node<T> current = root;
        int cmp;
        while(current != null){
            parent = current;
            cmp = value.compareTo(current.value);
            if(cmp == 0){
                return;
            }else if(cmp < 0){
                current = current.leftChild;
            }else{
                current = current.rightChild;
            }
        }
        Node<T> node = new Node<>();
        node.value = value;
        node.parent = parent;
        node.height = 1;
        size++;
        if(parent == null){
            this.root = node;
        }else if(node.value.compareTo(parent.value) < 0 ){
            parent.leftChild = node;
        }else{
            parent.rightChild = node;
        }
        balanceTree(node);

	}
    private void balanceTree(Node<T> node){
        while(node != root){
            node.parent.height = maxHeight(node.parent) + 1;
            if(isUnbalanced(node.parent)){
                if(node.parent.leftChild == node) {
                        if (isLeftBiggerThanRight(node)) {
                            rightRotation(node);
                        } else {
                            Node<T> right = node.rightChild;
                            leftRotation(right);
                            rightRotation(right);
                            node = right;
                        }
                }else {
                        if (isLeftBiggerThanRight(node)) {
                            Node<T> left = node.leftChild;
                            rightRotation(left);
                            leftRotation(left);
                            node = left;
                        } else {
                            leftRotation(node);
                        }
                    }
            }else{
                node = node.parent;
            }
        }
    }
    private int maxHeight(Node<T> node){
        int first=0, second=0;
        if(node.leftChild != null){
            first = node.leftChild.height;
        }
        if(node.rightChild != null){
            second = node.rightChild.height;
        }
        return Math.max(first, second);
    }
    private boolean isLeftBiggerThanRight(Node<T> node){
        int left = 0, right = 0;
        if(node.leftChild != null){
            left = node.leftChild.height;
        }
        if(node.rightChild != null){
            right = node.rightChild.height;
        }
        if(left > right){
            return true;
        }else{
            return false;
        }

    }

    private boolean isUnbalanced(Node<T> node){
        int left = 0, right = 0, result;
        if(node.leftChild != null){
            left = node.leftChild.height;
        }
        if(node.rightChild != null){
            right = node.rightChild.height;
        }
        result = Math.abs((left-right));
        if(result > 1){
            return true;
        }else{
            return false;
        }
    }
    private boolean areEqualHeights(Node<T> node){
        int left = 0, right = 0;
        if(node.leftChild != null){
            left = node.leftChild.height;
        }
        if(node.rightChild != null){
            right = node.rightChild.height;
        }
        if(left == right){
            return true;
        }else{
            return false;
        }
    }

	@Override
	public void deleteNode(T value) {
        Node<T> node = findNode(value);
        if(node == null){
            return;
        }
        if(node.rightChild == null && node.leftChild == null){
            deleteWithNoChilds(node);
        }else if(node.rightChild != null && node.leftChild != null){
            Node<T> child = max(node.leftChild);
            node.value = child.value;
            if(child.leftChild == null){
                deleteWithNoChilds(child);
            }else{
                deleteWithLeftChild(child);
            }
        }else {
            if(node.leftChild != null){
                deleteWithLeftChild(node);
            }else{
                deleteWithRightChild(node);
            }
        }
	}
    private void deleteWithLeftChild(Node<T> node){
        if(node.parent == null){
            root = node.leftChild;
            node.leftChild.parent = null;

        }else{
            if(node.parent.leftChild == node){
                node.parent.leftChild = node.leftChild;

            }else{
                node.parent.rightChild = node.leftChild;
            }
            node.leftChild.parent = node.parent;
            node.leftChild.height = maxHeight(node.leftChild)+1;
            balanceTree(node.leftChild);
        }
        size--;

    }
    private void deleteWithRightChild(Node<T> node){
        if(node.parent == null){
            root = node.rightChild;
            node.rightChild.parent = null;


        }else{
            if(node.parent.leftChild == node){
                node.parent.leftChild = node.rightChild;

            }else{
                node.parent.rightChild = node.rightChild;
            }
            node.rightChild.parent = node.parent;
            node.rightChild.height = maxHeight(node.rightChild)+1;
            balanceTree(node.rightChild);
        }
        size--;

    }
    private void deleteWithNoChilds(Node<T> node){
        if(node.parent == null){
            root = null;
        }else{
            if(node.parent.leftChild == node){
                node.parent.leftChild = null;
                node.parent.height = maxHeight(node.parent)+1;
                if(node.parent.rightChild != null){
                    balanceTree(node.parent.rightChild);
                }else{
                    balanceTree(node.parent);
                }
            }else{
                node.parent.rightChild = null;
                node.parent.height = maxHeight(node.parent)+1;
                if(node.parent.leftChild != null){
                    balanceTree(node.parent.leftChild);
                }else{
                    balanceTree(node.parent);
                }
            }
        }
        size--;
    }
    private Node<T> min(Node<T> node){
        while(node.leftChild != null){
            node = node.leftChild;
        }
        return node;
    }
    private void leftRotation(Node<T> node) {
        Node<T> grandParent = node.parent.parent;
        Node<T> leftChild = node.leftChild;
        Node<T> parent = node.parent;
        if (grandParent != null) {
            if (grandParent.leftChild == parent) {
                grandParent.leftChild = node;
                node.parent = grandParent;
            } else {
                grandParent.rightChild = node;
                node.parent = grandParent;
            }
        }else{
            root = node;
            node.parent = null;
        }
        node.leftChild = parent;
        parent.parent = node;
        if(leftChild != null){
            parent.rightChild = leftChild;
            leftChild.parent = parent;
        }else{
            parent.rightChild = null;
        }
        parent.height = maxHeight(parent) + 1;
        node.height = maxHeight(node)+1;
    }
    private Node<T> max(Node<T> node){
        while(node.rightChild != null){
            node = node.rightChild;
        }
        return node;
    }
    private void rightRotation(Node<T> node){
        Node<T> rightChild = node.rightChild;
        Node<T> grandParent = node.parent.parent;
        Node<T> parent = node.parent;
        if(grandParent != null){
            if(grandParent.leftChild == parent){
                grandParent.leftChild = node;
                node.parent = grandParent;
            }else{
                grandParent.rightChild = node;
                node.parent = grandParent;
            }
        }else{
            root = node;
            node.parent = null;
        }
        node.rightChild = parent;
        parent.parent = node;

        if(rightChild != null){
            rightChild.parent = parent;
            parent.leftChild = rightChild;
        }else{
            parent.leftChild = null;
        }
        parent.height = maxHeight(parent) + 1;
        node.height = maxHeight(node)+1;
    }
    public void display(){

        if(root == null){
            return;
        }
        System.out.printf("root: %s\n", root.value.toString());
        ArrayDeque<Node<T>> arrayDeque = new ArrayDeque<>();
        System.out.printf("Parent: %s height: %d ", root.value.toString(), root.height);
        if(root.leftChild != null){
            System.out.printf("left: %s height: %d ", root.leftChild.value.toString(), root.leftChild.height);
            arrayDeque.add(root.leftChild);
        }
        if(root.rightChild != null){
            System.out.printf("right: %s height: %d", root.rightChild.value.toString(), root.rightChild.height);
            arrayDeque.add(root.rightChild);
        }
        System.out.println();
        while(!arrayDeque.isEmpty()){
            Node<T> tmp = arrayDeque.removeFirst();
            System.out.printf("Parent: %s height: %d ", tmp.value.toString(), tmp.height);
            if(tmp.leftChild != null){
                System.out.printf("left: %s height: %d ", tmp.leftChild.value.toString(), tmp.leftChild.height);
                arrayDeque.add(tmp.leftChild);
            }
            if(tmp.rightChild != null){
                System.out.printf("right: %s height: %d", tmp.rightChild.value.toString(), tmp.rightChild.height);
                arrayDeque.add(tmp.rightChild);
            }
            System.out.println();
        }


    }

}
