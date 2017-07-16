import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.HOGDescriptor;

/**
 * Created by CMM on 2017/7/9.
 */
public class SVMTest {

    String path = "././HandledImages/test/test.jpg";
    @Test
    public void test() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat imageMat = Imgcodecs.imread(path);
        MatOfFloat a = new MatOfFloat();
        int width = imageMat.width();
        int height = imageMat.height();
        HOGDescriptor hog = new HOGDescriptor(new Size(width, height), new Size(16, 16), new Size(8, 8), new Size(8, 8), 9);
        hog.compute(imageMat, a);
        for(int i=0;i<a.toArray().length;i++){
            System.out.print(a.toArray()[i]+" ");
        }
        System.out.print(a.toArray().length);
        float[] aa = a.toArray();
        hog.setSVMDetector(a);
    }
}
