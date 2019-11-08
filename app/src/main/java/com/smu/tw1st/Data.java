package com.smu.tw1st;

public class Data {

    private String name;
    private int icon;
    private String money;
    private String url;

    public Data(String name, int icon, String money, String url) {
        this.name = name;
        this.icon = icon;
        this.money = money;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
