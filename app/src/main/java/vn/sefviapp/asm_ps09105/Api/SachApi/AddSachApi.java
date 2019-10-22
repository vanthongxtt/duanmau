package vn.sefviapp.asm_ps09105.Api.SachApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface AddSachApi {
    @FormUrlEncoded
    @POST(Constant.URL_ADD_SACH)
    Call<ResponseBody> AddSach(@Field("tensach") String tenSach, @Field("tacgia") String tacGia, @Field("nxb") String nxb, @Field("giaban") String giaBan, @Field("soluong") String soLuong, @Field("admin") String idUser, @Field("imageName") String imageName, @Field("image") String image, @Field("idTheLoai") String idTheLoai, @Field("date") String date);
}
