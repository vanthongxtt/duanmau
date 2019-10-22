package vn.sefviapp.asm_ps09105.Model;


import java.io.Serializable;

public class Sach implements Serializable {
    private int id;
    private String tenSach;
    private String tacGia;
    private String nxb;
    private String giaBan;
    private String soLuong;
    private String image;
    private String idUser;
    private String idTheLoai;
    private String date;

    public Sach() {
    }

    public Sach(int id, String tenSach, String tacGia, String nxb, String giaBan, String soLuong, String image, String idUser, String idTheLoai, String date) {
        this.id = id;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.nxb = nxb;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.image = image;
        this.idUser = idUser;
        this.idTheLoai = idTheLoai;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }

    public String getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(String giaBan) {
        this.giaBan = giaBan;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdTheLoai() {
        return idTheLoai;
    }

    public void setIdTheLoai(String idTheLoai) {
        this.idTheLoai = idTheLoai;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
