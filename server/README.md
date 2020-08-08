# WSPro Server: Runner of core games

Responsible for creating/hosting games and allowing players to connect over a network to play the game.

## Building 
#### Pre-requisites:

 - Java JDK 8+
 - Maven 3.3+
 - (Optional) Git 
 
 1. from the root of the project (WSPro)
 2. package the projects (builds Server and its dependency core)
    ` mvn package`
    
 3. in `server/target` will be a `server-<version>.jar`

## Configure Server
The Server needs to find and retrieve two pieces of information before creating any games:
- Card Source - a place to retrieve information about a card based on its Card ID.
- Ability Script Source - a place to retrieve scripts for the cards to be played
#### Card Source

Currently Supported Card info sources:
 - [WeissSchwarz-ENG-DB](https://github.com/CCondeluci/WeissSchwarz-ENG-DB) by [CCondeluci](https://github.com/CCondeluci) 
    1. Clone or download the project using git into a directory 
        - `git clone https://github.com/CCondeluci/WeissSchwarz-ENG-DB.git`
    2. pass the `/DB` folder into the `.propreties` file
    ```
   wspro.server.card.source.type=WS_ENG_DB
   wspro.server.card.directory=C:\\WeissSchwarz-ENG-DB\\DB
    ```

Future Sources
 - Database via a open-source schema 
 - json files via a open-souce schema 


#### Ability Script Source 

currently Supported ability info sources:
- N/A

Future Sources:
 - DIRCTORY - a directory containing .lua files unique names for each card
 - Database via a open-souce schema
 - json files via a open-souce schema
  
  
### Properties file
When run from commandline the server needs to know where it can get required properties:

A example of a `.properties` for windows is shown below
```
wspro.server.debug=false
wspro.server.loglevel=WARN
wspro.server.gamelimit=1
wspro.server.card.source.type=WS_ENG_DB
wspro.server.card.directory=C:\\WeissSchwarz-ENG-DB\\DB
wspro.server.ability.source.type=DIRECTORY
wspro.server.ability.directory=/
wspro.server.port=3155
```

### Run the Server
`java -jar server-<version>.jar --properties=<path to config.properties>`

Ex: 
```
java -jar "server\target\server-0.1.jar --properties="server\target\serverConfig.properties"
```

### Manual Testing: Create and run a game
To Create a Test game:
1. From the root of the repository: `mvn install`
2. Run the server with a defined card source on localhost `java -jar server-<version>.jar --properties=<path to config.properties>`
3. In a different terminal navigate to the server root directory: `cd server`
4. Run [CreateGameFromEncoreDecks.java](https://github.com/klaki892/WSPro/blob/master/server/src/test/java/to/klay/wspro/server/grpc/manual/CreateGameFromEncoreDecks.java) \
    `mvn exec:java  -D"exec.classpathScope"="test" -D"exec.mainClass"="to.klay.wspro.server.grpc.manual.CreateGameFromEncoreDecks"`

    1. Enter a **HTTP** EncoreDeck API URL: (e.g. `http://www.encoredecks.com/api/deck/rtJoTS6Hj` )
        1. Note: HTTPS is not supported while testing
    2. Enter the server's port (e.g. 3155)
    3. The file will output a game Token GUID save this for future steps. ex: `dfed87be-0d5d-3835-bdf0-61e80973ceef
   
    Example output:
    ```
    EncoreDeck API Url: 
    http://www.encoredecks.com/api/deck/rNai8eekx
    Local server port: 
    3155
    12:41:37.445 [main] INFO  to.klay.wspro.server.grpc.manual.ManualTestAutomatedCommands - Connection InfoPlayer 1 Name: p1Player 2 Name: p2Game ID: dfed87be-0d5d-3835-bdf0-61e80973ceef
    Connection InfoPlayer 1 Name: p1Player 2 Name: p2Game ID: dfed87be-0d5d-3835-bdf0-61e80973ceef
    dfed87be-0d5d-3835-bdf0-61e80973ceef
    ```
   
   This creates a game with players named `p1` and `p2` both are using the same deck.
   
4. Run [GameListeningClient.java](https://github.com/klaki892/WSPro/blob/master/server/src/test/java/to/klay/wspro/server/grpc/manual/GameListeningClient.java) or connect with a WSPro game client \
`mvn exec:java  -D"exec.classpathScope"="test" -D"exec.mainClass"="to.klay.wspro.server.grpc.manual.GameListeningClient"`\
Note: output is as verbose as Core's testing CommandLinePlayer, an IDE is recommended to comprehend the output
    1. enter the local server port (ex: `3155`)
    2. enter the player name (`p1` or `p2`) 
    3. enter the game ID from the previous step
    4. The Client will start listening for game events and will let you respond to Play Choices by entering the corresponding number.
        1. GameListentingClient will tell the game to start if it is the last player to connect.
    
    Example Output:
    ```
   Local server port: 3155
   Input Player Name: p2
   Enter Game ID: dfed87be-0d5d-3835-bdf0-61e80973ceef
   
   Next Resposne: 
   cardDiscardedTrigger { ...
    ```
### Contributing

Join the [Discord Server](https://discord.gg/hkW5Xwc) for all questions relating to development, contributing, bug reports, and feature requests.
