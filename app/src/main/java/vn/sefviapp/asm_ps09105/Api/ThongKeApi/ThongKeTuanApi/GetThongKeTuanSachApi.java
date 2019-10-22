package vn.sefviapp.asm_ps09105.Api.ThongKeApi.ThongKeTuanApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface GetThongKeTuanSachApi {
    @FormUrlEncoded
    @POST(Constant.URL_GET_Thong_Ke_Tuan_Sach)
    Call<ResponseBody> GetThongKeTuan(@Field("date") String date);
}
