import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.lang.*;
/**
 * Labyrinth class to take in the information from the text file.
 * @author Darwin
 *
 */
public class Labyrinth {
	
	/**
	 * Init Variables
	 */
	private Graph graph;
	private int scaleFactor,width,length,start,exit;
	private int[] keyArr = new int[10];
	private int[] keyOrder;
	private int keyCounter = 0;
	Stack<Node> path = new Stack<Node>();

	
	/**
	 * Taking in the file and 
	 * @param inputFile
	 * @throws LabyrinthException
	 */
	public Labyrinth(String inputFile) throws LabyrinthException{
		try {
			BufferedReader input = new BufferedReader(new FileReader(inputFile));
			
			/*
			 * Reading in the text file's information.
			 */
			scaleFactor = Integer.parseInt(input.readLine());
			width = Integer.parseInt(input.readLine());
			length = Integer.parseInt(input.readLine());
			String keys = input.readLine();
			String tempArr[] = keys.split(" ");	

			/*
			 * This array turns the String key amounts into integers so I can add/subtract them at will.
			 */
			for (int n = 0; n < tempArr.length; n++) {
				keyArr[n] = Integer.parseInt(tempArr[n]);
			}
			
			/*
			 * Adds the total amount of keys given of any kind. Used for key management
			 */
			int amountOfKeys = 0;
			for (int n = 0; n < tempArr.length; n++) {
				amountOfKeys += keyArr[n];
			}
			
			/*
			 * An array of the size of how many key's we are given
			 */
			keyOrder = new int[amountOfKeys];
			
			/*
			 * Makes the graph
			 */
			graph = new Graph(width*length);
			
			String rowInfo;
			int row = -1;
			
			
			/**
			 * This reads in the lines and categorizes them into whether they are a room, a horizontal edge/connection or a verticle edge/connection
			 * 
			 * 		Rooms must be on the even rows and columns since they must have one edge between them in the text file
			 * 		Horizontal Edges are on Even rows but odd columns
			 * 		Verticle are on odd rows, but even columns
			 * 		Walls do not need to be categorized. Just have them as a 'w'
			 * 
			 * 		
			 */
			while ((rowInfo = input.readLine()) != null) {
				row++;
				boolean isRoom = false, isHorizontal = false, isVertical = false;

				for(int col = 0; col < rowInfo.length(); col++) {
					if (col%2 == 0 && row%2 == 0) {
						isRoom = true;
					}else {
						isRoom = false;
					}
					
					if(col%2 == 1 && row%2 == 0) {
						isHorizontal = true;
					}else {
						isHorizontal = false;
					}
					
					if(col%2 == 0 && row%2 == 1) {
						isVertical = true;
					}else {
						isVertical = false;
					}
					
					
					/**
					 * If its a special room like the start and exit, then record where they are number wise.
					 */
					if(isRoom) {
						if(rowInfo.charAt(col) == 's') {
							start = (col/2) + (row/2)*width;
						}
						else if(rowInfo.charAt(col) == 'x') {
							exit = (col/2) + (row/2)*width;
						}
					}
					
					/**
					 * 
					 * If its a connection, then make the edge with the node before the one recorded and after the one recorded.
					 * 
					 */
					else if (isHorizontal) {
						if (rowInfo.charAt(col) == 'w') {
							graph.insertEdge(graph.getNode(col/2 + (row/2)*width), graph.getNode(col/2 + (row/2)*width + 1),Character.getNumericValue('w'),"wall");
						}
						
						else if (rowInfo.charAt(col) == 'c') {
							graph.insertEdge(graph.getNode(col/2 + (row/2)*width), graph.getNode(col/2 + (row/2)*width + 1),Character.getNumericValue('c'));
						}
						
						else if(Character.isDigit(rowInfo.charAt(col))) {
							graph.insertEdge(graph.getNode(col/2 + (row/2)*width), graph.getNode(col/2 + (row/2)*width + 1),Character.getNumericValue(rowInfo.charAt(col)));

						}
					/**
					 * 
					 * If its a vertical connection, then make the edge with the node one row above and one row below.
					 * 
					 */
					}else if (isVertical) {
						if (rowInfo.charAt(col) == 'w') {
							graph.insertEdge(graph.getNode(col/2 + ((row-1)/2)*width), graph.getNode(col/2 + ((row+1)/2)*width),Character.getNumericValue('w'),"wall");
						}
						
						else if (rowInfo.charAt(col) == 'c') {
							graph.insertEdge(graph.getNode(col/2 + ((row-1)/2)*width), graph.getNode(col/2 + ((row+1)/2)*width),Character.getNumericValue('c'));
						}
						
						else if(Character.isDigit(rowInfo.charAt(col))) {
							graph.insertEdge(graph.getNode(col/2 + ((row-1)/2)*width), graph.getNode(col/2 + ((row+1)/2)*width),Character.getNumericValue(rowInfo.charAt(col)));

						}
					}
				}
			}

			input.close();
		
		}catch (Exception e){
			System.out.println(e.getMessage());
			throw new LabyrinthException("Error Creating Labyrinth");
		}
	}
	
	
	/**
	 * Makes the graph.
	 * @return
	 * @throws LabyrinthException
	 */
	public Graph getGraph() throws LabyrinthException{
		if (graph == null) {
			throw new LabyrinthException("Graph does not exist");
		}else {
			return graph;
		}
	}
	
	
	/**
	 * Solving the Labyrinth. Tries out a path and sees if it is possible. I tried to do a DFS search but it didn't turn out well.
	 * 	Used 2 stacks initially, one for the potential nodes, and one for the actual solution at the time.
	 * 
	 * This did not turn out well. please scroll down farther for my actual Solve function.
	 * 
	 * @return
	 * @throws LabyrinthException
	 */
	
//	public Iterator solve() throws LabyrinthException {
//
//
//		try {
//			Stack<Node> path = new Stack<Node>(), tempPath = new Stack();
//			Node current, begin = graph.getNode(start), end = graph.getNode(exit);
//			Edge edge = null;
//			
//			int counter = 0, keyCounter = 0;
//			
//			path.push(begin);
//			begin.setMark(true);
//			
//			/**
//			 * Goes through all the non-marked nodes
//			 */
//			while(path.isEmpty() == false) {
//				current = (Node) path.peek();
//				
//				if (current == end) {
//					return path.iterator();
//					
//				}else {
//					//To see if anything was added to the stack
//					counter = 0;
//
//					// Goes through the edges for the current node.
//					Iterator iterator = graph.incidentEdges(current);
//					while(iterator.hasNext()) {
//						edge = (Edge) iterator.next();
//						current = edge.secondEndpoint();
//								
//						//if the endpoint of that edge isn't marked/visited before then try to push it onto the path if the edge is valid
//						if(current.getMark() == false) {
//							//If edge is a corridor
//							if(edge.getType() == Character.getNumericValue('c') && edge.getLabel() == null) {
//								current.setMark(true);
//								tempPath.push(current);
//								counter ++;
//
//							}
//							// If the edge was a door, then manage the keys. Using smalles possible key to open it and recording which key was used.
//							else if (edge.getType() <= 9 && edge.getLabel() == null) {
//								for(int i = edge.getType(); i < 10; i++) {
//									if(keyArr[i] > 0) {
//										keyArr[i]--;
//										keyOrder[keyCounter] = i;
//										keyCounter ++;
//										current.setMark(true);
//										tempPath.push(current);
//										edge.setLabel("used");
//										counter ++;
//										break;
//									}
//								}
//							}
//						
//						}
//					}
//					
//					//If no element was added to the path, then that means there are no options to add. 
//					//Removes that element from the stack
//					if (counter == 0) {
//						path.pop();
//						
//						//Pops nodes until it is possible to be connected to another possible move that is stored in tempPath.
//						/**
//						 * This doens't work. It messes up on corners and goes through walls. I tried fixing it but I've spent way too much time on this.
//						 */
//						while(path.size() > 0 && tempPath.size() > 0 && graph.areAdjacent(path.peek(), tempPath.peek()) == false) {
//							Node tempNode = (Node) path.pop();
//							Edge tempEdge = graph.getEdge((Node) path.peek(),tempNode);							
//							
//							//Unmark the node we just popped since we might need to traverse it later.
//							tempNode.setMark(false);
//							//Regain the key used if it was a door that was connecting the two nodes
//							if (tempEdge.getType() <= 9){
//								keyArr[keyOrder[keyCounter-1]]++;
//								keyOrder[keyCounter-1] = -1;
//								keyCounter --;
//							}
//							
//							 
//						}
//						
//						//If there are no more options and all the nodes have already been visited, then return null
//						if (path.size() == 0 || tempPath.size() == 0) {
//							return null;
//						}
//
//					}
//					if(graph.getEdge(path.peek(),tempPath.peek()).getLabel() == "wall") {
//						path.peek();	
//					}else {
//						path.push(tempPath.pop());
//
//					}
//
//				}
//			}
//			return null;	
//
//			
//		}catch(Exception e){
//			throw new LabyrinthException(e.getMessage());
//		}
//	}
	/**
	 * 
	 * 
	 * The following is a working solution unlike the largely commented out portion above. This one actually produces results.
	 * 
	 * Unforunately, it isn't really a depth first search. 
	 * 
	 * Honestly, I just played around with recursion with some ideas from the above code until it worked.
	 * 		--> it tries to find a path to the end from each start node it is given. If it leads to the end, it returns that stack. 
	 * 			If it does not, it will pop that node off of the stack and proceed with different nodes.
	 * 
	 * 
	 * @return
	 * @throws LabyrinthException
	 */
	
