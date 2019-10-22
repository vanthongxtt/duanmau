package vn.sefviapp.asm_ps09105.Api.NguoiDungApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.sefviapp.asm_ps09105.Api.BaseRetrofitIml;
import vn.sefviapp.asm_ps09105.Interface.LoginListener;
import vn.sefviapp.asm_ps09105.Model.Account;
import vn.sefviapp.asm_ps09105.View.Activity.LoginActivity;

public class GetAllNguoiDungApilml extends BaseRetrofitIml {
    GetAllNguoiDungApi getAllNguoiDungApi;
    Retrofit retrofit = getRetrofit();

    public void GetAllNguoiDung(String admin, final LoginListener loginListener){
        getAllNguoiDungApi = retrofit.create(GetAllNguoiDungApi.class);
        Call<List<Account>> listCall = getAllNguoiDungApi.GetAllNguoiDung(admin);
        listCall.enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                if (response.isSuccessful()){
                    ArrayList<Account> accounts = (ArrayList<Account>) response.body();
                    loginListener.getDataArraySuccess(accounts);
                }
            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {
                loginListener.getMessageError(new Exception(t));
            }
        });
    }
}
