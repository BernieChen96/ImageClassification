package imageHandle.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CMM on 2017/7/7.
 */
public class PictureGradient {
    List<Double> size;
    List<Double> direction;
    int width;
    int height;

    public PictureGradient() {
        size = new ArrayList<Double>();
        direction = new ArrayList<Double>();
    }

    public List<Double> getSize() {
        return size;
    }

    public void setSize(List<Double> size) {
        this.size = size;
    }

    public List<Double> getDirection() {
        return direction;
    }

    public void setDirection(List<Double> direction) {
        this.direction = direction;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
