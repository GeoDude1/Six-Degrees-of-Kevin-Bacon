
import java.io.BufferedReader;  
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.*;

//class that will represent the main application for the actors data
public class A3 {

	//Main method that receives the filename as a command line argument, the CSV file with all the actors and movies to read
	public static void main(String[] args) {
		//First, the CSV file is read using a BufferedReader with a FileReader with the 1st argument from the args array
		try {
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			//Now, the first line is the header, so we skip it
			br.readLine();
			//For the rest of the lines, actual movies, the idea is to read all movies and create a HashMap with the
			//movies as keys and the actors they have as the values. 
			HashMap<String,ArrayList<String>> movies = new HashMap<String,ArrayList<String>>();
			//Also, a HashSet will be used to store all the actors. The idea of the set is to store each actor only one time to keep track of the total amount of actors
			TreeSet<String> actors = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
			JSONParser parser = new JSONParser();
			String line = br.readLine();
			while(line != null) {
				//For the current line, the movie title is given between the 1st and 2nd comma chars
				int first = line.indexOf(",");
				int second = line.indexOf(",",first+1);
				//Now, the movie title is the String in between the indices for these commas
				String movieTitle = line.substring(first+1,second);
				line = line.substring(second+1); //'cuts' the current String to start now after the 2nd comma
				//The next value to get is the cast on a JSON format. To do this, we look for the indices of '['
				//and ']'. The list of actors (in JSON format) is given by the String in between those indices
				first = line.indexOf("[");
				//To find the closing ']', we need to loop through all the chars and find the respective ]
				//Why loop instead of indexOf? Because, if there's another [ on the way, the first ] found will be
				//for an inner list, not for the actual list of actors. Then, we keep track of the opening [
				//and, when the numOpen is 1 and we get to a ], we get to the one we need
				int numOpen = 1;
				for(int i = first+1; i < line.length(); i++) {
					//If the current char is [, one more opening bracket
					if(line.charAt(i) == '[') {
						numOpen++;
					} 
					//Else if we get to ] and only one open, we're done
					else if(line.charAt(i) == ']' && numOpen == 1) {
						second = i;
						break;
					} 
					//Else if we get to ] but more than one open, we decrease the num of open ones
					else if(line.charAt(i) == ']' && numOpen > 1) {
						numOpen--;
					} 
					//Otherwise, just any char, we continue on the next loop
					else {
						continue;
					}
				}
				//Then, we get the current cast as the JSON format of array inside the indices
				String cast = line.substring(first,second+1);
				cast = cast.replaceAll("\"\"", "\"");
				JSONArray array = (JSONArray)parser.parse(cast);
				//After that, 'cast' now represents an array of JSON Strings. Here, we have as many actors as the
				//number of JSON strings on the given list. To find each of the JSON formats (then each of the
				//actors), we loop through the JSONArray object to get each of the JSONObjects inside
				ArrayList<String> movieActors = new ArrayList<String>();
				for(int i = 0; i < array.size(); i++) {
					JSONObject json = (JSONObject)array.get(i);
					//For the given JSON String, we get the actor name and we add it to the ArrayList of actors
					//for the current movie and to the Set of all actors
					String actorName = (String)json.get("name");
					movieActors.add(actorName);
					actors.add(actorName);
				}
				//Then, we add the current movie with its actors to the HashMap
				movies.put(movieTitle, movieActors);
				line = br.readLine(); //Upade to go to next line, so next movie
			}
			br.close();
			//Once we're done with the reading, we pass the HashMap to the method that creates the graph for the
			//actors. Then the method looks for the shortest path of given actors.
			createGraph(movies, actors);
		} catch (Exception e) {
			//If any error, we just print an error message
			e.printStackTrace();
		}
	}
	
	//method that receives the HashMap for all movies and the Set of all actors. Creates the current graph and finds the shortest path between two actors
	public static void createGraph(HashMap<String,ArrayList<String>> movies, TreeSet<String> actors) {
		//first the ActorsGraph object is created and we loop through all actors to add them to the graph
		ActorsGraph graph = new ActorsGraph();
		for(String actor: actors) {
			graph.addActor(actor);
		}
		
		//Then, we loop through all Movies to add all the relations between actors
		for(String movie : movies.keySet()) {
			ArrayList<String> movieActors = movies.get(movie);
			//Here, we do a nested loop to relate each actor with the rest
			for(int i = 0; i < movieActors.size(); i++) {
				for(int j = i+1; j < movieActors.size(); j++) {
					graph.addEdge(movieActors.get(i), movieActors.get(j));
				}
			}
		}
		//Here, you can add a loop as long as the user wants to keep looking for paths
		//Finally, once the graph was created, we just do the interaction with the user to look for shortest paths between two actors.
		Scanner input = new Scanner(System.in);
		try {
			System.out.print("Actor 1 Name: ");
			String actor1 = input.nextLine();
			if(!actors.contains(actor1)) {
				throw new Exception("No such actor");
			}
			System.out.print("Actor 2 Name: ");
			String actor2 = input.nextLine();
			if(!actors.contains(actor2)) {
				throw new Exception("No such actor");
			}
			ArrayList<String> path = graph.shortestPath(actor1, actor2);
			if(path == null) {
				System.out.println("No such path between actors");
			}
			else {
				System.out.print("Path between "+actor1+" and "+actor2+": ");
				for(int i = 0; i < path.size(); i++) {
					if(i != path.size() -1) {
						System.out.print(path.get(i) + " --> ");
					} else {
						System.out.print(path.get(i));
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			input.close();
		}
	}
	
}
