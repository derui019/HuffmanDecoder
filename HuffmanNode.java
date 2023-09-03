// Peter de Ruiter
// CSCI 1913
// Project 3
// Huffman Node

/**
 * This class represents one node in huffman tree, each node has one node corresponfing to zero and
 * the other corresponding to and stores the data of a cahrcter
 */
public class HuffmanNode {
    HuffmanNode zero;
    HuffmanNode one;
    Character data;

    /**
     * This is the default constructor that sets zero, one and data all to null
     */
    public HuffmanNode(){
        zero = null;
        one = null;
        data = null;
    }
    /**
     * This is a constructor that assigns the node a left and a right node and sets the data to null.
     * @param zero the node the zero node is being assigned
     * @param one the node the ond node is being assigned
     */
    public HuffmanNode(HuffmanNode zero, HuffmanNode one){
        this.zero = zero;
        this.one = one;
        data = null;
    }

    /**
     * This is a constructor that take a character to store in the nodes data and set both the one and zero nodes to null
     * @param data the data being assinged to the nodes data.
     */
    public HuffmanNode(char data){
        this.data = data;
        zero = null;
        one = null;
    }

    /**
     * This function returns true if bothe the zero and one nodes are assigned null, else returns false
     * @return if zero and one nodes are null returns true, else returns false
     */
    public boolean isLeaf(){
        if(zero == null && one == null && data != null){ // if both children are null and the node has data it is a leaf
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This function checks if the node is valid, for a node to be valid the node is a leaf or is not a leaf and
     * has data stored
     * @return true if the node is valid, else false
     */
    public boolean isValidNode(){
        if((isLeaf()) || (!isLeaf() && data == null && zero != null && one != null)){ // if it is a leaf or not a leaf and data is null
            return true;
        }
        return false;
    }

    /**
     * This function checks if the tree is valid starting at the current node, the function uses a recursive helper
     * function created to return if the tree is valid starting at a given node
     * @return true if the tree is valid, else false
     */
    public boolean isValidTree(){
        if(isLeaf()){ // if the node is a leaf and the node is valid return true
            return true;
        }
        else if (!isLeaf() && isValidNode()){ // if the node is not a leaf and is valid
            return isValidTreeHelper(zero) && isValidTreeHelper(one); // if both are true returns true
        }
        else{
            return false;
        }
    }

    /**
     * This function is a helper function I created to check if the tree is valid recursivley
     * @param node the root of the node tree being check if it is valid
     * @return true if the tree is valid
     */
    private boolean isValidTreeHelper(HuffmanNode node){
        if(node.isLeaf()){ // if the node is a leaf and a valid node return true
            return true;
        }
        else if(!node.isValidNode()){ // if the node is not valid return false
            return false;
        }
        return isValidTreeHelper(node.getZero()) && isValidTreeHelper(node.getOne()); // recursivley checks if each node is valid
    }

    public HuffmanNode getZero() {
        return zero;
    }

    public void setZero(HuffmanNode zero) {
        this.zero = zero;
    }

    public HuffmanNode getOne() {
        return one;
    }

    public void setOne(HuffmanNode one) {
        this.one = one;
    }

    public Character getData() {
        return data;
    }

    public void setData(Character data) {
        this.data = data;
    }
}
