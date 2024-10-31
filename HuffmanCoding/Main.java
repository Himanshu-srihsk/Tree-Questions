
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


class Node implements Comparable<Node>{
    Integer count;
    Character c;
    Node left,right;

    public Node() {
    }

    public Node(Integer count, Character c) {
        this.count = count;
        this.c = c;
    }

    @Override
    public int compareTo(Node o) {
        return this.count - o.count;
    }
    
}
// package TreeQuestions.HuffmanCoding;
public class Main{
    public static void dfs(Node root, Map<Character, String> charMap, String input){
        if(root==null){
            return;
        }
        if(root.left==null && root.right==null){
            charMap.put(root.c, input.length()>0?input:"1");
            return;
        }
        dfs(root.left, charMap, input+"0");
        dfs(root.right, charMap, input+"1");
        
    }
    public static int decode(String decdodeText,Node root, int index, StringBuilder s){
       if(root==null){
        return index;
       }
       if(root.left==null && root.right==null){
        s.append(root.c);
        return index;
       }
       if(decdodeText.charAt(index)=='0'){
        index = decode(decdodeText, root.left, index+1, s);
       }else{
        index = decode(decdodeText, root.right, index+1, s);
       }
       return  index;
    }
    public static void buildHuffmanTree(String text){
        Map<Character, Integer> mp = new HashMap<Character, Integer>();
        for(Character c: text.toCharArray()){
            mp.put(c, mp.getOrDefault(c, 0) + 1);
        }
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for(Map.Entry<Character, Integer> entry: mp.entrySet()){
            pq.add(new Node(entry.getValue(), entry.getKey()));
        }
        Node root = null;
        while(pq.size() !=1){
         Node left = pq.poll();
         Node right = pq.poll();

         Node newNode = new Node(left.count + right.count, null);
         newNode.left = left;
         newNode.right=right;
         pq.add(newNode);

        }
        root = pq.poll();
        System.out.println("Huffman Tree is built successfully.");
        Map<Character, String> charMap = new HashMap<>();
        dfs(root, charMap,"");

        for(Map.Entry<Character, String> entry: charMap.entrySet()){
           System.out.println("key is "+ entry.getKey()+ " and value is "+ entry.getValue());
        }
        String encodedText = "";
        for(Character c: text.toCharArray()){
            encodedText += charMap.get(c);
        }
        System.out.println("encoded test is "+ encodedText);
        System.out.println("decoding Huffman");
        String decodedText = "";
        Node currentNode = root;
        // for(Character c: encodedText.toCharArray()){
        //     if(c.equals('0')){
        //         currentNode = currentNode.left;
        //     }else{
        //         currentNode = currentNode.right;
        //     }
        //     if(currentNode.left==null && currentNode.right==null){
        //         decodedText += currentNode.c;
        //         currentNode = root;
        //     }
        // }
        StringBuilder s = new StringBuilder("");
        if(root.left==null  && root.right==null){
            int cnt =root.count;
           while(cnt-->0){
             s.append(root.c);
           }
        }else{
            int index =0;
            while(index < encodedText.length()){
                index = decode(encodedText, root, index, s);

            }
        }
        decodedText = s.toString();
        System.out.println("decoded test is "+ decodedText);
        
    }
    public static void main(String[] args)
    {
       // String text = "Huffman coding is a data compression algorithm.";
        String text = "aaaaaaaaaaaaaaaa";
        buildHuffmanTree(text);
    }
}