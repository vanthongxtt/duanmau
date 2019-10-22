package vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeThangApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface GetThongKeThangHoaDonApi {
    @FormUrlEncoded
    @POST(Constant.URL_GET_THONG_KE_THANG_HOA_DON)
    Call<ResponseBody> GetThongKeThangHoaDon(@Field("month") String month, @Field("year") String year);
}
