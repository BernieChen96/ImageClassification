package imageHandle.pretreament;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Created by CMM on 2017/7/6.
 */
public class ImageGamma {
    public static final String GAMMACORRECT_PATH = "././HandledImages/gammaCorrect/";

    /**
     * 通过灰度化的图片
     * 伽马校正
     */
    public static Mat gammaCorrecting(Mat imageMat,float gamma, String[] concreteContent, int count, int flag) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        int width = imageMat.cols();
        int height = imageMat.rows();
        byte[] data = new byte[width * height];
        imageMat.get(0, 0, data);
        int index = 0;
        float i = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                i = data[index] & 0xff;

                //归一化
                i = (i + 0.5F) / 256;
                //预补偿
                i = (float) Math.pow(i, gamma);
                //反归一化
                i = i * 256 - 0.5F;

                data[index] = (byte) i;
            }
        }
        imageMat.put(0, 0, data);
        if (flag == 3) {
            String pictureType = concreteContent[0];
            String picturePath = GAMMACORRECT_PATH + pictureType + "-" + count + ".jpg";
            Imgcodecs.imwrite(picturePath, imageMat);
        }
        return imageMat;
    }
}
