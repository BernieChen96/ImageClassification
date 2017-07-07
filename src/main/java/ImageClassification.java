import imageHandle.pretreament.ImageParition;
import imageHandle.textProcessing.ReadImageParitionTxt;
import imageHandle.domain.Picture;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 51157 on 2017/7/6.
 */
public class ImageClassification {

    List<Picture> pictures = null;
    ReadImageParitionTxt readImageParitionTxt = null;
    ImageParition imageParition = null;
    //记录当前处理的图片数
    int count = 0;

    public ImageClassification() {
        readImageParitionTxt = new ReadImageParitionTxt();
        imageParition = new ImageParition();
    }

    //开始进行图像特征提取
    public void start() {
        //首先读取注释文件
        readImageParitionTxt.read();
        //得到每张图片包含注解
        pictures = readImageParitionTxt.getPictures();
        Iterator<Picture> pictureIterator = pictures.iterator();
        //1输出指定区域图片，2输出灰度化图片，3输出gamma校正图，4输出各方向梯度图，0不输出图片
        while (pictureIterator.hasNext()) {
            Picture picture = pictureIterator.next();
            imageParition.startParition(picture, 0);
            count++;
            System.out.println("第" + count + "张图片结束");
        }
        //根据区域图片，对图像进行划分
    }

    public static void main(String args[]) {
        new ImageClassification().start();
    }
}
