/**
 * 
 * @author Darwin
 *A node class to store the parent, children and information for easier access
 */
public class BinaryNode {
	
	//Init variables
	private DataItem data;
	private BinaryNode left, right, parent;
	
	//Class Constructor
	public BinaryNode(DataItem newData) {
		data = newData;
		parent = null;
		right = new BinaryNode();
		right.setParent(this);
		left = new BinaryNode();
		left.setParent(this);
		
	}
	
	//Empty class constructor for leafs
	public BinaryNode() {
		data = null;
		
	}
	
	
	//Sets the left Child
	public void setLeft(BinaryNode leftNode) {
		left = leftNode;
		
	}
	
	//Sets the right child
	public void setRight(BinaryNode rightNode) {
		right = rightNode;
	
	}
	
	//Sets the Parent Node
	public void setParent(BinaryNode parentNode) {
		parent = parentNode;
	}
	
	//Gets the left Child
	public BinaryNode getLeft() {
		return left;
		
	}
	
	//Gets the right child
	public BinaryNode getRight() {
		return right;
		
	}
	
	
	//Gets the Parent Node
	public BinaryNode getParent() {
		return parent;
		
	}
	
	
	//Returns true if the node is a leaf
	public boolean isLeaf() {
		return data == null;
	}
	
	
	//Returns true if the node is a left Child
	public boolean isLeftChild() {
		return parent.getLeft() == this;
		
	}
	
	//Returns true if the node is a right child
	public boolean isRightChild() {
		return parent.getRight() == this;
		
	}
	
	//Sets the data of the node as well as making leaf nodes
	public void setData(DataItem newData) {
		data = newData;
		left = new BinaryNode();
		left.setParent(this);
		right = new BinaryNode();
		right.setParent(this);
			

		
	}
	
	//returns the Data in the node
	public DataItem getData() {
		return data;
	}
	
	//Used when removing a node. Swaps two node's data
	public void swapData(BinaryNode node) {
		data = node.getData();
		
	}
	
	//Turns node into a leaf.
	public void removeData() {
		data = null;
		right = null;
		left = null;
		
	}
	

}
