package imageHandle.textProcessing;

import imageHandle.domain.Picture;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 51157 on 2017/7/6.
 * 根据提供的文本来读取图像划分的区域
 */
public class ReadImageParitionTxt {
    public static String primaryFilePath = "././HandledText/annotations.txt";
    public static String handledFilePath = "././HandledText/initTxt/";
    InputStream in = null;
    OutputStream out = null;
    BufferedReader br = null;
    BufferedWriter bw = null;


    List<Picture> pictures = null;

    public ReadImageParitionTxt() {
        pictures = new ArrayList<Picture>();
        try {
            in = new FileInputStream(primaryFilePath);
            br = new BufferedReader(new InputStreamReader(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        File file = new File(primaryFilePath);
        if (!file.exists()) {
            System.out.println("文件不存在！");
        }
        try {
            String currentLineContent = "";
            int currentPicture = -1;
            while (true) {
                //读取当前行的内容
                currentLineContent = br.readLine();
                if (currentLineContent == null)
                    break;
//                System.out.println(currentLineContent);
                //判断当前行内容有没有jpg，一直向下再读直到独到下一个jpg
                int m = currentLineContent.indexOf(".jpg");
                if (m != -1) {
                    currentPicture++;
                    Picture picture = new Picture();
                    pictures.add(picture);
                    picture.setPictureName(currentLineContent);
                    List<String> contents = new ArrayList<String>();
                    picture.setContents(contents);
                } else {
                    pictures.get(currentPicture).getContents().add(currentLineContent);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将图像注释进行分解，写到initTxt文件夹中
     */
    public void write() {
        Iterator<Picture> pictureIte = pictures.iterator();
        while (pictureIte.hasNext()) {
            Picture picture = pictureIte.next();
            String pictureName = picture.getPictureName();
            String fileName = pictureName.replaceAll(".jpg", ".txt");
            try {
                File file = new File(handledFilePath + fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                out = new FileOutputStream(handledFilePath + fileName);
                bw = new BufferedWriter(new OutputStreamWriter(out));
                //bw.write(pictureName + "\r\n");
                Iterator<String> contentIte = picture.getContents().iterator();
                while (contentIte.hasNext()) {
                    String content = contentIte.next();
                    //去掉最后一行
                    if (content.charAt(0) <= '9' && content.charAt(0) > '0')
                        break;
                    bw.write(content + "\r\n");
                }
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Picture> getPictures() {
        return pictures;
    }
}
