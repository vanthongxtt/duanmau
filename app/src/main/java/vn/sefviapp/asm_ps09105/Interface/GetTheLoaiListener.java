package vn.sefviapp.asm_ps09105.Interface;

import java.util.ArrayList;

import vn.sefviapp.asm_ps09105.Model.TheLoai;

public interface GetTheLoaiListener {
    void GetDataTheLoaiSuccess(ArrayList<TheLoai> theLoais);
    void GetDataTheLoaiError(Exception e);
}
