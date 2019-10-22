package vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeNgayApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface GetThongKeNgayHoaDonApi {
    @FormUrlEncoded
    @POST(Constant.URL_GET_THONG_KE_NGAY_HOA_DON)
    Call<ResponseBody> GetThongKeNgayHoaDon(@Field("date") String date);
}
