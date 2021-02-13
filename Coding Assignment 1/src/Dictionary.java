/**
 * 
 * @author Darwin
 *		Dictionary Class for the hash Table. Also contains the manipulation of the Data Nodes.
 */
public class Dictionary implements DictionaryADT{
	/*
	 * Datalist contains nodes that contain the info
	 * keyList contains all the keys used
	 * size is the size of the Dictionary
	 * n is the amount of items in the dictionary
	 * 
	 */
	private Node[] dataList;
	private String[] keyList;
	private int size;
	private int n = 0;

	/*
	 * init
	 */
	public Dictionary(int size) {
		dataList = new Node[size];
		keyList = new String[size];
		this.size = size;
		
	}
	
	/*
	 * Hash Function similar to the one shown in class.
	 * 
	 */
	private String hashFunction(String key) {
		int x = 29;
		long hashNum = 0;
		for(int i = 0; i <  key.length(); i++) {
			int charToInt = Integer.valueOf(key.charAt(i)) - 'A' + 1;
			
			hashNum = (long) (hashNum * x + charToInt);

		}
		
		return String.valueOf(Math.abs(hashNum % size));
	}
		
	/*
	 * (non-Javadoc)
	 * @see DictionaryADT#put(Data)
	 * puts in the record into the dictionary at the index of the hash function and inside a linked list node.
	 * 		Then uses seperate chaining to link the nodes together in the case of a collision
	 * 
	 */
	@Override
	public int put(Data record) throws DuplicatedKeyException {
		String hash = hashFunction(record.getKey());
		Data newRecord = new Data(record.getKey(),record.getScore(),record.getLevel());
		/*
		 * Checks if the key is in the list of keys yet. if it isn't then add it. If it is, then return the exception.
		 */
		for(int x = 0; x < size - 1; x++) {
			if (record.getKey() == keyList[x]) {
				throw new DuplicatedKeyException("Already in list");

			}
			if (keyList[x] == null) {
				keyList[x] = record.getKey();
			}
		}
		//Incerases number of data items
		n++;
		
		/*
		 * If a collision occures, then make a new node and link it together.
		 */
		if (dataList[Integer.parseInt(hash)] != null) {
			Node newNode = new Node();
			Node currentNode = dataList[Integer.parseInt(hash)];
			Node nextNode = currentNode.getNext();
			newNode.setData(newRecord);
			
			while(nextNode != null) {
				currentNode = nextNode;
				nextNode = currentNode.getNext();
			}
			
			currentNode.setNext(newNode);
			return 1;
		}
		/*
		 * if there is no collision, then just put the value in
		 */
		else {
			dataList[Integer.parseInt(hash)] = new Node();
			dataList[Integer.parseInt(hash)].setData(newRecord);
			return 0;
		}
		

	}
	/*
	 * (non-Javadoc)
	 * @see DictionaryADT#remove(java.lang.String)
	 * Looks at the index of the hash, then goes through the list of values at that index and checks if the key is there.
	 * 		If it is, remove it, if it isn't then return the exception.
	 * 
	 */
	@Override
	public void remove(String key) throws InexistentKeyException {
		String hash = hashFunction(key);
		Node currentNode = dataList[Integer.parseInt(hash)];
		/*
		 * if the value exists then check
		 */
		if(currentNode != null) {
			Node nextNode = currentNode.getNext();
			Data nodeInfo = (Data) currentNode.getData();
			
			if ((nodeInfo.getKey()).equals(key)) {
				currentNode = null;
			}
			/*
			 * goes through the values at that index.
			 */
			while(!(nodeInfo.getKey()).equals(key)) {
				currentNode = nextNode;
				nextNode = currentNode.getNext();
				nodeInfo = (Data) currentNode.getData();
				
				if ((nodeInfo.getKey()).equals(key)) {
					currentNode = null;
					break;
				}
			}
			
		}else{
			throw new InexistentKeyException("Does not Exist");
		}
		/*
		 * removes the key from the list of keys
		 * and decreases number of items by one
		 * 
		 */

		for(int x = 0; x < size - 1; x++) {
			if (key.equals(keyList[x])) {
				keyList[x] = null;
				n--;
			}
		}
		
	}
	/*
	 * (non-Javadoc)
	 * @see DictionaryADT#get(java.lang.String)
	 * 
	 * returns the Data at the key. Same as the remove function, it checks all the key values at the index and returns the data of the key if it finds a match.
	 * 
	 */
	@Override
	public Data get(String key) {
		String hash = hashFunction(key);
		if(dataList[Integer.parseInt(hash)] == null) {
			return null;
		}
		
		Node baseNode = dataList[Integer.parseInt(hash)];
		Node currentNode = dataList[Integer.parseInt(hash)];
	
		while(baseNode.getNext() != null) {
			currentNode = baseNode.getNext();
			Data nodeInfo = (Data) currentNode.getData();
			if ((nodeInfo.getKey()).equals(key)) {
				return nodeInfo;
			}
			baseNode = baseNode.getNext();
			
		}
		 
		return (Data) baseNode.getData();
	}
	/*
	 * (non-Javadoc)
	 * @see DictionaryADT#numDataItems()
	 * returns number of values in the dictionary.
	 */
	@Override
	public int numDataItems() {
		return n;
	}


}
