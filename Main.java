
package adaptivhuffmandecoder;

import java.util.Scanner;


public class Main {
    
   
    public static void main(String[] args) {
        AdaptiveHuffmanEncoder encoder = new AdaptiveHuffmanEncoder();
        System.out.println("Enter sequence to encode");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        System.out.println("ENCODING...");
        String str = encoder.encode(s);
        System.out.println();
        
      // System.out.println("Enter sequence to decode");
        //Scanner scn =new Scanner(System.in);
       // String str=scn.next();
        AdaptiveHuffmanDecoder decoder = new AdaptiveHuffmanDecoder();
        System.out.println("DECODING...");
        decoder.decode(str);
        System.out.println();
        
    }
    
}
