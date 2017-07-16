package imageHandle.pretreament;

import constant.Constant;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

/**
 * Created by CMM on 2017/7/10.
 */
public class InputImage {
    public static float[] inputImage(String filePath) {
        Mat imageMat = new Mat();
        imageMat = Imgcodecs.imread(filePath);
        Imgproc.resize(imageMat, imageMat, new Size(Constant.PICTURE_SIZE_WIDTH, Constant.PICTURE_SIZE_HEIGHT));

        HOGDescriptor hog = new HOGDescriptor(new Size(Constant.PICTURE_SIZE_WIDTH, Constant.PICTURE_SIZE_HEIGHT), new Size(16, 16), new Size(8, 8), new Size(8, 8), 9);
        MatOfFloat descriptorsOfMat = new MatOfFloat();
        hog.compute(imageMat, descriptorsOfMat);
        return descriptorsOfMat.toArray();
    }

    public static float[] inputImage(Mat imageMat) {
        Mat mat = new Mat();
        Imgproc.resize(imageMat, mat, new Size(Constant.PICTURE_SIZE_WIDTH, Constant.PICTURE_SIZE_HEIGHT));

        HOGDescriptor hog = new HOGDescriptor(new Size(Constant.PICTURE_SIZE_WIDTH, Constant.PICTURE_SIZE_HEIGHT), new Size(16, 16), new Size(8, 8), new Size(8, 8), 9);
        MatOfFloat descriptorsOfMat = new MatOfFloat();
        hog.compute(mat, descriptorsOfMat);
        return descriptorsOfMat.toArray();
    }
}
