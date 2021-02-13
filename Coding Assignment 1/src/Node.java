/**
 * 
 * @author Darwin
 *	A node for a linked list used in Dictionary.
 * @param <T>
 */
public class Node<Data> {
	
	private Node<Data> next;

	private Data data;
	
	/**
	 * Inits by setting all values to null
	 */
	public Node() {
		data = null;
		next = null;
		
	}
	
	public Node(Data newData) {
		data = newData;
		
	}
	
	/**
	 * @return returns the node that is linked in "next"
	 */
	public Node<Data> getNext(){
		return next;
	}
	
	/**
	 * @return the data stored in "data"
	 */
	public Data getData() {
		return data;
	}

	/**
	 * @param nextNode
	 * sets what next is
	 */
	public void setNext(Node<Data> nextNode) {
		next = nextNode;
	}
	
	/**
	 * @param newData
	 * sets what data is.
	 */
	public void setData(Data newData) {
		data = newData;
	}

}

