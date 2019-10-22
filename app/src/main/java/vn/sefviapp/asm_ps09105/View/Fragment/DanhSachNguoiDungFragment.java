package vn.sefviapp.asm_ps09105.View.Fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import vn.sefviapp.asm_ps09105.Adapter.NguoiDungAllAdapter;
import vn.sefviapp.asm_ps09105.Api.NguoiDungApi.AddUserAllApilml;
import vn.sefviapp.asm_ps09105.Api.NguoiDungApi.DeleteUsersAllApi;
import vn.sefviapp.asm_ps09105.Api.NguoiDungApi.DeleteUsersAllApilml;
import vn.sefviapp.asm_ps09105.Api.NguoiDungApi.GetAllNguoiDungApilml;
import vn.sefviapp.asm_ps09105.Api.NguoiDungApi.UploadImageNguoiDungApilml;
import vn.sefviapp.asm_ps09105.Config.Constant;
import vn.sefviapp.asm_ps09105.Interface.LoginListener;
import vn.sefviapp.asm_ps09105.Model.Account;
import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.View.Activity.LoginActivity;
import vn.sefviapp.asm_ps09105.View.Activity.MainActivity;
import vn.sefviapp.asm_ps09105.View.Activity.MapsActivity;
import vn.sefviapp.asm_ps09105.View.ShowSnackBar;

import static android.app.Activity.RESULT_OK;
import static android.util.Base64.DEFAULT;


public class DanhSachNguoiDungFragment extends Fragment {
    static RecyclerView recyclerViewNguoiDungAll;
    FloatingActionButton fabAddNguoiDungNews;
    private static NguoiDungAllAdapter nguoiDungAllAdapter;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    private Uri filePath;
    ImageView imgUser;
    ProgressDialog progressDialog;
    static Context context;

