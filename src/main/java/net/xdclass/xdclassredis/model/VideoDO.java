package net.xdclass.xdclassredis.model;

public class VideoDO {

    private int id;

    private String img;

    private String title;

    private int price;

    public VideoDO(int id, String img, String title, int price) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
