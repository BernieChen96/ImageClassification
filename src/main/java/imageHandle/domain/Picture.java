package imageHandle.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 51157 on 2017/7/6.
 */
public class Picture {
    String pictureName;
    List<String> contents;

    public Picture() {
        contents = new ArrayList<String>();
    }
    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }
}
