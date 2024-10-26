package org.example;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
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
        this.kth = k;
    }


    public void add(Integer key) {
        root = insert(root, key);
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

    public List<String> inorder() {
        List<String> res = new ArrayList<>();
        inorder(root, res);
        return res;
    }

    private void inorder(Node cur, List<String> res) {
        if (cur == null) {
            return;
        }
        inorder(cur.left, res);
        res.add(cur.toString());
        inorder(cur.right, res);
    }



    public boolean isEmpty() {
        return root == null;
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
            this(key, RND.nextInt());
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
            return String.format("(%d,%d,%d)", key, priority, actualDoorNumber);
        }
    }
}
