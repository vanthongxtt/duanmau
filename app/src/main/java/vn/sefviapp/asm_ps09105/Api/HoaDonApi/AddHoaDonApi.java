package vn.sefviapp.asm_ps09105.Api.HoaDonApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface AddHoaDonApi {
    @FormUrlEncoded
    @POST(Constant.URL_ADD_HOA_DON)
    Call<ResponseBody> AddHoaDon(@Field("name") String name, @Field("date") String date, @Field("key") String key);
}
