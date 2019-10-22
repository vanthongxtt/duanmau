package vn.sefviapp.asm_ps09105.Model;

import java.io.Serializable;

public class HoaDon implements Serializable {
    private int id;
    private String name;
    private String date;
    private String key;

    public HoaDon(int id, String name, String date, String key) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.key = key;
    }

    public HoaDon() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HoaDon(String key) {
        this.key = key;
    }
}
