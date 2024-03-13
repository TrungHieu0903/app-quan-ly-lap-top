package model;

import java.io.Serializable;

public class NhaCungCap implements Serializable {
    private int id;
    private String ten;

    public NhaCungCap() {
    }

    public NhaCungCap(int id, String ten) {
        this.id = id;
        this.ten = ten;
    }

    @Override
    public String toString() {
        return "ID: "+id+"\n"+"Tên: "+ten ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
