package imageHandle.pretreament;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by CMM on 2017/7/6.
 */
public class ImageGray {
    public static final String GRAYIMAGES_PATH = "././HandledImages/grayImages/";

    /**
     * 灰度化
     *
     * @param imageMat
     * @param concreteContent
     * @param count
     * @param flag
     * @return
     */
    public static Mat gray(Mat imageMat, String[] concreteContent, int count, int flag) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat gray = new Mat();
        Imgproc.cvtColor(imageMat, gray, Imgproc.COLOR_RGB2GRAY);
        if (flag == 2) {
            String pictureType = concreteContent[0];
            String picturePath = GRAYIMAGES_PATH + pictureType + "-" + count + ".jpg";
            Imgcodecs.imwrite(picturePath, gray);
        }
        return gray;
    }
}
