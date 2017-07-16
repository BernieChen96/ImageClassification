import constant.Constant;
import imageHandle.SVM.SVMOpenCV;
import imageHandle.pretreament.ImageParition;
import imageHandle.textProcessing.ReadImageParitionTxt;
import imageHandle.domain.Picture;
import Thread.InputImageThread;
import imageHandle.textProcessing.ReadTestImageParition;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 51157 on 2017/7/6.
 */
public class ImageClassification {

    List<Picture> trainPictures = null;
    List<Picture> testPictures = null;
    ReadImageParitionTxt readImageParitionTxt = null;
    ReadTestImageParition readTestImagePartionTxt = null;
    ImageParition imageParition = null;

    public ImageClassification() {
        readImageParitionTxt = new ReadImageParitionTxt();
        readTestImagePartionTxt = new ReadTestImageParition();
        imageParition = new ImageParition();
    }


    //开始进行图像特征提取
    public void start() {
        //首先读取训练图片的注释文件
        readImageParitionTxt.read();
        //得到每张图片包含注解
        trainPictures = readImageParitionTxt.getPictures();
        Iterator<Picture> trainPictureIterator = trainPictures.iterator();
        //得到Hog特征
        //1输出指定区域图片，2输出灰度化图片，3输出gamma校正图，4输出各方向梯度图，5输出Hog特征txt文本,0不输出图片
        while (trainPictureIterator.hasNext()) {
            Picture picture = trainPictureIterator.next();
            imageParition.startParition(picture, 0);
            Constant.PICTURE_COUNT++;
            System.out.println("第" + Constant.PICTURE_COUNT + "张图片结束");
        }

        //开始训练
        SVMOpenCV.startTrain();


        //读取测试图片的注释文件
        readTestImagePartionTxt.read();
        //得到测试图片中单标图片的位置
        testPictures = readTestImagePartionTxt.getPictures();
        Iterator<Picture> testPictureIterator = testPictures.iterator();

        //测试所有图片的得到准确率
        while (testPictureIterator.hasNext()) {
            Picture picture = testPictureIterator.next();
            //flag为10时可以得到分割的图片
            imageParition.startParition(picture, 10);
            Constant.TESTPICTURE_COUNT++;
            System.out.println("第" + Constant.PICTURE_COUNT + "张测试图片测试结束");
        }
        //算准确率
        SVMOpenCV.getAccuracy();

        new InputImageThread().start();
    }

    public static void main(String args[]) {
        new ImageClassification().start();
    }
}
