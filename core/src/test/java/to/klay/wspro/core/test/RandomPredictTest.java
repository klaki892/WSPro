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

package to.klay.wspro.core.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;

public class RandomPredictTest {

    private static final Logger log = LogManager.getLogger();

    public static void main(String... args) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Long value = 1L;
       SecureRandom sRandom= new SecureRandom(longToBytes(value));
       long seed = sRandom.nextLong();
        Random random = new Random(seed);


        System.out.println("srandom seed: " + bytesToLong(sRandom.generateSeed(longToBytes(value).length)));
        System.out.println("Srandom " + sRandom.nextInt());
        System.out.println("random " + random.nextInt());

//        Scanner in = new Scanner(System.in);
//
//        switch(in.nextInt()) {
//            case 0:
//
//        }
    }

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();
    }

}
