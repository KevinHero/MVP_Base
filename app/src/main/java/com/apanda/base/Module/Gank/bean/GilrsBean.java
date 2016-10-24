package com.apanda.base.Module.Gank.bean;

import java.util.List;

/**
 * Created by apanda on 2016/10/24.
 */
public class GilrsBean {


    public boolean error;

    public List<ResultsBean> results;

    public static class ResultsBean {
        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
    }
}
