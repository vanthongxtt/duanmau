package vn.sefviapp.asm_ps09105.Api.TheLoaiApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Model.TheLoai;

public interface GetTheLoaiApi {
    @FormUrlEncoded
    @POST(Constant.URL_GETTHELOAI)
    Call<List<TheLoai>> getTheLoai (@Field("iduser") String idUser, @Field("type") String type );
}
