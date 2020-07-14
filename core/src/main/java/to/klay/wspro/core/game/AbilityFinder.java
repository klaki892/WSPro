package to.klay.wspro.core.game;

import to.klay.wspro.core.game.cardLogic.ability.TypedAbilityList;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public interface AbilityFinder {

    TypedAbilityList getAbilitiesForCard(Game game, PlayingCard card);
}
