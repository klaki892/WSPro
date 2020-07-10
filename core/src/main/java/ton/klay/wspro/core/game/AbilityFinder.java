package ton.klay.wspro.core.game;

import ton.klay.wspro.core.game.cardLogic.ability.TypedAbilityList;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public interface AbilityFinder {

    TypedAbilityList getAbilitiesForCard(Game game, PlayingCard card);
}
