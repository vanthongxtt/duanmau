package vn.sefviapp.asm_ps09105.Interface;

import java.util.ArrayList;

import vn.sefviapp.asm_ps09105.Model.HoaDon;

public interface HoaDonListener {
    void getDataSuccess(HoaDon hoaDon);
    void getMessageError(Exception e);
    void getDataArraySuccess(ArrayList<HoaDon> hoaDonArrayList);
    void getDataError(HoaDon hoaDon);
}
