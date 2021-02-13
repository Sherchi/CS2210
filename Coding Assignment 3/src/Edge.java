/**
 * Edge Class. Makes the edges/connections between two nodes
 * @author Darwin
 *
 */
public class Edge {
	/**
	 * Init
	 */
	private Node start, end;
	private int type;
	private String label = null;
	
	/**
	 * Constructor
	 * @param u First Node for the edge
	 * @param v	2nd Node for the edge
	 * @param type Type of Edge
	 */
	public Edge(Node u, Node v, int type) {
		this.start = u;
		this.end = v;
		this.type = type;
		
	}

	/**
	 * 
	 * @param u First Node for the edge
	 * @param v 2nd Node for the edge
	 * @param type type Type of Edge
	 * @param label Label for the edge
	 */
	public Edge(Node u, Node v, int type, String label) {
		this.start = u;
		this.end = v;
		this.type = type;
		this.label = label;
	
	}
	
	/**
	 * Returns the First node of the edge
	 * @return
	 */
	public Node firstEndpoint() {
		return start;
		
	}
	
	/**
	 * Returns the 2nd node of the edge
	 * @return
	 */
	public Node secondEndpoint() {
		return end;
		
	}
	
	/**
	 * Returns the type
	 * @return
	 */
	public int getType() {
		return type;
		
	}
	
	/**
	 * Changes/Sets the type of the edge when a door is unlocked
	 * @param newType 
	 */
	public void setType (int newType) {
		this.type = newType;
		
	}
	
	/**
	 * Returns the label
	 * @return
	 */
	public String getLabel() {
		return this.label;
	}
	
	/**
	 * Changes/sets the label
	 * @param newLabel
	 */
	public void setLabel(String newLabel) {
		this.label = newLabel;
	}
	
}
