package imageHandle.textProcessing;

import imageHandle.domain.SingleHog;
import org.apache.poi.hssf.usermodel.*;

import java.io.*;

/**
 * Created by CMM on 2017/7/8.
 */
public class WriteHogDescriptor {
    static String hogDescriptorTxt = "././HandledText/hogDescriptorTxt/hogDescripto.txt";
    static BufferedWriter bw = null;


    static HSSFWorkbook wb = null;
    static HSSFSheet sheet = null;
    static HSSFRow row = null;
    static HSSFCell cell = null;

    static String HOGDESCRIPTOREXCEL_PATH = "././handledText/hogDescriptorExcel/hogDescriptor.xls";
    static int rowCount = 0;

    static {
        File file = new File(hogDescriptorTxt);
        try {
            if (!file.exists())
                file.createNewFile();
            OutputStream out = new FileOutputStream(file, true);
            bw = new BufferedWriter(new OutputStreamWriter(out));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        // 第一步，创建一个webbook，对应一个Excel文件
        wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        sheet = wb.createSheet("Hog");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        cell = row.createCell((short) 0);
        cell.setCellValue("特征");
        cell = row.createCell((short) 1);
        cell.setCellValue("标签");
        File file = new File(HOGDESCRIPTOREXCEL_PATH);
        try {
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeHogDescriptor(float[] hogDescriptor, String type, int flag) {
        if (flag == 6) {
            row = sheet.createRow(rowCount);
            for (int i = 0; i < hogDescriptor.length; i++) {
                row.createCell(i).setCellValue(hogDescriptor[i]);
            }
            row.createCell(hogDescriptor.length + 1).setCellValue(type);
            // 第六步，将文件存到指定位置
            try {
                FileOutputStream fout = new FileOutputStream(HOGDESCRIPTOREXCEL_PATH);
                wb.write(fout);
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (flag == 5) {
            StringBuilder hog = new StringBuilder();
            for (int i = 0; i < hogDescriptor.length; i++) {
                if (i == hogDescriptor.length - 1) {
                    hog.append(hogDescriptor[i] + " ");
                    break;
                }
                hog.append(hogDescriptor[i] + ",");
            }
            hog.append(type + "\r\n");
//        System.out.print(hog.toString());
            try {
                bw.write(hog.toString());
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void test() {

    }
}
