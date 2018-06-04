package cn.devinkin.domain;


import java.util.List;

public class User {
    private int id;
    @SuppressWarnings("unused")
    private String username;
    private List list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static void main(String[] args) {
        add();
    }

    @Deprecated
    public static void add() {

    }

    public static void add(int ... args) {
    }
}
