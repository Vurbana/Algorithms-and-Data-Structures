package interval.tree;

/**
 * Interval Tree implementation in Java
 * @author: Ivan Vurbanov
 * @keywords: Data Structures, Dynamic RMQ, Interval update and query
 * @modified:
 * 
 * Implement a dynamic RMQ with interval query and update. The update should
 * add a certain value to all cells in a given interval, while the query should
 * return the maximum value inside an interval.
 */

public class IntervalTree {
    private int[] tree;
    private int n;
    private int[] lazy;
    /**
     * Creates a new interval tree with initial values given in values.
     * Each update and query should allow indices [0, values.size() - 1].
     */
    IntervalTree(int[] values) {
        setTree(values);
    }
    public int pow2roundup(int x) {
        if (x < 0)
            return 0;
        --x;
        x |= x >> 1;
        x |= x >> 2;
        x |= x >> 4;
        x |= x >> 8;
        x |= x >> 16;
        return x + 1;
    }

    public void setTree(int[] values) {
        n = values.length;
        int x = (int)(Math.ceil(Math.log(n)/Math.log(2)))+1;
        int size = (1 << x);
        lazy = new int[size];
        this.tree = new int[size];
        buildTree(1,0,n-1,values);


    }

    void buildTree(int node, int a, int b, int arr[]) {
        if(a > b) return; // Out of range

        if(a == b) { // Leaf node
            tree[node] = arr[a]; // Init value
            return;
        }

        buildTree(node*2, a, (a+b)/2,arr); // Init left child
        buildTree(node*2+1, 1+(a+b)/2, b,arr); // Init right child

        tree[node] = Math.max(tree[node*2], tree[node*2+1]); // Init root value
    }
    /**
     * Adds the value add to each element in the interval [idx1, idx2].
     */
    void update(int idx1, int idx2, int add) {
        updateTree(1,0,n-1,idx1,idx2, add);
    }
    private void updateTree(int node, int a, int b, int i, int j, int value) {


        if(lazy[node] != 0) { // This node needs to be updated
            tree[node] += lazy[node]; // Update it

            if(a != b) {
                lazy[node*2] += lazy[node]; // Mark child as lazy
                lazy[node*2+1] += lazy[node]; // Mark child as lazy
            }

            lazy[node] = 0; // Reset it
        }

        if(a > b || a > j || b < i) // Current segment is not within range [i, j]
            return;

        if(a >= i && b <= j) { // Segment is fully within range
            tree[node] += value;

            if(a != b) { // Not leaf node
                lazy[node*2] += value;
                lazy[node*2+1] += value;
            }

            return;
        }

        updateTree(node * 2, a, (a + b) / 2, i, j, value); // Updating left child
        updateTree(1 + node * 2, 1 + (a + b) / 2, b, i, j, value); // Updating right child

        tree[node] = Math.max(tree[node * 2], tree[node * 2 + 1]); // Updating root with max value
    }
    /**
     * Returns the maximum value in the interval [idx1, idx2].
     */
    int query(int idx1, int idx2) {

        return query_tree(1,0,n-1,idx1,idx2);

    }
    int query_tree(int node, int a, int b, int i, int j) {

        if(a > b || a > j || b < i) return Integer.MIN_VALUE; // Out of range

        if(lazy[node] != 0) { // This node needs to be updated
            tree[node] += lazy[node]; // Update it

            if(a != b) {
                lazy[node*2] += lazy[node]; // Mark child as lazy
                lazy[node*2+1] += lazy[node]; // Mark child as lazy
            }

            lazy[node] = 0; // Reset it
        }

        if(a >= i && b <= j) // Current segment is totally within range [i, j]
            return tree[node];

        int q1 = query_tree(node*2, a, (a+b)/2, i, j); // Query left child
        int q2 = query_tree(1+node*2, 1+(a+b)/2, b, i, j); // Query right child

        int res = Math.max(q1, q2); // Return final result

        return res;
    }

};
