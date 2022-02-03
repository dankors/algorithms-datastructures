import java.io.*;
import java.util.*;

public class Open_Addressing {
	
     public int m; // number of SLOTS AVAILABLE
     public int A; // the default random number
     int w;
     int r;
     public int[] Table;

     protected Open_Addressing(int w, int seed, int A) {

         this.w = w;
         this.r = (int) (w-1)/2 +1;
         this.m = power2(r);
         if (A==-1){
            this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
         }
        else{
            this.A = A;
        }
         this.Table = new int[m];
         for (int i =0; i<m; i++) {
             Table[i] = -1;
         }
         
     }
     
     /** Calculate 2^w*/
     public static int power2(int w) {
         return (int) Math.pow(2, w);
     }
     
     public static int generateRandom(int min, int max, int seed) {     
         Random generator = new Random(); 
                 if(seed>=0){
                    generator.setSeed(seed);
                 }
         int i = generator.nextInt(max-min-1);
         return i+min+1;
     }
     
     
        /**Implements the hash function g(k)*/
        public int probe(int key, int i) {
        	int h = ((A * key) % power2(w)) >> (w - r);
        	int g = (h + i) % power2(r);
        	return g;
        }
     
     
        /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int insertKey(int key){
        	//Initialize i for probe() function
        	int i = 0;
            int slot = probe(key, i);
            
            //Traverse table until empty slot is found
            while (Table[slot] > -1) {
        		i++;
        		//if i exceeds size of table, return number of collisions (key not inserted)
        		if (i == m) {return i;} 
            	slot = probe(key, i);
        	}
            
            //Insert key into first empty slot and return # collisions
            Table[slot] = key;
            return i;
        }
        
        
        /**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
        public int insertKeyArray (int[] keyArray){
            int collision = 0;
            for (int key: keyArray) {
                collision += insertKey(key);
            }
            return collision;
        }
        
            
         /**Removes key k from hash table if it exists. Returns the number of collisions encountered*/
        public int removeKey(int key){
        	int i = 0;
            int slot = probe(key, i);
         
            //Traverse table until either (1) key found, (2) all slots checked, or (3) Table[slot] == -1
            while (Table[slot] != -1) {
            	
            	if (Table[slot] == key) {    //(1) Set slot of removed key to -2 and return # collisions
            		Table[slot] = -2;
            		return i;
            	
            	} else {
            		i++;
            		if (i == m) {return i;}  //(2) If i exceeds size of table, all slots have been checked
                	slot = probe(key, i);
            	}
        	}
            
            //(3) If Table[slot] == -1, key is definitely not in array (would have been added to that slot)
            //Return the number of slots visited
            return i + 1;
        }
}