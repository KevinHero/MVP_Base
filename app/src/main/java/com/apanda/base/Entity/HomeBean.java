package com.apanda.base.Entity;

import java.util.List;

/**
 * Created by Android on 2016/10/17.
 */

public class HomeBean {

    /**
     * attraction_name : 隔壁老王
     * attraction_id : 123
     * recommended_attractions : [{"attraction_id":234,"attraction_name":"山水人家"}]
     * theme_trip : [{"attraction_id":1,"trip_name":"水中小榭","trip_time":"2018 0923","trip_views":123}]
     */

    private String attraction_name;
    private int attraction_id;
    /**
     * attraction_id : 234
     * attraction_name : 山水人家
     */

    private List<RecommendedAttractionsBean> recommended_attractions;
    /**
     * attraction_id : 1
     * trip_name : 水中小榭
     * trip_time : 2018 0923
     * trip_views : 123
     */

    private List<ThemeTripBean> theme_trip;

    public String getAttraction_name() {
        return attraction_name;
    }

    public void setAttraction_name(String attraction_name) {
        this.attraction_name = attraction_name;
    }

    public int getAttraction_id() {
        return attraction_id;
    }

    public void setAttraction_id(int attraction_id) {
        this.attraction_id = attraction_id;
    }

    public List<RecommendedAttractionsBean> getRecommended_attractions() {
        return recommended_attractions;
    }

    public void setRecommended_attractions(List<RecommendedAttractionsBean> recommended_attractions) {
        this.recommended_attractions = recommended_attractions;
    }

    public List<ThemeTripBean> getTheme_trip() {
        return theme_trip;
    }

    public void setTheme_trip(List<ThemeTripBean> theme_trip) {
        this.theme_trip = theme_trip;
    }

    public static class RecommendedAttractionsBean {
        private int attraction_id;
        private String attraction_name;

        public int getAttraction_id() {
            return attraction_id;
        }

        public void setAttraction_id(int attraction_id) {
            this.attraction_id = attraction_id;
        }

        public String getAttraction_name() {
            return attraction_name;
        }

        public void setAttraction_name(String attraction_name) {
            this.attraction_name = attraction_name;
        }
    }

    public static class ThemeTripBean {
        private int attraction_id;
        private String trip_name;
        private String trip_time;
        private int trip_views;
        public String theme_img;

        public int getAttraction_id() {
            return attraction_id;
        }

        public void setAttraction_id(int attraction_id) {
            this.attraction_id = attraction_id;
        }

        public String getTrip_name() {
            return trip_name;
        }

        public void setTrip_name(String trip_name) {
            this.trip_name = trip_name;
        }

        public String getTrip_time() {
            return trip_time;
        }

        public void setTrip_time(String trip_time) {
            this.trip_time = trip_time;
        }

        public int getTrip_views() {
            return trip_views;
        }

        public void setTrip_views(int trip_views) {
            this.trip_views = trip_views;
        }
    }

    public static String getTestJson() {
        return "{\n" +
                "    \"attraction_name\": \"岳麓山\",\n" +
                "    \"attraction_id\": 1,\n" +
                "    \"recommended_attractions\": [\n" +
                "        {\n" +
                "            \"attraction_id\": 2,\n" +
                "            \"attraction_name\": \"橘子洲\"\n" +
                "        },{\n" +
                "            \"attraction_id\": 1,\n" +
                "            \"attraction_name\": \"大学城\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attraction_id\": 3,\n" +
                "            \"attraction_name\": \"高桥\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attraction_id\": 4,\n" +
                "            \"attraction_name\": \"新民学会\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"theme_trip\": [\n" +
                "        {\n" +
                "            \"attraction_id\": 2,\n" +
                "            \"trip_name\": \"春天花会开5\",\n" +
                "            \"trip_time\": \"2016-10-22\",\n" +
                "            \"trip_views\": 233,\n" +
                "            \"theme_img\": \"http://odf775exx.bkt.clouddn.com/Upload/article/20161022/13411566.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attraction_id\": 2,\n" +
                "            \"trip_name\": \"春天花会开4\",\n" +
                "            \"trip_time\": \"2016-10-22\",\n" +
                "            \"trip_views\": 233,\n" +
                "            \"theme_img\": \"http://odf775exx.bkt.clouddn.com/Upload/article/20161022/13503284.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attraction_id\": 2,\n" +
                "            \"trip_name\": \"春天花会开3\",\n" +
                "            \"trip_time\": \"2016-10-22\",\n" +
                "            \"trip_views\": 233,\n" +
                "            \"theme_img\": \"http://odf775exx.bkt.clouddn.com/Upload/article/20161022/3d6d45e4f8534c83a797f7f92472e181%5C.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attraction_id\": 2,\n" +
                "            \"trip_name\": \"春天花会开2\",\n" +
                "            \"trip_time\": \"2016-10-22\",\n" +
                "            \"trip_views\": 233,\n" +
                "            \"theme_img\": \"http://odf775exx.bkt.clouddn.com/Upload/article/20161022/e9651c3783554424a00332f81e068ea9jpg\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }


}
