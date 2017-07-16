package imageHandle.SVM;

import constant.Constant;
import imageHandle.pretreament.InputImage;
import org.apache.poi.util.SystemOutLogger;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.SVM;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.ml.Ml.ROW_SAMPLE;

/**
 * Created by CMM on 2017/7/10.
 */
public class SVMOpenCV {
    static List<Integer> img_cag = null;
    static List<float[]> descriptor = null;
    static List<Mat> testImgMat = null;
    static List<Integer> testImgCag = null;
    static SVM svm;
    static int correctCount = 0;

    static {
        img_cag = new ArrayList<Integer>();
        descriptor = new ArrayList<float[]>();
        testImgCag = new ArrayList<Integer>();
        testImgMat = new ArrayList<Mat>();
    }

    public static void addHog(float[] data, int label) {

        img_cag.add(label);
        descriptor.add(data);
    }

    public static void startTrain() {
        Mat data_mat = new Mat(Constant.SAMPLE_COUNT, (int) Constant.PICTURE_FEATURE_DIM, CvType.CV_32FC1);
        Mat res_mat = new Mat(Constant.SAMPLE_COUNT, 1, CvType.CV_32S);
        for (int i = 0; i < descriptor.size(); i++) {
            for (int j = 0; j < descriptor.get(i).length; j++) {
//                System.out.println(descriptor.get(i)[j]);
                data_mat.put(i, j, descriptor.get(i)[j]);
            }
            res_mat.put(i, 0, img_cag.get(i));
        }

        svm = SVM.create();
        svm.setType(SVM.C_SVC);
        svm.setKernel(SVM.LINEAR);
        svm.setTermCriteria(new TermCriteria(TermCriteria.MAX_ITER, Constant.ITERATION_NUM, 1e-6));

        svm.train(data_mat, ROW_SAMPLE, res_mat);

//        svm.save("././HandledText/resultTxt/result.xml");


    }

    public static void startTest(String filePath) {
        float flag = 0;
        Mat inputImageMat = new Mat(1, (int) Constant.PICTURE_FEATURE_DIM, CvType.CV_32FC1);
        float[] inputImageMatFloat = InputImage.inputImage(filePath);
        for (int i = 0; i < inputImageMatFloat.length; i++) {
            inputImageMat.put(0, i, inputImageMatFloat[i]);
        }
        flag = svm.predict(inputImageMat);
        if (flag == 1)
            System.out.println("类别：Car");
        else if (flag == 2)
            System.out.println("类别：Cat");
        else if (flag == 3)
            System.out.println("类别：Train");
        else if (flag == 4)
            System.out.println("类别：Person");
        else if (flag == 5)
            System.out.println("类别：Bus");

    }


    public static void addTestImg(Mat imageMat, int label) {
        testImgMat.add(imageMat);
        testImgCag.add(label);
    }

    public static void getAccuracy() {
        for (int i = 0; i < testImgMat.size(); i++) {
            Mat inputImageMat = new Mat(1, (int) Constant.PICTURE_FEATURE_DIM, CvType.CV_32FC1);
            float[] inputImageMatFloat = InputImage.inputImage(testImgMat.get(i));

            for (int j = 0; j < inputImageMatFloat.length; j++) {
                inputImageMat.put(0, j, inputImageMatFloat[j]);
            }
            float flag = svm.predict(inputImageMat);
            if (flag == testImgCag.get(i))
                correctCount++;
        }
        System.out.println("图像分辨准确率是：" + (float) correctCount / testImgMat.size() * 100 + "%");
    }
}
