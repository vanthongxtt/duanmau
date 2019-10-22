package vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeThangApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface GetThongKeThangSachApi {
    @FormUrlEncoded
    @POST(Constant.URL_GET_THONG_KE_THANG_SACH)
    Call<ResponseBody> GetThongKeThangSach(@Field("month") String month, @Field("year") String year);
}
