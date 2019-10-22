package vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeNgayApi;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface GetThongKeNgaySachApi {
    @FormUrlEncoded
    @POST(Constant.URL_GET_Thong_Ke_Ngay_Sach)
    Call<ResponseBody> GetThongKeNgaySach(@Field("date") String date);
}
