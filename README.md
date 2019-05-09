# Backend for Purdue Ultimate Trivia
The backend implementation for an online trivia game based on facts about Purdue.

#### Key features
- Player accounts: track player statistics
- Global ranking: see how you as a player compare to other players
- Dynamic scoring: points earned based on response time and question difficulty

-----------------

#### Tools
- [Spring Boot](https://spring.io/projects/spring-boot) - RESTful Webservice
- [Apache Ignite](https://ignite.apache.org/index.html) - In-Memory DB
- [Project Lombok](https://projectlombok.org/) - Class builder
- [Apache Commons](https://commons.apache.org/)
- [Apache Maven](https://maven.apache.org/) - Project build, and dependency management

-----------------

#### TODO
- [ ] Integrate with a proper front-end
- [ ] Host the webservice on AWS
    - Facing an issue with connecting to an Ignite node started
    on EC2
    
-----------------

#### Launching the backend
Make sure that your `JAVA_HOME` points to some version of JDK 8.

Successfully launching the backend requires a running Apache Ignite node running locally.
The binaries for Ignite can be downloaded from [here](https://ignite.apache.org/download.cgi#binaries).
This project uses version 2.7.0. Instructions for starting  up your ignite node can be found [here](https://apacheignite.readme.io/docs/getting-started).
The latest jar for this project is found in the `target/` folder as `purduetrivia-X.X-SNAPSHOT.jar`.

Launch your ignite node as a server by navigating to your `IGNITE_HOME` and running: 
`bin/ignite.sh` (on Unix) or `bin/ignite.bat` (on Windows).

Run the jar by typing `java -jar purduetrivia-X.X-SNAPSHOT.jar`.

-----------------

#### Exposed REST Endpoints

Cross origins is currently `http://localhost:4200`.

##### Mappings for creating/validating a User
###### /user/validate?name=username&password=pswd

* Checks for whether a user with name `username` and password `pswd` exists
* Returns true if one exists, or false otherwise

###### /user/create?name=username&password=pswd
* Tries to create a new user with name `username` and password `pswd`
* Returns true if successful, false otherwise

##### Mappings for the playing game
###### /game?name=username
* Starts a new game for user with name `username`


###### /game/option?opt=response&time=millis
* Sends user response to the back for validation
* Returns a Pair of true and the correct answer if the answer was correct (in this case, the correct answer can be ignored)
* Returns a Pair of false and the correct answer if the answer was wrong


###### /game/next
* Gets the next question from the game
* As soon as `gameOver` is set to true, the game is over in the backend

##### Mappings for Leaderboard and User Info

###### /leaderboard/trophies?name=username
###### /leaderboard/ratio?name=username
###### /leaderboard/respTime?name=username

* Returns the ranking based on the given field (trophies, ratio, respTime) as well as the information for
the user with name `username`. The specified user is first in the list.
