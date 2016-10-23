package com.apanda.base.Entity;

import java.util.List;

/**
 * Created by Android on 2016/10/11.
 */

public class ArticleIntroBean {


    /**
     * article_title : eqwe
     * article_url : 234234
     * article_id : 1
     * username : 23
     * user_id : 2
     * user_img : http://odf775exx.bkt.clouddn.com/Upload/article/20161022/13411566.jpg
     * article_shares : 2
     * article_collects : 2
     * article_brief_intro : 123412341234123412341
     */

    public List<RecommendedArticleBean> recommended_article;

    /**
     * article_title :
     * article_url :
     * article_id : 1
     * username :
     * user_id : 2
     * user_img :
     * article_shares : 2
     * article_collects : 2
     * article_brief_intro :
     */
//
//    public String article_title;
//    public String article_url;
//    public int article_id;
//    public String username;
//    public int user_id;
//    public String user_img;
//    public int article_shares;
//    public int article_collects;
//    public String article_brief_intro;





    public static String getTestJson(){
        return "{\n" +
                "    \"recommended_article\": [\n" +
                "        {\n" +
                "            \"article_title\": \"知乎好问题 · 维修保养汽车时有哪些陷阱？如何避免被坑？\",\n" +
                "            \"article_url\": \"234234\",\n" +
                "            \"article_id\": 1,\n" +
                "            \"username\": \"动力火车\",\n" +
                "            \"user_id\": 2,\n" +
                "            \"user_img\": \"http://odf775exx.bkt.clouddn.com/Upload/article/20161022/13411566.jpg\",\n" +
                "            \"article_shares\": 2,\n" +
                "            \"article_collects\": 2,\n" +
                "            \"article_brief_intro\": \"当山峰没有棱角的时候当河水不再流当时间停住日夜不分\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"article_title\": \"客厅不想放沙发？那就放床吧\",\n" +
                "            \"article_url\": \"1234\",\n" +
                "            \"article_id\": 1,\n" +
                "            \"username\": \"还珠格格\",\n" +
                "            \"user_id\": 2,\n" +
                "            \"user_img\": \"http://odf775exx.bkt.clouddn.com/Upload/article/20161022/13503284.jpg\",\n" +
                "            \"article_shares\": 2,\n" +
                "            \"article_collects\": 2,\n" +
                "            \"article_brief_intro\": \"1998年，中国台湾作家琼瑶着手为自己编剧的电视剧《还珠格格》创作主题曲，\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"article_title\": \"刚刚让大半个美国「断网」的 DDoS 攻击是怎么回事？\",\n" +
                "            \"article_url\": \"234\",\n" +
                "            \"article_id\": 1,\n" +
                "            \"username\": \"我是歌手\",\n" +
                "            \"user_id\": 2,\n" +
                "            \"user_img\": \"http://odf775exx.bkt.clouddn.com/Upload/article/20161022/3d6d45e4f8534c83a797f7f92472e181%5C.jpg\",\n" +
                "            \"article_shares\": 2,\n" +
                "            \"article_collects\": 2,\n" +
                "            \"article_brief_intro\": \"拍摄《当》的MV时，动力火车两人弹着吉他，留着长发，扎着辫子，站在屋顶上迎风高唱。黑暗的灯光下，两个俊朗的年轻人穿着黑色的T恤，背靠背，拨动着琴弦，高亢地歌唱着。缠绵悱恻、撕心裂肺的爱情在动力火车的演绎下格外地生动。\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }


    public static class RecommendedArticleBean {
        public String article_title;
        public String article_url;
        public int article_id;
        public String username;
        public int user_id;
        public String user_img;
        public int article_shares;
        public int article_collects;
        public String article_brief_intro;
    }
}
