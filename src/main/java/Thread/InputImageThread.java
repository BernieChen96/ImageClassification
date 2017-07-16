package Thread;

import constant.Constant;
import imageHandle.SVM.SVMOpenCV;

import java.util.Scanner;

/**
 * Created by CMM on 2017/7/10.
 */
public class InputImageThread extends Thread {
    public void run() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("请输入你想要测试的图片：");
            String line = input.nextLine();
            line = Constant.INPUTIMAGE_PATH + line + ".jpg";
            SVMOpenCV.startTest(line);
        }
    }
}
