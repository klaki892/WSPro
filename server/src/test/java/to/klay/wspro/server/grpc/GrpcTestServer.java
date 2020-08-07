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

package to.klay.wspro.server.grpc;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.ServerGameManager;
import to.klay.wspro.server.setup.modules.ServerOptions;

import java.io.IOException;

public class GrpcTestServer  {

    private static final Logger log = LogManager.getLogger();

    @Inject
    @Named(ServerOptions.PORT_KEY)
    int port;




    public GrpcTestServer(){}

    public void startServer(ServerGameManager serverGameManager) throws IOException {

        Server server = ServerBuilder.forPort(port)
                .addService(new SetupGameService(serverGameManager))
                .addService(new PlayWeissService(serverGameManager))
                .build();

//        new Thread(() -> {
            try {
                server.start();
                log.info("Server started, listening on " + port);
                server.awaitTermination();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
                log.fatal(e);
                System.exit(-1);
            }
//        }).start();

    }
}
