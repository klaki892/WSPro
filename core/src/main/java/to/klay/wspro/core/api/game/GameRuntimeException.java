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

package to.klay.wspro.core.api.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameRuntimeException extends RuntimeException {

    private static final Logger log = LogManager.getLogger();
    //todo log errors in the logger

    public GameRuntimeException() {
        super();
    }

    public GameRuntimeException(String message) {
        super(message);
    }

    public GameRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameRuntimeException(Throwable cause) {
        super(cause);
    }

}
