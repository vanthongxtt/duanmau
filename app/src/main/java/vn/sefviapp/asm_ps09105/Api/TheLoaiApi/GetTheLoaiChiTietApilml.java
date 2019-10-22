package vn.sefviapp.asm_ps09105.Api.TheLoaiApi;

import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.sefviapp.asm_ps09105.Api.BaseRetrofitIml;
import vn.sefviapp.asm_ps09105.Interface.TheLoaiListener;
import vn.sefviapp.asm_ps09105.Model.TheLoai;
import vn.sefviapp.asm_ps09105.R;

public class GetTheLoaiChiTietApilml extends BaseRetrofitIml {
    GetTheLoaiChiTietApi getTheLoaiChiTietApi;
    Retrofit retrofit = getRetrofit();

    public void GetTheLoaiChiTiet(String idUser, String id, final TheLoaiListener theLoaiListener){
        getTheLoaiChiTietApi = retrofit.create(GetTheLoaiChiTietApi.class);
        Call<ResponseBody> requestBodyCall = getTheLoaiChiTietApi.GetTheLoaiChiTiet(idUser, "0", id);
        requestBodyCall.enqueue(new Callback<ResponseBody>() {
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
                            theLoaiListener.getDataTheLoaiSuccess(theLoai);
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
                theLoaiListener.getMessageError(new Exception(t));
            }
        });
    }
}
