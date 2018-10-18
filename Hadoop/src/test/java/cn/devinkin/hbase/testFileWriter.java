package cn.devinkin.hbase;

import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class testFileWriter {
    @Test
    public void test() throws IOException {
        FileWriter fileWriter = new FileWriter("/tmp/stormdata/stormoutput/" + UUID.randomUUID());
        fileWriter.write("asdlkfjasldkfjas");
        fileWriter.flush();
    }
}
