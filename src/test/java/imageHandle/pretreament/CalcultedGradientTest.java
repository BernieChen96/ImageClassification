package imageHandle.pretreament;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by CMM on 2017/7/6.
 */
public class CalcultedGradientTest {
    String primaryFilePath = "././JPEGImages/000001.jpg";
    String gammaFilePath = "././HandledImages/gammaCorrect/000001-gamma.jpg";

    /**
     * 通过伽马校正的图片
     * 计算图像梯度
     * row行，col列
     */
    @Test
    public void calculatedGradient() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(gammaFilePath, -1);
        Mat gradientImage = new Mat(src.rows(), src.cols(), src.type());
        Mat gradientImageX = new Mat();
        Mat gradientImageY = new Mat();
        Imgproc.Sobel(src, gradientImageX, CvType.CV_64F, 1, 0, 1, 1, 1);
        Imgproc.Sobel(src, gradientImageY, CvType.CV_64F, 0, 1, 3, 1, 0);
//        Imgproc.Sobel(src, gradientImage, CvType.CV_64F, 1, 1, 3, 1, 0);
        int width = src.cols();
        int height = src.rows();
        int index = 0;
        double[] dataGradient = new double[width * height];
        double[] dataGradientX = new double[width * height];
        double[] dataGradientY = new double[width * height];
        gradientImageX.get(0, 0, dataGradientX);
        gradientImageY.get(0, 0, dataGradientY);
        //计算梯度方向
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                double gradientX = dataGradientX[index];
                double gradientY = dataGradientY[index];
                dataGradient[index] = Math.sqrt(Math.pow(gradientX, 2) + Math.pow(gradientY, 2));
                double gradientDirection = Math.atan2(gradientY, gradientX) * (180.0 / Math.PI);
                System.out.println("index:" + index + ",x方向梯度：" + gradientX + ",y方向梯度：" + gradientY + ",方向梯度：" + dataGradient[index] + ",梯度方向：" + gradientDirection);
            }
        }
        gradientImage.put(0, 0, dataGradient);
        Imgcodecs.imwrite("HandledImages/gradientImages/000001-gradient-primary-2.jpg", gradientImage);
    }

}
