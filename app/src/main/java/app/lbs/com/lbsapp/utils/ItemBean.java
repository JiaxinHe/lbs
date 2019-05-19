package app.lbs.com.lbsapp.utils;

/**
 * Created by Jessie on 2019/5/15.
 */

public class ItemBean {

    private int id;
    private String title;

    public ItemBean(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
