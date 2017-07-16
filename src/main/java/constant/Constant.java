package constant;

import org.opencv.core.Size;
import org.opencv.objdetect.HOGDescriptor;

/**
 * Created by CMM on 2017/7/10.
 */

public class Constant {
    //图片类型
    public static final int CAR = 1;
    public static final int CAT = 2;
    public static final int TRAIN = 3;
    public static final int PERSON = 4;
    public static final int BUS = 5;
    //路径地址
    public static final String SPECIFIEDAREA_PATH = "././HandledImages/specifiedArea/";
    public static final String PRIMARYIMAGES_PATH = "././JPEGImages/";
    public static final String INPUTIMAGE_PATH = "././Test/Image/";
    public static final String TESTSPECIFIEDAREA_PATH = "././Test/specifiedArea/";
    public static final String TESTPRIMARYIMAGES_PATH = "././Test/Image/";
    //样本数量
    public static int SAMPLE_COUNT = 0;
    public static int TESTSAMPLE_COUNT = 0;
    //图片数量
    public static int PICTURE_COUNT = 0;
    public static int TESTPICTURE_COUNT = 0;
    //所有图片大小
    public static int PICTURE_SIZE_WIDTH = 128;
    public static int PICTURE_SIZE_HEIGHT = 128;
    //图片特征维数
    public static int HOG_BLOCK_SIZE = 16;
    public static int HOG_CELL_SZIE = 8;
    public static int HOG_STRIDE_SIZE = 8;
    public static int HOG_BIN = 9;
    public static long PICTURE_FEATURE_DIM = new HOGDescriptor(new Size(PICTURE_SIZE_WIDTH, PICTURE_SIZE_HEIGHT), new Size(HOG_BLOCK_SIZE, HOG_BLOCK_SIZE), new Size(HOG_STRIDE_SIZE, HOG_STRIDE_SIZE), new Size(HOG_CELL_SZIE, HOG_CELL_SZIE), HOG_BIN).getDescriptorSize();
    //迭代次数
    public static int ITERATION_NUM = 5000;
}
