---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by Klayton.
--- DateTime: 7/7/2020 2:11 AM
---

-- return a list of all abilities (of all types

-- define each ability


function getAbilities(card)
    thisCard = card
    a1 = Ability:createAutomatic(card)

    a1:setKeywords(AbilityKeyword.KEYWORD_ENCORE)
    a1:setTriggerCondition(a1TriggerCondition)

    c1 = Cost:createStockCost(card, 3)
    a1:setCost(c1)

    return a1;
end

function a1TriggerCondition(trigger)
    local aCardWasMoved = trigger:getTriggerName() == TriggerName.CARD_MOVED
    if not(aCardWasMoved) then
        return
    end

    local cardMovedWasThisCard = trigger:getDestinationCard() == thisCard
    local wasMovedFromStage = Zones:isOnStage(trigger:getSourceZone())
    local wasMovedToWaitingRoom = trigger:getDestinationZone():getZoneName() == Zones.ZONE_WAITING_ROOM

    if aCardWasMoved and cardMovedWasThisCard
    and wasMovedFromStage and wasMovedToWaitingRoom then

        standby = Ability:standbyAutomaticAbility(a1)

        --    create the effect here
        movedTrigger = trigger
        e1 = Effect:createOneShotEffect(doEncore)
        standby:setEffect(e1)
        c1 = standby:getCost()
    end
end

function doEncore()
    local waitingRoom = movedTrigger:getDestinationZone()
    local stagePosition = movedTrigger:getSourceZone()
    local topOfZone = Utilities:getTopOfZoneIndex(stagePosition)
    if  c1:isPayable() then
    --    ask if they would like to perform
        if Utilities:getConfirmationFromPlayer(thisCard:getMaster()) then
            Commands:payCost(c1, thisCard)
            Commands:moveCard(thisCard, waitingRoom, stagePosition, topOfZone,
                    CardOrientation.REST, GameVisibility.VISIBLE_TO_ALL, TriggerCause.CARD_EFFECT, thisCard:getMaster())
            --Commands:announce(TriggerName.CARD_ENCORED, thisCard)
        end
    end
end


