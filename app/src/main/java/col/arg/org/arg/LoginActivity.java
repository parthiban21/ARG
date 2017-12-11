package col.arg.org.arg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import col.arg.org.arg.bal.PreferenceConnector;
import col.arg.org.arg.bal.Utils;
import col.arg.org.arg.model.LoginRequestModel;
import col.arg.org.arg.model.response.LoginRegResponseModel;
import col.arg.org.arg.nal.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/*
*
 * Created by arg on 22/9/17.

*/


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout lLayoutRegistration;
    private ProgressDialog mProgressDialog;
    private TextView btnLogin;
    private EditText txtUserName, txtPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getViewCasting();
      //  PrintHashkey();
    }
    private void PrintHashkey() {

            try {
                PackageInfo info = getPackageManager().getPackageInfo(
                        "col.arg.org.fblogin",
                        PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            } catch (PackageManager.NameNotFoundException e) {

            } catch (NoSuchAlgorithmException e) {

            }
    }

    private void getViewCasting() {
        lLayoutRegistration = (LinearLayout) findViewById(R.id.lLayoutRegistration);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPwd = (EditText) findViewById(R.id.txtPwd);
        btnLogin = (TextView) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        lLayoutRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lLayoutRegistration:
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLogin:
                if ("".equals(txtUserName.getText().toString())) {
                    txtUserName.requestFocus();
                    txtUserName.setError("Please enter the user name");
                } else if ("".equals(txtPwd.getText().toString())) {
                    txtPwd.requestFocus();
                    txtPwd.setError("Please enter the password");
                } else {


                    Intent intent1 = new Intent(LoginActivity.this, CollegeNameListActivity.class);
                    startActivity(intent1);
                    //  loginAPICalling(txtUserName.getText().toString(), txtPwd.getText().toString());
                    //   }

                }
            default:
                break;
        }
    }
    private void loginAPICalling(String userName, String password) {
        try {
            System.out.println("==============Login=========");
            if (Utils.isNetworkAvailable(this)) {
                mProgressDialog = new ProgressDialog(LoginActivity.this);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                LoginRequestModel userDatas = new LoginRequestModel();
                System.out.println("==============Login====66=====");
                userDatas.setUsername(userName);
                userDatas.setPassword(password);
                Retrofit retrofit = Utils.callRetrofit();
                ApiInterface service = retrofit.create(ApiInterface.class);
                Call<LoginRegResponseModel> call = service.getUserLoginDetails("", userDatas);
                call.enqueue(new Callback<LoginRegResponseModel>() {
                    @Override
                    public void onResponse(Call<LoginRegResponseModel> call, Response<LoginRegResponseModel> response) {
                        System.out.println("================" + new Gson().toJson(response.body()));
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            System.out.println("=======success===========" + response.body().getStatus());

                            if ("ARG_01".equalsIgnoreCase(response.body().getStatus()) || "ARG_03".equalsIgnoreCase(response.body().getStatus())) {
                                PreferenceConnector.writeString(LoginActivity.this, PreferenceConnector.USER_ID, response.body().getUserDatas().getId().toString());
                                PreferenceConnector.writeString(LoginActivity.this, PreferenceConnector.USER_NAME, response.body().getUserDatas().getUsername());

                                String userId = PreferenceConnector.readString(LoginActivity.this, PreferenceConnector.USER_ID, null);
                                String userName = PreferenceConnector.readString(LoginActivity.this, PreferenceConnector.USER_NAME, null);
                                System.out.println(userId + "========login====" + userName);


                                Intent intent = new Intent(LoginActivity.this, CollegeNameListActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid User name and Password", Toast.LENGTH_SHORT).show();
                            }
                        }

//                        PreferenceConnector.writeString(LoginActivity.this, PreferenceConnector.USER_ID, "");
//                        PreferenceConnector.readString(LoginActivity.this, PreferenceConnector.USER_NAME, "");
//
                    }

                    @Override
                    public void onFailure(Call<LoginRegResponseModel> call, Throwable t) {
                        t.printStackTrace();
                        System.out.println("============failure=====" + t.getMessage());
                        Toast.makeText(LoginActivity.this, "Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "Connect to internet to login", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
