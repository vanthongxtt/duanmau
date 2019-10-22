package vn.sefviapp.asm_ps09105.Interface;

import vn.sefviapp.asm_ps09105.Model.TheLoai;

public interface TheLoaiListener {
    void getDataTheLoaiSuccess(TheLoai theLoai);
    void getMessageError(Exception e);
    void getDataSuccess(String thanhcong);
}
