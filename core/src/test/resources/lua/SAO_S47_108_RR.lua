--
-- Created by IntelliJ IDEA.
-- User: Klayton
-- Date: 8/12/2017
-- Time: 1:57 AM
-- To change this template use File | Settings | File Templates.
--

function SAO_S47_108_RR.initStats(c)
    local card = SAO_S47_108_RR.createCard(CardType.CHARACTER);

    card:setID("SAO/S47-108 RR");
    card:setLevel(3);
    card:setCost(2);
    card:setDescription("fill this out later");
    card:setPower(9000);
    card:setSoulCount(2);
    card:setColor(CARD_COLOR_RED)
    card:setAffiliation(AFFILIATION_SCHWARZ)
end

function SAO_S47_108_RR.InitalizeCard(c)

    local e1 = Effect.createEffect(EFFECT_ABILITY_CONTINUOUS)
    e1:setCategory(EFFECT_CATEGORY_CONTINUOUS)
    local searchCritClimaxes = SearchCritera.byCardType(CARD_TYPE_CLIMAX);
    local climaxesInWaitingRoom = Duel.getZoneGroupCount(c:getOwner(), ZONE_WAITING_ROOM, searchCritClimaxes)
    local earlyPlayCond = SAO_S47_108_RR.earlyPlay(c, climaxesInWaitingRoom)
    e1:setCondition(earlyPlayCond);
    e1:setOptional(EFFECT_OPTION_MANDATORY)
    e1:setCode(EFFECT_CODE_MINUS_LEVEL)
    e1:setTarget(EFFECT_TARGET_SELF)
    e1:setValue1(1);
    c:registerEffect(e1)

    local e2 = Effect.createEffect(EFFECT_ABILITY_CONTINUOUS)
    e2:setCategory(EFFECT_CATEGORY_CONTINUOUS)
    local searchCritTrait = SearchCritera.byCardTrait("Avatar", "Net");
    local otherCards = Duel.getZoneGroupCount(c:getOwner(), ZONE_STAGE, searchCritTrait)
    e2:setCondition(c:inZone(ZONE_STAGE))
    e2:setOptional(EFFECT_OPTION_MANDATORY)
    e2:setCode(EFFECT_CODE_ADD_POWER)
    e2:setTarget(EFFECT_TARGET)
    e2:setValue(500 * otherCards)
    c:registerEffect(e2)

    local e3 = Effect.CreateEffect(EFFECT_ABILITY_AUTOMATIC)
    e3:setCategory(EFFECT_CATEGORY_ONESHOT)
    e3:setTrigger(CONDITION_ON_PLAY_FROM_HAND)
    e3:setOptional(EFFECT_OPTION_OPTIONAL)

    local cost1 = EffectCost.createCost(EFFECT_COST_DISCARD)
    cost1:setValue(1);
    e3:setCost(cost1);

    e3:setCode(EFFECT_CODE_HEAL_TO_STOCK)
    e3:setTarget(Duel.getCurrentTurnPlayer)
    e3:setValue1(1)
    c:registerEffect(e3);




end

function SAO_S47_108_RR.earlyPlay(c, climaxesInWaitingRoom)
    return c:inZone(ZONE.HAND) and climaxesInWaitingRoom <= 2
end

function SAO_S47_108_RR.atkBoost(c)
    local searchCritTrait = SearchCritera.byCardTrait("Avatar", "Net");
    local otherCards = Duel.getZoneGroupCount(c:getOwner(), ZONE.STAGE, searchCritTrait)

    return
end
