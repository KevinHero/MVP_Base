package com.apanda.base.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apanda on 2016/10/18.
 */

public class GankBean implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;
    /**
     * error : false
     * results : [{"_id":"57bbae92421aa9125fa3ed62","createdAt":"2016-08-23T10:01:54.511Z","desc":"Freeline - Android平台上的秒级编译方案","publishedAt":"2016-08-23T11:29:45.813Z","source":"web","type":"Android","url":"https://yq.aliyun.com/articles/59177?spm=5176.100238.goodcont.51.bedUuR","used":true,"who":"XiaoGu"},{"_id":"57ba3aa2421aa950d35eb33e","createdAt":"2016-08-22T07:34:58.215Z","desc":"类似 Duolingo 的 Card 滑动和选择效果","images":["http://img.gank.io/45b036d6-432a-43ea-bd77-dd700672fc7c","http://img.gank.io/d340793d-e68f-4f07-8a68-226baccaebf0"],"publishedAt":"2016-08-22T11:29:37.164Z","source":"chrome","type":"Android","url":"https://github.com/rubensousa/ViewPagerCards","used":true,"who":"代码家"},{"_id":"57ba3adc421aa950db66bcbf","createdAt":"2016-08-22T07:35:56.993Z","desc":"封装 zxing 二维码扫描功能库，用起来更简单","images":["http://img.gank.io/7e400ffb-cfc3-4394-a922-e7ae9733fdf5","http://img.gank.io/6fb20036-642f-4f74-90ae-b123b0eee145","http://img.gank.io/c4018a72-ab2f-4dcd-a340-837a5e470e9e"],"publishedAt":"2016-08-22T11:29:37.164Z","source":"chrome","type":"Android","url":"https://github.com/yipianfengye/android-zxingLibrary","used":true,"who":"代码家"},{"_id":"57ba3b1f421aa950d35eb33f","createdAt":"2016-08-22T07:37:03.123Z","desc":"利用 Renderscript 实现的一个简单的模糊效果封装组件","images":["http://img.gank.io/73baa413-4d17-41ef-a64f-960adeeb77f2"],"publishedAt":"2016-08-22T11:29:37.164Z","source":"chrome","type":"Android","url":"https://github.com/wl9739/BlurredView","used":true,"who":"代码家"},{"_id":"57ba3c35421aa950db66bcc0","createdAt":"2016-08-22T07:41:41.25Z","desc":"漂亮的色彩渐变式背景效果。","images":["http://img.gank.io/2bd9b26c-8758-4aa1-84d9-7ae8306d4a06"],"publishedAt":"2016-08-22T11:29:37.164Z","source":"chrome","type":"Android","url":"https://github.com/dynamitechetan/Flowing-Gradient","used":true,"who":"代码家"},{"_id":"57ba3c84421aa950d7bc7b60","createdAt":"2016-08-22T07:43:00.364Z","desc":"基于相对位置实现弹出 PopupWindow","images":["http://img.gank.io/ef77f8b5-cc3d-466b-9aee-478ba3396c11"],"publishedAt":"2016-08-22T11:29:37.164Z","source":"chrome","type":"Android","url":"https://github.com/kakajika/RelativePopupWindow","used":true,"who":"代码家"},{"_id":"57ba638a421aa9125fa3ed54","createdAt":"2016-08-22T10:29:30.495Z","desc":"通过一行代码与ViewPager合体的跟随ViewPager移动的进度展示控件","images":["http://img.gank.io/da830207-1ce3-4472-b129-5cadd427c4aa"],"publishedAt":"2016-08-22T11:29:37.164Z","source":"web","type":"Android","url":"https://github.com/hzw1199/android_ProcessBar","used":true,"who":"Adam Wu"},{"_id":"57b42355421aa93a804bea12","createdAt":"2016-08-17T16:41:57.951Z","desc":"Android Toast花式使用","publishedAt":"2016-08-19T11:26:30.163Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/5c5c8ee31ddb","used":true,"who":null},{"_id":"57b56da2421aa93a7603ed6c","createdAt":"2016-08-18T16:11:14.260Z","desc":"进度展示也可以这么玩~","images":["http://img.gank.io/f8f8fc52-5508-425d-b5c6-7aa1be166b33","http://img.gank.io/3089b46f-9a38-4379-9544-6012273e914d","http://img.gank.io/14782809-f6e6-4056-9460-2f08a4c284ff"],"publishedAt":"2016-08-19T11:26:30.163Z","source":"web","type":"Android","url":"https://github.com/zhangke3016/SpecialProgressBar","used":true,"who":"张珂"},{"_id":"57b57316421aa93a74c3463a","createdAt":"2016-08-18T16:34:30.801Z","desc":"Gradle多渠道打包(动态设定App名称，应用图标，替换常量，更改包名，变更渠道)","publishedAt":"2016-08-19T11:26:30.163Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/533240d222d3","used":true,"who":"Wing-Li"}]
     */

    public boolean error;
    /**
     * _id : 57bbae92421aa9125fa3ed62
     * createdAt : 2016-08-23T10:01:54.511Z
     * desc : Freeline - Android平台上的秒级编译方案
     * publishedAt : 2016-08-23T11:29:45.813Z
     * source : web
     * type : Android
     * url : https://yq.aliyun.com/articles/59177?spm=5176.100238.goodcont.51.bedUuR
     * used : true
     * who : XiaoGu
     */

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
        public List<String> images;
    }


//    public boolean error;
//
//    public List<Results> _results;
//
//    public static class Results {
//        public String _id;
//        public String createdAt;
//        public String desc;
//        public String publishedAt;
//        public String source;
//        public String type;
//        public String url;
//        public boolean used;
//        public String who;
//        public List<String> images;
//
//    }


    //
//    public String date;
//
//
//    public List<StoriesBean> stories;
//
//
//    public List<TopStoriesBean> top_stories;
//
//
//    public static class StoriesBean {
//        public String title;
//        public String ga_prefix;
//        public boolean multipic;
//        public int type;
//        public int id;
//        public List<String> images;
//    }
//
//    public static class TopStoriesBean {
//        public String image;
//        public int type;
//        public int id;
//        public String ga_prefix;
//        public String title;
//    }
//
//
//    @Override
//    public String toString() {
//
//        StringBuffer sb = new StringBuffer();
//
//        sb.append("date" + date + "\n");
//
//        for (StoriesBean bean : stories) {
//            sb.append("title" + bean.title + "\n");
//            sb.append("ga_prefix" + bean.ga_prefix + "\n");
//            sb.append("multipic" + bean.multipic + "\n");
//            sb.append("type" + bean.type + "\n");
//            sb.append("id" + bean.id + "\n");
//
//            for (String image : bean.images) {
//                sb.append("image" + image + "\n");
//            }
//        }
//        for (TopStoriesBean bean : top_stories) {
//            sb.append("image" + bean.image + "\n");
//            sb.append("ga_prefix" + bean.ga_prefix + "\n");
//
//            sb.append("type" + bean.type + "\n");
//            sb.append("id" + bean.id + "\n");
//            sb.append("title" + bean.title + "\n");
//
//
//        }
//
//
//        return sb.toString();
//    }


}
