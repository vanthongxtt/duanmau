package vn.sefviapp.asm_ps09105.Api.SachApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.sefviapp.asm_ps09105.Config.Constant;

public interface DeleteSachApi {
    @FormUrlEncoded
    @POST(Constant.URL_DELETE_SACH)
    Call<ResponseBody> DeleteSach(@Field("id") String id);
}
