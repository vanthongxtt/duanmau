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

public class DeleteTheLoaiApilml extends BaseRetrofitIml {
    DeleteTheLoaiApi deleteTheLoaiApi;
    Retrofit retrofit = getRetrofit();

    public void DeleteTheLoai(String id, String idUser, final TheLoaiListener theLoaiListener){
        deleteTheLoaiApi = retrofit.create(DeleteTheLoaiApi.class);
        Call<ResponseBody> call = deleteTheLoaiApi.DeleteTheLoai(id, idUser);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("success");
                        if (status == 200) {
                            String nity = jsonObject.getString("message");
                            theLoaiListener.getDataSuccess(nity);

                        } else if(status == 300) {
                            theLoaiListener.getDataSuccess("Không Thể Xóa Thể Loại");
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
