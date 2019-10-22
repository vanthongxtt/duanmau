package vn.sefviapp.asm_ps09105.Api.TopSachBanChayApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.sefviapp.asm_ps09105.Api.BaseRetrofitIml;
import vn.sefviapp.asm_ps09105.Interface.HoaDonChiTietListener;
import vn.sefviapp.asm_ps09105.Model.HoaDonChiTiet;

public class GetTopSachBanChayApilml extends BaseRetrofitIml {
    GetTopSachBanChayApi getTopSachBanChayApi;
    Retrofit retrofit = getRetrofit();
    public void GetTopSachBanChay(String date, final HoaDonChiTietListener hoaDonChiTietListener){
        getTopSachBanChayApi = retrofit.create(GetTopSachBanChayApi.class);
        Call<List<HoaDonChiTiet>> listCall = getTopSachBanChayApi.GetTopSachBanChay(date);
        listCall.enqueue(new Callback<List<HoaDonChiTiet>>() {
            @Override
            public void onResponse(Call<List<HoaDonChiTiet>> call, Response<List<HoaDonChiTiet>> response) {
                if (response.isSuccessful()) {
                    ArrayList<HoaDonChiTiet> arrayList = (ArrayList<HoaDonChiTiet>) response.body();
                    hoaDonChiTietListener.getDataArraySuccess(arrayList);
                }
            }

            @Override
            public void onFailure(Call<List<HoaDonChiTiet>> call, Throwable t) {
                    hoaDonChiTietListener.getMessageError(new Exception(t));
            }
        });
    }
}
