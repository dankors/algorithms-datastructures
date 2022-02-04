import java.io.*;
import java.util.ArrayList;

public class main {     

    public static void main(String[] args) {
    //TODO:build the hash table and insert keys using the insertKeyArray function.
    	int[] keys = {0, 1, 2, 3, 4, 5}; //, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    	
    	//CodePost tests
    	Chaining chain = new Chaining(10, 0, -1);    	
    	System.out.println("Chaining tests:");
    	System.out.println("\t k = 1, Expected output: 30, Output: " + chain.chain(1));
    	System.out.println("\t k = 4, Expected output: 25, Output: " + chain.chain(4));
    	System.out.println("\t k = 8, Expected output: 19, Output: " + chain.chain(8) + "\n");
    	
    	
    	Open_Addressing probe = new Open_Addressing(10, 0, -1);
    	System.out.println("Open Addressing tests:");
    	System.out.println("\t k = 1, i = 0, Expected output: 30, Output: " + probe.probe(1, 0));
    	System.out.println("\t k = 1, i = 1, Expected output: 31, Output: " + probe.probe(1, 1));
    	System.out.println("\t k = 1, i = 3, Expected output:  1, Output: " + probe.probe(1, 3));
    	System.out.println();
    	
    	//Additional tests
    	Chaining chain2 = new Chaining(5, 0, -1);
    	Open_Addressing probe2 = new Open_Addressing(5, 0, -1);
    	
    	chain2.insertKeyArray(keys);
    	probe2.insertKeyArray(keys);
    	
    	System.out.println("Full Open Addressing table:");
    	for (int i = 0; i < probe2.Table.length; i++) {
    		System.out.print(probe2.Table[i] + " ");
    	}
    	
    	System.out.println("\n\n Hash code of 16: " + probe2.probe(16, 0));
    	System.out.println("Collisions for adding 16: " + probe2.insertKey(16));
    	
    	System.out.println("\nOpen Addressing table with 16 added:");
    	for (int i = 0; i < probe2.Table.length; i++) {
    		System.out.print(probe2.Table[i] + " ");
    	}
    	
    	System.out.println("\n\n Hash code of 3: " + probe2.probe(3, 0));    
    	System.out.println("Collisions from removing 3: " + probe2.removeKey(3));
    	
    	System.out.println("\n Hash code of 10: " + probe2.probe(10, 0));    
    	System.out.println("Collisions from removing 10 (DNE): " + probe2.removeKey(10));
    	
    	System.out.println("\nOpen Addressing table with 3 and 10 removed:");
    	for (int i = 0; i < probe2.Table.length; i++) {
    		System.out.print(probe2.Table[i] + " ");
    	}
    	
    	System.out.println("\n\n Full Chaining table:");
    	
    	for (int i = 0; i < chain2.Table.size(); i++) {
    		System.out.print(chain2.Table.get(i) + " ");
    	}
    	
    	
    }
    
}