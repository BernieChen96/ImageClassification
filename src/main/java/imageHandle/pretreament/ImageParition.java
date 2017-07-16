package imageHandle.pretreament;

import constant.Constant;
import imageHandle.SVM.SVMOpenCV;
import imageHandle.domain.PictureGradient;
import imageHandle.domain.Picture;
import imageHandle.domain.SingleHog;
import imageHandle.hog.Hog;
import imageHandle.textProcessing.WriteHogDescriptor;
import imageHandle.textProcessing.WriteToExcel;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 51157 on 2017/7/6.
 * 图像根据文本划分为若干块
 */
public class ImageParition {


    static float gamma = 0.8F;
    static boolean overlapFlag = true;


    //保存所有图片的梯度值和方向(暂看)
    List<PictureGradient> gradients = new ArrayList<PictureGradient>();

    /**
     * 通过注释的内容  将图片上的图片进行划分
     */
    public void startParition(Picture picture, int flag) {
        String primaryImagePath = "";
        if (flag != 10)
            primaryImagePath = Constant.PRIMARYIMAGES_PATH + picture.getPictureName();
        else
            primaryImagePath = Constant.TESTPRIMARYIMAGES_PATH + picture.getPictureName();


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
                String labelString = concreteContent[0];

                int label = 0;
                if (labelString.equals("car"))
                    label = Constant.CAR;
                else if (labelString.equals("cat"))
                    label = Constant.CAT;
                else if (labelString.equals("bus"))
                    label = Constant.BUS;
                else if (labelString.equals("train"))
                    label = Constant.TRAIN;
                else if (labelString.equals("person"))
                    label = Constant.PERSON;

                Mat imageMat = parition(primaryImagePath, concreteContent, flag);
                if (flag != 10) {
                    //openCV提取hog特征
                    float[] data = Hog.openCVGetHog(imageMat);

                    //灰度化
                    //             imageMat = ImageGray.gray(imageMat, concreteContent, count, flag);
                    //伽马校正
//                imageMat = ImageGamma.gammaCorrecting(imageMat, gamma, concreteContent, count, flag);
                    //计算梯度
//                PictureGradient pictureGradient = ImageGradient.calculatedGradient(imageMat, concreteContent, count, flag);
                    //Hog特征提取
//                float[] hogDeDescriptor = Hog.acquiredHogDescriptor(imageMat, pictureGradient, overlapFlag);
                    //将Hog特征写入
//               WriteHogDescriptor.writeHogDescriptor(hogDeDescriptor, concreteContent[0], flag);
                    //将Ｈｏｇ特征存储
                    SVMOpenCV.addHog(data, label);
                    Constant.SAMPLE_COUNT++;
                } else {
                    SVMOpenCV.addTestImg(imageMat, label);
                    Constant.TESTSAMPLE_COUNT++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将图片的宽高分割成16的倍数
     *
     * @param primaryFilePath
     * @param concreteContent
     * @param flag
     * @return
     */
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
//        if (width % 16 != 0) {
//            width = (width / 16) * 16;
//            if (width == 0)
//                width = 16;
//        }
//        if (height % 16 != 0) {
//            height = (height / 16) * 16;
//            if (height == 0)
//                height = 16;
//        }
//        System.out.println("width:"+width + ",height:" + height);
        Rect rect = new Rect(x1, y1, width, height);
        Mat specifiedArea = new Mat(src, rect);
        Imgproc.resize(specifiedArea, specifiedArea, new Size(Constant.PICTURE_SIZE_WIDTH, Constant.PICTURE_SIZE_HEIGHT));
        if (flag == 1) {
            String pictureType = concreteContent[0];
            String picturePath = Constant.SPECIFIEDAREA_PATH + pictureType + "-" + Constant.SAMPLE_COUNT + ".jpg";
            Imgcodecs.imwrite(picturePath, specifiedArea);
        } else if (flag == 10) {
            String pictureType = concreteContent[0];
            String picturePath = Constant.TESTSPECIFIEDAREA_PATH + pictureType + "-" + Constant.TESTSAMPLE_COUNT + ".jpg";
            Imgcodecs.imwrite(picturePath, specifiedArea);
        }
        return specifiedArea;
    }


}
