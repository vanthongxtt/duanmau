package vn.sefviapp.asm_ps09105.Api.NguoiDungApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface AddUserAllApi {
    @FormUrlEncoded
    @POST(Constant.URL_ADDUSERALL)
    Call<ResponseBody> AddUserAll(@Field("email") String email, @Field("password") String password, @Field("name") String name, @Field("phone") String phone, @Field("diachi") String diachi, @Field("admin") String admin, @Field("image") String image, @Field("imageName") String imageName, @Field("token") String token);
}
