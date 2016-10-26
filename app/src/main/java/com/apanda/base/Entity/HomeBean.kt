package com.apanda.base.Entity

/**
 * Created by Android on 2016/10/17.
 */

class HomeBean {

    /**
     * attraction_name : 隔壁老王
     * attraction_id : 123
     * recommended_attractions : [{"attraction_id":234,"attraction_name":"山水人家"}]
     * theme_trip : [{"attraction_id":1,"trip_name":"水中小榭","trip_time":"2018 0923","trip_views":123}]
     */

    var attraction_name: String? = null
    var attraction_id: Int = 0
    /**
     * attraction_id : 234
     * attraction_name : 山水人家
     */

    var recommended_attractions: List<RecommendedAttractionsBean>? = null
    /**
     * attraction_id : 1
     * trip_name : 水中小榭
     * trip_time : 2018 0923
     * trip_views : 123
     */

    var theme_trip: List<ThemeTripBean>? = null

    class RecommendedAttractionsBean {
        var attraction_id: Int = 0
        var attraction_name: String? = null
    }

    class ThemeTripBean {
        var attraction_id: Int = 0
        var trip_name: String? = null
        var trip_time: String? = null
        var trip_views: Int = 0
        var theme_img: String? = null
    }

    companion object {

        val testJson: String
            get() = "{\n" +
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
                    "}"
    }


}
