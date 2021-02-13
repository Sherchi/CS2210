/**
 * Node Class
 * @author Darwin
 *
 */
public class Node {
	
	/**
	 * Init. Mark is if the node has been traversed.
	 */
	private int name;
	private boolean mark;
	
	public Node(int name) {
		this.name = name;
		
	}
	
	/**
	 * Sets mark
	 * @param mark
	 */
	public void setMark(boolean mark) {
		this.mark = mark;
		
	}
	/**
	 * Gets mark
	 * @return
	 */
	public boolean getMark() {
		return mark;
		
	}
	/**
	 * Gets name
	 * @return
	 */
	public int getName() {
		return name;
		
	}

}
