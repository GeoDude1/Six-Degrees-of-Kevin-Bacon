import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

//Class that represents an unweighted undirected graph
public class ActorsGraph {

	//The actors are going to be stored on a HashMap, where the keys are the Actors names and the values are the actual Nodes containing all the relations
	private Map<String, Node> actors;

	public ActorsGraph() {
		actors = new TreeMap<String, Node>(String.CASE_INSENSITIVE_ORDER);
	}

	//Method that adds an actor to the current graph
	public void addActor(String actor) {
		Node newNode = new Node(actor);
		actors.put(actor, newNode);
	}

	//Method that adds a relation (edge) between two actors
	public void addEdge(String actor1, String actor2) {
		//First, we get the nodes of the actors
		Node node1 = actors.get(actor1);
		Node node2 = actors.get(actor2);

		//Then, we add the relation between the actors
		node1.addRelation(node2);
		node2.addRelation(node1);
	}

	//Method that finds the shortest path between two actors
	public ArrayList<String> shortestPath(String actor1, String actor2) {
		//first, a Hashmap and an ArrayList are made to hold both parents and to store the temp nodes
		Map<String, String> parents = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
		ArrayList<Node> temp = new ArrayList<Node>();

		//Now, we start adding the actor1 node to the List and adding it to the parent Map
		Node start = actors.get(actor1);
		temp.add(start);
		parents.put(actor1, null);

		//Now, we loop as long as there are nodes to visit
		while (temp.size() > 0) {
			Node currentNode = temp.get(0);
			List<Node> knownActors = currentNode.getKnownActors();

			//The next step is to loop through all the known actors of the current actor
			for (int i = 0; i < knownActors.size(); i++) {
				Node nextActor = knownActors.get(i);
				String actorName = nextActor.getName();
				//Now, a node (actor) can be visited only one time
				boolean visited = parents.containsKey(actorName);
				//If the current now was visited already, then we continue with the next known actor
				if (visited) {
					continue;
				}
				//If not, we add it to the List of temp nodes to be visited
				else {
					temp.add(nextActor);
					//then, a node is added to the parent map, with parent being the actual node and the path it follows the next non-visited node
					parents.put(actorName, currentNode.getName());
					//Before going onto the next node to traverse, it checks if the next known actor is the one
					//we're looking for. If it is, it returns an ArrayList with the actual path
					if (actorName.equalsIgnoreCase(actor2)) {
						return getPath(parents, actor2);
					}
					//If not, it just continues with the next know actor for the current one
				}
			}
			//If a loop ends with no returns, it means the current actor doesn't hold a path to the actor that is being looked for
			//It is removed and it continues with the known actors of the next known actor
			temp.remove(0);
		}
		//Finally, if no returns at all, it means the path for the two actors doesn't exist, then return null
		return null;
	}

	//Method that gets the path for a given actor given its parents (as a Map)
	private ArrayList<String> getPath(Map<String, String> parents, String actor2) {
		//This method just loops, starting at the end node, and going backwards on the path until it run out of parents, which mean we get to the starting actor
		ArrayList<String> path = new ArrayList<String>();
		String node = actor2;
		while (node != null) {
			path.add(0, node); 
			String parent = parents.get(node); 
			node = parent;
		}
		return path;
	}

	//nested class that represents a Node to hold the actor name and known actors
	private class Node {
		String name;
		List<Node> known = new ArrayList<Node>();

		//Constructor
		public Node(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public void addRelation(Node actor) {
			//We check if the relation doesn't exist already
			boolean exist = false;
			for(Node n : known) {
				if(n.getName().equals(actor.getName())) {
					exist = true;
					break;
				}
			}
			//then, we add it only if it doesn't exist
			if(!exist) {
				known.add(actor);
			}
		}
		//getter for the list of all know actors
		public List<Node> getKnownActors() {
			return known;
		}
	}
}

