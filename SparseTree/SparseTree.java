// package TreeQuestions.SparseTree;

class Tree{
    int[][] parser;
    int n; 
    public Tree(int n) {
        this.n = n;
        int maxColumns = (int) (Math.log(n) / Math.log(2)) + 1; 
        parser = new int[n][maxColumns];
    }

    void buildSparseTable(int[] arr){
         for(int i=0;i<n;i++){
            parser[i][0]= arr[i];
         }
         for(int j=1;(1<<j)<=n;j++){
            for(int i=0;(i+(1<<j)-1)<n;i++){
                parser[i][j] = Math.min(parser[i][j-1], parser[i+(1<<(j-1))][j-1]);
            }
         }

    }
    int query(int qs,int qe){
       int j = (int)Math.log(qe-qs+1);
       return Math.min(parser[qs][j], parser[qe-(1<<j)+1][j]);
    }
    
}
public class SparseTree {
    public static void main(String[] args) {
        int a[] = { 7, 2, 3, 0, 5, 10, 3, 12, 18 }; 
        int n = a.length; 
        Tree t = new Tree(n);  
        t.buildSparseTable(a); 
          
        System.out.println(t.query(0, 4)); 
        System.out.println(t.query(4, 7)); 
        System.out.println(t.query(7, 8)); 

        System.out.println(t.query(1, 7)); 
        System.out.println(t.query(2, 7)); 
        System.out.println(t.query(4, 9)); 
    }
}
