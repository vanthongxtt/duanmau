package vn.sefviapp.asm_ps09105.Api.HoaDonApi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Model.HoaDon;

public interface GetHoaDonApi {
    @FormUrlEncoded
    @POST(Constant.URL_GET_HOA_DON)
    Call<List<HoaDon>> GetHoaDon(@Field("id") String id);
}
