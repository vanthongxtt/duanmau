package vn.sefviapp.asm_ps09105.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.sefviapp.asm_ps09105.Api.LoginAccount.LoginAccountApiIml;
import vn.sefviapp.asm_ps09105.Api.NguoiDungApi.DangKyApi;
import vn.sefviapp.asm_ps09105.Api.NguoiDungApi.DangKyApilml;
import vn.sefviapp.asm_ps09105.Api.NguoiDungApi.GetEmailCheckLoginApilml;
import vn.sefviapp.asm_ps09105.Interface.LoginListener;
import vn.sefviapp.asm_ps09105.Model.Account;
import vn.sefviapp.asm_ps09105.R;
import vn.sefviapp.asm_ps09105.Receive.NetworkChangeReceive;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.editTextEmail)
    EditText edtEmail;
    @BindView(R.id.editTextPassword)
    EditText edtPassword;
    LoginAccountApiIml accountApiIml;
    //    EditText edtEmail, edtPassword;
    static Button btnLogin, btnLoginWithFacebook, btnLoginWithGoogle;
    TextView txtResetPassword;
    ProgressDialog progressDialog;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    LoginManager loginManager;
    List<String> preFacebook = Arrays.asList("public_profile", "email");
    CallbackManager manager;
    NetworkChangeReceive networkChangeReceive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        networkChangeReceive = new NetworkChangeReceive();
        registerNetworkBroadcastForNougat();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        FacebookSdk.sdkInitialize(getApplicationContext());
        manager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(getApplicationContext());

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        loginWithFb();
        initControls();
        initEvents();
    }
    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(networkChangeReceive, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(networkChangeReceive, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(networkChangeReceive);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    private void initEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        txtResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEmail();
            }
        });
        btnLoginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        btnLoginWithFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginManager.getInstance().logInWithReadPermissions(LoginActivity.this, preFacebook);
            }
        });

    }

    private void initControls() {
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        btnLogin = findViewById(R.id.btnLogin);
        btnLoginWithFacebook = findViewById(R.id.btnLoginWithFacebook);
        btnLoginWithGoogle = findViewById(R.id.btnLoginWithGoogle);
        edtEmail = findViewById(R.id.editTextEmail);
        edtPassword = findViewById(R.id.editTextPassword);
        txtResetPassword = findViewById(R.id.txtResetPassword);

        ButterKnife.bind(this);
        accountApiIml = new LoginAccountApiIml();

    }

    private void showDialogEmail() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.forgot_password);
        dialog.show();
    }

    private void login() {
        if (!validate()) {
            Toast.makeText(this, "Đăng nhập lỗi", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Đang đăng nhập...");
            progressDialog.setCanceledOnTouchOutside(false); //không cho nhấn ngoài
            progressDialog.show();
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();
            accountApiIml.authAccount(email, password, new LoginListener() {
                @Override
                public void getDataSuccess(Account account) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("Account", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("idUser", account.getId() + "");
                    editor.commit();

                    progressDialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }

                @Override
                public void getMessageError(Exception e) {
                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void getDataArraySuccess(ArrayList<Account> accounts) {

                }

                @Override
                public void getDataError(Account account) {

                }
            });
        }
    }

    private void loginWithFb() {

        loginManager.getInstance().registerCallback(manager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Đang đăng nhập...");
                progressDialog.setCanceledOnTouchOutside(false); //không cho nhấn ngoài
                progressDialog.show();
//                Toast.makeText(LoginActivity.this, ""  + loginResult.getRecentlyGrantedPermissions().contains("email"), Toast.LENGTH_SHORT).show();
//                Toast.makesText(LoginActivity.this, "" + loginResult.getAccessToken().getToken(), Toast.LENGTH_SHORT).show();
                loginFacebook(loginResult);


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    public boolean validate() {
        boolean valid = true;
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email không đúng định dạng");
            valid = false;
        } else {
            edtEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edtPassword.setError("Mật khẩu phải từ 4 đến 10 ký tự");
            valid = false;
        } else {
            edtPassword.setError(null);
        }
        return valid;
    }

    private void updateUI(@Nullable final GoogleSignInAccount accounts) {
        if (accounts != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Đang đăng nhập...");
            progressDialog.setCanceledOnTouchOutside(false); //không cho nhấn ngoài
            progressDialog.show();
            GetEmailCheckLoginApilml getEmailCheckLoginApilml = new GetEmailCheckLoginApilml();
            getEmailCheckLoginApilml.GetEmailCheckLogin(accounts.getId(), new LoginListener() {
                @Override
                public void getDataSuccess(Account account) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("Account", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("idUser", account.getId() + "");
                    editor.commit();
                    progressDialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void getMessageError(Exception e) {
                }

                @Override
                public void getDataArraySuccess(ArrayList<Account> accounts) {

                }

                @Override
                public void getDataError(Account account) {
                    dangKyUser(accounts.getDisplayName(), accounts.getEmail(), "0", String.valueOf(accounts.getPhotoUrl()), String.valueOf(accounts.getId()));
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        manager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            updateUI(null);
        }
    }

    private void dangKyUser(String name, String email, String admin, String img, String token) {
        DangKyApilml dangKyApilml = new DangKyApilml();
        dangKyApilml.DangKy(name, email, admin, img, token, new LoginListener() {
            @Override
            public void getDataSuccess(Account account) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("Account", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("idUser", account.getId() + "");
                editor.commit();
                progressDialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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

    private void loginFacebook(final LoginResult loginResult){
        GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {

                    final String name = object.getString("name");
                    GetEmailCheckLoginApilml getEmailCheckLoginApilml = new GetEmailCheckLoginApilml();
                    getEmailCheckLoginApilml.GetEmailCheckLogin(loginResult.getAccessToken().getUserId(), new LoginListener() {
                        @Override
                        public void getDataSuccess(Account account) {
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("Account", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("idUser", account.getId() + "");
                            editor.commit();
                            progressDialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void getMessageError(Exception e) {
                        }

                        @Override
                        public void getDataArraySuccess(ArrayList<Account> accounts) {

                        }

                        @Override
                        public void getDataError(Account account) {
                            dangKyUser(name, "", "0", "", loginResult.getAccessToken().getUserId());
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).executeAsync();

    }
    public static void checkIsConnects(boolean value){
        if (value){
            btnLogin.setEnabled(true);
        }else {
            btnLogin.setEnabled(false);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }
}
