package sample;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Тим on 17.04.2017.
 */
public class Reader {
    short[] indices;
    float[] positions;
    float[] normals;
    void readFile(){
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream("Duck/indices.bin");
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();

            indices = new short[bytes.length / 2];
            ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(indices);

            inputStream = new FileInputStream("Duck/positions.bin");
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();

            positions = new float[bytes.length / 4];
            ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer().get(positions);

            inputStream = new FileInputStream("Duck/normals.bin");
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();

            normals = new float[bytes.length / 4];
            ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer().get(normals);
        }

        catch (IOException io) {

        }
    }
}
