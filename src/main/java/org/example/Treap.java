package org.example;

import lombok.Getter;

import java.util.Random;

/**
 *
 * Теория<br/>
 * <a href="http://e-maxx.ru/algo/treap">http://e-maxx.ru/algo/treap</a><br/>
 * <a href="https://www.geeksforgeeks.org/treap-a-randomized-binary-search-tree/">https://www.geeksforgeeks.org/treap-a-randomized-binary-search-tree/</a><br/>
 * <a href="https://www.geeksforgeeks.org/implementation-of-search-insert-and-delete-in-treap/">https://www.geeksforgeeks.org/implementation-of-search-insert-and-delete-in-treap/</a><br/>
 * <a href="http://faculty.washington.edu/aragon/pubs/rst89.pdf">http://faculty.washington.edu/aragon/pubs/rst89.pdf</a><br/>
 * <a href="https://habr.com/ru/articles/101818/">https://habr.com/ru/articles/101818/</a><br/>
 * Примеение в linux kernel<br/>
 * <a href="https://www.kernel.org/doc/mirror/ols2005v2.pdf">https://www.kernel.org/doc/mirror/ols2005v2.pdf</a>
 *
 */
public class Treap {

    Node root;

    int k;

    int kth;

    int size;



    public Treap() {
    }

    public Treap(int k, int size) {
        this.k = k;
        this.size = size;
        this.kth = 0;
    }


    public void add(Integer key) {
        root = insert(root, key);
    }

    public int takeKth() {
        kth = (kth + k - 1) % (size);
        return searchByKthPos();
    }

    public void remove(Integer key) {
        root = deleteNode(root, key);
        size--;
    }

    private Node deleteNode(Node cur, Integer key) {
        if (cur == null)
            return cur;

        if (key.compareTo(cur.key) < 0)
            cur.left = deleteNode(cur.left, key);
        else if (key.compareTo(cur.key) > 0)
            cur.right = deleteNode(cur.right, key);

            // IF KEY IS AT ROOT

            // If left is NULL
        else if (cur.left == null) {
            Node temp = cur.right;
            cur = temp;  // Make right child as root
        }
        // If Right is NULL
        else if (cur.right == null) {
            Node temp = cur.left;
            cur = temp;  // Make left child as root
        }
        // If key is at root and both left and right are not NULL
        else if (cur.left.priority < cur.right.priority) {
            cur = leftRotation(cur);
            cur.left = deleteNode(cur.left, key);
        } else {
            cur = rightRotation(cur);
            cur.right = deleteNode(cur.right, key);
        }

        return cur;
    }


    public Integer get(Integer key) {
        return searchNode(root, key).actualDoorNumber;
    }

    public void updateNodes(Integer key) {
        updateNode(root, key);
    }


    private void updateNode(Node cur, Integer key) {
        if(cur != null) {
            if(key.compareTo(cur.key) > 0) {
                updateNode(cur.right, key);
                cur.delta++;
                updateDelta(cur.left);
            } else {
                cur.actualDoorNumber++;
                cur.actualDoorNumber += cur.delta;
                updateNode(cur.left, key);
                updateNode(cur.right, key);
            }
        }
    }

    public int searchByKthPos() {
        Node result = new Node(-1);
        searchByKthPos(root, result);
        currentIndex = 0;
        return result.key;
    }

    static int currentIndex = 0;

    private void searchByKthPos(Node cur, Node result) {
        if (cur == null) {
            return;
        }
        searchByKthPos(cur.left, result);
        if(currentIndex == kth) {
            result.key = cur.key;
        }
        currentIndex++;
        searchByKthPos(cur.right, result);
    }

    private void updateDelta(Node cur) {
        if (cur != null){
            cur.delta++;
            updateDelta(cur.left);
            updateDelta(cur.right);
        }
    }

    private Node searchNode(Node cur, Integer key) {
        if (cur == null || key.compareTo(cur.key) == 0) {
            return cur;
        }
        if (key.compareTo(cur.key) > 0) {
            return searchNode(cur.right, key);
        }
        return searchNode(cur.left, key);
    }


    private Node insert(Node cur, Integer key) {
        if (cur == null) {
            return new Node(key);
        }
        if (key.compareTo(cur.key) > 0) {
            cur.right = insert(cur.right, key);
            if (cur.right.priority < cur.priority) {
                cur = leftRotation(cur);
            }

        } else {
            cur.left = insert(cur.left, key);
            if (cur.left.priority < cur.priority) {
                cur = rightRotation(cur);
            }

        }
        return cur;
    }

    /* T1, T2 and T3 are subtrees of the tree rooted with y
  (on left side) or x (on right side)
                y                               x
               / \     Right Rotation          /  \
              x   T3   – – – – – – – >        T1   y
             / \       < - - - - - - -            / \
            T1  T2     Left Rotation            T2  T3 */

    private Node leftRotation(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        return y;
    }

    private Node rightRotation(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        return x;
    }


    @Getter
    public static class Node {
        static Random RND = new Random();
        Integer key;
        int priority;
        int actualDoorNumber;
        int delta;
        Node left;
        Node right;

        public Node(Integer key) {
            this(key, RND.nextInt(10));
        }

        public Node(Integer key, int priority) {
            this(key, priority, null, null);
        }

        public Node(Integer key, int priority, Node left, Node right) {
            this.key = key;
            this.priority = priority;
            this.left = left;
            this.right = right;
            this.actualDoorNumber = key;
            this.delta = 0;
        }

        @Override
        public String toString() {
            return String.format("(%d,%d)", key, priority);
        }
    }
}