	public Iterator solve()throws LabyrinthException {
		try {
			//Init
			Node s = graph.getNode(start);
			Node e = graph.getNode(exit);
			Iterator iterator = graph.incidentEdges(s);
			Edge edge;
			
			//While the iterator has possible edges to go through, then proceed with the function.
			while(iterator.hasNext()) {
				edge = (Edge) iterator.next();
				pathFinder(s,e,edge);
				
				//Returns the path.
				if(!path.isEmpty()){
					return path.iterator();
				}
			}
			
		}catch(Exception e) {
			throw new LabyrinthException(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Recursive part of the function.
	 * @param start Starting node. It is the node that will be changed along with what type of edge it is
	 * @param end The exit. Will stay the same
	 * @param edge	What type of edge links the nodes
	 * @return	A true/False if it can find the exit
	 * @throws LabyrinthException
	 */
	private boolean pathFinder(Node start, Node end, Edge edge) throws LabyrinthException{
		path.push(start);
		
		/**
		 * If it can find the exit, return true else, recurse onto itself
		 */
		if (start == end) {
			return true;
		}else {
			try {
				/**
				 * Marks the starting point as visited and makes the iterator
				 */
				start.setMark(true);
				Iterator iterator = graph.incidentEdges(start);
				
				/**
				 * while the iterator has a next edge to iterate over
				 */
				while(iterator.hasNext()) {
					
					/**
					 * init
					 */
					Edge nextEdge = (Edge) iterator.next();
					Node v = nextEdge.secondEndpoint();
					
					/**
					 * If the next node hasn't been visited yet, then see if the linking edge is
					 * 		either a doorway or a corridor. If it is a doorway, then subtract the keys as neccessary
					 * 
					 * 		I used an array to store what keys I have and of what amount (Taken from the file).
					 * 		Then I had some counters to count how many keys were used, and then added them into an array to store the order that they were used in.
					 * 		When I needed to backtrack, I took the keys out of the array and added +1 to the index.
					 * 
					 * 
					 */
					if(v.getMark() != true) {
						//If the edge is a door, then subtract the smallest key possible to open that door.
						if(nextEdge.getType() <= 9){
							for(int i = nextEdge.getType(); i < 10; i++) {
								if(keyArr[i] > 0) {
									keyArr[i]--;
									keyOrder[keyCounter] = i;
									keyCounter++;
									//Sets the edge to the next edge
									edge = nextEdge;
									//Goes through the process again with the next node
									if(pathFinder(v,end,edge)) {
										return true;
										
										//If the next node can't make it, then come back and take the key that was spent back.
									}else {
										keyArr[keyOrder[keyCounter-1]]++;
										keyOrder[keyCounter-1] = -1;
										keyCounter --;
									}
									//Exits out of the key using loop so it doens't use more than one key
									break;
								}
							}
							
							//If its not a door, then it must be a corridor for us to get to the next node.
						}else if(nextEdge.getType() == Character.getNumericValue('c')){
							edge = nextEdge;
							if(pathFinder(v,end,edge)) {
								return true;
							}
						}
					}
				}
				//If it can't go anywhere, set the mark as false and pop of off of the path.
				start.setMark(false);
				path.pop();
				
			}catch(Exception e) {
				throw new LabyrinthException(e.getMessage());
			}
		}
		//Returns false if there is no path.
		return false;
	}
}
