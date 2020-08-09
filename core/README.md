# WSPro Core: Heart of the Simulator

The Core module of WSPro contains the automatic game playing engine written in Java and a Lua scripting engine for interpreting scripts for cards.

Although a Lua Scripting engine is included, the scripting interface is generic enough for theoretical support of other languages. 

The tests included in this module allow the engine to be tested via AI players and a Command Line interface. 
However this is *not meant for general use*. Please use an actual game player (i.e. [Web-Client](https://github.com/klaki892/WSPro/tree/master/web-client)) to play the game 

See the Proof of Concept command line game on the website

## Scripting 

The following is a [portion](https://github.com/klaki892/WSPro/blob/master/core/src/test/resources/lua/TestEncoreAbility.lua) of the lua script for creating the 3 cost encore auto ability found on all characters:
```lua
function getAbilities(card)
    thisCard = card
    a1 = Ability:createAutomatic(card)

    a1:setKeywords(AbilityKeyword.KEYWORD_ENCORE)
    a1:setTriggerCondition(a1TriggerCondition)

    c1 = Cost:createStockCost(card, 3)
    a1:setCost(c1)

    return a1;
end

```

Scripting is made possible through use of LuaJ and its bindings. little documentation exists, see the [test lua files]((https://github.com/klaki892/WSPro/blob/master/core/src/test/resources/lua/TestEncoreAbility.lua)) for a better understanding

## Building

#### Pre-requisites:

 - Java JDK 8+
 - Maven 3.3+
 
#### Compiling:
Using command line / terminal:

```
cd core
mvn compile
```
## Contributing
Join the [Discord Server](https://discord.gg/6fszwZK) for all questions relating to development, contributing, bug reports, and feature requests.


### documentation
Documentation on this system is sparse, see relevant files in the [Documentation section](https://github.com/klaki892/WSPro/tree/master/documentation) or ask in the discord.
#### See it in action: command line game test:

To see output more clearly, I recommend using an IDE (e.g. Intellij) as tests are verbose. 

To test the system you can play with a test deck (42 of the same card, 8 no-effect climaxes) \
In an IDE: run [TestCommandLineGame.java](https://github.com/klaki892/WSPro/blob/master/core/src/test/java/to/klay/wspro/core/test/manual/TestCommandLineGame.java)

or execute via command line:
```
mvn compiler:testCompile
mvn exec:java  -D"exec.classpathScope"="test" -D"exec.mainClass"="to.klay.wspro.core.test.manual.TestCommandLineGame"
```

The output is **not user-friendly** and choices are made by typing in numbers via spaces. \
You will see the log say cards are being moved from hand, deck, and other zones. 

Beginning of game example (Mulligan):

```
Play Choice - UP_TO- 5
-----------
#0 | PlayChoice{choiceType=CHOOSE_CARD, card=TestEncoreAbility || [Test Card]}
#1 | PlayChoice{choiceType=CHOOSE_CARD, card=TEST/TEST || [Test Climax Card]}
#2 | PlayChoice{choiceType=CHOOSE_CARD, card=TestEncoreAbility || [Test Card]}
#3 | PlayChoice{choiceType=CHOOSE_CARD, card=TestEncoreAbility || [Test Card]}
#4 | PlayChoice{choiceType=CHOOSE_CARD, card=TestEncoreAbility || [Test Card]}
#5 | PlayChoice{choiceType=CHOOSE_ACTION, action=END_ACTION}
```
`0 1`

In the example, you move 2 cards from hand to waiting room and the game continues.
