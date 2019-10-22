package vn.sefviapp.asm_ps09105.Api.HoaDonChiTiet;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface DeleteHoaDonChiTietApi {
    @FormUrlEncoded
    @POST(Constant.URL_DELETE_HOA_DON_CHI_TIET)
    Call<ResponseBody> DeleteHoaDonChiTiet(@Field("id") String id);
}
