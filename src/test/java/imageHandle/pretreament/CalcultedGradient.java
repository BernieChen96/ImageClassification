package imageHandle.pretreament;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by CMM on 2017/7/6.
 */
public class CalcultedGradient {
    /**
     * 通过伽马校正的图片
     * 计算图像梯度
     * row行，col列
     */
    @Test
    public void calculatedGradient() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread("HandledImages/gammaCorrect/000001-gamma.jpg", -1);
        System.out.print(src.width() + src.depth());
        Mat gradientImage = new Mat();
        Imgproc.Sobel(src, gradientImage, -1, 1, 1, 1, 1, 0);
        Imgcodecs.imwrite("HandledImages/gradientImages/000001-gradient.jpg", gradientImage);
    }

}
