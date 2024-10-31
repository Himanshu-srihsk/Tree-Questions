package TreeQuestions.MultinaryTreeCamera;

import java.util.ArrayList;
import java.util.List;

/*
 * 
 * Problem Statement (Modified for Multi-nary Tree)
Given a multi-nary tree, determine the minimum number of cameras needed to monitor all nodes. A camera placed at a node can monitor itself, its parent, and all its immediate children.

Each node can have multiple children. You need to decide where to place the cameras so that every node in the tree is either monitored by a camera or has a camera placed at it.

Approach
We can use a similar greedy, Depth First Search (DFS) approach, but instead of checking only left and right children, we’ll iterate through all children of each node. The goal is to place cameras at nodes that can cover the largest number of uncovered nodes while minimizing redundant coverage.

Define States for Each Node:

0: Node needs a camera (it's not covered by any camera).
1: Node has a camera.
2: Node is covered (either it has a camera or one of its children has a camera covering it).
DFS Traversal:

If any child is in state 0 (needs a camera), place a camera at the current node.
If any child has a camera (1), the current node becomes covered (2).
If all children are covered (either 1 or 2), but no camera is present at the current node, mark the node as needing a camera (0).
 */

class MultiTreeNode {
    int val;
    List<MultiTreeNode> children;

    MultiTreeNode(int val) {
        this.val = val;
        this.children = new ArrayList<>();
    }
}

public class Solution {
    private int cameras = 0;

    public int minCameraCover(MultiTreeNode root) {
        // If the root is uncovered, place a camera at the root.
        if (cover(root) == 0) {
            cameras++;
        }
        return cameras;
    }

    private int cover(MultiTreeNode node) {
        if (node == null) {
            return 2; // Null nodes are covered by default.
        }

        int coveredChildren = 0; // Tracks if any child has a camera
        int needCamera = 0; // Tracks if any child needs a camera

        for (MultiTreeNode child : node.children) {
            int childState = cover(child);
            if (childState == 0) {
                needCamera++;
            }
            if (childState == 1) {
                coveredChildren++;
            }
        }
        // If any child has a camera, this node is covered.
        if (coveredChildren > 0) {
            return 2; // This node is covered by a child's camera.
        }
        
        // If any child needs a camera, place a camera at this node.
        if (needCamera > 0) {
            cameras++;
            return 1; // This node now has a camera.
        }

        

        // If all children are covered, but no camera is here, this node needs a camera.
        return 0; // This node needs a camera.
    }

    public static void main(String[] args) {
        // Create a sample multi-nary tree
        MultiTreeNode root = new MultiTreeNode(0);

        MultiTreeNode child1 = new MultiTreeNode(1);
        MultiTreeNode child2 = new MultiTreeNode(2);
        MultiTreeNode child3 = new MultiTreeNode(3);

        MultiTreeNode child11 = new MultiTreeNode(4);
        MultiTreeNode child12 = new MultiTreeNode(5);
        MultiTreeNode child21 = new MultiTreeNode(6);

        // Building the tree structure
        root.children.add(child1);
        root.children.add(child2);
        root.children.add(child3);

        child1.children.add(child11);
        child1.children.add(child12);
        child2.children.add(child21);

        // Running the solution
        Solution solution = new Solution();
        int minCameras = solution.minCameraCover(root);

        System.out.println("Minimum number of cameras needed: " + minCameras);
    }
}
