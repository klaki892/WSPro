package ton.klay.wspro.core.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

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
