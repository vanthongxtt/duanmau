package vn.sefviapp.asm_ps09105.Api.NguoiDungApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface DangKyApi {
    @FormUrlEncoded
    @POST(Constant.URL_DANGKY)
    Call<ResponseBody> DangKy(@Field("name") String name, @Field("email") String email, @Field("admin") String admin, @Field("imageName") String image, @Field("token") String token);
}
