package imageHandle.pretreament;

import imageHandle.domain.PictureGradient;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.List;

/**
 * Created by CMM on 2017/7/7.
 */
public class ConstructingHistogramTest {
    String gammaFilePath = "././HandledImages/gammaCorrect/000001-gamma.jpg";

    @Test
    public void constructingHistogram() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat imageMat = Imgcodecs.imread(gammaFilePath, -1);
        Mat gradientImage = new Mat(imageMat.rows(), imageMat.cols(), imageMat.type());
        Mat gradientImageX = new Mat();
        Mat gradientImageY = new Mat();
        Imgproc.Sobel(imageMat, gradientImageX, CvType.CV_64F, 1, 0, 1, 1, 1);
        Imgproc.Sobel(imageMat, gradientImageY, CvType.CV_64F, 0, 1, 3, 1, 0);
        int width = imageMat.cols();
        int height = imageMat.rows();
        int index = 0;
        double[] dataGradient = new double[width * height];
        double[] dataGradientX = new double[width * height];
        double[] dataGradientY = new double[width * height];
        gradientImageX.get(0, 0, dataGradientX);
        gradientImageY.get(0, 0, dataGradientY);

        //创建梯度图片对象，创建对象时已经将Size和Direction的List初始化完毕
        PictureGradient pictureGradient = new PictureGradient();
        pictureGradient.setHeight(height);
        pictureGradient.setWidth(width);
        //计算梯度方向
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                double gradientX = dataGradientX[index];
                double gradientY = dataGradientY[index];
                dataGradient[index] = Math.sqrt(Math.pow(gradientX, 2) + Math.pow(gradientY, 2));
                double gradientDirection = Math.atan2(gradientY, gradientX) * (180.0 / Math.PI);

                pictureGradient.getDirection().add(gradientDirection);
                pictureGradient.getSize().add(dataGradient[index]);

//                System.out.println("index:" + index + ",x方向梯度：" + gradientX + ",y方向梯度：" + gradientY + ",方向梯度：" + dataGradient[index] + ",梯度方向：" + gradientDirection);
            }
        }
        //块包含3个细胞
        int blockSection = 3;
        //细胞包含6个像素
        int cellSection = 6;
        //一个block所对应的宽度像素个数
        int blockwidth = blockSection * cellSection;
        //特征向量数（bin）
        int bin = 9;
        int[] weight = new int[bin];
        //判断x,y方向可以包含多少个block
        int xBlockSection = width / blockwidth;
        int yBlockSection = height / blockwidth;
        System.out.println("宽度：" +  width + ",xBlockSection:" + xBlockSection);
        System.out.println("高度：" + height + ",yBlockSection:" + yBlockSection);

        List<Double> direction = pictureGradient.getDirection();
        List<Double> size = pictureGradient.getSize();
        index = 0;
        //遍历图片（图片中的Block）
        for (int n = 0; n < yBlockSection; n++) {
            for (int m = 0; m < xBlockSection; m++) {
                index = n * xBlockSection * blockwidth + m * blockwidth;
                //遍历Block（block中的细胞）
                for (int i = 0; i < blockSection; i++) {
                    for (int j = 0; j < blockSection; j++) {
                        index += i * blockSection * cellSection + j * cellSection;
                        //遍历细胞（梯度直方图）
                        for (int z = 0; z < cellSection; z++) {
                            for (int x = 0; x < cellSection; x++) {
                                index += z * cellSection + x;
                                System.out.println("index：" + index + "梯度方向为：" + direction.get(index));
                            }
                        }
                    }
                }
            }
        }


    }
}
