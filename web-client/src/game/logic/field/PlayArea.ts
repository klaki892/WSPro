/*******************************************************************************
 *  WSPro - Trading Card Game Simulator
 *  Copyright (C) 2020  Klayton Killough
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

import {ZoneName} from "./ZoneName";
import PlayZone from "./PlayZone";
import * as PIXI from "pixi.js";
import PlayAreaView from "../../view/field/PlayAreaView";
import Player from "../Player";


const {CLIMAX, MEMORY, CENTER_STAGE_LEFT, CENTER_STAGE_RIGHT, STOCK, BACK_STAGE_RIGHT, WAITING_ROOM, CENTER_STAGE_MIDDLE, LEVEL, HAND, DECK, CLOCK, BACK_STAGE_LEFT} = ZoneName;
export default class PlayArea extends PIXI.Container{

    private zonesMap: Map<ZoneName, PlayZone> = new  Map<ZoneName, PlayZone>();
    view: PlayAreaView
    owner: Player

    constructor(owner : Player) {
        super();
        this.owner = owner;
        this.initZones();
        this.view = new PlayAreaView(this);
    }

    private initZones() {
        this.zonesMap.set(MEMORY, new PlayZone(MEMORY, this.owner));
        this.zonesMap.set(DECK, new PlayZone(DECK, this.owner));
        this.zonesMap.set(WAITING_ROOM, new PlayZone(WAITING_ROOM, this.owner));
        this.zonesMap.set(CLIMAX, new PlayZone(CLIMAX, this.owner));
        this.zonesMap.set(LEVEL, new PlayZone(LEVEL, this.owner));
        this.zonesMap.set(HAND, new PlayZone(HAND, this.owner));
        this.zonesMap.set(STOCK, new PlayZone(STOCK, this.owner));
        this.zonesMap.set(CLOCK, new PlayZone(CLOCK, this.owner));
        this.zonesMap.set(CENTER_STAGE_RIGHT, new PlayZone(CENTER_STAGE_RIGHT, this.owner));
        this.zonesMap.set(CENTER_STAGE_MIDDLE, new PlayZone(CENTER_STAGE_MIDDLE, this.owner));
        this.zonesMap.set(CENTER_STAGE_LEFT, new PlayZone(CENTER_STAGE_LEFT, this.owner));
        this.zonesMap.set(BACK_STAGE_LEFT, new PlayZone(BACK_STAGE_LEFT, this.owner));
        this.zonesMap.set(BACK_STAGE_RIGHT, new PlayZone(BACK_STAGE_RIGHT, this.owner));
    }

    getZone(name: ZoneName): PlayZone{
        let zone = this.zonesMap.get(name);

        if (zone !== undefined){
            return zone;
        } else {
            console.error(name + " was undefined in the PlayArea.");
            // @ts-ignore - this should break the code if it happens.
            return undefined;
        }
    }


}