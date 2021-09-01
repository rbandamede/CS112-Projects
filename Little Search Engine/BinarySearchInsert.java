import java.util.ArrayList;

public class BinarySearchInsert{
    // ArrayList<Occurence> o;

    // public void add(ArrayList<Occurrence> x){
    //     for(Occurrence s : x){
    //         o.add(s);
    //     }
    // }

    // private class Occurrence {
	
    //     /**
    //      * Document in which a keyword occurs.
    //      */
    //     String document;
        
    //     /**
    //      * The frequency (number of times) the keyword occurs in the above document.
    //      */
    //     int frequency;
        
    //     /**
    //      * Initializes this occurrence with the given document,frequency pair.
    //      * 
    //      * @param doc Document name
    //      * @param freq Frequency
    //      */
    //     public Occurrence(String doc, int freq) {
    //         document = doc;
    //         frequency = freq;
    //     }
        
    //     /* (non-Javadoc)
    //      * @see java.lang.Object#toString()
    //      */
    //     public String toString() {
    //         return "(" + document + "," + frequency + ")";
    //     }
    // }

    public static ArrayList<Integer> insertLastOccurrence(ArrayList<Integer> occs) {
            /** COMPLETE THIS METHOD **/
        
            ArrayList<Integer> indexes = new ArrayList<>();
            int target = occs.get(occs.size() - 1);
            //Occurrence targetO = occs.get(occs.size() - 1);
            occs.remove(occs.size() - 1);
            //occs now has the index to be inserted removed
            int lo = 0;
            int hi = occs.size() - 1;
            int mid = 0;
           
            while(lo <= hi){
                mid = (lo + hi) /2;
                // System.out.print("low: " + lo + " high: " + hi + " mid : " + mid);
                // System.out.println();

                indexes.add(mid);
                if(occs.get(mid) < target){
                    // occs.add(mid,target);
                    // break;
                    //lo = mid + 1;
                    hi = mid - 1;
                }else if(occs.get(mid) > target){
                    lo = mid + 1;
                    //hi = mid - 1;
                }else{
                    lo = mid;
                    break;
                    //occs.add(mid,targetO);

                }
            }
            // System.out.print("low: " + lo + " high: " + hi + " mid : " + mid);
            // System.out.println();

            occs.add(lo,target);
            //System.out.println();

            return indexes;
        }

        public static void main(String[] args) {
            //Test tt = new Test();
            int[] tt = {82,76,71,71,70,65,61,56,54,51,48,45,41,36,34,30,25,20,20,18,17,17,14,12,17};
            ArrayList<Integer> t = new ArrayList<>();
            for (int i : tt){
                t.add(i);
            }
            //new ArrayList<>();
            // t.add(12);
            // t.add(8);
            // t.add(7);
            // t.add(5);
            // t.add(3);
            // t.add(2);
            // t.add(8);

            //tt.add(t);
            ArrayList<Integer>  x = insertLastOccurrence(t);
            System.out.println("Printing");
            for(int i : x){
                System.out.print(i + " ");
            }
            System.out.println();
            for(int i : t){
                System.out.print(i + " ");
            }
            System.out.println();


        }

}