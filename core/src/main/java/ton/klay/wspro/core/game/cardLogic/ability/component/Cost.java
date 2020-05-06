package ton.klay.wspro.core.game.cardLogic.ability.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ton.klay.wspro.core.game.scripting.IllegalScriptExecutionException;
import ton.klay.wspro.core.game.actions.GameAction;

import java.util.ArrayDeque;
import java.util.Collections;

/**
 * Component which abstractly defines a cost of an ability
 * @see <code>Weiss Schwarz Rule 8.4</code>
 */
public class Cost {

    private static final Logger log = LogManager.getLogger();

    private ArrayDeque<GameAction> actionQueue = new ArrayDeque<>();
    private LuaFunction scriptedCost;

    /**
     * Defines a cost to preform an effect based on the actions requied to pay the cost <br>
     * The actions for the cost will be preformed in the order in which they are stated as per the rules.
     * @see <code>Weiss Schwarz Rule 8.4.2.1</code>
     * @param actions - the actions needing to be preform so that the cost is fulfilled.
     */
    public Cost(GameAction[] actions){
        Collections.addAll(actionQueue, actions);
        throw new NotImplementedException();
        //FIXME - Only way to use a cost at the moment is through scripting. need to look into an alternative way.
    }

    /**
     * Defines a cost to preform an effect based on the actions requied to pay the cost <br>
     * @see <code>Weiss Schwarz Rule 8.4.1</code>
     */
    public Cost(GameAction action){
        this(new GameAction[]{action});
    }

    /**
     * Defines a cost to preform an effect based on the actions requied to pay the cost <br>
     * @param actions  - the actions needed be preformed as defined inside of a {@link LuaFunction} created by a script
     * @see <code>Weiss Schwarz Rule 8.4.1</code>
     */

    public Cost(LuaFunction actions){
        try {
            actions.checkfunction(); //check to make sure the value is a luaFunction or throw an error.
        } catch (LuaError ex){
            log.trace(ex);
            throw new IllegalScriptExecutionException("Lua Error occurred in cost-determining script");
        }

        scriptedCost = actions;
    }


    /**
     * Retrieves the list of actions (in order that they need to be preformed) for the cost to be fulfilled
     * @return an array where the specified actions are in the order in which they need to be preformed for the cost.
     */
    public GameAction[] getComponents(){
        throw new NotImplementedException();
        //FIXME - see other FIXME in this file.
//        return actionQueue.toArray(new GameAction[actionQueue.size()]);
    }

    /**
     * Tells if a cost has been defined by a user-made function or not.
     * @return - true if the cost is defined by a {@link LuaFunction}, false if the cost is defined in another way.
     * @see LuaFunction
     */
    public boolean isScriptedCost(){
        return (scriptedCost != null);
    }
}


