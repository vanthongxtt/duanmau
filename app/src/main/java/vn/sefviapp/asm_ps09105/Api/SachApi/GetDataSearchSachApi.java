package vn.sefviapp.asm_ps09105.Api.SachApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Model.Sach;

public interface GetDataSearchSachApi {
    @FormUrlEncoded
    @POST(Constant.URL_GET_DATA_SEARCH_SACH)
    Call<List<Sach>> GetDataSearchSach(@Field("keyword") String keyword);
}
