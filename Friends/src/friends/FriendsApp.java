package friends;

import java.io.*;
import java.util.*;

// Testing client for Friends
public class FriendsApp {

    public static void main (String[] args) {
	
	if ( args.length < 1 ) {
	    System.out.println("Expecting graph text file as input");
	    return;
	}

	String filename = args[0];
	try {
	    Graph g = new Graph(new Scanner(new File(filename)));

		//print(g);

	    // Update p1 and p2 to refer to people on Graph g
	    // sam and sergei are from sample graph
	    String p1 = "nick";
	    String p2 = "sam";
	    ArrayList<String> shortestChain = Friends.shortestChain(g, p1, p2);

	    // Testing Friends.shortestChain
		System.out.println("Shortest chain from " + p1 + " to " + p2);
		if(shortestChain == null){
			System.out.println("there is no path between the two");
		}else{
			System.out.println(shortestChain);
						// for ( String s : shortestChain ) {
						// 	System.out.print(s + " ");
						// 	}
						// 	System.out.println();
		}
	    
	    
		//ADD test for Friends.cliques() here
		ArrayList<ArrayList<String>> cliques = Friends.cliques(g, "rutgers");
		System.out.println("CLIQUES");
		if(cliques != null){
			System.out.println(cliques);
					// System.out.println(cliques);
					// for(ArrayList<String> x : cliques){
					// 	//System.out.println(x.size());
					// 	if(x != null){
					// 		System.out.println(x);
					// 		// for(String y : x){
					// 		// 	System.out.print(y + "\t");
					// 		// }
					// 	}
					// }
	
		}
		
	    
	    
		// ADD test for Friends.connectors() here
		ArrayList<String> c = Friends.connectors(g);
		System.out.println("CONNECTORS ");
		System.out.println(c);
	



	} catch (FileNotFoundException e) {

	    System.out.println(filename + " not found");
	}
	}
	public static void print(Graph g){
		//prints out the adjency list
		for(int i = 0; i < g.members.length; i++){
			String index = g.members[i].name;
			System.out.print(index);
			for(Friend f = g.members[i].first; f != null; f = f.next){
				System.out.print(" --> " + g.members[f.fnum].name);
			}
			System.out.println();
		}
	}
}
