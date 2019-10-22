package vn.sefviapp.asm_ps09105.Model;

public class TheLoai {
    private int id;
    private String nameTheLoai;
    private String moTa;
    private String idUser;

    public TheLoai() {
    }

    public TheLoai(int id, String tenTheLoai) {
        this.id = id;
        this.nameTheLoai = tenTheLoai;
    }

    public TheLoai(int id, String tenTheLoai, String moTaTheLoai, String idUser) {
        this.id = id;
        this.nameTheLoai = tenTheLoai;
        this.moTa = moTaTheLoai;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenTheLoai() {
        return nameTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.nameTheLoai = tenTheLoai;
    }

    public String getMoTaTheLoai() {
        return moTa;
    }

    public void setMoTaTheLoai(String moTaTheLoai) {
        this.moTa = moTaTheLoai;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
