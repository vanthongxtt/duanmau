package vn.sefviapp.asm_ps09105.Api.TheLoaiApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface DeleteTheLoaiApi {
    @FormUrlEncoded
    @POST(Constant.URL_DELETETHELOAI)
    Call<ResponseBody> DeleteTheLoai (@Field("id") String id, @Field("iduser") String idUser);
}
