package imageHandle.pretreament;

import imageHandle.domain.PictureGradient;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.List;

/**
 * Created by 51157 on 2017/7/7.
 */
public class ImageGradient {
    public static final String GRADIENTIMAGES_PATH = "././HandledImages/gradientImages/";

    public static PictureGradient calculatedGradient(Mat imageMat, String[] concreteContent, int count, int flag) {
        Mat gradientImage = new Mat(imageMat.rows(), imageMat.cols(), imageMat.type());
        Mat gradientImageX = new Mat();
        Mat gradientImageY = new Mat();
        Imgproc.Sobel(imageMat, gradientImageX, CvType.CV_64F, 1, 0, 1, 1, 1);
        Imgproc.Sobel(imageMat, gradientImageY, CvType.CV_64F, 0, 1, 3, 1, 0);
//        Imgproc.Sobel(src, gradientImage, CvType.CV_64F, 1, 1, 3, 1, 0);
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


        if (flag == 4) {
            gradientImage.put(0, 0, dataGradient);
            String pictureType = concreteContent[0];
            String picturePathX = GRADIENTIMAGES_PATH + pictureType + "-" + count + "X" + ".jpg";
            String picturePathY = GRADIENTIMAGES_PATH + pictureType + "-" + count + "Y" + ".jpg";
            String picturePath = GRADIENTIMAGES_PATH + pictureType + "-" + count + ".jpg";
            Imgcodecs.imwrite(picturePath, gradientImage);
            Imgcodecs.imwrite(picturePathX, gradientImageX);
            Imgcodecs.imwrite(picturePathY, gradientImageY);
        }
        return pictureGradient;
    }
}
