package vn.sefviapp.asm_ps09105.Api.TheLoaiApi;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.sefviapp.asm_ps09105.Api.BaseRetrofitIml;
import vn.sefviapp.asm_ps09105.Interface.TheLoaiListener;
import vn.sefviapp.asm_ps09105.Model.TheLoai;

public class AddTheLoaiApilml extends BaseRetrofitIml {
    AddTheLoaiApi addTheLoaiApi;
    Retrofit retrofit = getRetrofit();

    public void AddTheLoai(String nameTheLoai, String moTa, String idUser, final TheLoaiListener loaiListener){
        addTheLoaiApi = retrofit.create(AddTheLoaiApi.class);
        Call<ResponseBody> call = addTheLoaiApi.addTheLoai(nameTheLoai, moTa, idUser);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("success");
                        if (status == 200) {
                            TheLoai theLoai = new TheLoai();
                            theLoai.setTenTheLoai(jsonObject.getString("nameTheLoai"));
                            theLoai.setMoTaTheLoai(jsonObject.getString("moTa"));
                            theLoai.setIdUser(jsonObject.getString("idUser"));
                            loaiListener.getDataTheLoaiSuccess(theLoai);
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
                loaiListener.getMessageError(new Exception(t));
            }
        });
    }
}