    private static final int REQUEST_CALL = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_danh_sach_nguoi_dung, container, false);
        initControls(v);
        initEvents();
        return v;
    }

    private void initEvents() {
        fabAddNguoiDungNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddUsers();
            }
        });
    }

    private void initControls(View v) {
        recyclerViewNguoiDungAll = v.findViewById(R.id.recyclerViewNguoiDungAll);
        fabAddNguoiDungNews = v.findViewById(R.id.fabAddNguoiDungNews);
        Toolbar toolbar = v.findViewById(R.id.toolbarUser);
        toolbar.setTitle("Danh Sách Người Dùng");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        context = getContext();
        progressDialog = new ProgressDialog(getContext());
        getDataAllUser();

    }

    private static void getDataAllUser() {
        GetAllNguoiDungApilml allNguoiDungApilml = new GetAllNguoiDungApilml();
        allNguoiDungApilml.GetAllNguoiDung("0", new LoginListener() {
            @Override
            public void getDataSuccess(Account account) {

            }

            @Override
            public void getMessageError(Exception e) {

            }

            @Override
            public void getDataArraySuccess(ArrayList<Account> accounts) {
                nguoiDungAllAdapter = new NguoiDungAllAdapter(context, accounts);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context.getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewNguoiDungAll.setHasFixedSize(true);
                recyclerViewNguoiDungAll.setLayoutManager(layoutManager);
                recyclerViewNguoiDungAll.setItemAnimator(new DefaultItemAnimator());
                recyclerViewNguoiDungAll.setAdapter(nguoiDungAllAdapter);
            }

            @Override
            public void getDataError(Account account) {

            }
        });
    }

    private void dialogAddUsers() {
        final Dialog dialogAdd = new Dialog(getContext());
        dialogAdd.setContentView(R.layout.dialog_add_user);
        final EditText edtEmail = dialogAdd.findViewById(R.id.edtEmailUsers);
        final EditText edtPassword = dialogAdd.findViewById(R.id.edtPasswordUsers);
        final EditText edtName = dialogAdd.findViewById(R.id.edtTenUsers);
        final EditText edtPhone = dialogAdd.findViewById(R.id.edtPhoneUsers);
        final EditText edtDiaChi = dialogAdd.findViewById(R.id.edtDiaChiUsers);

        Button btnAdd = dialogAdd.findViewById(R.id.btnDialogAddUsersNews);
        Button btnHuy = dialogAdd.findViewById(R.id.btnDialogHuyAddUser);

        imgUser = dialogAdd.findViewById(R.id.imgAddUsers);

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                if (isUser(email, password, name, phone, diaChi)) {
                    dialogAdd.dismiss();
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Vui lòng chờ...");
                    progressDialog.show();
                    luuUser(email, password, name, phone, diaChi);
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdd.dismiss();
            }
        });
        dialogAdd.show();
    }

    private boolean isUser(String email, String password, String name, String phone, String diaChi) {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || phone.isEmpty() || diaChi.isEmpty()) {
            Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.PHONE.matcher(phone).matches()) {
            Toast.makeText(getContext(), "Số diện thoại không đúng định dạng", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgUser.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            filePath = data.getData();
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgUser.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
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

    private void luuUser(String email, String password, String name, String phone, String diaChi) {
        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgUser.getDrawable();
            Bitmap bitmapLuu = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmapLuu.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] hinh = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            String encodedImage = Base64.encodeToString(hinh, DEFAULT);


            AddUserAllApilml addUserAllApilml = new AddUserAllApilml();
            addUserAllApilml.AddUserAll(email, password, name, phone, diaChi, "0", encodedImage, getFileName(filePath), "",new LoginListener() {
                @Override
                public void getDataSuccess(Account account) {
                    progressDialog.dismiss();
                    getDataAllUser();
                    ShowSnackBar showSnackBar = new ShowSnackBar();
                    showSnackBar.showSnackbar(getView(), "Thêm người dùng thành công");
                }

                @Override
                public void getMessageError(Exception e) {
                }

                @Override
                public void getDataArraySuccess(ArrayList<Account> accounts) {

                }

                @Override
                public void getDataError(Account account) {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(int id, final Context m, final View view) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.show();
        DeleteUsersAllApilml deleteUsersAllApilml = new DeleteUsersAllApilml();
        deleteUsersAllApilml.DeleteUserAll(String.valueOf(id), new LoginListener() {
            @Override
            public void getDataSuccess(Account account) {
                progressDialog.dismiss();
                ShowSnackBar showSnackBar = new ShowSnackBar();
                showSnackBar.showSnackbar(view, "Xóa người dùng thành công");
                getDataAllUser();
            }

            @Override
            public void getMessageError(Exception e) {

            }

            @Override
            public void getDataArraySuccess(ArrayList<Account> accounts) {

            }

            @Override
            public void getDataError(Account account) {

            }
        });
    }

    public static void itemClickUser(final Account account, final Context m, View view) {
        final Dialog dialog = new Dialog(m);
        dialog.setContentView(R.layout.dialog_info_user);
        ImageView imgUser = dialog.findViewById(R.id.imgInfoUserDialog);
        TextView txtName = dialog.findViewById(R.id.txtNameUserDialog);
        final TextView txtEmail = dialog.findViewById(R.id.txtEmailInfoDialog);
        final TextView txtPhone = dialog.findViewById(R.id.txtPhoneInfoDialog);
        final TextView txtDiachi = dialog.findViewById(R.id.txtDiachiInfoDialog);
        Button btnClose = dialog.findViewById(R.id.btnDialogCloseUserInfo);

        Picasso.get().load(Constant.URL_BASE_UPLOAD + account.getImage()).error(R.drawable.img_sach).into(imgUser);
        txtName.setText(account.getName());
        txtEmail.setText(account.getEmail());
        txtPhone.setText(account.getPhone());
        txtDiachi.setText(account.getDiachi());

        txtDiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (txtDiachi.getText().toString().isEmpty()){
                  Toast.makeText(m, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
              }else {
                  Intent i = new Intent(context, MapsActivity.class);
                  Bundle bundle = new Bundle();
                  bundle.putString("NameUserMap", account.getName());
                  bundle.putString("DiaChiUserMap", account.getDiachi());
                  i.putExtras(bundle);
                  context.startActivity(i);
              }

            }
        });

        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtEmail.getText().toString().isEmpty()){
                    Toast.makeText(m, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }else {
                    composeEmail(account.getEmail(),"Lâm Văn Thông - PS09105", context);
                }
            }
        });

        txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtPhone.getText().toString().isEmpty()){
                    Toast.makeText(m, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }else {
                    xuLyGoiLuon(account.getPhone(), context);
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private static void composeEmail(String addresses, String subject, Context context) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
    private static void xuLyGoiLuon(String sdt, Context context){
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            Uri uri = Uri.parse("tel:" + sdt);
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(uri);
            context.startActivity(intent);
        }

    }
}
