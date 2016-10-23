package com.apanda.base.Entity;

import java.io.Serializable;
import java.util.List;


public class SuperBean<T> implements Serializable{
    public String date;
    public List<StoryModel> stories;
    public T top_stories;
}
