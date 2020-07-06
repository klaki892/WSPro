package ton.klay.wspro.core.game.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

import java.util.Optional;

/**
 * Represents the altercation between two cards during battle
 */
public class Combat {

    private static final Logger log = LogManager.getLogger();
    private final AttackType attackType;
    private  PlayingCard attackingCharacter;
    private  PlayingCard defendingCharacter;

    //todo check for movement of attacking character and defending character (if exists)
    // if movement, remove state of attacked or defending

    public Combat(AttackType attackType, PlayingCard attackingCharacter) {
        this.attackType = attackType;
        this.attackingCharacter = attackingCharacter;

        //todo assign battle states
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public Optional<PlayingCard> getAttackingCharacter() {
        return Optional.ofNullable(attackingCharacter);
    }

    public void setAttackingCharacter(PlayingCard attackingCard) {
        this.attackingCharacter = attackingCard;
    }

    public Optional<PlayingCard> getDefendingCharacter() {
        return Optional.ofNullable(defendingCharacter);
    }

    public void setDefendingCharacter(PlayingCard defendingCharacter) {
        this.defendingCharacter = defendingCharacter;
    }
}
