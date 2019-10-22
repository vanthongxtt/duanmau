package vn.sefviapp.asm_ps09105.Api.SachApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.sefviapp.asm_ps09105.Api.BaseRetrofitIml;
import vn.sefviapp.asm_ps09105.Interface.SachListener;
import vn.sefviapp.asm_ps09105.Model.Sach;

public class GetSachAllApilml extends BaseRetrofitIml {
    GetSachAllApi getSachAllApi;
    Retrofit retrofit = getRetrofit();
    public void GetSach(String idUser, final SachListener sachListener){
        getSachAllApi = retrofit.create(GetSachAllApi.class);
        Call<List<Sach>> listCall = getSachAllApi.GetSach(idUser, "1");
        listCall.enqueue(new Callback<List<Sach>>() {
            @Override
            public void onResponse(Call<List<Sach>> call, Response<List<Sach>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Sach> arrayList = (ArrayList<Sach>) response.body();
                    sachListener.getDataArraySuccess(arrayList);
                }
            }

            @Override
            public void onFailure(Call<List<Sach>> call, Throwable t) {
                sachListener.getMessageError(new Exception(t));
            }
        });
    }
    public void GetSachItem(String idUser, String idSach, final SachListener sachListener){
        getSachAllApi = retrofit.create(GetSachAllApi.class);
        Call<ResponseBody> call = getSachAllApi.GetSachItem(idUser, "0", idSach);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("success");
                        if (status == 200) {
                          Sach sach = new Sach();
                          sach.setTenSach(jsonObject.getString("tenSach"));
                          sach.setTacGia(jsonObject.getString("tacGia"));
                          sach.setNxb(jsonObject.getString("nxb"));
                          sach.setSoLuong(jsonObject.getString("soLuong"));
                          sach.setGiaBan(jsonObject.getString("giaBan"));
                          sach.setImage(jsonObject.getString("image"));
                          sach.setIdTheLoai(jsonObject.getString("idTheLoai"));
                          sachListener.getDataSachSuccess(sach);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                sachListener.getMessageError(new Exception(t));
            }
        });
    }
}
