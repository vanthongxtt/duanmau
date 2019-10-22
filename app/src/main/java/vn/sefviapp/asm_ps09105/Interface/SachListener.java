package vn.sefviapp.asm_ps09105.Interface;

import java.util.ArrayList;

import vn.sefviapp.asm_ps09105.Model.Sach;

public interface SachListener {
    void getDataSachSuccess(Sach sach);
    void getMessageError(Exception e);
    void getDataArraySuccess(ArrayList<Sach> sachArrayList);
}
