Six Degrees of Kevin Bacon
This program allows the user to search for pairs of actors and connect them using one of the shortest possible paths between pairs based on the movies they have acted in. The program reads data from a file representing actors, and then allows the user to search this data for individual actors and find the possible connections between pairs of actors through the movies in which they have acted.

The data is read from the file "tmdb_5000_credits.csv", which is available for download through the Kaggle’s TMDB 5000 Movie Dataset: https://www.kaggle.com/tmdb/tmdb-movie-metadata# or via this link on Google Drive. In order to download this file, you will need credentials for the Kaggle site.

Requirements
- Java version 8 or later
- JSON-simple library to parse JSON data.

How to Run
1. Download or clone the repository.
2. Open the command prompt or terminal in the project directory.
3. Run the command javac SixDegrees.java to compile the java file.
4. Run the command java SixDegrees <file_path> to run the program, where <file_path> is the absolute path to the tmdb_5000_credits.csv file.

How to Use
1. Once the program is running, it will prompt the user to enter the name of the first actor.
2. After entering the name, the program will prompt the user to enter the name of the second actor.
3. The program will then find one of the shortest paths between the two actors based on the movies they have acted in, and display it on the screen.
4. If no such path exists, the program will indicate so.

Note: The names of actors are not case sensitive, so the program will work regardless of the uppercase/lowercase characters provided by the user.
