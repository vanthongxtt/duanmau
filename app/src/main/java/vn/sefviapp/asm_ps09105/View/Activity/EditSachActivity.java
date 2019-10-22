package vn.sefviapp.asm_ps09105.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import vn.sefviapp.asm_ps09105.Adapter.SpinnerTheLoaiAdapter;
import vn.sefviapp.asm_ps09105.Api.NguoiDungApi.AddUserAllApilml;
import vn.sefviapp.asm_ps09105.Api.SachApi.EditSachApilml;
import vn.sefviapp.asm_ps09105.Api.TheLoaiApi.GetTheLoaiApilml;
import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Interface.GetTheLoaiListener;
import vn.sefviapp.asm_ps09105.Interface.LoginListener;
import vn.sefviapp.asm_ps09105.Interface.SachListener;
import vn.sefviapp.asm_ps09105.Model.Account;
import vn.sefviapp.asm_ps09105.Model.Sach;
import vn.sefviapp.asm_ps09105.Model.TheLoai;
import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.View.ShowSnackBar;

import static android.util.Base64.DEFAULT;

public class EditSachActivity extends AppCompatActivity {
    Toolbar toolBarEditSach;
    ImageView imgEditSach;
    Spinner spTheLoaiEdit;
    EditText edtEditTenSach, edtEditTacGia, edtEditNXB, edtEditGiaBan, edtEditSoLuong;
    Button btnCapNhatSach;
    private int idTheLoai;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    private static String idUser;
    private Uri filePath;
    private String idSach;
    String nameImage;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sach);

        initControls();
        initEvents();
    }

    private void initEvents() {
        btnCapNhatSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenSach = edtEditTenSach.getText().toString();
                String tacGia = edtEditTacGia.getText().toString();
                String nxb = edtEditNXB.getText().toString();
                String giaBan = edtEditGiaBan.getText().toString();
                String soLuong = edtEditSoLuong.getText().toString();

                if (isCheck(tenSach,tacGia,nxb,giaBan,soLuong)){
                    luuSach(tenSach, tacGia, nxb, giaBan, soLuong);
//                    Toast.makeText(EditSachActivity.this, "ok", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditSachActivity.this, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imgEditSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });
    }

    private void initControls() {
        toolBarEditSach = findViewById(R.id.toolBarEditSach);
        toolBarEditSach.setTitle("Chỉnh Sửa Sách");

        SharedPreferences pref = this.getSharedPreferences("Account", 0);
        idUser = pref.getString("idUser", null);

        imgEditSach = findViewById(R.id.imgEditSach);
        spTheLoaiEdit = findViewById(R.id.spTheLoaiEdit);
        edtEditTenSach = findViewById(R.id.edtEditTenSach);
        edtEditTacGia = findViewById(R.id.edtEditTacGia);
        edtEditNXB = findViewById(R.id.edtEditNXB);
        edtEditGiaBan = findViewById(R.id.edtEditGiaBan);
        edtEditSoLuong = findViewById(R.id.edtEditSoLuong);
        btnCapNhatSach = findViewById(R.id.btnEditSach);

        Sach sach = (Sach) getIntent().getSerializableExtra("sachEdit");
        Picasso.get().load(Constant.URL_BASE_UPLOAD + sach.getImage()).error(R.drawable.img_sach).into(imgEditSach);
        edtEditTenSach.setText(sach.getTenSach());
        edtEditTacGia.setText(sach.getTacGia());
        edtEditNXB.setText(sach.getNxb());
        edtEditGiaBan.setText(sach.getGiaBan());
        edtEditSoLuong.setText(sach.getSoLuong());

        idSach = String.valueOf(sach.getId());

        nameImage = sach.getImage();


        progressDialog = new ProgressDialog(this);
        addItemsToSpinnerTheLoai(spTheLoaiEdit);



    }
    private boolean isCheck(String tenSach, String tacGia, String nxb, String giaBan, String soLuong){
        return !tenSach.isEmpty() && !tacGia.isEmpty() && !nxb.isEmpty() && !giaBan.isEmpty() && !soLuong.isEmpty();
    }
    private void addItemsToSpinnerTheLoai(final Spinner spChonTheLoai) {
        GetTheLoaiApilml getTheLoaiApilml = new GetTheLoaiApilml();
        getTheLoaiApilml.GetTheLoai(idUser, new GetTheLoaiListener() {
            @Override
            public void GetDataTheLoaiSuccess(final ArrayList<TheLoai> theLoais) {
                SpinnerTheLoaiAdapter spinAdapter = new SpinnerTheLoaiAdapter(EditSachActivity.this, theLoais);
                spChonTheLoai.setAdapter(spinAdapter);
                spChonTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapter, View v,
                                               int position, long id) {
                        idTheLoai = theLoais.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }

            @Override
            public void GetDataTheLoaiError(Exception e) {

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgEditSach.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            filePath = data.getData();
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgEditSach.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void luuSach(String tenSach, String tacGia, String nxb, String giaBan, String soLuong) {
        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgEditSach.getDrawable();
            Bitmap bitmapLuu = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmapLuu.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] hinh = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            String encodedImage = Base64.encodeToString(hinh, DEFAULT);

            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Vui lòng chờ...");
            progressDialog.show();

            if (filePath != null){
                nameImage = getFileName(filePath);
            }

            EditSachApilml editSachApilml = new EditSachApilml();
            editSachApilml.EditSach(idSach, String.valueOf(idTheLoai), tenSach, tacGia, nxb, giaBan, soLuong, nameImage, encodedImage, idUser, new SachListener() {
                @Override
                public void getDataSachSuccess(Sach sach) {
                    progressDialog.dismiss();
                    Toast.makeText(EditSachActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void getMessageError(Exception e) {

                }

                @Override
                public void getDataArraySuccess(ArrayList<Sach> sachArrayList) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
