
package adaptivhuffmandecoder;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;


public class AdaptiveHuffmanTree {
    
    
    private Node root;
    private int NYT; // number of symbols
    private boolean[] transmitted;
    private Map<Integer, TreeSet<Node> > block; // NodeCounter -> Sorted Tree of Nodes belonging to the same block (Same weight)


    AdaptiveHuffmanTree(int ALPHA_SIZE){
        this.NYT = ALPHA_SIZE; // number of symbols = 2^BITS
        root = new Node(ALPHA_SIZE, 0, 2 * ALPHA_SIZE - 1, null, null, null); // make sure 2 * ALPHA_SIZE - 1 is enough
        transmitted = new boolean[ALPHA_SIZE + 1]; // +1 to hold up for NYT node
        block = new HashMap<>();
        addNode(root);
    }


    /*
    Bruteforces on all nodes to find node with symbol c
     */
    private Node find(int c, Node node)
    {
        if (node == null || node.getSymbol() == c)
            return node;
        Node n = find(c, node.getLeftChild());
        if (n == null)
            n = find(c, node.getRightChild());
        return n;
    }

    public Node find(int c){
        return find(c, root);
    }

    public void updateTree(int c){
        Node node;
        if (!transmitted[c])
        {
            node = find(NYT);
            assert(node != null);
            Node newNYT = new Node(NYT, 0, node.getNumber() - 2, null, null, node);
            Node symbolNode = new Node(c, 1, node.getNumber() - 1, null, null, node);
            removeNode(node);

            node.setLeftChild(newNYT);
            node.setRightChild(symbolNode);
            node.setCounter(1);
            node.setSymbol(0);

            addNode(node);
            addNode(symbolNode);
            addNode(newNYT);
            node = node.getParent();
        }
        else{
            node = find(c);
            assert(node != null);
        }
        updateTree(node);
        transmitted[c] = true;
    }

    private void updateTree(Node p){
        if (p == null)
            return;
        checkSwap(p);
        incrementCounter(p);
        updateTree(p.getParent());
    }

    private void removeNode(Node p){
        if (block.containsKey(p.getCounter())) {
            block.get(p.getCounter()).remove(p);
        }
    }

    private void addNode(Node p){
        if (!block.containsKey(p.getCounter()))
            block.put(p.getCounter(), new TreeSet<>());
        block.get(p.getCounter()).add(p);
    }

    private void incrementCounter(Node p){
        p.setCounter(p.getCounter() + 1);
        addNode(p);
    }

    private boolean checkSwap(Node p){
        Node swapNode = null;
        TreeSet<Node> currentBlock = block.get(p.getCounter());

        for (Node v : currentBlock){
            if (v.getNumber() > p.getNumber() && v.getLeftChild() != p && v.getRightChild() != p)
            {
                if (swapNode == null || v.getNumber() > swapNode.getNumber())
                    swapNode = v;
            }
        }

        removeNode(p);
        if (swapNode != null)
        {
            removeNode(swapNode);
            p.swapParent(swapNode);
            p.swapNumbers(swapNode);
            addNode(swapNode);
            return true;
        }
        return false;
    }


    public boolean isTransmitted(int c){
        return transmitted[c];
    }

    public void printTree(){
        printTree(root, 0, 10);
    }

    private void printTree(Node p, int spaces, int COUNT){
        if (p == null)
            return;
        spaces += COUNT;
        printTree(p.getRightChild(), spaces, COUNT);
        System.out.println();
        for (int i = COUNT; i < spaces; ++i)
            System.out.print(" ");
        System.out.println(p.toString());
        printTree(p.getLeftChild(), spaces, COUNT);

    }

    private Node find(int i, Node p, String s){
        if (i == s.length())
            return p;
        if (s.charAt(i) == '0')
            return find(i + 1, p.getLeftChild(), s);
        return find(i + 1, p.getRightChild(), s);
    }

    public Node find(String s){
        return find(0, root, s);
    }
    
}
