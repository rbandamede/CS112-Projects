package trie;

import java.util.ArrayList;

/*
javac -d bin src/trie/*.java to compile
java -cp bin trie.TrieApp to execute
 */

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/
		
		// 1. First make the empty root node and attach the first node to it 
		TrieNode root = new TrieNode(null,null,null);
		short endIndex = (short) (allWords[0].length() - 1);
		short firstIndex = 0;
		Indexes first = new Indexes(0,firstIndex,endIndex);
		root.firstChild = new TrieNode(first,null,null);

		//2. Call the build helper function that actually builds the tree using an iterative algorithm
		// if the array has more than one word

		if(allWords.length > 1){
			itBuild(root.firstChild, allWords);
		}
		return root;
	}

	private static void itBuild(TrieNode root, String[] allWords){
		//1. outer loop that loops through the array containing words 
		for(int i = 1; i < allWords.length; i++){
			TrieNode start = root;

			//2. keeps track of the word that needs to be inserted, with the end and start index
			String newWord = allWords[i];
			short newEnd = (short) (newWord.length() - 1); 
			short newStart = 0;
			//System.out.println(i + " ----------");

			//3. inner loop that traverses the trie and adds words case by case
			while(start != null){
				//4. gets the word that is at the node in its substring form 
				String word = allWords[start.substr.wordIndex].substring(start.substr.startIndex, start.substr.endIndex + 1);
				//5. commonEnd holds the index of the last character the word that needs to be inserted and the word at the node share
				short commonEnd = commonPrefix(word, newWord);
				//6. cuts the characters/part of the word that needs to be inserted that it shares with other prefixes on the tree
				String test = newWord.substring(commonEnd + 1);
				//System.out.println(word + "\t" + commonEnd);

				//Case where the newWord and word at current node share characters/prefixes
				if(commonEnd != -1){

					//updates the starting index of the new word that is being inserted according to the prefixes the tree contains
					newStart += commonEnd + 1;

					//Case 1 : where the prefix of the newWord is already on the tree, so we move into the child of that prefix
					if(commonEnd == word.length() - 1){    // || test.equals(word)
						start = start.firstChild;
						newWord = test;
						// System.out.print(i + "\t");
						// System.out.print(newWord + "\t");
						// System.out.println(start);

						//Case 2 : where prefix doesn't already exist on the tree
					}else {     
						//there are partial common letters with the newWord and the current words so we make a new prefix
						
						// make a childNode that holds the current word of the tree but only the parts that are not shared by the newWord
						Indexes childI = new Indexes(start.substr.wordIndex, (short) (commonEnd + start.substr.startIndex + 1), (start.substr.endIndex));
						// make a new node that holds the newWord that is being inserted with the shared parts cut off which will become a sibling node
						Indexes siblingI = new Indexes(i, newStart, newEnd);
						//System.out.println("child: " + childI + "sibling: " + siblingI);

						// updates the prefix to the new prefix found
						start.substr.endIndex = (short) (commonEnd + start.substr.startIndex);

						//connects all the new nodes with the current node/new prefix
						TrieNode child = new TrieNode(childI, start.firstChild, null);
						TrieNode sibling = new TrieNode(siblingI, null, null);
						start.firstChild = child;
						child.sibling = sibling;
						//break out of the loop traversing the tree after the newword has been inserted, move onto the next word
						break;
					}
					// Case where the new word doesn't share any common prefixes/characters with existing tree nodes
				}else {   //if(commonEnd == -1){

					 //Case 3: the newWord doesn't have common prefix with current word so make it a sibling if the current node doesn't have any other siblings
					if(start.sibling == null){
						Indexes sibling = new Indexes(i, newStart, newEnd);
						start.sibling = new TrieNode(sibling, null, null);
						//break out of the loop traversing the tree after the newword has been inserted, move onto the next word
						break;

					// Case 4: the newWord doesn't have any common prefixes with the current word so check its siblings
					}else{
						start = start.sibling;
					}
				}
		
			}
		}
	}

	private static short commonPrefix(String word, String word2){
		//returns the endIndex of the last common element in the two words, if it is -1 means they have nothing common
		short endIndex = -1;
		short i = 0;
		while(i < word.length() && i < word2.length()){
			if(word.charAt(i) == word2.charAt(i)){
				endIndex = i;
			}else{
				break;
			}
			i++;
		}
		return endIndex;
	}
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {

		
		// makes an arraylist to return, creates a pointer and sets it to the first word of the tree and calls
		// a recursive helper method that performs the completion list task
		ArrayList<TrieNode> list = new ArrayList<TrieNode>();
		TrieNode start = root.firstChild;
		recLeafAdd(list, start, prefix, allWords);


		// if(root == null){
		// 	return null; 
		// }
		// while(start != null){
		// 	if(start.substr == null){
		// 		start = start.firstChild;
		// 	}
		// 	String word = allWords[start.substr.wordIndex];
		// 	String wordSub = word.substring(0, start.substr.endIndex+1);
		// 	System.out.println(start + " " + word);
		// 	// short check = commonPrefix(word, prefix);

		// 	if(word.startsWith(prefix)|| prefix.startsWith(wordSub)){
		// 		if(start.firstChild == null){  //&& check == prefix.length() -1 
		// 			list.add(start);
		// 			start = start.sibling;
		// 		}else{
		// 			list.addAll(completionList(start.firstChild, allWords, prefix));
		// 			start = start.sibling;
		// 		}
				
		// 	}else{
		// 		start = start.sibling;
		// 	}

		// }
	
		// according to the assignment's instructions, if the arraylist is empty it returns null, otherwise return arraylist
		if(list.size() == 0){
			return null;
		}else{
			return list;
		}
		//ITERATIVE SOLUTION
		//while(start != null){
			// String word = allWords[start.substr.wordIndex].substring(0, start.substr.endIndex+1);
			// System.out.println(word);
			// if(commonPrefix(prefix, word) > -1){
			// 	start = start.firstChild;
			// 	String word2 = allWords[start.substr.wordIndex].substring(0, start.substr.endIndex+1);
			// 	System.out.println(word);

			// 	if(commonPrefix(prefix, word2) > -1){
			// 	//going through all the siblings so I cover all the leafs at the bottom
			// 	while(start != null){
			// 		leafAdd(list, start);
			// 		start = start.sibling;
			// 	}
			// }


				//getting to the leaf nodes of first sibling
				// while(start.firstChild != null){
				// 	start = start.firstChild;

				// }
				// //after breaking out of this loop it will be in the last level
				// list.add(start);
				// start = start.sibling;
				// while(start != null){
				// 	list.add(start);
				// 	start = start.sibling;
				// }
				//loop through its children and siblings
		// 	}else{
		// 		start = start.sibling;
		// 	}
		// }

		
	}

	private static void recLeafAdd(ArrayList<TrieNode> head, TrieNode ptr,String prefix, String[]allWords){
		TrieNode start = ptr;

		// overall base case : exits the method if the node being examined is null
		if(start == null){
			return;
		}

		// gets the current word, putting together the prefixes that precede it (starts at 0 and ends at the endIndex) 
		// gets the index of the last character shared
		String word = allWords[start.substr.wordIndex].substring(0, start.substr.endIndex+1);

		//unneccesarry variable if we use the startsWith condition in the Case 1 if statment
		short check = commonPrefix(word, prefix);

		System.out.println(start + " " + word);
		//System.out.println(word + " " + "check: " + check);

		//Case 1 :  if the prefix being searched for either starts with the current word or the current word starts with the prefix

		//if((word.startsWith(prefix) || prefix.startsWith(word))){  
		if(check == prefix.length() - 1 || check == start.substr.endIndex){
			//if the next node underneath the current one is a leaf node then we add it to the list becasue it contains the prefix 
			if(start.firstChild == null ){ 
				head.add(start);

				// if the current node that matches the prefix to an extent has a sibling, check if the sibling also matches the prefix before moving to the sibling
				if(start.sibling != null){
					String temp = allWords[start.sibling.substr.wordIndex].substring(0, start.sibling.substr.endIndex+1);
					short secondCheck = commonPrefix(temp, prefix);
					if(secondCheck == prefix.length() - 1 || secondCheck == start.substr.endIndex){
					//if(temp.startsWith(prefix)){
						recLeafAdd(head, start.sibling, prefix, allWords);
	
					}
				}

			// if the current nodes child is not null, meaning not a leaf node (so it is still a prefix and we cannot add it to the list), then move to the child
			}else{                    
				recLeafAdd(head, start.firstChild, prefix, allWords);   

				// beofre moving on to the current nodes sibling check if it even matches the prefix, only move to the sibling if it does
				if(start.sibling != null){
					String temp = allWords[start.sibling.substr.wordIndex].substring(0, start.sibling.substr.endIndex+1);
					short secondCheck = commonPrefix(temp, prefix);
					if(secondCheck == prefix.length() - 1 || secondCheck == start.substr.endIndex){
					//if(temp.startsWith(prefix)){
						recLeafAdd(head, start.sibling, prefix, allWords);
	
					}
				}
				
			}

		//Case 2: the prefix doesn't match the current node at all so we check its sibling
		}else{
			recLeafAdd(head, start.sibling, prefix, allWords);   
		}
	}


	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
