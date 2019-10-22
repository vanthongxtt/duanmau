package vn.sefviapp.asm_ps09105.Api.LoginAccount;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface LoginAccountApi {
    @FormUrlEncoded
    @POST(Constant.URL_LOGIN)
    Call<ResponseBody> loginAccount (@Field("email") String email, @Field("password") String password);
}
