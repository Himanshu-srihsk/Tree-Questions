// package TreeQuestions.Tree-Questions.Treap;

import java.util.Random;

class TreapNode{
    TreapNode left;
    TreapNode right;
    Integer weight;
    Integer data;
    TreapNode(Integer data) {
        this.data = data;
        this.weight = new Random().nextInt(100);
        this.left = this.right = null;
    }

}
public class Main {
    public static TreapNode insertNode(TreapNode root , int key){
        if(root==null){
            return new TreapNode(key);
        }
        if(root.data > key){
            root.left = insertNode(root.left, key);
            if(root.left!=null && root.left.weight  > root.weight){
                root = rotateRight(root);
            }
        }
        else{
            root.right = insertNode(root.right, key);
            if(root.right!=null && root.right.weight  > root.weight){
                root = rotateLeft(root);
            }
        }
        return root;
    }
    public static TreapNode rotateLeft(TreapNode root)
    {
      TreapNode R = root.right;
      TreapNode X = root.right.left;
      R.left = root;
      root.right = X;
      return R;
    }
    public static TreapNode rotateRight(TreapNode root)
    {
        TreapNode  L = root.left;
        TreapNode Y = root.left.right;
        L.right = root;
        root.left = Y;
        return L;
    }

    
    public static boolean searchNode(TreapNode root, int key)
    {
        if(root==null){
            return false;
        
        }
        if(root.left.data > key){
            return searchNode(root.left,key);
        }
        else if(root.right.data < key){
            return searchNode(root.right,key);
        }
        else{
            return true;
        }
    }
    public static TreapNode deleteNode(TreapNode root, int key){
        if(root==null){
            return root;
        }
        if(root.data > key){
            root.left = deleteNode(root.left, key);
        }
        else if(root.data < key){
            root.right = deleteNode(root.right, key);
        }
        else{
            if(root.left==null && root.right==null){
                root=null;
               
            }
            else if(root.left != null && root.right !=null){
                if(root.left.weight < root.right.weight){
                    root = rotateLeft(root);
                    root.left = deleteNode(root.left, key);
                }else{
                    root = rotateRight(root);
                    root.right = deleteNode(root.right, key);
                }
            }else{
                root = (root.left!=null)? root.left : root.right;
            }
        }
        return root;
    }

    public static void printTreap(TreapNode root, int space)
    {
        final int height = 10;
 
        // Base case
        if (root == null) {
            return;
        }
 
        // increase distance between levels
        space += height;
 
        // print the right child first
        printTreap(root.right, space);
        System.lineSeparator();
 
        // print the current node after padding with spaces
        for (int i = height; i < space; i++) {
            System.out.print(' ');
        }
 
        System.out.println(root.data + "(" + root.weight + ")");
 
        // print the left child
        System.lineSeparator();
        printTreap(root.left, space);
    }
 
    public static void main(String[] args) {
         // Treap keys
         int[] keys = { 5, 2, 1, 4, 9, 8, 10 };
 
         // construct a treap
         TreapNode root = null;
         for (int key: keys) {
             root = insertNode(root, key);
         }
  
         System.out.println("Constructed treap:\n\n");
         printTreap(root, 0);
  
         System.out.println("\nDeleting node 1:\n\n");
         root = deleteNode(root, 1);
         printTreap(root, 0);
  
         System.out.println("\nDeleting node 5:\n\n");
         root = deleteNode(root, 5);
         printTreap(root, 0);
  
         System.out.println("\nDeleting node 9:\n\n");
         root = deleteNode(root, 9);
         printTreap(root, 0);
    }
}
