package vn.sefviapp.asm_ps09105.Api.LoginAccount;

import android.util.Log;

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

public class LoginAccountApiIml extends BaseRetrofitIml {
    String TAG = LoginAccountApiIml.class.getSimpleName();
    LoginAccountApi loginAccountApi;
    Retrofit retrofit = getRetrofit();

    //Xác thực tài khoản
    public void authAccount (String email, String passWord, final LoginListener listener) {
        loginAccountApi = retrofit.create(LoginAccountApi.class);
        Call<ResponseBody> call = loginAccountApi.loginAccount(email,passWord);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //Kết quả trả về dạng String nên cần chuyển về dạng Json
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("success");
                        if (status == 200) {
                            Account account = new Account();
                            account.setId(jsonObject.getInt("id"));
                            account.setEmail(jsonObject.getString("email"));
                            account.setName(jsonObject.getString("name"));
                            account.setPhone(jsonObject.getString("phone"));
                            account.setDiachi(jsonObject.getString("diachi"));
                            account.setAdmin(jsonObject.getString("admin"));
                            listener.getDataSuccess(account);
                        } else {
                            Log.d(TAG, "onResponse: "+jsonObject.toString());
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
                listener.getMessageError(new Exception(t));
            }
        });
    }
}
