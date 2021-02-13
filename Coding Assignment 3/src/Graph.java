import java.util.Iterator;
import java.util.Stack;
/**
 * Graph Class
 * @author Darwin
 *
 */
public class Graph implements GraphADT{
	/**
	 * Init
	 */
	private int numNodes;
	private Edge[][] edges;
	private Node[] nodes;
	/**
	 * Makes a graph with n amount of nodes and nxn edge matrix
	 * @param n
	 */
	public Graph(int n) {
		this.numNodes = n;
		this.edges = new Edge[n][n];
		this.nodes = new Node[n];
		
		for (int i = 0; i < n; i++) {
			nodes[i] = new Node(i);
		}
	}
	/**
	 * Inserts an edge with nodeu as the start, nodev as the end, type as type and label as the label
	 * 					--> and with NodeV as start and nodeU as end for both ways
	 */
	@Override
	public void insertEdge(Node nodeu, Node nodev, int type, String label) throws GraphException {
		// TODO Auto-generated method stub
		if (nodeu.getName() < 0 || nodev.getName() < 0 || nodeu.getName() > nodes.length - 1|| nodev.getName() > nodes.length - 1) {
			throw new GraphException("Error with Node");
			
		}else if(edges[nodeu.getName()][nodev.getName()] != null || edges[nodev.getName()][nodeu.getName()] != null) {
			throw new GraphException("Edge Already Exists");
			
		}else {
			edges[nodeu.getName()][nodev.getName()] = new Edge(nodeu,nodev,type,label);
			edges[nodev.getName()][nodeu.getName()] = new Edge(nodev,nodeu,type,label);
		}
		
	}
	/**
	 * Inserts an edge with nodeu as the start, nodev as the end, type as type
	 * 			--> and with NodeV as start and nodeU as end for both ways
	 */
	@Override
	public void insertEdge(Node nodeu, Node nodev, int type) throws GraphException {
		// TODO Auto-generated method stub
		
		if (nodeu.getName() < 0 || nodev.getName() < 0 || nodeu.getName() > nodes.length - 1 || nodev.getName() > nodes.length - 1) {
			throw new GraphException("Error with Node");
			
		}else {
			if(edges[nodeu.getName()][nodev.getName()] != null || edges[nodev.getName()][nodeu.getName()] != null) {	
				throw new GraphException("Edge Already Exists");
			}
			
			else {
				edges[nodeu.getName()][nodev.getName()] = new Edge(nodeu,nodev,type);
				edges[nodev.getName()][nodeu.getName()] = new Edge(nodev,nodeu,type);
			}
		}
		
	}
		
		
	/**
	 * Returns the node at u
	 */
	@Override
	public Node getNode(int u) throws GraphException {
		// TODO Auto-generated method stub
		
		if (u < 0 || u >= numNodes) {
			throw new GraphException("Node does not exist");
		}
		else {
			return nodes[u];
		}
	}

	/**
	 * Return an interator that would go through the edges adjacent to a node using a stack.
	 * 	Loops through the row and pushes it onto the stack if it is adjacent
	 */
	@Override
	public Iterator incidentEdges(Node u) throws GraphException {
		// TODO Auto-generated method stub
		if (u.getName() < 0 || u.getName() > numNodes) {
			throw new GraphException("Error with Node");
			
		}else {
			Stack incidents = new Stack();
			
			for (int i = 0; i < nodes.length; i++) {
				if (edges[u.getName()][i] != null){
					incidents.push(edges[u.getName()][i]);
				}
			}
			
			if (incidents.isEmpty()) {
				return null;
				
			}else {
				return incidents.iterator();
			}
		}
	}
		
	/**
	 * Returns the edge that is connected by node u and node v. Returns an exception if there is no edge
	 */
	@Override
	public Edge getEdge(Node u, Node v) throws GraphException {
		// TODO Auto-generated method stub
		if (u.getName() < 0 || v.getName() < 0 || u.getName() > nodes.length - 1 || v.getName() > nodes.length - 1) {
			throw new GraphException("Error with Node");
			
		}else if(edges[u.getName()][v.getName()] == null) {
			throw new GraphException("No Edge");
			
		}else {
			return edges[u.getName()][v.getName()];
		}

	}

	/**
	 * checks if two nodes are connected.
	 */
	@Override
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		// TODO Auto-generated method stub
		if (u.getName() < 0 || v.getName() < 0 || u.getName() > nodes.length || v.getName() > nodes.length) {
			throw new GraphException("Error with Node");
			
		}else if(edges[u.getName()][v.getName()] != null) {
			return true;
			
		}else {
			return false;
		}
	}

}
