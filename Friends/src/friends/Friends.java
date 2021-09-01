package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

/*
javac -d bin src/friends/*.java src/structures/*.java to compile
java -cp bin friends.FriendsApp graph_filename to execute
*/ 


public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		
		/** COMPLETE THIS METHOD **/

		//bfs 
		ArrayList<String> finalPath = new ArrayList<String>();
		int[] path = bfs(g,p1,p2);
		// for(int i : path){
		// 	System.out.println(i);
		// }
		if(path == null){
			return null;
		}else{
			Stack<Integer> s = new Stack<Integer>();
			int index1 = g.map.get(p1);
			int index2 = g.map.get(p2);
			for(int x = index2; x != index1; x = path[x]){
				s.push(x);
			}
			s.push(index1);

			while(!s.isEmpty()){
				finalPath.add(g.members[s.pop()].name);
			}
	}
		return finalPath;
	}
	
	private static int[] bfs(Graph g, String s, String s2){
		int index = g.map.get(s);
		int[] edgeTo = new int[g.members.length];
		boolean[] marked = new boolean[g.members.length];
		Queue<Person> q = new Queue<Person>();
		q.enqueue(g.members[index]);
		while(!q.isEmpty()){
			Person p = q.dequeue();
			int v = g.map.get(p.name);
			//System.out.println(p.name);
			for(Friend f = p.first; f!= null; f = f.next){
				if(!marked[f.fnum]){
					q.enqueue(g.members[f.fnum]);
					marked[f.fnum] = true;
					edgeTo[f.fnum] = v;
				}
			}
		}
		// System.out.println("PRINTING EDGETO");

		// for(int i : edgeTo){
		// 	System.out.println(i);
		// }

		if(!marked[g.map.get(s2)]){
			return null;
		}else{
			return edgeTo;
		}
	}

	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null or empty array list if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		
		/** COMPLETE THIS METHOD **/
		// islands, everytime break out of bfs/dfs you know its island
		
		ArrayList<ArrayList<String>> clique = new ArrayList<ArrayList<String>>();
		//Person start = g.members[0];

		boolean[] marked = new boolean[g.members.length];

		for(int i = 0; i < marked.length; i++){
			if(!marked[i]){
				ArrayList<String> partList = new ArrayList<String>();
				dfs(g,i,marked,school,partList);
				if(partList != null && !partList.isEmpty()){
					// for(String s : partList){
					// 	System.out.print(s + " ");
					// }
					// System.out.println();
					clique.add(partList);
				}
				
			}
			
		}


		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return clique;
		
	}
	private static void dfs(Graph g, int v, boolean[] marked, String school, ArrayList<String> partList){
		//ArrayList<String> c = new ArrayList<String>();
		marked[v] = true;
		//System.out.println(g.members[v].school + " " + !g.members[v].school.equals(school));
		if(g.members[v].school == null || !g.members[v].school.equals(school)){
			return;
		}else{
			if(!partList.contains(g.members[v].name)){
				partList.add(g.members[v].name);
			}
			//System.out.println(g.members[v].school + " " + g.members[v].name);
			for(Friend ptr = g.members[v].first; ptr != null; ptr = ptr.next){
				//System.out.println(g.members[ptr.fnum].name);
				if(g.members[ptr.fnum].school == null || !g.members[ptr.fnum].school.equals(school)){
					continue;
				}
				if(!marked[ptr.fnum] && g.members[ptr.fnum].school.equals(school)){
					partList.add(g.members[ptr.fnum].name);
					dfs(g,ptr.fnum,marked,school,partList);
				}
			}
		}
		
		return;
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null or empty array list if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		
		/** COMPLETE THIS METHOD **/
		
		//dfs
		//track arraylist islands, back #, dfs #, arraylist of connectors
		ArrayList<String> connectors = new ArrayList<String>();
		boolean[] marked = new boolean[g.members.length];
		

		for(int i = 0; i < marked.length; i++){
			if(!marked[i]){
				// System.out.print("in " + g.members[i].name);
				// System.out.println();
				int count = 1;
				int[] dfsnum = new int[g.members.length];
				int[] back = new int[g.members.length];
				int[] startCount = new int[1];
				startCount[0] = 0;
				//System.out.println("Start : " + g.members[i].name);
				dfsC(g, i, marked, dfsnum, back, count, connectors, startCount, i);
				// if(startCount[0] >= 2){
				// 	connectors.add(g.members[i].name);
				// }
				// if(connectors.contains(g.members[i].name)){
				// 	connectors.remove(g.members[i].name);
				// }
				
			}

		}
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return connectors;
		
	}

	private static void dfsC(Graph g, int v, boolean[] marked, int[] dfsnum, int[] back, int count, ArrayList<String> c, int[] startC, int start){
		marked[v] = true;
		dfsnum[v] = count;
		back[v] = count;
		// System.out.print(g.members[v].name + " ");
		// System.out.println();
		for(Friend f = g.members[v].first; f != null; f = f.next){
			if(!marked[f.fnum]){
				// System.out.print("friend 1 : " + g.members[f.fnum].name + " --not visited ");
				// System.out.println();
				dfsC(g, f.fnum, marked, dfsnum, back, ++count, c, startC, start);
				//System.out.println("backed up : "  + g.members[v].name);
				if(dfsnum[v] > back[f.fnum]){
					back[v] = Math.min(back[v], back[f.fnum]);
				}else{
					if(v == start){
						startC[0]++;
						//System.out.println("startC " + startC[0]);
					} 
					//System.out.println("connector " + g.members[v].name + " startC " + startC[0]);
					if((dfsnum[v]!= 1) || (dfsnum[v] == 1 && startC[0] >= 2)){
						// System.out.print(g.members[v].name + " added " + startC[0]);
						// System.out.println();
						if(!c.contains(g.members[v].name)){
							c.add(g.members[v].name);
						}
					}
					//(dfsnum[v] <= back[f.fnum]) &&  
				}
			}else if(marked[f.fnum]){
				// System.out.print("friend 1 : " + g.members[f.fnum].name + " --visited ");
				// System.out.println();
				back[v] = Math.min(back[v], dfsnum[f.fnum]);
				
			}
			
		}



		
	}
}

