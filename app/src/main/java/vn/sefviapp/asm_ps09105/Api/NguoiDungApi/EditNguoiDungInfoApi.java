package vn.sefviapp.asm_ps09105.Api.NguoiDungApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface EditNguoiDungInfoApi {
    @FormUrlEncoded
    @POST(Constant.URL_EDITNGUOIDUNGINFO)
    Call<ResponseBody> EditNguoiDungInfo (@Field("id") String id, @Field("name") String name, @Field("email") String email, @Field("phone") String phone, @Field("diachi") String diachi);
}
