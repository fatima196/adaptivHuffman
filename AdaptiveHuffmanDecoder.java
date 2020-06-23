
package adaptivhuffmandecoder;


public class AdaptiveHuffmanDecoder {

   
    private AdaptiveHuffmanTree tree;
    private final int nBits=8;
    private int NYT;
    
    public AdaptiveHuffmanDecoder(){//Start tree with NYT
        NYT = (1<<nBits);// Shift 1 to left by 8 bits --> (byte)NYT=0
        tree = new AdaptiveHuffmanTree(NYT);
    }
    

    public void decode(String s){
        
        String temp = "";
        
        for (int i = 0; i < s.length(); i++){
            temp += s.charAt(i);
            //System.out.println("temp1-->"+temp);
            Node node = tree.find(temp);
            
            //tree.printTree();
            //System.out.println("t-->"+node.getSymbol());
            
            if (i == 0 || node.getSymbol() != 0){
                
                //System.out.println("true1");

                int c;
                
                if (i==0 || node.getSymbol() == NYT){//if first time
                   // System.out.println("true2");
                    int addbit=0;
                    
                    if (i != 0) 
                        addbit= 1;

                    String character = s.substring(i + addbit, i + addbit + 8);
                    i += 7 + addbit;
                    
                    c = getCharacter(character);
                    System.out.print((char)c);
                }
                else{
                    c = node.getSymbol();
                    System.out.print((char)c);
                    
                }
                temp = "";
                tree.updateTree(c);
                
            }
        }
    }


    private int getCharacter(String s){
        int c = 0;
        int p = 1;
        for (int i = s.length() - 1 ; i >= 0; --i){
            if (s.charAt(i) == '1')
                c += p;
            p *= 2;
        }
        System.out.println(c);
        return c;
    }
    
}
