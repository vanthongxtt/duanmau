package vn.sefviapp.asm_ps09105.Interface;

import java.util.ArrayList;

import vn.sefviapp.asm_ps09105.Model.HoaDonChiTiet;

public interface HoaDonChiTietListener {
    void getDataSuccess(HoaDonChiTiet hoaDonChiTiet);
    void getMessageError(Exception e);
    void getDataArraySuccess(ArrayList<HoaDonChiTiet> hoaDonChiTietArrayList);
    void getDataError(HoaDonChiTiet hoaDonChiTiet);
}
