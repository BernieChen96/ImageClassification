package imageHandle.SVM;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.SVM;
import org.opencv.ml.TrainData;

import static org.opencv.ml.Ml.ROW_SAMPLE;


/**
 * Created by 51157 on 2017/7/10.
 */
public class SVMTest {
    //训练样本的准备，10个样本，每个样本有12组数据，每个样本第一个数是标签。
    static double inputArr[][] =
            {{1, 0.708333, 1, 1, -0.320755, -0.105023, -1, 1, -0.419847, -1, -0.225806, 0, 1},


                    {-1, 0.583333, -1, 0.333333, -0.603774, 1, -1, 1, 0.358779, -1, -0.483871, 0, -1},


                    {1, 0.166667, 1, -0.333333, -0.433962, -0.383562, -1, -1, 0.0687023, -1, -0.903226, -1, -1},


                    {-1, 0.458333, 1, 1, -0.358491, -0.374429, -1, -1, -0.480916, 1, -0.935484, 0, -0.333333},


                    {-1, 0.875, -1, -0.333333, -0.509434, -0.347032, -1, 1, -0.236641, 1, -0.935484, -1, -0.333333},


                    {-1, 0.5, 1, 1, -0.509434, -0.767123, -1, -1, 0.0534351, -1, -0.870968, -1, -1},


                    {1, 0.125, 1, 0.333333, -0.320755, -0.406393, 1, 1, 0.0839695, 1, -0.806452, 0, -0.333333},


                    {1, 0.25, 1, 1, -0.698113, -0.484018, -1, 1, 0.0839695, 1, -0.612903, 0, -0.333333},


                    {1, 0.291667, 1, 1, -0.132075, -0.237443, -1, 1, 0.51145, -1, -0.612903, 0, 0.333333},


                    {1, 0.416667, -1, 1, 0.0566038, 0.283105, -1, 1, 0.267176, -1, 0.290323, 0, 1}};
    //测试数据
    static double testArr[] =


            {


                    0.5, 1, 1, -0.509434, -0.767123, -1, -1, 0.0534351, -1, -0.870968, -1, -1


            };

    @Test
    public void start() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        SVM svm = SVM.create();
        svm.setType(SVM.C_SVC);
        svm.setKernel(SVM.LINEAR);
        svm.setTermCriteria(new TermCriteria(TermCriteria.MAX_ITER, 100, 1e-6));
        Mat dataMat = new Mat();
        Mat labelMat = new Mat();
        int i;
        int j;

        dataMat.create(10, 12, CvType.CV_32FC1);
        labelMat.create(10, 1, CvType.CV_32SC1);
        double a[] = new double[12];


        for (i = 0; i < 10; i++) {
            for (j = 0; j < 12; j++) {
                dataMat.put(i, j, inputArr[i][j + 1]);
            }
            labelMat.put(i, 0, inputArr[i][0]);
        }

        svm.train(dataMat, ROW_SAMPLE, labelMat);

//        svm.save("././HandledText/resultTxt/result.xml");
        Mat testMat = new Mat();
        testMat.create(1, 12, CvType.CV_32FC1);


        for (i = 0; i < 12; i++) {
            testMat.put(0, i, testArr[i]);
        }


        float flag = 0;


        flag = svm.predict(testMat);


        System.out.print(flag);
        dataMat.release();
        labelMat.release();
        testMat.release();

    }

}
