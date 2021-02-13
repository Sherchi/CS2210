/**
 * Ordered Dictionary Class. Basically a Binary Tree.
 * @author Darwin
 *
 */
public class OrderedDictionary implements OrderedDictionaryADT{
	
	private int size = 0;
	private BinaryNode root;

	@Override
	public DataItem get(Key k) {
		// TODO Auto-generated method stub
		
		return findNode(k).getData();
	}
	
	//Function find the key. Needed this one for the insert function to find the proper location to put the key.

	private BinaryNode findNode(Key k) {
		//If the Tree is Empty, return null
		if(size == 0) {
			return null;
			
		}
		
		//Checks if the root is the same as the key given, if it is, return the root
		int isSame = root.getData().getKey().compareTo(k);
		if (isSame == 0) {
			return root;
		
		}
		//If it is greater, then the key is lower than the key given, so we must go to the left. Vice Versa for Lower value
		if(isSame > 0) {
			return findNodeRecusive(root.getLeft(), k);
			
		}else {
			return findNodeRecusive(root.getRight(),k);
		}
		
	
	}
	//Recursive function to go through the binary tree/Dictionary and try to find the key. Same as the FindNode, but includes a root to be recursed. 

	private BinaryNode findNodeRecusive(BinaryNode root, Key k) {
		if(root.isLeaf()) {
			return root;
		}
		
		int isSame = root.getData().getKey().compareTo(k);
		
		if (isSame == 0) {
			return root;
		}
		
		if (isSame > 0) {
			return findNodeRecusive(root.getLeft(), k);
				
		}
		else {
			return findNodeRecusive(root.getRight(), k);
		}
	
		
	}
	
	
	@Override
	/**
	 * Puts the data into the tree
	 */
	public void put(DataItem d) throws DictionaryException {
		// TODO Auto-generated method stub
		
		if (size == 0) {
			root = new BinaryNode(d);
			
		}else {
		
			BinaryNode nodeToInsert = findNode(d.getKey());							//Sets the node we're going to insert as the result of finding the node if it were to exist
				
			if (nodeToInsert.isLeaf() == false) {									//If the findNode didn't not arrive at a leaf, then it must have been an internal node and key already exists
				throw new DictionaryException("Node Already Exists");
				
			}else {																	//Its a leaf, then change the leaf to an internal node.
				nodeToInsert.setData(d);
				
			}
		}
		size++;
		
	}

	@Override
	/**
	 * removes a data from the dicitonary/tree
	 */
	public void remove(Key k) throws DictionaryException {
		// TODO Auto-generated method stub
		
		BinaryNode nodeToRemove = findNode(k);
		
		// If the node we want to remove is a leaf or doens't exist, throw the exception
		if (nodeToRemove.isLeaf() || nodeToRemove == null) {
			throw new DictionaryException("Does not Exist");
			
		}
		
		else {
			//If both of its children are leafs, then simply remove the node
			if(nodeToRemove.getLeft().isLeaf() && nodeToRemove.getRight().isLeaf()) {
				nodeToRemove.removeData();
			}
			
			//If only the right node is a leaf, then set the left child of nodeToRemove as the either the left or right child of parent depending on what nodeToRemove was.
			else if(nodeToRemove.getLeft().isLeaf() && !nodeToRemove.getRight().isLeaf()){
				if(nodeToRemove.isLeftChild()) {
					nodeToRemove.getParent().setLeft(nodeToRemove.getLeft());
					
				}else if (nodeToRemove.isRightChild()) {
					nodeToRemove.getParent().setRight(nodeToRemove.getLeft());

					
				}
			}
			//Same as above but with setting the opposite side
			else if(!nodeToRemove.getLeft().isLeaf() && nodeToRemove.getRight().isLeaf()){
				if(nodeToRemove.isLeftChild()) {
					nodeToRemove.getParent().setLeft(nodeToRemove.getRight());
					
				}else if (nodeToRemove.isRightChild()) {
					nodeToRemove.getParent().setRight(nodeToRemove.getRight());

					
				}
			// If the nodeToRemove is an internal Node without any leafs, then we need to replace the node with the appropriate node.
			// 		Want to replace with the next node when listed an in an inorder successor / smallest node on the right of the nodeToRemove.
			//		Then delete that node.
			}else {
				BinaryNode tempNode = getSmallest(nodeToRemove.getRight());
				nodeToRemove.swapData(tempNode);
				
				if(!tempNode.getRight().isLeaf()) {
					tempNode.removeData();
				}else {
					tempNode.getParent().setLeft(tempNode.getRight());
				}
				
			}
		}
		size --;
		
	}

