# WSPro: A Rule-Enforced WS-TCG Simulator 
![WSPro Logo](/web-client/public/logo.png)

**WSPro is still in development and has not been released for general / non-programmatic use.**

### About
The Goal of this project to provide a means to simulate a game of WS while automatically enforcing all game and card rules.


### State of Project: Usable; Not Playable 
To see the current roadmap and development timeline of the project, view the [State of WSPro](StateOfWSPro.md) document.

### Contributing

Join the [Discord Server](https://discord.gg/6fszwZK) for all questions relating to development, contributing, bug reports, and feature requests.

----
Development
-----
See the respective modules for more about how the WSPro ecosystem works:

#### [Core](https://github.com/klaki892/WSPro/tree/master/core)
Heart of the Project. \
Contains the rule engine & the Lua scripting engine to execute a game from start to finish.

#### [Server](https://github.com/klaki892/WSPro/tree/master/server)
Responsible for creating/hosting games and allowing players to connect over a network.\
Utilities Grpc and Protocol Buffers to allow for easy implementation of clients. 


#### [Web-Client](https://github.com/klaki892/WSPro/tree/master/web-client)
***Experimental*** browser client to play WSPro over a network. \
Meant as a proof of concept avenue for using the simulator.

#### Helper Modules 
#### [Core-Proto](https://github.com/klaki892/WSPro/tree/master/core-proto)
Generates a `.proto` definition of [Core](https://github.com/klaki892/WSPro/tree/master/core) model classes for serialization and network use.

#### [Documentation](https://github.com/klaki892/WSPro/tree/master/documentation)
Design Documents breaking down the early development of the project before it went public.  

---
### License
WSPro is free and open source software licensed under the GNU Affero General Public License, version 3. Dependencies and resources may be provided under different licenses. Please see [LICENSE](https://github.com/klaki892/WSPro/blob/master/LICENSE) for more details.


Weiss Schwarz is copyright of Bushiroad Inc. 
This project is **not** affiliated with or endorsed by Bushiroad.
