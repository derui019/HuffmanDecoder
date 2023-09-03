// Peter de Ruiter
// CSCI 1913
// Project 3
// Huffman Code Book

/**
 * This class represents a code book that has pairs of specified letter/binary sequences and represents them in an
 * organized array that be searched through much easier give the charter we are looking for.
 */
public class HuffmanCodeBook {
    private CharSeqPair[] pairLst;
    private int counter;
    private int length;

    /**
     * This is the default constructor, sets the length of the array to 1 and the counter to 0;
     */
    public HuffmanCodeBook(){
        pairLst = new CharSeqPair[1];
        length = 1;
        counter = 0;
    }
    /**
     * This function adds a creates a CharSeqPair and adds it to the array using my own form of hashing following the
     * ASCII/Unicode code values of the charcters as keys
     * @param c the charcter being added to the list
     * @param seq the sequence being added to the list
     */
    public void addSequence(char c, BinarySequence seq){
        CharSeqPair addPair = new CharSeqPair(c, seq); // creates new pair of a char and a seq
        put(addPair); // adds the pair using the put helper function I created.
        counter++;
    }

    /**
     * This function takes a charter uses a helper function I created to gets a pair or null, if the letter in the
     * pair is the letter we are looking for returns true
     * @param letter the letter we are seraching for in the letter list
     * @return returns true if the letter is in the list, false if not
     */
    public boolean contains(char letter){
        if(getPair(letter) == null){ // if the pair is null
            return false;
        }
        else if(getPair(letter).ltr == letter){ // if the letter in the pair assoicated with the key is the letter
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This function checks if all of the letters in a string are contained in the letter list using the contains
     * function, if all of the letters are in the list true is returned, else false
     * @param letters the string of letters we are checking for
     * @return true if all of the letters in the string are in the list, else false
     */
    public boolean containsAll(String letters){
        char letter1;
        for (int i = 0; i < letters.length(); i++){ // checks all of the letters
            letter1 = letters.charAt(i); //asigns the charcter in the string at the index to letter1
            if(!contains(letter1)){ //uses the contains function to check if the letter is in the list
                return false; //if the letter is not in the list
            }
        }
        return true; // if all of the letters are in the list
    }

    /**
     * This function takes a charcter and returns the seqeunce corresponding to that letter, it uses a helper function
     * to find the index of the character
     * @param c the charter we are finding the corresponding seqeunce to
     * @return the corresponding sequence is returned, if the sequence is not the list null is returned
     */
    public BinarySequence getSequence(char c){
        CharSeqPair pair1 = getPair(c); //gets the pair assoicaited with c
        if(pair1 == null){ // if the pair is null
            return null;
        }
        else if(pair1.getLtr() == c){ //if the letter assciaited with the pair we are looking for the corresponding sequence is returned
            return pair1.getSeq();
        }
        else{
            return null;
        }
    }


    /**
     * This function takes a string of letters and returns a binary sequence that is each of the binary sequences of
     * the letters added together
     * @param s the string of letters that we are creating the binary sequence of
     * @return the binary sequence of the string of letters, if not all letters are in the list null is returned
     */
    public BinarySequence encode(String s){
        if(!containsAll(s) || s.length() < 1){ // if not all of the letters have assoiated sequence in the code book returns null
            if(s.length() == 0){ // if the length of the string is 0 and empty binary sequence is returned
                BinarySequence empty = new BinarySequence();
                return empty;
            }
            return null;
        }
        String bits1 = getSequence(s.charAt(0)).toString(); //creates a bit intialized to the first charcter
        BinarySequence returnSeq = new BinarySequence(bits1); //the new sequence is intialized to the sequence of the first number of the string
        for(int i = 1; i < s.length(); i++){ //loops through all of the letters starting after the first index
            returnSeq.append(getSequence(s.charAt(i))); //appends the corresponding sequence to the new sequence
        }
        return returnSeq;
    }
    /**
     * This is a private helper function i created to get the index of the key, the index takes the unicode value
     * of the charcter and returns that number mod the length of the list
     * @param c the chacrter we are finding the index of
     * @return the index of where the pair goes or -1 if length is 0
     */
    private int getIndex(char c){
        int letterKey = (int) c; // gets the unicode value of c
        if(length == 0){ // if the length is 0
            return -1;
        }
        else{
            return Math.abs(letterKey % length); // the unicode value of c is returned mod the current length of the array
        }
    }

    /**
     * This is a helper function I created to add a pair to the array correctly, as well as resize the list when the
     * list is not big enough
     * @param pairToAdd this is the pair we are adding to out array
     */
    private void put(CharSeqPair pairToAdd){
        if(length == 0){ // if the length is 0 the array is resized
            resize();
        }
        int index = getIndex(pairToAdd.getLtr());
        if(index == -1 || length == 0){ // if the pair does not have a letter the list is resized
            resize();
            put(pairToAdd);
        }
        else if(pairLst[index] == null){ // if the index where the pair needs to go is empty, the pair is added at that index
            pairLst[index] = pairToAdd;
        }
        else{ // if the pair is valid, the list is resized until there is space for the charcter
            resize();
            put(pairToAdd);
        }
    }

    /**
     * This is a helper function I created to get the char/seq pair given the letter whe are looking for
     * @param ltr the letter we are getting the pair of
     * @return the pair corresponding to the ltr
     */
    private CharSeqPair getPair(char ltr){
        int index = getIndex(ltr); // the index corresponding to that letter
        if(index == -1){ // if the letter is null
            return null;
        }
        else if(pairLst[index] == null){ // if the pair of letters is null return null
            return null;
        }
        else{
            return pairLst[index]; // returns the pair at that index
        }
    }

    /**
     * This is a helper function I created to resize the pair list, if the function is called the length is multiplied
     * by two and the values stored in the list are moved to there new corresponding locations
     */
    private void resize(){
        if(length == 0){ // if the length is 0 the length is set to one
            length = 1;
            CharSeqPair[] newPairLst = new CharSeqPair[length];
            pairLst = newPairLst;
        }
        else{
            length = 2 * length;
            CharSeqPair[] newTrioLst = new CharSeqPair[length]; //create new list with twice the size
            for(int i = 0; i < pairLst.length; i++){ // runs through the length of the old list
                if(pairLst[i] != null){ // if the pairr is not null
                    int newIndex = getIndex(pairLst[i].getLtr());
                    newTrioLst[newIndex] = pairLst[i]; // the pair is moved to its new index in the new list
                }
            }
            pairLst = newTrioLst; // the pair list is assigned the new list
        }
    }
    public CharSeqPair[] getPairLst() {
        return pairLst;
    }

    public int getLength() {
        return length;
    }

    public int getCounter() {
        return counter;
    }

    /**
     * This is a simple internal class that I created to create letter sequence pairs to use in the array
     */
    public class CharSeqPair {
        private char ltr;
        private BinarySequence seq;

        /**
         * This is the constructor for CharSeqPair, it sets the letter and the sequence of the pair to the
         * specified values
         * @param ltr the letter in the pair
         * @param seq the sequence in the pair
         */
        public CharSeqPair(char ltr, BinarySequence seq){
            this.ltr = ltr;
            this.seq = seq;
        }
        public char getLtr() {
            return ltr;
        }
        public BinarySequence getSeq() {
            return seq;
        }

    }



}
