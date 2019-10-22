package vn.sefviapp.asm_ps09105.Api.HoaDonChiTiet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Model.HoaDonChiTiet;

public interface GethoaDonChiTietApi {
    @FormUrlEncoded
    @POST(Constant.URL_GET_HOA_DON_CHI_TIET)
    Call<List<HoaDonChiTiet>> GetHoaDonChiTiet(@Field("id") String id);
}
