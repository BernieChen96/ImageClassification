package imageHandle.hog;

import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by 51157 on 2017/7/10.
 */
public class HogTest {
    @Test
    public void openCVGetHog() {
        String imagePath = "././HandledImages/specifiedArea/bus-33.jpg";
        Mat imageMat = new Mat();
        imageMat = Imgcodecs.imread(imagePath);
    }
}
