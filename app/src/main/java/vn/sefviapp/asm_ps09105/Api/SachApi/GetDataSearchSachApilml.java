package vn.sefviapp.asm_ps09105.Api.SachApi;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.sefviapp.asm_ps09105.Api.BaseRetrofitIml;
import vn.sefviapp.asm_ps09105.Interface.SachListener;
import vn.sefviapp.asm_ps09105.Model.Sach;

public class GetDataSearchSachApilml extends BaseRetrofitIml {
    GetDataSearchSachApi getDataSearchSachApi;
    Retrofit retrofit = getRetrofit();
    public void GetDataSearchSach(String keyword, final SachListener sachListener){
        getDataSearchSachApi = retrofit.create(GetDataSearchSachApi.class);
        Call<List<Sach>> call = getDataSearchSachApi.GetDataSearchSach(keyword);
        call.enqueue(new Callback<List<Sach>>() {
            @Override
            public void onResponse(Call<List<Sach>> call, Response<List<Sach>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Sach> arrayList = (ArrayList<Sach>) response.body();
                    sachListener.getDataArraySuccess(arrayList);
                }
            }

            @Override
            public void onFailure(Call<List<Sach>> call, Throwable t) {

            }
        });
    }
}
