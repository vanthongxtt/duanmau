package vn.sefviapp.asm_ps09105.Api.NguoiDungApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Model.Account;
import vn.sefviapp.asm_ps09105.Model.TheLoai;

public interface GetAllNguoiDungApi {
    @FormUrlEncoded
    @POST(Constant.URL_GETALLNGUOIDUNG)
    Call<List<Account>> GetAllNguoiDung(@Field("admin") String admin);
}
