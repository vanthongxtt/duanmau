package vn.sefviapp.asm_ps09105.Api.TheLoaiApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface GetTheLoaiChiTietApi {
    @FormUrlEncoded
    @POST(Constant.URL_GETTHELOAI)
    Call<ResponseBody> GetTheLoaiChiTiet(@Field("iduser") String idUser, @Field("type") String type, @Field("id") String id);
}
