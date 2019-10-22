package vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeNgayApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.sefviapp.asm_ps09105.Adapter.ThongKeAdapter;
import vn.sefviapp.asm_ps09105.Api.BaseRetrofitIml;
import vn.sefviapp.asm_ps09105.Interface.ThongKeListener;

public class GetThongKeNgayHoaDonApilml extends BaseRetrofitIml {
    GetThongKeNgayHoaDonApi getThongKeNgayHoaDonApi;
    Retrofit retrofit = getRetrofit();
    public void GetThongKeNgayHoaDon(String date, final ThongKeListener thongKeListener){
        getThongKeNgayHoaDonApi =  retrofit.create(GetThongKeNgayHoaDonApi.class);
        Call<ResponseBody> call = getThongKeNgayHoaDonApi.GetThongKeNgayHoaDon(date);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("success");
                        if (status == 200) {
                            String tongGiaSach = jsonObject.getString("tong");
                            thongKeListener.GetDataSuccessXuat(tongGiaSach);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                thongKeListener.GetDataErr(new Exception(t));
            }
        });
    }
}
