/* Author: Spencer Gilcrest
* Date Due: 12/20/2026
* This program is my implementation of a hash table with the O(1) average runtimes
*/
import java.util.*;
import java.io.*;
import java.util.function.Consumer;

public class HashTable {

    private int tableSize = 100;
    private int count = 0;
    private Payload[] table;

    /* key-value pair stored in table
    * points to next bucket for chaining
     */
    private static class Payload {
        String key;
        Object value;
        Payload next;

        Payload(String key, Object value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    public HashTable(){
        table = new Payload[tableSize];
    }

    //precondition: key is string u want to hash
    //postcondition: gives u the value to store in table
    private int getIndex(String key){
        return Math.abs(key.hashCode()) % tableSize;
    }

    //this makes the table twice as big and rehashes the original payloads
    private void resize(){
        tableSize *= 2;
        Payload[] newTable = new Payload[tableSize];

        for (int i = 0; i < tableSize/2; i++){
            Payload current = table[i];
            while (current != null){
                int newIndex = getIndex(current.key);
                Payload next = current.next;
                current.next = newTable[newIndex];
                newTable[newIndex] = current;
                current = next;
            }
        }
        table = newTable;
    }
    
    //precondition: key is the string key value is the value to store
    //post condition: puts payload into hash table, resizes if 2/3 full, and i chose chaining 
    public void put(String key, Object value) {
        int index = getIndex(key);
        Payload current = table[index];

        while (current != null){
            if (current.key.equals(key)){
                current.value = value;
                return;
            }
            current = current.next;
        }

        Payload newPayload = new Payload(key, value);
        newPayload.next = table[index];
        table[index] = newPayload;
        count++;

        if (count > (tableSize * 2) / 3){
            resize();
        }

    }
    
    //precondition: key is the key ur searching for
    //postcondition: returns value as string or null if not in table
    public String get(String key) {
        int index = getIndex(key);
        Payload current = table[index];

        while (current != null){
            if (current.key.equals(key)){
                return current.value.toString();
            }
            current = current.next;
        }
        return null;
    }

    //precondition: key is the key to remove from table
    //postcondition: removes key's payload from table and returns value as string or null if not found
    public String remove(String key){
        int index = getIndex(key);
        Payload current = table [index];
        Payload prev = null;

        while (current != null){
            if (current.key.equals(key)){
                if (prev == null){
                    table[index] = current.next;
                }
                else{
                    prev.next = current.next;
                }
                count--;
                return current.value.toString();
            }
            prev = current;
            current = current.next;
        }

        return null;
    }

    public Iterator keys() {
        return new myItr();
    }

    private class myItr implements Iterator<String>{
        private int bucket = 0;
        private Payload current = null;

        public myItr(){
            goToBucket();
        }

        //finds not empty bucket
        private void goToBucket(){
            while (bucket < tableSize && table[bucket] == null){
                bucket++;
            }
            if (bucket < tableSize){
                current = table[bucket];
            }
        }

        //true if theres more  keys
        public boolean hasNext(){
            return current != null;
        }

        public String next(){
            String key = current.key;
            current = current.next;
            if (current == null){
                bucket++;
                goToBucket();
            }
            return key;
        }

        public void remove() { 
            throw new UnsupportedOperationException();
        }

        public void forEachRemaining(Consumer<? super String> action){
            throw new UnsupportedOperationException();
        }

        
    }

    //prints all the pairs in hash table
    public void print(){
        Iterator itr = keys();
        while(itr.hasNext()){
            String key = itr.next().toString();
            System.out.println(key + ": " + get(key));
        }
    }




    //rest not my job

	/**
	 * Loads this HashTable from a file named "Lookup.dat".
	 */
    public void load() {
        FileReader fileReader;
        BufferedReader bufferedReader = null;
        
        // Open the file for reading
        try {
            File f = new File(System.getProperty("user.home"), "Lookup.dat");
            fileReader = new FileReader(f);
            bufferedReader = new BufferedReader(fileReader);
        }
        catch (FileNotFoundException e) {
            System.err.println("Cannot find input file \"Lookup.dat\"");
        }
        
        // Read the file contents and save in the HashTable
        try {
            while (true) {
                String key = bufferedReader.readLine();
                if (key == null) return;
                String value = bufferedReader.readLine();
                if (value == null) {
                    System.out.println("Error in input file");
                    System.exit(1);
                }
                String blankLine = bufferedReader.readLine();
                if (!"".equals(blankLine)) {
                    System.out.println("Error in input file");
                    System.exit(1);
                }
                put(key, value);
            }
        }
        catch (IOException e) {
            e.printStackTrace(System.out);
        }
        
        // Close the file when we're done
        try {
            bufferedReader.close( );
        }
        catch(IOException e) {
            e.printStackTrace(System.out);
        }
    }

	/**
	 * Saves this HashTable onto a file named "Lookup.dat".
	 */
	public void save() {
        FileOutputStream stream;
        PrintWriter printWriter = null;
        Iterator iterator;
        
        // Open the file for writing
        try {
            File f = new File(System.getProperty("user.home"), "Lookup.dat");
            stream = new FileOutputStream(f);
            printWriter = new PrintWriter(stream);
        }
        catch (Exception e) {
            System.err.println("Cannot use output file \"Lookup.dat\"");
        }
       
        // Write the contents of this HashTable to the file
        iterator = keys();
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            printWriter.println(key);
            String value = (String)get(key);
            value = removeNewlines(value);
            printWriter.println(value);
            printWriter.println();
        }
       
        // Close the file when we're done
        printWriter.close( );
    }
    
    /**
     * Replaces all line separator characters (which vary from one platform
     * to the next) with spaces.
     * 
     * @param value The input string, possibly containing line separators.
     * @return The input string with line separators replaced by spaces.
     */
    private String removeNewlines(String value) {
        return value.replaceAll("\r|\n", " ");
    }
}


