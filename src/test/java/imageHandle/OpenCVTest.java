package imageHandle;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by 51157 on 2017/7/5.
 */
public class OpenCVTest {
    String primaryFilePath = "./JPEGImages/000001.jpg";
    String handledFilePath = "./HandledImages/000001.jpg";
    static final double PI = 3.1415926;

    /**
     * 读取图片，转为mat格式
     */
    @Test
    public void getPicture() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(primaryFilePath);
        if (src.empty()) System.out.println("图片为空");
        System.out.println(src);
    }

    /**
     * 获取mat图片的属性
     * imread中的参数flags为-1时读取单通道为-1
     */
    @Test
    public void getPictureProperties() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(handledFilePath, -1);
        int width = src.cols();
        int height = src.rows();
        int dims = src.channels();
        System.out.println("宽度：" + width + ",高度：" + height + ",通道数：" + dims);
        byte[] data = new byte[width * height * dims];
        System.out.println("图片整体像素值：" + src.get(0, 0, data));
    }


    /**
     * 遍历像素操作与操作改变
     */
    @Test
    public void traversePiexlRGB() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(primaryFilePath);
        int width = src.cols();
        int height = src.rows();
        int dims = src.channels();
        byte[] data = new byte[width * height * dims];
        src.get(0, 0, data);
        int index = 0;
        int r = 0, g = 0, b = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width * dims; col += dims) {
                index = row * width * dims + col;
                b = data[index] & 0xff;
                g = data[index + 1] & 0xff;
                r = data[index + 2] & 0xff;
                System.out.println("b:" + b + ",g:" + g + ",r:" + r);

                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                data[index] = (byte) b;
                data[index + 1] = (byte) g;
                data[index + 2] = (byte) r;
            }
        }
        src.put(0, 0, data);
        Imgcodecs.imwrite("HandledImages/test/test1.jpg", src);
    }

    /**
     * 获取指定区域的图片
     */
    @Test
    public void getSpecifiedAreaPicture() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        int x1, y1, x2, y2;
        x1 = 139;
        y1 = 200;
        x2 = 207;
        y2 = 301;
        int width = x2 - x1;
        int height = y2 - y1;
        Rect rect = new Rect(x1, y1, width, height);
        Mat src = Imgcodecs.imread(primaryFilePath);
        Mat specifiedArea = new Mat(src, rect);
        Imgcodecs.imwrite("./HandledImages/specifiedArea/000001-1.jpg", specifiedArea);
    }

    /**
     * 灰度化
     */
    @Test
    public void grayTest() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(primaryFilePath);
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_RGB2GRAY);
        Imgcodecs.imwrite("./HandledImages/000001.jpg", gray);
    }

    /**
     * 通过灰度化的图片
     * 伽马校正
     */
    @Test
    public void gammaCorrecting() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(handledFilePath, -1);
        int width = src.cols();
        int height = src.rows();
        byte[] data = new byte[width * height];
        src.get(0, 0, data);
        int index = 0;
        float i = 0;
        float gamma = (float) 0.8;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                i = data[index] & 0xff;
                System.out.println("i:" + i);

                //归一化
                i = (i + 0.5F) / 256;
                //预补偿
                i = (float) Math.pow(i, gamma);
                //反归一化
                i = i * 256 - 0.5F;

                data[index] = (byte) i;
            }
        }
        src.put(0, 0, data);
        Imgcodecs.imwrite("HandledImages/gammaCorrect/000001-gamma.jpg", src);
    }



    /**
     * 通过伽马校正的图片
     * 计算图像梯度
     * row行，col列
     */
    @Test
    public void calculatedGradient() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread("./HandledImages/gammaCorrect/000001-gamma.jpg", -1);
        int width = src.cols();
        int height = src.rows();
        byte[] data = new byte[width * height];
        src.get(0, 0, data);
        int index = 0;
        int i = 0;
        for (int row = 1; row < height - 1; row++) {
            for (int col = 1; col < width - 1; col++) {
                index = row * width + col;
                int xGradient = data[index + 1] & 0xff - data[index - 1] & 0xff;
                int yGradient = data[index + width] & 0xff - data[index - width] & 0xff;
                float gradient = (float) Math.sqrt(Math.pow(xGradient, 2) + Math.pow(yGradient, 2));
                float gradientDirection = (float) Math.atan((float) (yGradient / xGradient));
                System.out.println("xGradient:" + xGradient + ",yGradient:" + yGradient + ",gradient:" + gradient + ",gradientDirection:" + gradientDirection);
            }
        }
    }
    @Test
    public void test1() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread("./HandledImages/gammaCorrect/000001-gamma.jpg", -1);
        int width = src.cols();
        int height = src.rows();
        byte[] data = new byte[width * height];
        src.get(0, 0, data);
        System.out.println("（0，0）位置像素：" + (data[0] & 0xff) + "，（0，1）位置像素：" + (data[0] & 0xff) + ",（1，0）位置像素：" + src.get(1, 0));
    }

    @Test
    public void mathTest() {
        float i = (float) Math.atan(91/6);
        int d = (int) ((float) (i / PI) * 180);
        System.out.println("atan:" + i + ",i/PI:" + (float) (i / PI) + ",角度：" + d);
    }
}
