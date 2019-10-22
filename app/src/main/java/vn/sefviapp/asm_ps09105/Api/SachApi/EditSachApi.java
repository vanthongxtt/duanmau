package vn.sefviapp.asm_ps09105.Api.SachApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface EditSachApi {
    @FormUrlEncoded
    @POST(Constant.URL_EDIT_SACH)
    Call<ResponseBody> EditSach(@Field("id") String id, @Field("idTheLoai") String idTheLoai, @Field("tensach") String tensach, @Field("tacgia") String tacgia, @Field("nxb") String nxb, @Field("giaban") String giaban, @Field("soluong") String soluong, @Field("imageName") String imageName, @Field("admin") String admin, @Field("image") String image);
}
