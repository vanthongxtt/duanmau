package vn.sefviapp.asm_ps09105.Api.HoaDonApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.sefviapp.asm_ps09105.Api.BaseRetrofitIml;
import vn.sefviapp.asm_ps09105.Interface.HoaDonListener;
import vn.sefviapp.asm_ps09105.Model.HoaDon;

public class GetHoaDonApilml extends BaseRetrofitIml {
    GetHoaDonApi getHoaDonApi;
    Retrofit retrofit = getRetrofit();
    public void GetHoaDon(String id, final HoaDonListener hoaDonListener){
        getHoaDonApi = retrofit.create(GetHoaDonApi.class);
        Call<List<HoaDon>> listCall = getHoaDonApi.GetHoaDon(id);
        listCall.enqueue(new Callback<List<HoaDon>>() {
            @Override
            public void onResponse(Call<List<HoaDon>> call, Response<List<HoaDon>> response) {
                if (response.isSuccessful()) {
                    ArrayList<HoaDon> arrayList = (ArrayList<HoaDon>) response.body();
                    hoaDonListener.getDataArraySuccess(arrayList);
                }
            }

            @Override
            public void onFailure(Call<List<HoaDon>> call, Throwable t) {
                hoaDonListener.getMessageError(new Exception(t));
            }
        });
    }
}
