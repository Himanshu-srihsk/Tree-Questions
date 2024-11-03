class Bracket{
    int open;
    int close;
    int pairs;

    public Bracket() {
        this.open = 0;
        this.close = 0;
        this.pairs = 0;
    }
    
}
class SegmentTree{
    int n;
    Bracket[] arr;
    SegmentTree(int n){
        this.n =n;
        arr = new Bracket[4*n];
        for(int i=0;i<4*n;i++){
            arr[i] = new Bracket();
        }
    }
    int query(int qs,int qe){
        return 2 * queryUtil(0, n-1, qs, qe,0).pairs;
    }
    Bracket queryUtil(int start, int end, int qs, int qe,int idx){
        if(start> qe || qs> end){
            return new Bracket();
        }
        if(qs<=start && qe>=end){
            return arr[idx];
        }
        int mid = start + ((end - start) >> 1);
        return mergeResult(queryUtil(start, mid, qs, qe, 2*idx+1) , queryUtil(mid+1, end, qs,qe, 2* idx+2));

    }
    public void constructST(String str){
         constructUtil(0, n-1, str,0);
    }
    public Bracket constructUtil(int start,int end,String str, int idx){
        if(start> end){
            return new Bracket();
        }
        if(start==end){
            Bracket b = new Bracket();
            if(str.charAt(start)=='('){
                b.open = 1;
            }else{
                b.close = 1;
            }
            b.pairs = 0;
            arr[idx] = b;
            return arr[idx];
        }
       int mid = (start + ((end-start)>>1));
       arr[2*idx+1] = constructUtil(start, mid, str, 2* idx+1);
       arr[2*idx+2] = constructUtil(mid+1,end, str, 2* idx+2);
       arr[idx] = mergeResult(arr[2*idx+1],arr[2*idx+2]);
       return arr[idx];
    }

    
    public Bracket mergeResult(Bracket b1, Bracket b2){
        Bracket result = new Bracket();
        int minMatched = Math.min(b1.open,b2.close);
        result.open = b1.open + b2.open - minMatched;
        result.close = b1.close + b2.close - minMatched;
        result.pairs = b1.pairs+ b2.pairs + minMatched;
        return result;
    }
}
public class LongestBracketSequence{
    public static void main(String[] args) {
        String str = "())(())(())(";
        int n = str.length();
        SegmentTree tree = new SegmentTree(n);
        tree.constructST(str);
        int qs = 0;
        int qe = n - 3;
        System.out.println("Maximum Length Correct Bracket Subsequence between " + qs + " and " + qe + " = " + tree.query(qs, qe));
  
        qs = 1;
        qe = 6;
        System.out.println("Maximum Length Correct Bracket Subsequence between " + qs + " and " + qe + " = " + tree.query(qs, qe));
    }
}