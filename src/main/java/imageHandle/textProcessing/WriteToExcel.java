package imageHandle.textProcessing;

import imageHandle.domain.SingleHog;
import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by CMM on 2017/7/9.
 */
public class WriteToExcel {
    static HSSFWorkbook wb = null;
    static HSSFSheet sheet = null;
    static HSSFRow row = null;
    static HSSFCell cell = null;

    static String HOGDESCRIPTOREXCEL_PATH = "././handledText/hogDescriptorExcel/hogDescriptor.xls";
    static int rowCount = 0;

    static {
        // 第一步，创建一个webbook，对应一个Excel文件
        wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        sheet = wb.createSheet("Hog");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
//        cell = row.createCell((short) 0);
//        cell.setCellValue("特征");
//        cell = row.createCell((short) 1);
//        cell.setCellValue("标签");
        File file = new File(HOGDESCRIPTOREXCEL_PATH);
        try {
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeHogToExcel(SingleHog singleHog) {

        row = sheet.createRow(rowCount);
        String features = singleHog.getFeatures();
        row.createCell(0).setCellValue(singleHog.getFeatures());
        row.createCell(1).setCellValue(singleHog.getTag());
        rowCount++;
        // 第六步，将文件存到指定位置
        try {
            FileOutputStream fout = new FileOutputStream(HOGDESCRIPTOREXCEL_PATH);
            wb.write(fout);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
