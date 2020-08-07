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

package to.klay.wspro.server.grpc.manual;

import co.unruly.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.setup.modules.ServerOptions;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class PropertiesFileConfigModule extends ServerOptions {

    private static final Logger log = LogManager.getLogger();


    public PropertiesFileConfigModule() throws URISyntaxException {
        URL resource = this.getClass().getClassLoader().getResource("server-config.properties");
        config = Configuration.from(Configuration.properties(new File(resource.toURI()).getAbsolutePath()));
    }

    @Override
    public void initConfig() {
    }
}
