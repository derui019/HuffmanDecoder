// Peter de Ruiter
// CSCI 1913
// Project 3
// Huffman Code Tree

/**
 * This class represents a binary tree of Huffman nodes and can be used to encode and decode text given
 */
public class HuffmanCodeTree {
    private HuffmanNode root;
    private HuffmanCodeBook codeBook;
    public HuffmanCodeTree(HuffmanNode root){
        this.root = root;
    }

    /**
     * This is a constructor that adds a given code book to the trees repsenting each value in the tree as a
     * differnt node, the function uses the put method to do so
     * @param codeBook the codebook we are adding to the tree
     */
    public HuffmanCodeTree(HuffmanCodeBook codeBook){
        this.codeBook = codeBook;
        root = new HuffmanNode();
        BinarySequence addingSequence;
        HuffmanCodeBook.CharSeqPair[] newPairList = codeBook.getPairLst(); // gets the array of pairs from the codebook
        for(int i = 0; i < newPairList.length; i++){
            if(newPairList[i] != null){ // each pair that is not null is not null is added to the list
                addingSequence = newPairList[i].getSeq();
                put(addingSequence, newPairList[i].getLtr());
            }
        }
    }

    /**
     * This function takse a sequence and a letter and adds it to the huffman code tree following the path of
     * zero and one branches specfied in the sequence
     * @param seq the sequence we are following
     * @param letter the letter being added to the tree
     */
    public void put(BinarySequence seq, char letter){
        HuffmanNode currentNode = root;
        for(int index = 0; index < seq.size() - 1; index++){ // runs once less then the length of the sequence
            if(!seq.get(index) && currentNode.getZero() == null){  //if the digit of the seuence is 0 and the zero node is null
                currentNode.setZero(new HuffmanNode()); //creates new node
                currentNode = currentNode.getZero(); //sets the current node to the zero node
            }
            else if(!seq.get(index) && currentNode.getZero() != null){ // if the digit is 0 and the zero node is not null
                currentNode = currentNode.getZero();
            }
            else if(seq.get(index) && currentNode.getOne() == null){ //if the digit is 1 and the next one node is null
                currentNode.setOne(new HuffmanNode()); // creates new node
                currentNode = currentNode.getOne();
            }
            else if(seq.get(index) && currentNode.getOne() != null){ // if the digit is 1 and the one node is not null
                currentNode = currentNode.getOne();
            }
            else{ // if something goes wrong prints error message
                System.out.println("error in put");
            }
        }
        if(!seq.get(seq.size() - 1)){ // if the last digit in the sequence is a 0 the next 0 node is assigned the letter
            currentNode.setZero(new HuffmanNode(letter));
        }
        else if(seq.get(seq.size() - 1)){ // if the last digit in the sequence is a 1 the next 1 node is assigned the letter
            currentNode.setOne(new HuffmanNode(letter));
        }
    }

    /**
     * This is a simple function that just calls the is valid tree function from the node class and returns true if valid
     * @return true if valid tree, else false
     */
    public boolean isValid(){
        return root.isValidTree();
    }

    /**
     * This function takes a binary sequence, decodes the sequence and returns the decoded string the sequence represents
     * @param s the sequence of bools we are decoding
     * @return the string the sequence represents
     */
    public String decode(BinarySequence s){
        StringBuilder str = new StringBuilder(); // uses the string builder method
        int index = 0;
        HuffmanNode myNode = root;
        while(index < s.size()){ // while the index is less then the size of the sequence
            if(myNode == null){ // if the node is null, ther is an error and nothing is returned
                System.out.println("error in decode"); //Peter addition
                return "";
            }
            else if(myNode.isValidNode() && myNode.isLeaf()){ // if the node is valid and the node is a leaf, the data is appended to the string
                str.append(myNode.getData());
                myNode = root;
            }
            else if(s.get(index) == false){ //if the number is a 0 the node is assigned the zero node and index is incremented
                myNode = myNode.getZero();
                index++;
            }
            else if(s.get(index) == true){ //if the number is a 1 the node is assigned the one node and index is incremented
                myNode = myNode.getOne();
                index++;
            }
            else{ // if there is some other error in the function
                System.out.println("Decode error 2"); //Peter addition
                index++;
            }
        }
        if(myNode.isLeaf()){ // if the node we end on is a leaf, the data is appended to the string
            str.append(myNode.getData());
        }
        String str2 = str.toString(); // turns the strinfbuilder into a string
        return str2;
    }
}
