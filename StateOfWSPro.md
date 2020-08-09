# State of WSPro 
Last Updated: August 9th, 2020

**TL;DR: WSPRO can played via command line. no established GUI yet, no scripted cards yet.**

**Because of my upcomming job start (August 10th, 2020) I will not own the rights to any further work done on the project and thus have decided to stop coding until i can get those rights back.**

**Until then, [I need collaborators for WSPro to become playable](https://discord.gg/6fszwZK).**

These are the major milestones and the progress surrounding them. \
See [Scrum Board.txt](https://github.com/klaki892/WSPro/blob/master/Scrum%20Board.txt) for a very detailed progress list that I used for getting the project to its current state.

# Core Gameplay Engine

## Done 
- Supports all current competitive game rules. **Done**
- Complete a vanilla no card effect game from start to finish.
## Not Done
- Replay Engine 
- Hide card information from respective players (ex: opponent shouldnt recieve any information from the engine about a card going from deck -> stock)

#Scripting Engine

## Done
- [Proof of concept script created (Auto (3) Encore script created)]((https://wspro.klay.to/#h.oc2mf6jhqyzm))

## Not Done
- Support scripting basic card effects (Draw a card) **Not Tested *(Via Lua)***
- Create Helper Scripts for faster Script Application
- Climax Card Scripts
- At least 1 card set scripted

# Server
## Done
- Can Read from at least 1 card repository source
- Can create and run a game from start to finish
- Can interact fully with remote players using GRPC. 
## Not Done
- Can Read from at least 1 Ability repository source
- Save game log for review
- Lobby server for matching players and starting the game

# Web Client
## Done
- Communicate with the server for basic events
- draw game board
## Not Done
- Make all choices needed to play the game
- Sounds
- Card Animations
- Player name selection
- Player card source selection
- EncoreDecks API deck selection
- Image source selection


# About Me & History of the Project Development 

### Who I am
I started playing Weiss in fall 2016 right after I entered college with a friend when we discovered a card shop showing off trial decks on campus. I had played Yu-Gi-Oh before, owned a simple deck from Target as a kid and played casually on Dueling Network more so than physically. Never went to card shop.

Weiss would be the first TCG I would buy seriously into. The community was small but welcoming; eventually I invested in some booster boxes, pulled my first SSP, and went to a SpringFest team tournament and got second place. 

I would casually play on TradeCardsOnline (RIP) to get more practice, and tried 2 Skype games before casting off that style of play. As I was playing i would randomly see other fully rule-enforced online TCG systems. Tried out Magic Arena, Forge (MTG), discovered YGOPro and would constantly wonder as the Computer Science kid I was, why something similar didn’t exist for Weiss Schwarz? 

Because no public simulator existed, it would be a perfect opportunity for me to get a project on my resume for internships. Games after all put into practice a lot of computer science concepts, and to show a project being used by others around the world is an achievement. So on August 7th, 2017 I sat down and started the first iteration of WSPro. 

### How'd WSPro get here?
The development timeline of the project is a rough one with many patches. It’s been 3 years since initial code was written. 

The project over the years has been done in sprints, I would work actively on it for 2-3 weeks before either getting burned out (worked on it 12+ hours at a time), or blocked by running into a wall with design, or "life happened".(Growing college kid after all)

#### Q4 2017
- **Ground break:** First Design, and initial code creation (the play field, initial design doc, untested concept for Lua scripts)

#### Sep 2018 - Dec 2018
-	Another Weiss community member (greatwanz) announced they were working on a rule-enforced simulator, and showed off pictures of the deck creator and game board in a TCO discord. Gaining support and assistance from well-known community members.\
\
Seeing their progress compared to mine discouraged me from wanting to continue. No reason to split the community after all. \
\
But then in December 2018, he abandoned implementing automatic rule-enforcement, and expressed that they would not make the program open source (had valid concerns) \
\
Because of this, I saw the gap the community needed and the fire was re-lit to resume work once more.
#### Dec 2018 – Jan 2019
-	**Progress Done:** Massive refactoring for design patterns, and first testable proof of concept mulligan phase, proof of concept Lua script calling from java (not used in game yet)
May 5th 2020 – August 9th 2020

-	At the beginning of May I graduated from college, and had my dream job waiting for me in 3 months. Realizing this would be my last “Summer break” in life before the 40 hour grind, I decided to quit the job  I had during college so that I could be a NEET, enjoy fleeting freedom, and finish WSPro. \

-  However, I was now also up against a time limit for finishing the project.\
 
My dream job came with the legal clause that basically said the moment I started working with them, any programming work I did either for them or in my free time now legally belonged to the company and I owned no rights to it. \

**This effectively stops me from working on open source or any personal projects** as I’m no longer contributing as myself. For personal and legal liability reasons I did not want to slap a company’s copyright on this project I had now envisioned being reality for 3 years.
  
All hope is not lost yet, because the company did have a process for gaining those explicit rights back to continue working on an open source project under your own name, **_but there was no guarantee they would grant you permission._**
 
If that wasn’t enough, I was also personally sick of my entire programming career consisting of personal projects that never saw the light of day because I would work on them, get them to some partial state of working and then abandon them, never releasing them to the world.
 
I did not want this to happen to WSPro because I believed it had the potential to be the most impacting of my projects to a greater community. 

And thus, the third push to get WSPro off the ground began. 

For various reasons I was delayed and interrupted in my ability to work on the project (preparing to move cross country, 2 week notice of quitting current job, etc.) and thus did not resume serious development until mid-June.

With my new experience gained over the college years, I did the third major overhaul to how the project was structured. Within weeks, WSPro’s core system of being able to play the game was fully functional and although it was a vanilla game with no abilities you could fully play a game of Weiss from start to finish and for the first time in 3 years, scripting was integrated into the game. 

[A Successful proof of concept was complete.](https://wspro.klay.to/#h.oc2mf6jhqyzm) 
## Present day & Future
Unfortunately, because “life happens” although I tried very hard, the project was not in the state I wanted to release it to the world by my deadline. I envisioned having a complete product and telling the community “Hey this exists. Here’s the link, go play” and watching the community go wild. (I’m still dreaming for this opportunity)

I’m very happy with the progress that I have made as the project is in a usable state for others to expand upon, but I am mad I'm release it in a non-playable state, much less an alpha state.

Now until I get my own rights back, **I need the community** to keep the project alive.

Therefore a day before my new job I silently release the project to the world. In hopes that a curious developer or person sees it and [wants to find out where do we go from here?](https://discord.gg/6fszwZK)  Honestly I don’t know where it goes from here. But I don’t want WSPro to go silently into the night. 



