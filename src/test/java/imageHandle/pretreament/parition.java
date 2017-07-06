package imageHandle.pretreament;

import imageHandle.textProcessing.ReadImageParitionTxt;
import imageHandle.textProcessing.domain.Picture;
import org.junit.Test;

import java.util.List;

/**
 * Created by CMM on 2017/7/6.
 */
public class parition {
    List<Picture> pictures = null;
    ReadImageParitionTxt readImageParitionTxt = null;
    ImageParition imageParition = null;

    @Test
    public void parition() {
        readImageParitionTxt = new ReadImageParitionTxt();
        imageParition = new ImageParition();
        //首先读取注释文件
        readImageParitionTxt.read();
        //得到图像区域图片
        pictures = readImageParitionTxt.getPictures();
        //根据区域图片，对图像进行划分
    }
}
