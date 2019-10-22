package vn.sefviapp.asm_ps09105.Api.SachApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.sefviapp.asm_ps09105.Api.BaseRetrofitIml;
import vn.sefviapp.asm_ps09105.Interface.SachListener;
import vn.sefviapp.asm_ps09105.Model.Sach;

public class DeleteSachApilml extends BaseRetrofitIml {
    DeleteSachApi deleteSachApi;
    Retrofit retrofit = getRetrofit();

    public void DeleteSach(String id, final SachListener sachListener) {
        deleteSachApi = retrofit.create(DeleteSachApi.class);
        Call<ResponseBody> responseBodyCall = deleteSachApi.DeleteSach(id);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("success");
                        if (status == 200) {
                            Sach sach = new Sach();

                            sachListener.getDataSachSuccess(sach);
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
                sachListener.getMessageError(new Exception(t));
            }
        });
    }
}
