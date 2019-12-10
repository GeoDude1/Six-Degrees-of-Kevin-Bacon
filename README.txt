My assignment 2 is complete and it does the tasks that it is required to do. I am able to run it in the terminal on my mac device. Please follow the instructions below to compile the program in the terminal. 

The Graph class implements the shortest path and the way to store the relations. Then, the main class just reads the CSV file, parse the values it needs (using the JSON library) and finally asks the user for the path to look for. 

The runtime of the assignment is Θ(|V| + |E|) because BFS (Breadth First Search) is used
.
The comments located in the code show what is going on and should help the reader understand what is happening when the code is passed.

If the reader or grader does not have a copy of the tmdb_5000_credits.csv: unzip the zip file in the github repo that contains this file. Then add it to the same directory that all the other files will be put in.

Use this command when running the code in terminal: javac -cp "./:json-simple-1.1.1.jar:commons-csv-1.7.jar" *.java
java -cp "./:json-simple-1.1.1.jar:commons-csv-1.7.jar" A3 tmdb_5000_credits.csv

