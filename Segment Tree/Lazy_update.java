// package TreeQuestions.Segment Tree;

class LazyTree{
    int[] ST;
    int[] lazy;
    public LazyTree(int[] arr) {
        ST = new int[4 * arr.length +1];
        lazy = new int[4 * arr.length + 1 ];
    }
    public void build(int[] arr){
        buildTree(arr, 0, 0, arr.length - 1);
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
        buildTree(arr, 2 * idx+1, start, mid);
        buildTree(arr, 2*idx+2, mid+1, end);
        ST[idx] = ST[2*idx+1] + ST[2*idx+2];
    }
    public int getSum(int idx, int start, int end, int qs,int qe){
        if(lazy[idx] !=0){
            ST[idx] +=  lazy[idx] * ( end-start+1);
            if(start!=end){
                lazy[2*idx+1] += lazy[idx];
            lazy[2*idx+2] += lazy[idx];
            }
            lazy[idx] = 0;

        }
        if(start> end || qe< start ||  end < qs){
            return 0;
          }
          if(qs<=start && end<=qe){
            return ST[idx];
          }
          int mid = start + ((end - start) >> 1);
          
          return getSum(2*idx+1, start, mid, qs, qe) + getSum(2*idx+2, mid+1, end, qs, qe);

    }
    public void updateRange(int A[], int idx, int start, int end,int startUpdateIdx, int endUpdateIdx, int val){
        if(lazy[idx] !=0){
            ST[idx] +=  lazy[idx] * (end-start+1);;
            if(start!=end){
                lazy[2*idx+1] += lazy[idx];
                lazy[2*idx+2] += lazy[idx];
               
            }
            lazy[idx] = 0;
            
        }
        if (start > end || endUpdateIdx < start || startUpdateIdx > end) {
            return;
        }

        
        if(startUpdateIdx <=start && endUpdateIdx >= end){
            ST[idx] += val * (end-start+1);
            if(start!=end){
                lazy[2*idx+1] += val;
                lazy[2*idx+2] += val;
            }
            
            return;
        }
         int mid = (start + (end-start)>>1);
         updateRange(A, 2*idx+1, start, mid, startUpdateIdx, endUpdateIdx, val);
         updateRange(A, 2*idx+2, mid+1, end, startUpdateIdx, endUpdateIdx, val);

         ST[idx] = ST[2 * idx + 1] + ST[2 * idx + 2];
    }
}

public class Lazy_update {
    public static void main(String[] args) {
        int A[] = { 1, 3, 5, 7, 9, 11 };;
        int n = A.length;
        LazyTree tree = new LazyTree(A);
        tree.build(A);

       // tree.print();
        
       System.out.println("Sum of values in given range = "+ tree.getSum(0, 0, n - 1, 1, 3));
 
        // Add 10 to all nodes at indexes
        // from 1 to 5.
        tree.updateRange(A,0,0,n-1, 1, 5, 10);
  
        System.out.println("sum of value in range 1-3 are: "
                           + tree.getSum(0, 0, n - 1, 1, 3));
    }
}
