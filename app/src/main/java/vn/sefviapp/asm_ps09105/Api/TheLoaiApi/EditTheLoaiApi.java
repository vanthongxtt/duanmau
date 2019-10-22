package vn.sefviapp.asm_ps09105.Api.TheLoaiApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface EditTheLoaiApi {
    @FormUrlEncoded
    @POST(Constant.URL_EDITTHELOAI)
    Call<ResponseBody> EditTheLoai (@Field("iduser") String idUser, @Field("id") String id, @Field("nametheloai") String nameTheLoai, @Field("mota") String moTa);
}
