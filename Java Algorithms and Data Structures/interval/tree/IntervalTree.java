package interval.tree;

/**
 * Interval Tree implementation in Java
 *
 * @author: Ivan Vurbanov
 * @keywords: Data Structures, Dynamic RMQ, Interval update and query
 * @modified: Implement a dynamic RMQ with interval query and update. The update should
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

    public void setTree(int[] values) {
        n = values.length;
        int height = (int) (Math.ceil(Math.log(n) / Math.log(2))) + 1;
        int size = (1 << height);
        lazy = new int[size];
        this.tree = new int[size];
        buildTree(1, 0, n - 1, values);
    }

    void buildTree(int node, int left, int right, int values[]) {
        if (left > right) {
            return;
        }

        if (left == right) {
            tree[node] = values[left];
            return;
        }
        int middle = getMiddle(left, right);
        int leftChild = 2 * node;
        int rightChild = leftChild + 1;
        buildTree(leftChild, left, middle, values);
        buildTree(rightChild, middle + 1, right, values);

        tree[node] = Math.max(tree[leftChild], tree[rightChild]);
    }

    private int getMiddle(int left, int right) {
        return left + ((right - left) / 2);
    }

    /**
     * Adds the value add to each element in the interval [idx1, idx2].
     */
    void update(int idx1, int idx2, int add) {
        updateTree(1, 0, n - 1, idx1, idx2, add);
    }

    private void updateTree(int node, int left, int right, int i, int j, int value) {

        int leftChild = 2 * node;
        int rightChild = leftChild + 1;
        if (lazy[node] != 0) {
            tree[node] += lazy[node];

            if (left != right) {
                lazy[leftChild] += lazy[node];
                lazy[rightChild] += lazy[node];
            }

            lazy[node] = 0;
        }

        if (left > right || left > j || right < i)
            return;

        if (left >= i && right <= j) {
            tree[node] += value;

            if (left != right) {
                lazy[leftChild] += value;
                lazy[rightChild] += value;
            }

            return;
        }
        int middle = getMiddle(left, right);

        updateTree(leftChild, left, middle, i, j, value);
        updateTree(rightChild, middle + 1, right, i, j, value);


        tree[node] = Math.max(tree[leftChild], tree[rightChild]);
    }

    /**
     * Returns the maximum value in the interval [idx1, idx2].
     */
    int query(int idx1, int idx2) {

        return query_tree(1, 0, n - 1, idx1, idx2);

    }

    int query_tree(int node, int left, int right, int i, int j) {

        if (left > right || left > j || right < i) {
            return Integer.MIN_VALUE;
        }
        int leftChild = 2 * node;
        int rightChild = leftChild + 1;
        if (lazy[node] != 0) {
            tree[node] += lazy[node];

            if (left != right) {
                lazy[leftChild] += lazy[node];
                lazy[rightChild] += lazy[node];
            }

            lazy[node] = 0;
        }

        if (left >= i && right <= j) {
            return tree[node];
        }

        int middle = getMiddle(left, right);

        if (i > middle) {
            return query_tree(rightChild, middle + 1, right, i, j);
        }
        if (j <= middle) {
            return query_tree(leftChild, left, middle, i, j);
        }

        int q1 = query_tree(leftChild, left, middle, i, j);
        int q2 = query_tree(rightChild, middle + 1, right, i, j);

        int result = Math.max(q1, q2);

        return result;
    }

}