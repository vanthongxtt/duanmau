package vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeNgayApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.sefviapp.asm_ps09105.Api.BaseRetrofitIml;
import vn.sefviapp.asm_ps09105.Interface.ThongKeListener;

public class GetThongKeNgaySachApilml extends BaseRetrofitIml {
    GetThongKeNgaySachApi getThongKeNgaySachApi;
    Retrofit retrofit = getRetrofit();
    public void GetThongKeNgaySach(String date, final ThongKeListener thongKeListener){
        getThongKeNgaySachApi = retrofit.create(GetThongKeNgaySachApi.class);
        Call<ResponseBody> call = getThongKeNgaySachApi.GetThongKeNgaySach(date);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("success");
                        if (status == 200) {
                            String tongGiaSach = jsonObject.getString("giaBan");
                            thongKeListener.GetDataSuccessNhap(tongGiaSach);
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
