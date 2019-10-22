package vn.sefviapp.asm_ps09105.Model;

public class HoaDonChiTiet {
    private int id;
    private String idSach;
    private String idHoaDon;
    private String soLuong;
    private String tenSach;
    private String giaBan;
    private String image;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int id, String idSach, String idHoaDon, String soLuong) {
        this.id = id;
        this.idSach = idSach;
        this.idHoaDon = idHoaDon;
        this.soLuong = soLuong;
    }

    public HoaDonChiTiet(int id, String idSach, String idHoaDon, String soLuong, String tenSach, String giaSach, String image) {
        this.id = id;
        this.idSach = idSach;
        this.idHoaDon = idHoaDon;
        this.soLuong = soLuong;
        this.tenSach = tenSach;
        this.giaBan = giaSach;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdSach() {
        return idSach;
    }

    public void setIdSach(String idSach) {
        this.idSach = idSach;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getGiaSach() {
        return giaBan;
    }

    public void setGiaSach(String giaSach) {
        this.giaBan = giaSach;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
