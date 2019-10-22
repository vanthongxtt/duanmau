package vn.sefviapp.asm_ps09105.Interface;

import java.util.ArrayList;

import vn.sefviapp.asm_ps09105.Model.Account;

public interface LoginListener {
    void getDataSuccess(Account account);
    void getMessageError(Exception e);
    void getDataArraySuccess(ArrayList<Account> accounts);
    void getDataError(Account account);
}
