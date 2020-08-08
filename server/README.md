# WSPro Server: Runner of core games

Responsible for creating/hosting games and allowing players to connect over a network to play the game.

## Building 
#### Pre-requisites:

 - Java JDK 8+
 - Maven 3.3+
 
 1. from the root of the project (WSPro)
 2. package the projects (builds Server and its dependency core)
    ` mvn package`
    
 3. in `WSPro/server/target` will be a `server-<version>.jar`

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

A example of a `.properties` is shown below
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

### Contributing

Join the [Discord Server](https://discord.gg/hkW5Xwc) for all questions relating to development, contributing, bug reports, and feature requests.
