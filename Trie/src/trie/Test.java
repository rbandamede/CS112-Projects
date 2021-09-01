package trie;

public class Test {
    public static void main(String[] args) {
        // build Trie
        String[] allWords = {"burrito, beans, burger, soda"};
		TrieNode root = Trie.buildTrie(allWords);
    }
}
