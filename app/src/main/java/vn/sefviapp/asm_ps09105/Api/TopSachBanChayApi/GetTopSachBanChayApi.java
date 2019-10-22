package vn.sefviapp.asm_ps09105.Api.TopSachBanChayApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Model.HoaDonChiTiet;

public interface GetTopSachBanChayApi {
    @FormUrlEncoded
    @POST(Constant.URL_GET_TOP_SACH_BAN_CHAY)
    Call<List<HoaDonChiTiet>> GetTopSachBanChay(@Field("date") String date);
}
