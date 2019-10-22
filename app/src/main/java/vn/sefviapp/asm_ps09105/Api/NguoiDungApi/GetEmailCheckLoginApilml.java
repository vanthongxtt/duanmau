package vn.sefviapp.asm_ps09105.Api.NguoiDungApi;

import android.util.Log;
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
import vn.sefviapp.asm_ps09105.Interface.LoginListener;
import vn.sefviapp.asm_ps09105.Model.Account;

public class GetEmailCheckLoginApilml extends BaseRetrofitIml {
    GetEmailCheckLoginApi getEmailCheckLoginApi;
    Retrofit retrofit = getRetrofit();

    public void GetEmailCheckLogin(String token, final LoginListener loginListener){
        getEmailCheckLoginApi = retrofit.create(GetEmailCheckLoginApi.class);
        Call<ResponseBody> call = getEmailCheckLoginApi.GetEmailCheckLogin(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("success");
                        Log.i("checkLogin", status+"");
                        if (status == 200) {
                            Account account = new Account();
                            account.setId(jsonObject.getInt("id"));
                            loginListener.getDataSuccess(account);
                        } else {
                            Account account = new Account();
                            loginListener.getDataError(account);
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
