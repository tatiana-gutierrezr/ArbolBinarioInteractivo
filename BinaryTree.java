class TreeNode {
    int data;
    TreeNode left, right;

    public TreeNode(int data) {
        this.data = data;
        left = right = null;
    }
}

public class BinaryTree {
    private TreeNode root;

    public BinaryTree() {
        root = null;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private TreeNode insertRecursive(TreeNode root, int value) {
        if (root == null) {
            root = new TreeNode(value);
            return root;
        }

        if (value < root.data) {
            root.left = insertRecursive(root.left, value);
        } else {
            root.right = insertRecursive(root.right, value);
        }

        return root;
    }

    public boolean search(int value) {
        return searchRecursive(root, value);
    }

    private boolean searchRecursive(TreeNode root, int value) {
        if (root == null || root.data == value) {
            return root != null;
        }

        if (value < root.data) {
            return searchRecursive(root.left, value);
        } else {
            return searchRecursive(root.right, value);
        }
    }

    public void delete(int value) {
        root = deleteRecursive(root, value);
    }

    private TreeNode deleteRecursive(TreeNode root, int value) {
        if (root == null) {
            return root;
        }

        if (value < root.data) {
            root.left = deleteRecursive(root.left, value);
        } else if (value > root.data) {
            root.right = deleteRecursive(root.right, value);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            root.data = minValue(root.right);

            root.right = deleteRecursive(root.right, root.data);
        }

        return root;
    }

    private int minValue(TreeNode root) {
        int minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
    }

    public void edit(int oldValue, int newValue) {
        delete(oldValue);
        insert(newValue);
    }
}