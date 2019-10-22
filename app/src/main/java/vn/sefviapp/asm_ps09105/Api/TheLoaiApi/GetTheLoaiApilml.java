package vn.sefviapp.asm_ps09105.Api.TheLoaiApi;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.sefviapp.asm_ps09105.Api.BaseRetrofitIml;
import vn.sefviapp.asm_ps09105.Interface.GetTheLoaiListener;
import vn.sefviapp.asm_ps09105.Interface.TheLoaiListener;
import vn.sefviapp.asm_ps09105.Model.TheLoai;

public class GetTheLoaiApilml extends BaseRetrofitIml {
    GetTheLoaiApi getTheLoaiApi;
    Retrofit retrofit = getRetrofit();

    public void GetTheLoai(String idUser, final GetTheLoaiListener theLoaiListener){
        getTheLoaiApi = retrofit.create(GetTheLoaiApi.class);
        Call<List<TheLoai>> theLoaiCall = getTheLoaiApi.getTheLoai(idUser, "1");
        theLoaiCall.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                if (response.isSuccessful()) {
                    ArrayList<TheLoai> arrayList = (ArrayList<TheLoai>) response.body();
                    theLoaiListener.GetDataTheLoaiSuccess(arrayList);
                }
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {
                theLoaiListener.GetDataTheLoaiError(new Exception(t));
            }
        });
    }
}
