
package adaptivhuffmandecoder;


public class AdaptiveHuffmanEncoder {
    
    private AdaptiveHuffmanTree tree;
    private int BITS=8;
    private int NYT;
    
    
    public AdaptiveHuffmanEncoder(){
        NYT = (1<<BITS);// Shift 1 to left by 8 
        tree = new AdaptiveHuffmanTree(NYT);
    }

    public String encode(String s){
        String ret = "";
        for (int i = 0; i < s.length(); ++i){
            if (!tree.isTransmitted(s.charAt(i))){
                ret += getCode(NYT);
                ret += getShortCode(s.charAt(i));
                sendCode(getCode(NYT));
                sendCode(getShortCode(s.charAt(i)));
            }
            else{
                ret += getCode(s.charAt(i));
                sendCode(getCode(s.charAt(i)));
            }
            tree.updateTree(s.charAt(i));

//            System.out.println("At step: " + i);
//            tree.printTree();
//            System.out.println("Finished step: " + i + '\n');
        }
        return ret;
    }

    String getCode(Node p)
    {
        if (p.getParent() == null)
            return "";
        Node parent = p.getParent();
        if (parent.getLeftChild() == p)
            return getCode(parent) + "0";
        assert(parent.getRightChild() == p);
        return getCode(parent) + "1";
    }

    String getCode(int c){
        return getCode(tree.find(c));
    }

    private void sendCode(String s){
        System.out.print(s + " ");
    }

    private String getShortCode(int x){
        String ret = new String();
        for (int i = 0; i < BITS; ++i){
            ret = (((x&1) == 1) ? "1" : "0") + ret;
            x /= 2;
        }
        return ret;
    }
    
}
