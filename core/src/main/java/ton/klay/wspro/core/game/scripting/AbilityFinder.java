package ton.klay.wspro.core.game.scripting;

import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.cardLogic.ability.TypedAbilityList;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public interface AbilityFinder {

    public TypedAbilityList getAbilitiesForCard(Game game, PlayingCard card);
}
