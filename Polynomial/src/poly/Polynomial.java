package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	private static Node addEnd(Node start, Node ptr, Node prev){
		if(start == null){
			start = ptr;
			prev = ptr;
		}else{
			prev.next = ptr;
			prev = ptr;
		}
		return prev;
	}

	private static Node copy(Node head){
		Node ptr = head;
		while(ptr!= head){

		}
		return head;
	}
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node ptr1 = poly1;
		Node ptr2 = poly2;
		Node sumList = null;
		Node ptr3 = sumList;
		Node ptr3prev = null;
		if(poly1 == null){
			while(ptr2 != null){
				//copying all the elements from the other Node to the other
				ptr3 = new Node(ptr2.term.coeff,ptr2.term.degree,null);
				ptr3prev = addEnd(sumList, ptr3, ptr3prev);
					// if(sumList == null){
					// 	sumList = ptr3;
					// 	ptr3prev = ptr3;
					// }else{
					// 	ptr3prev.next = ptr3;
					// 	ptr3prev = ptr3;
					// }
				ptr2 = ptr2.next;
			}
		}else if(poly2 == null){
			while(ptr1 != null){
				ptr3 = new Node(ptr1.term.coeff,ptr1.term.degree,null);
				ptr3prev = addEnd(sumList, ptr3, ptr3prev);
					// if(sumList == null){
					// 	sumList = ptr3;
					// 	ptr3prev = ptr3;
					// }else{
					// 	ptr3prev.next = ptr3;
					// 	ptr3prev = ptr3;
					// }
					// ptr1 = ptr1.next;
			}
		}else{
		while(ptr1 != null && ptr2 != null){
			if(ptr1.term.degree == ptr2.term.degree){
				float sum = ptr1.term.coeff + ptr2.term.coeff;
				if(sum != 0){
					ptr3 = new Node(sum,ptr1.term.degree,null);
					ptr3prev = addEnd(sumList, ptr3, ptr3prev);
					// if(sumList == null){
					// 	sumList = ptr3;
					// 	ptr3prev = ptr3;
					// }else{
					// 	ptr3prev.next = ptr3;
					// 	ptr3prev = ptr3;
					// }
				}
				ptr1 = ptr1.next;
				ptr2 = ptr2.next;
			}else if(ptr1.term.degree > ptr2.term.degree){
				float coeff = ptr2.term.coeff;
				int deg = ptr2.term.degree;
				ptr3 = new Node(coeff, deg, null);
				ptr3prev = addEnd(sumList, ptr3, ptr3prev);
				// if(sumList == null){
				// 	sumList = ptr3;
				// 	ptr3prev = ptr3;
				// }else{
				// 	ptr3prev.next = ptr3;
				// 	ptr3prev = ptr3;
				// }
				ptr2 = ptr2.next;
			}else{
				float coeff= ptr1.term.coeff;
				int deg = ptr1.term.degree;
				ptr3 = new Node(coeff, deg, null);
				ptr3prev = addEnd(sumList, ptr3, ptr3prev);
				// if(sumList == null){
				// 	sumList = ptr3;
				// 	ptr3prev = ptr3;
				// }else{
				// 	ptr3prev.next = ptr3;
				// 	ptr3prev = ptr3;
				// }
				ptr1 = ptr1.next;
			}

		}

		//if poly1 has more terms than poly2
		while(ptr1 != null){
			float coeff= ptr1.term.coeff;
			int deg = ptr1.term.degree;
			ptr3 = new Node(coeff, deg, null);
			ptr3prev = addEnd(sumList, ptr3, ptr3prev);
			// if(sumList == null){
			// 	sumList = ptr3;
			// 	ptr3prev = ptr3;
			// }else{
			// 	ptr3prev.next = ptr3;
			// 	ptr3prev = ptr3;
			// }
			ptr1 = ptr1.next;
		}

		//if poly2 has more terms than poly1
		while(ptr2 != null){
			float coeff= ptr2.term.coeff;
			int deg = ptr2.term.degree;
			ptr3 = new Node(coeff, deg, null);
			ptr3prev = addEnd(sumList, ptr3, ptr3prev);
			// if(sumList == null){
			// 	sumList = ptr3;
			// 	ptr3prev = ptr3;
			// }else{
			// 	ptr3prev.next = ptr3;
			// 	ptr3prev = ptr3;
			// }
			ptr2 = ptr2.next;
		}
	}
		//testing sumList
		
		for(Node ptr = sumList; ptr != null; ptr = ptr.next){
			System.out.print(ptr.term + "\t");
			System.out.println();
		}
		
		return sumList;
}
	
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node ptr1 = poly1;
		Node ptr2 = poly2;
		Node ptr3 =  null; //multList;
		Node ptr3prev = null;
		Node sum = null;
		while(ptr1!= null){
			Node multList = null;
			while(ptr2 != null){
				float mult = ptr1.term.coeff * ptr2.term.coeff;
				int multDeg = ptr1.term.degree + ptr2.term.degree;
				ptr3 = new Node(mult, multDeg,null);
				if(multList == null){
					multList =  ptr3; //new Node(mult, multDeg,null);
					ptr3prev = ptr3;
				}else{
					ptr3prev.next =  ptr3; //new Node(mult, multDeg,null);
					ptr3prev = ptr3;
				}
				ptr2 = ptr2.next;
				//System.out.print(ptr3.term + "\t");
			}
			//System.out.println("adding now");
			sum = add(sum,multList);
			ptr1 = ptr1.next;
			ptr2 = poly2;
			//System.out.println();
		}
		System.out.println("printing the traverse");
		Node ptr = sum;
		while(ptr != null){
			System.out.println(ptr.term);
			ptr = ptr.next;
		}
		Node finalList = sum;
		return finalList;
		//return sum;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		float eval = 0;
		for(Node ptr = poly; ptr != null; ptr = ptr.next){
			int deg = ptr.term.degree;
			eval += Math.pow(x, deg) * ptr.term.coeff;
		}
	    return eval;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
