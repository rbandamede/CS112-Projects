package lse;

import java.io.*;
import java.util.*;

/*
javac -d bin src/lse/*.java to compile
java -cp bin lse.LSETest to execute
 */
/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
		
		//*use scanner class 
		HashMap<String,Occurrence> ht = new HashMap<String, Occurrence>();
		Scanner doc = new Scanner(new File(docFile));
		//int count = 0;
		while(doc.hasNext()){
			//count ++;
			String word = doc.next();
			System.out.print(word + "\t");
			String keyword = getKeyword(word);
			if(keyword != null){
				if(ht.containsKey(keyword)){ // do i have to check if its the same document?  /how do i know another keyword won't map to something else 
					//this is overrriding the current value right?
					int update = ht.get(keyword).frequency + 1;
					Occurrence value = new Occurrence(docFile, update);
					//System.out.println(value);
					ht.put(keyword, value);
				}else{
					Occurrence value = new Occurrence(docFile, 1);
					//System.out.println(value);
					ht.put(keyword, value);
				}
			}
			//if(count > 20) break;
		}
		doc.close();
		System.out.println();
		System.out.println();



		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return ht;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
		for(String key : kws.keySet()){
			ArrayList<Occurrence> values = keywordsIndex.get(key); 
			if(values == null){
				values = new ArrayList<>();
			}
			values.add(kws.get(key));
			// if(key.equals("deep")){
			// 	for(Occurrence res : values){
			// 			System.out.println(res);
			// 	}
			// }
			
			//if(keywordsIndex.containsKey(key)){
			insertLastOccurrence(values);
			//}
			
			//System.out.println("merging");
			// for(Occurrence res : values){
			// 	System.out.print(res + "\t");
			// }
			// System.out.println();
			keywordsIndex.put(key, values);
			// if(key.equals("deep")){
			// 	// for(int i : index){
			// 	// 	System.out.println(i);
			// 	// }
			// 	System.out.println("deep's values");
			// 	for(Occurrence o : keywordsIndex.get(key)){
			// 		System.out.println(o);
			// 	}
			// 	System.out.println("deep's values done");

			// }

		}
		
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation(s), consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * NO OTHER CHARACTER SHOULD COUNT AS PUNCTUATION
	 * 
	 * If a word has multiple trailing punctuation characters, they must all be stripped
	 * So "word!!" will become "word", and "word?!?!" will also become "word"
	 * 
	 * See assignment description for examples
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		String keyword = word.toLowerCase();

		//checking for end punctuation;
		for(int i = keyword.length() - 1; i >=0 /*!Character.isLetter(keyword.charAt(i))*/; i--){
			if(keyword.charAt(i) == '.' || keyword.charAt(i) == ',' ||keyword.charAt(i) == '?' ||keyword.charAt(i) == ':' ||keyword.charAt(i) == ';' ||keyword.charAt(i) == '!'){
				keyword = keyword.substring(0, i);
			}
			//if(keyword.isLetter(keyword.charAt(i))
			//if character is anything but the above, break;
			else break;
		}

		//checking for characters other than letters
		for(int i = 0; i < keyword.length(); i++){
			char c = keyword.charAt(i);
            // if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z')) {
            //     return null;
			// }
			if(!Character.isLetter(c)) return null;
		}
		if((noiseWords.contains(keyword))) return null;
		else return keyword;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
	
		ArrayList<Integer> indexes = new ArrayList<>();
		int target = occs.get(occs.size() - 1).frequency;
		Occurrence targetO = occs.get(occs.size() - 1);
		// if(targetO.document.equals("WowCh1.txt") && target == 1){
			
		// }
		occs.remove(occs.size() - 1);
		//occs now has the index to be inserted removed

		int lo = 0;
		int hi = occs.size() - 1;
		int mid = 0;
		while(lo <= hi){
			mid = (lo + hi) /2;
			//System.out.print(mid + "\t");
			indexes.add(mid);
			if(occs.get(mid).frequency > target){
				lo = mid + 1;
			}else if(occs.get(mid).frequency < target ){
				hi = mid - 1;
			}else{
				lo = mid;
				break;

			}
		}
		//System.out.println(lo);
		occs.add(lo,targetO);
		// if(occs.size() < 1){
		// 	occs.add(targetO);

		// }else if(occs.size() < 2){
		// 	if(occs.get(0).frequency > target){
		// 		occs.add(targetO);
		// 	}else{
		// 		occs.add(0,targetO);
		// 	}
		// }else{
		// 	int lo = 0;
		// 	int hi = occs.size() - 1;
		// 	System.out.println(hi);
		// 	while(lo <= hi){
		// 		int mid = (lo + hi) /2;
		// 		System.out.print(mid + "\t");
		// 		indexes.add(mid);
		// 		if(occs.get(mid).frequency < target && occs.get(mid - 1).frequency > target){
		// 			occs.add(mid,targetO);
		// 			//lo = mid + 1;
		// 		}else if(occs.get(mid).frequency > target && occs.get(mid - 1).frequency > target){
		// 			lo = mid + 1;
		// 			//hi = mid - 1;
		// 		}else{
		// 			hi = mid - 1;
		// 			//occs.add(mid,targetO);

		// 		}
		// 	}
		//}
		return indexes;
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			System.out.println(docFile);
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);

			//REMOVE THIS PRINTSTATEMENT!!!!
			//System.out.println(Arrays.asList(kws)); // method 1

			mergeKeywords(kws);
		}
		sc.close();

	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. 
	 * 
	 * Note that a matching document will only appear once in the result. 
	 * 
	 * Ties in frequency values are broken in favor of the first keyword. 
	 * That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2 also with the same 
	 * frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * See assignment description for examples
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, 
	 *         returns null or empty array list.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/

		// for(String k : keywordsIndex.keySet()){
		// 	if(keywordsIndex.get(k).size() > 2){
		// 		System.out.println("TRUE");
		// 		for(Occurrence o :keywordsIndex.get(k) )
		// 		System.out.println(o);
		// 	}
		// }
		
		
		ArrayList<Occurrence> word1 = keywordsIndex.get(kw1);
		ArrayList<Occurrence> word2 = keywordsIndex.get(kw2);
		ArrayList<String> search = new ArrayList<>();
		int ptr1 = 0;
		int ptr2 = 0;

		if(word1 == null && word2 == null) return null;
		else if(word1 == null){
			while(search.size() < 5 && ptr2 < word2.size()){
				if(!search.contains(word2.get(ptr2).document)){
					search.add(word2.get(ptr2).document);
				}				
				ptr2++;
			}
		}else if (word2 == null){
			while(search.size() < 5 && ptr1 < word1.size()){
				if(!search.contains(word1.get(ptr1).document)){
					search.add(word1.get(ptr1).document);
				}	
				ptr1++;
			}
		}else{
			// System.out.println(word1 == null);
			// System.out.println(word2 == null);
			// System.out.print(word1.size()+ " ");
			// System.out.print(word2.size() + " ");
			// System.out.println();
			// for (Occurrence res : word1) {
			// 		System.out.println(res);
			// 		}
			// 	System.out.println();
			// for (Occurrence res : word2) {
			// 		System.out.println(res);
			// 	}
			// 	System.out.println();

			
			while(ptr1 < word1.size() && ptr2 < word2.size() && search.size() < 5){
				//System.out.println("ptr1: " + ptr1 + " ptr2 : " + ptr2);
				if(word2.get(ptr2).frequency > word1.get(ptr1).frequency){
					//System.out.println("ptr2HERE");
					if(!search.contains(word2.get(ptr2).document)){
						search.add(word2.get(ptr2).document);
					}
					ptr2++;
				}else if(word1.get(ptr1).frequency > word2.get(ptr2).frequency){
					//System.out.println("ptr1HERE");
					if(!search.contains(word1.get(ptr1).document)){
						search.add(word1.get(ptr1).document);
					}
					ptr1++;
				}else{
					//System.out.println("else");

					if(!search.contains(word1.get(ptr1).document)){
						search.add(word1.get(ptr1).document);
					}
					if(search.size() < 5 && !search.contains(word2.get(ptr2).document)){
						search.add(word2.get(ptr2).document);
					}
					ptr1++;
					ptr2++;

				}
				//System.out.println("size :" + search.size());

			}

			//System.out.println(search.size());


			while(search.size() < 5 && ptr1 < word1.size()){
				if(!search.contains(word1.get(ptr1).document)){
					search.add(word1.get(ptr1).document);
				}
				ptr1++;
			}
			
			while(search.size() < 5 && ptr2 < word2.size()){
				if(!search.contains(word2.get(ptr2).document)){
					search.add(word2.get(ptr2).document);
				}
				ptr2++;
			}
		}
			

		// for (String res : search) {
		// 	System.out.println(res);
		// 	}
		//System.out.println(search.size());
		return search;
	
	}
}
