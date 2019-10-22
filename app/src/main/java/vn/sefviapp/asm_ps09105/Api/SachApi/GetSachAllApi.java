package vn.sefviapp.asm_ps09105.Api.SachApi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Model.Sach;

public interface GetSachAllApi {
    @FormUrlEncoded
    @POST(Constant.URL_GET_SACH_ALL)
    Call<List<Sach>> GetSach(@Field("iduser") String idUser, @Field("type") String type);
    Call<ResponseBody> GetSachItem(@Field("iduser") String idUser, @Field("type") String type, @Field("id") String id);
}
