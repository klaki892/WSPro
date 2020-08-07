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

import Game from "../Game";
import ChoiceSelectorView from "../../view/ChoiceSelectorView";
import Choice from "./Choice";


export default class ChoiceSelector {
    private game : Game
    private view : ChoiceSelectorView
    private options : Choice[];

    constructor(game : Game, options : Choice[]) {
        this.game = game;
        this.options = options;
        this.view = new ChoiceSelectorView(this);
    }
}