	@Override
	/**
	 * Returns Entry that contains the smallest key larger than the input key
	 */
	public DataItem successor(Key k) {
		// TODO Auto-generated method stub
		//If there are 0 or 1 entries, then there is no successor
		if(size < 2) {
			return null;
		}
		
		BinaryNode nodeToFind = findNode(k);
		
		//if There is a left and right child, find the smallest node of the right child
		if (!nodeToFind.isLeaf() && !nodeToFind.getRight().isLeaf()) {
			return getSmallest(nodeToFind.getRight()).getData();
		}
		
		//if the node is a leaf/doesn't have a right child, go up the tree until you get to a node that is 
		//	 the left child. The Parent of that node is the successor
		while(nodeToFind.getParent() != null) {
			if(nodeToFind.isLeftChild()) {
				return nodeToFind.getParent().getData();
			}
			nodeToFind = nodeToFind.getParent();
			
		}
		//Loop terminated by reaching the root. If it didn't then it terminated by reaching a left child.
		return null;
	}

	@Override
	/**
	 * Returns Entry that contains the largest key smaller than the input key.
	 */
	public DataItem predecessor(Key k) {
		// TODO Auto-generated method stub
		//If there are 0 or 1 entries, then there is no predecessor
		if(size < 2) {
			return null;
		}
		
		BinaryNode nodeToFind = findNode(k);
		
		//if There is a left and right child, find the largest node of the left child
		if(!nodeToFind.isLeaf() && !nodeToFind.getLeft().isLeaf()) {
			return getLargest(nodeToFind.getLeft()).getData();
		}
		//if the node is a leaf/doesn't have a right child, go up the tree until you get to a node that is 
		//	 the right child. The Parent of that node is the predecessor
		while(nodeToFind.getParent() != null) {
			if (nodeToFind.isRightChild()) {
				return nodeToFind.getParent().getData();
			}
			nodeToFind = nodeToFind.getParent();
		}
		
		return null;
	}

	@Override
	/**
	 * returns smallest node in the tree
	 */
	public DataItem smallest() {
		// TODO Auto-generated method stub
		return getSmallest(root).getData();
	}

	@Override
	/**
	 * returns largest node in the tree
	 */
	public DataItem largest() {
		// TODO Auto-generated method stub
		return getLargest(root).getData();
	}
	
	/**
	 * Finds the smallest node rooted at the node given by continuously going to the left child
	 * 		until unable to do so anymore
	 * @param node --> the Root node for the search, cannot go above this node.
	 * @return
	 */
	private BinaryNode getSmallest(BinaryNode node) {
		if (size == 0) {
			return null;
		}
		else if(size == 1){
			
			return root;
			
		}else {
			while(!node.getLeft().isLeaf()) {
				node = node.getLeft();
			}
			return node;
		}
	}
	
	/**
	 * Similar to GetSmallest, find the largest node rooted at the node given by continuously going to the right child
	 * 		until unable to do so anymore.
	 * @param node --> the Root node for the search cannot go above this node.
	 * @return
	 */
	private BinaryNode getLargest(BinaryNode node) {
		if (size == 0) {
			return null;
		}
		else if (size == 1) {
			return root;
		}else {
			while(!node.getRight().isLeaf()) {
				node = node.getRight();
				
			}
			return node;
		}
	}


}
