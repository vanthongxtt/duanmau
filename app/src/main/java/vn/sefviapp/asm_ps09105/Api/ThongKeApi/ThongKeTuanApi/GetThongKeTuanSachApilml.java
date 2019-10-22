package vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeTuanApi;

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

public class GetThongKeTuanSachApilml extends BaseRetrofitIml {
    GetThongKeTuanSachApi getThongKeTuanSachApi;
    Retrofit retrofit = getRetrofit();
    public void GetThongKeTuan(String date, final ThongKeListener thongKeListener){
        getThongKeTuanSachApi = retrofit.create(GetThongKeTuanSachApi.class);
        Call<ResponseBody> call = getThongKeTuanSachApi.GetThongKeTuan(date);
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
