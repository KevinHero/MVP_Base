package com.apanda.base.Entity;

import java.util.List;

/**
 * Created by apanda on 2016/10/18.
 */

public class StoryModel {

    /**
     * images : ["http://pic2.zhimg.com/a65f1abe759b69aeb1babe1d1497d369.jpg"]
     * type : 0
     * id : 8897580
     * ga_prefix : 101816
     * title : 美剧《西部世界》中出现的科技，哪些是已经存在的？
     */

    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private List<String> images;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
