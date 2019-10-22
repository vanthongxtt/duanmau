package vn.sefviapp.asm_ps09105.Api.NguoiDungApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.sefviapp.asm_ps09105.Api.BaseRetrofitIml;
import vn.sefviapp.asm_ps09105.Interface.LoginListener;
import vn.sefviapp.asm_ps09105.Model.Account;

public class EditNguoiDungApilml extends BaseRetrofitIml {
    EditNguoiDungInfoApi editNguoiDungInfoApi;
    Retrofit retrofit = getRetrofit();

    public void EditNguoiDung(String id, String name, String email, String phone, String diaChi, final LoginListener loginListener){
        editNguoiDungInfoApi = retrofit.create(EditNguoiDungInfoApi.class);
        Call<ResponseBody> responseBodyCall = editNguoiDungInfoApi.EditNguoiDungInfo(id,name,email,phone,diaChi);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("success");
                        if (status == 200) {
                            Account account = new Account();
                            account.setEmail(jsonObject.getString("email"));
                            account.setName(jsonObject.getString("name"));
                            account.setPhone(jsonObject.getString("phone"));
                            account.setDiachi(jsonObject.getString("diachi"));
                            loginListener.getDataSuccess(account);
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
                loginListener.getMessageError(new Exception(t));
            }
        });
    }
}
