package TreeQuestions.CameraAtTree;


/*
 * 
 * 1. Binary Tree Cameras
Problem: Given a binary tree, determine the minimum number of cameras needed to monitor all nodes. A camera placed at a node can monitor its parent, itself, and its immediate children.
Complexity: Dynamic programming, greedy approach, and recursion are involved.
Key concepts: Tree traversal, dynamic programming on trees.

Traverse the binary tree recursively.
For each node, determine if a camera is needed based on the state of its children.
Place cameras in a way that covers the maximum area without redundancy.
We need to:

Use a special marker for each node:
0: The node needs a camera (uncovered).
1: The node has a camera.
2: The node is covered (no camera needed).
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
        left = null;
        right = null;
    }
}

 class Solution {
    private int cameras = 0;

    public int minCameraCover(TreeNode root) {
        // If root is uncovered, place a camera at the root.
        if (cover(root) == 0) {
            cameras++;
        }
        return cameras;
    }

    private int cover(TreeNode node) {
        if (node == null) {
            return 2; // Null nodes are considered covered.
        }

        int left = cover(node.left);
        int right = cover(node.right);


         // If any child has a camera, this node is covered.
         if (left == 1 || right == 1) {
            node.val = 2; // Mark this node as covered.
            return 2; // This node is covered.
        }
        
         if (left == 0 || right == 0) {
            cameras++;
            node.val = 1; // This node now has a camera.
            return 1; // Return that this node has a camera.
        }

       
        

        // If children are covered, but no camera is here, this node needs a camera.
        node.val = 0; // This node needs a camera.
        return 0; // Return that this node needs a camera.
    }
}

public class CameraAtTree {
    // Example tree
   public static void main(String[] args) {
    TreeNode root = new TreeNode(0);
    root.left = new TreeNode(0);
    root.left.left = new TreeNode(0);
    root.left.left.left = new TreeNode(0);
    root.left.left.right = new TreeNode(0);
    root.right = new TreeNode(0);
    root.right.right = new TreeNode(0);
   // root.right.right.left = new TreeNode(0);

    Solution solution = new Solution();
    int minCameras = solution.minCameraCover(root);
    System.out.println("Minimum number of cameras needed: " + minCameras);
   }
}
