// package TreeQuestions.Segment Tree;
//Range sum Query, single element update, Segment tree
class Tree{
    int[] ST;
    public Tree(int[] arr) {
        ST = new int[4 * arr.length +1];
    }
    public void build(int[] arr){
        buildTree(arr, 1, 0, arr.length - 1);
    }
    public void print(){
        for(int i:ST){
            System.out.print(i + " ");
        }
        System.out.println();
    }
    public void buildTree(int[] arr, int idx, int start, int end){
        if(start==end){
            ST[idx] = arr[start];
            return;
        }
    
        int mid = start + ((end - start) >> 1);
        buildTree(arr, 2 * idx, start, mid);
        buildTree(arr, 2*idx+1, mid+1, end);
        ST[idx] = ST[2*idx] + ST[2*idx+1];
    }
    public int query(int idx, int start, int end, int qs,int qe){
          if(qe< start ||  end < qs){
            return 0;
          }
          if(qs<=start && end<=qe){
            return ST[idx];
          }
          int mid = start + ((end - start) >> 1);
          
          return query(2*idx, start, mid, qs, qe) + query(2*idx+1, mid+1, end, qs, qe);
    }
    public void update(int A[], int idx, int start, int end,int updateIdx, int val){
        if( start == end){
            ST[idx]+= val;
            A[updateIdx]+= val;
            return;
          
        }
        int mid = start + ((end - start) >> 1);

        if( updateIdx <=mid){
            update(A, 2*idx, start, mid, updateIdx, val);
        }else{
            update(A, 2*idx+1, mid+1, end, updateIdx, val);
        }
        ST[idx]= ST[2*idx] + ST[2*idx+1];
    
    }

}
public class Segmenttree {
    public static void main(String[] args) {
        int A[] = { 0, 1, 3, 5, -2, 3 };
        int n = A.length;
        Tree tree = new Tree(A);
        tree.build(A);

       // tree.print();
        
        System.out.println(
            "Sum of values in range 0-4 are: "
            + tree.query(1, 0, n - 1, 0, 4));
 
        tree.update(A,1, 0, n - 1, 1, 100);
        System.out.println(
            "Value at index 1 increased by 100");
        System.out.println("sum of value in range 1-3 are: "
                           + tree.query(1, 0, n - 1, 1, 3));
    }
}
