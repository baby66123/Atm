package com.tom.atm;

public class Funtion {
    String name;
    int icon;

    public Funtion() {
    }

    public Funtion(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public Funtion(String name) {
        this.name = name;
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
}
