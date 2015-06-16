/* 
 * File:   BinarySearchTree.h
 * Author: vankata
 *
 * Created on June 14, 2015, 8:58 PM
 */

#ifndef BINARYSEARCHTREE_H
#define	BINARYSEARCHTREE_H
#include<memory>
#include<iostream>
#include<stdexcept>
#include<queue>

using std::shared_ptr;
using std::make_shared;
using std::cout;
using std::queue;
using std::endl;

template<class T>
class Node {
public:
    shared_ptr<Node<T>> parent, left_child, right_child;
    T key;

    Node(T data)
    : key(data) {
    };
};

template<class T>
class BinarySearchTree {
    using tree_node = shared_ptr<Node<T>>;
    tree_node root;
    unsigned int number_of_nodes;
public:

    BinarySearchTree()
    : number_of_nodes(0) {
    }

    void insert(T key) {
        if (!root) {
            root = make_shared<Node < T >> (key);
            number_of_nodes++;
            return;
        }
        tree_node current = root;
        tree_node parent;
        while (current) {
            if (current->key < key) {
                parent = current;
                current = current->right_child;
            } else if (current->key == key) {
                throw std::logic_error("Key already in the tree");
            } else {
                parent = current;
                current = current->left_child;
            }
        }

        tree_node node = make_shared<Node < T >> (key);
        node->parent = parent;
        if (parent->key < key) {
            parent->right_child = node;
        } else {
            parent->left_child = node;
        }
        number_of_nodes++;
    }

    bool search(T key) {
        if (get_node(key)) {
            return true;
        }
        return false;
    }

    unsigned size() {
        return number_of_nodes;
    }

    T minimum() {
        if (!root) {
            throw std::logic_error("Tree is empty");
        }
        return minimum(root)->key;
    }

    T maximum() {
        if (!root) {
            throw std::logic_error("Tree is empty");
        }
        return maximum(root)->key;
    }

    void print() {
        if (!root) {
            return;
        }
        queue<tree_node> bst_queue;
        cout << "Root " << root->key << endl;
        cout << root->key << ": ";
        if (root->left_child) {
            cout << "left child: " << root->left_child->key;
            bst_queue.push(root->left_child);
        }
        if (root->right_child) {
            cout << "right child: " << root->right_child->key;
            bst_queue.push(root->right_child);
        }
        cout<<endl;
        while (!bst_queue.empty()) {
            tree_node node = bst_queue.front();
            bst_queue.pop();
            cout << node->key << ": ";
            if (node->left_child) {
                cout << "left child: " << node->left_child->key << " ";
                bst_queue.push(node->left_child);
            }
            if (node->right_child) {
                cout << "right child: " << node->right_child->key ;
                bst_queue.push(node->right_child);
            }
            cout<<endl;
        }
    }

    void delete_node(T key) {
        if (!root) {
            throw std::logic_error("Tree is empty");
        }
        tree_node current = get_node(key);
        if (!current) {
            throw std::logic_error("No such element in the tree");
        }
        delete_node(current);
        number_of_nodes--;
    }
private:

    void delete_node(tree_node node) {
        if ((!node->left_child) && (!node->right_child)) {
            delete_without_children(node);
        } else if (node->left_child && node->right_child) {
            tree_node tmp = minimum(node->right_child);
            node->key = tmp->key;
            delete_node(tmp);
        } else if(node->right_child) {
            delete_with_right_child(node);
        }else{
            delete_with_left_child(node);
        }
    }

    void delete_without_children(tree_node node) {
        if(!node->parent){
            root.reset();
            node.reset();
            return;
        }
        if(is_left_child(node->parent, node)){
            node->parent->left_child.reset();
        }else{
            node->parent->right_child.reset();
        }
        node.reset();
    }
    
    void delete_with_left_child(tree_node node){
        if(!node->parent){
            root = node->left_child;
            node->left_child->parent.reset();
            node.reset();
            return;
        }
        if(is_left_child(node->parent, node)){
            node->parent->left_child = node->left_child;
    
        }else{
            node->parent->right_child = node->left_child;    
        }
        node->left_child->parent = node->parent;
        node.reset();
    }
    
    void delete_with_right_child(tree_node node) {
        if(!node->parent){
            root = node->right_child;
            node->right_child->parent.reset();
            node.reset();
            return;
        }
        if(is_left_child(node->parent, node)){
            node->parent->left_child = node->right_child;         
        }else{
            node->parent->right_child = node->right_child;
        }
        node->right_child->parent = node->parent;
        node.reset();
    }
    
    bool is_left_child(tree_node parent, tree_node child){
        if(parent->left_child == child){
            return true;
        }
        return false;
    }
    
    tree_node maximum(tree_node node) {
        while (node->right_child) {
            node = node->right_child;
        }
        return node;
    }

    tree_node minimum(tree_node node) {
        while (node->left_child) {
            node = node->left_child;
        }
        return node;
    }

    tree_node get_node(T key) {
        tree_node current = root;
        while (current) {
            if (current->key == key) {
                return current;
            } else if (current->key > key) {
                current = current->left_child;
            } else {
                current = current->right_child;
            }
        }
        return current;
    }

};
#endif	/* BINARYSEARCHTREE_H */

