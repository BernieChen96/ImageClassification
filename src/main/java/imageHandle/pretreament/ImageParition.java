package imageHandle.pretreament;

import imageHandle.textProcessing.domain.Picture;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 51157 on 2017/7/6.
 * 图像根据文本划分为若干块
 */
public class ImageParition {
    public static final String SPECIFIEDAREA_PATH = "././HandledImages/specifiedArea/";
    public static final String PRIMARYIMAGES_PATH = "././JPEGImages/";
    static int count = 0;

    /**
     * 通过注释的内容  将图片上的图片进行划分
     */
    public void startParition(Picture picture, int flag) {
        String primaryImagePath = PRIMARYIMAGES_PATH + picture.getPictureName();
        Iterator<String> contentIterator = picture.getContents().iterator();
        String content = "";
        while (contentIterator.hasNext()) {
            content = contentIterator.next();
            //去掉最后一行
            if (content.charAt(0) <= '9' && content.charAt(0) > '0')
                break;
            //concreteContent包含类型和坐标
            String[] concreteContent = content.split(" ");
            try {
                Mat imageMat = parition(primaryImagePath, concreteContent, flag);
                imageMat = ImageGray.gray(imageMat, concreteContent, count, flag);
                imageMat = ImageGamma.gammaCorrecting(imageMat, (float) 0.8, concreteContent, count, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
            count++;
        }
    }

    public Mat parition(String primaryFilePath, String[] concreteContent, int flag) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(primaryFilePath);
        int x1, y1, x2, y2;
        x1 = Integer.parseInt(concreteContent[1]);
        y1 = Integer.parseInt(concreteContent[2]);
        x2 = Integer.parseInt(concreteContent[3]);
        y2 = Integer.parseInt(concreteContent[4]);
        int width = x2 - x1;
        int height = y2 - y1;
        Rect rect = new Rect(x1, y1, width, height);
        Mat specifiedArea = new Mat(src, rect);
        if (flag == 1) {
            String pictureType = concreteContent[0];
            String picturePath = SPECIFIEDAREA_PATH + pictureType + "-" + count + ".jpg";
            Imgcodecs.imwrite(picturePath, specifiedArea);
        }
        return specifiedArea;
    }


}
