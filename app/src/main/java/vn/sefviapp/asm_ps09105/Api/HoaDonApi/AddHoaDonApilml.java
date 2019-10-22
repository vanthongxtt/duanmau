package vn.sefviapp.asm_ps09105.Api.HoaDonApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.sefviapp.asm_ps09105.Api.BaseRetrofitIml;
import vn.sefviapp.asm_ps09105.Interface.HoaDonListener;
import vn.sefviapp.asm_ps09105.Model.HoaDon;

public class AddHoaDonApilml extends BaseRetrofitIml {
    AddHoaDonApi addHoaDonApi;
    Retrofit retrofit = getRetrofit();
    public void AddHoaDon(String name, String date, String key, final HoaDonListener hoaDonListener){
        addHoaDonApi = retrofit.create(AddHoaDonApi.class);
        Call<ResponseBody> call = addHoaDonApi.AddHoaDon(name,date,key);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("success");
                        if (status == 200) {
                            HoaDon hoaDon = new HoaDon();
                            hoaDon.setId(jsonObject.getInt("id"));
                            hoaDon.setName(jsonObject.getString("name"));
                            hoaDon.setDate(jsonObject.getString("date"));
                            hoaDonListener.getDataSuccess(hoaDon);
                        } else {

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
                hoaDonListener.getMessageError(new Exception(t));
            }
        });
    }
}
