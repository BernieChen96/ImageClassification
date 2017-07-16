import imageHandle.SVM.SVMOpenCV;
import imageHandle.domain.Picture;
import imageHandle.pretreament.ImageParition;
import imageHandle.textProcessing.ReadTestImageParition;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 51157 on 2017/7/7.
 */
public class ImageClassificationTest {
    @Test
    public void startTest() {
        new ImageClassification().start();
    }

    @Test
    public void start() {

        ReadTestImageParition readTestImagePartionTxt = new ReadTestImageParition();
        List<Picture> testPictures = null;
        ImageParition imageParition = new ImageParition();
        //读取测试图片的注释文件
        readTestImagePartionTxt.read();
        //得到测试图片中单标图片的位置
        testPictures = readTestImagePartionTxt.getPictures();
        Iterator<Picture> testPictureIterator = testPictures.iterator();


        //测试所有图片的得到准确率
        while (testPictureIterator.hasNext()) {
            Picture picture = testPictureIterator.next();
            //flag为10时可以算准确率精度
            imageParition.startParition(picture, 10);
        }
        //算准确率
//        SVMOpenCV.getAccuracy();
    }
}
