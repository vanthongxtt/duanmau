package vn.sefviapp.asm_ps09105.Api.NguoiDungApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface DoiMatKhauUserApi {
    @FormUrlEncoded
    @POST(Constant.URL_DOIMATKHAUUSER)
    Call<ResponseBody> DoiMatKhauUser(@Field("id") String id, @Field("password") String pass);
}
