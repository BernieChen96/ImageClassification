package imageHandle.textProcessing;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by 51157 on 2017/7/6.
 */
public class ReadImageParitionTxtTest {
    public static String handledFilePath = "././HandledText/initTxt/";

    @Test
    public void read() {
        ReadImageParitionTxt readImageParitionTxt = new ReadImageParitionTxt();
        readImageParitionTxt.read();
        readImageParitionTxt.write();
    }

    @Test
    public void createNewFile() {
        File file = new File(handledFilePath + "000001" + ".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
