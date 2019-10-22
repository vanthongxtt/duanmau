package vn.sefviapp.asm_ps09105.Interface;

public interface ThongKeListener {
    void GetDataSuccessNhap(String dataNhap);
    void GetDataSuccessXuat(String dataXuat);
    void GetDataErr(Exception e);
}
