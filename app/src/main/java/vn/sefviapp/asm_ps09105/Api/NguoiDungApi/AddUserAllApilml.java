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

public class AddUserAllApilml extends BaseRetrofitIml {
    AddUserAllApi addUserAllApi;
    Retrofit retrofit = getRetrofit();

    public void AddUserAll(String email, String password, String name, String phone, String diachi, String admin, String image, String imageName,String token, final LoginListener loginListener){
        addUserAllApi = retrofit.create(AddUserAllApi.class);
        Call<ResponseBody> responseBodyCall = addUserAllApi.AddUserAll(email, password, name, phone, diachi, admin, image, imageName, token);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("success");
                        if (status == 200) {
                            Account account = new Account();
                           
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
