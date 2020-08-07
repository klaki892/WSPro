import {ProtoGamePlayer, ProtoPlayingCard, ProtoPlayZone} from "./generated/ProtoEntities_pb";
import Game from "../logic/Game";
import PlayCard from "../logic/PlayCard";
import {ZoneName} from "../logic/field/ZoneName";
import Player from "../logic/Player";
import PlayActionUtilities from "../view/PlayActionUtilities";
import PlayZone from "../logic/field/PlayZone";

export default class ProtoUtils {
    static FromProto = class {
        static getZone(protoZone: ProtoPlayZone, game: Game) : PlayZone {
            let owner  = ProtoUtils.FromProto.getPlayer(protoZone.getOwner() as ProtoGamePlayer, game);
            return owner.playArea.getZone(ProtoUtils.FromProto.parseZoneName(protoZone.getZonename()));
        }
        static getPlayer(protoPlayer: ProtoGamePlayer, game: Game) : Player{
            let name = protoPlayer.getPlayername();
            return game.localPlayer.name === name ? game.localPlayer : game.oppPlayer;
        }

        static parseZoneName(number : number) : ZoneName{
            //credit: https://stackoverflow.com/a/42623905/4411099 because TS  enums suck.
            let keys = Object.keys(ZoneName);
            return ZoneName[keys[number] as keyof typeof ZoneName];
        }
        static createNewCard(sourceCard: ProtoPlayingCard, sourceZone: ProtoPlayZone, game: Game) : PlayCard {
            let newCard =  new PlayCard(sourceCard.getId(), game);
            newCard.guid = sourceCard.getGuid();
            let cardView = newCard.view;
            //draw the newcard in its source zone
            let zone = this.getZone(sourceZone, game);
            PlayActionUtilities.getStageView(game).addChild(cardView);
            cardView.position = zone.view.getNextCardPosition();

            return newCard;
        }

    }



}

