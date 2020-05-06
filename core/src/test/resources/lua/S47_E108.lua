--
-- Created by IntelliJ IDEA.
-- User: Klayton
-- Date: 1/2/2018
-- Time: 7:26 PM
-- To change this template use File | Settings | File Templates.
--

function S47_E108.init_card(c)

    --early play, 2 or less climaxes


    local a1 = AbilityFactory:createAbility(ABILITY_CONTINUOUS, e1);
    local a2 = AbilityFactory:createAbility(ABILITY_CONTINUOUS, e2);
    local a3 = AbilityFactory:CreateAbility(ABILITY_AUTOMATIC, S47_E108.cost1, con1, e3);


    c:RegisterAbility(a1);
    c:RegisterAbility(a2);
    c:RegisterAbility(a3);


end

function S47_E108.cost1() -- discard 1 card.
    --todo player choose a card in hand. (check that there is at least 1 card in hand). move to waiting room.
end
