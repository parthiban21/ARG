package col.arg.org.arg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import col.arg.org.arg.bal.Utils;
import col.arg.org.arg.model.RegisterRequestModel;
import col.arg.org.arg.model.response.RegisterResponseModel;
import col.arg.org.arg.nal.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by arg on 23/9/17.
 */

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout lLayoutLogin;
    private EditText txtUserName, txtMobileNo, txtEmail, txtPassword, txtConfirmPwd, txtName, txtLocation;
    private TextView btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getViewCasting();
    }

    private void getViewCasting() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtMobileNo = (EditText) findViewById(R.id.txtMobileNo);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPwd);
        txtConfirmPwd = (EditText) findViewById(R.id.txtConfirmPwd);
        lLayoutLogin = (LinearLayout) findViewById(R.id.lLayoutLogin);
        txtLocation = (EditText) findViewById(R.id.txtLocation);
        txtName = (EditText) findViewById(R.id.txtName);
        btnRegister = (TextView) findViewById(R.id.btnRegister);
        lLayoutLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.lLayoutLogin:
                finish();
                break;
            case R.id.btnRegister:
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if ("".equals(txtName.getText().toString())) {
                    txtName.setError("Please enter the Name");
                    txtName.requestFocus();
                } else if ("".equals(txtUserName.getText().toString())) {
                    txtUserName.setError("Please enter the Login name");
                    txtUserName.requestFocus();
                } else if ("".equals(txtEmail.getText().toString())) {
                    txtEmail.setError("Please enter the Email");
                    txtEmail.requestFocus();
                } else if (!txtEmail.getText().toString().matches(emailPattern)) {
                    txtEmail.setError("Invalid Email Address");
                    txtEmail.requestFocus();
                } else if ("".equals(txtMobileNo.getText().toString())) {
                    txtMobileNo.setError("Please enter the Mobile no");
                    txtMobileNo.requestFocus();
                } else if (txtMobileNo.getText().toString().length() < 10 || txtMobileNo.getText().toString().length() > 12) {
                    txtMobileNo.setError("Please enter the Mobile no");
                    txtMobileNo.requestFocus();
                } else if ("".equals(txtPassword.getText().toString())) {
                    txtPassword.setError("Please enter the Password");
                    txtPassword.requestFocus();
                } else if ("".equals(txtConfirmPwd.getText().toString())) {
                    txtConfirmPwd.setError("Please enter the Confirm-Password");
                } else if (!txtPassword.getText().toString().equals(txtConfirmPwd.getText().toString())) {
                    txtPassword.requestFocus();
                    txtPassword.setError("Check your password");
                } else if (txtPassword.getText().toString().length() < 6) {
                    System.out.println("======password========");
                    txtPassword.requestFocus();
                    txtPassword.setError("Please enter atleast 6 chars");
                } else if ("".equals(txtLocation.getText().toString())) {
                    txtLocation.requestFocus();
                    txtLocation.setError("Please enter the Location");
                } else {
                    loginAPICalling();
                }

                break;
        }

    }

    private void loginAPICalling() {
        try {
            System.out.println("==============Login=========");
            if (Utils.isNetworkAvailable(this)) {
                final ProgressDialog mProgressDialog = new ProgressDialog(RegistrationActivity.this);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                RegisterRequestModel userDatas = new RegisterRequestModel();
                System.out.println("==============Login====66=====");
                userDatas.setUsername(txtUserName.getText().toString());
                userDatas.setPhoneNum(txtMobileNo.getText().toString());
                userDatas.setEmail(txtEmail.getText().toString());
                userDatas.setPassword(txtPassword.getText().toString());
                userDatas.setName(txtName.getText().toString());
                userDatas.setLocation(txtLocation.getText().toString());
                Retrofit retrofit = Utils.callRetrofit();
                ApiInterface service = retrofit.create(ApiInterface.class);
                Call<RegisterResponseModel> call = service.setRegistrationDetails("", userDatas);
                call.enqueue(new Callback<RegisterResponseModel>() {
                    @Override
                    public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
                        System.out.println("================" + new Gson().toJson(response.body()));
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            System.out.println("=======success===========" + response.body().getStatus());

                            if ("ARG_01".equalsIgnoreCase(response.body().getStatus())) {
                                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Invalid User name and Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                        t.printStackTrace();
                        System.out.println("============failure=====" + t.getMessage());
                        Toast.makeText(RegistrationActivity.this, "Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                    }
                });
            } else {
                Toast.makeText(RegistrationActivity.this, "Connect to internet to login", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
