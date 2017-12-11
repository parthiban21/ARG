package col.arg.org.arg;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import col.arg.org.arg.bal.Utils;
import col.arg.org.arg.model.response.CollegeListResponseModel;
import col.arg.org.arg.nal.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by arg on 24/9/17.
 */

public class StudentDetailsActivity extends AppCompatActivity {
    private Spinner spinnerCategory;
    private HashMap<String, String> mCategoryHashMap = new HashMap<>();
    private ArrayList<String> eduCategoryDatas = new ArrayList<>();
    private EditText txtStudentName, txtSchoolName,txtStudentAdd,txtPincode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        getViewCasting();
    }

    private void getViewCasting() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        txtSchoolName = (EditText) findViewById(R.id.txtSchoolName);
        txtStudentAdd = (EditText) findViewById(R.id.txtStudentAdd);
        txtSchoolName = (EditText) findViewById(R.id.txtSchoolName);
        txtStudentName = (EditText) findViewById(R.id.txtStudentName);
        txtSchoolName = (EditText) findViewById(R.id.txtSchoolName);
        txtStudentName = (EditText) findViewById(R.id.txtStudentName);
        getCollegeListAPICalling();
    }

    private void getCollegeListAPICalling() {
        try {
            System.out.println("==============Login=========");
            if (Utils.isNetworkAvailable(this)) {
                final ProgressDialog mProgressDialog = new ProgressDialog(StudentDetailsActivity.this);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.show();
                Retrofit retrofit = Utils.callRetrofit();
                ApiInterface service = retrofit.create(ApiInterface.class);
                Call<CollegeListResponseModel> call = service.setCollegeNameList();
                call.enqueue(new Callback<CollegeListResponseModel>() {
                    @Override
                    public void onResponse(Call<CollegeListResponseModel> call, Response<CollegeListResponseModel> response) {
                        System.out.println("========college========" + new Gson().toJson(response.body()));
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            System.out.println(response.body().getLocationDatas().size() +
                                    "=======success===========" +
                                    response.body().getEduCategoryDatas().size());
                            eduCategoryDatas = new ArrayList<>();
                            eduCategoryDatas.clear();
                            mCategoryHashMap.clear();
                            mCategoryHashMap = new HashMap<>();
                            for (int i = 0; i < response.body().getEduCategoryDatas().size(); i++) {
                                eduCategoryDatas.add(response.body().getEduCategoryDatas().get(i).getEducationalCategoryName());
                                mCategoryHashMap.put(response.body().getEduCategoryDatas().get(i).getEducationalCategoryName(),
                                        response.body().getEduCategoryDatas().get(i).getId().toString());
                            }

                            System.out.println("====eduCategoryDatas========" + eduCategoryDatas.size());
                            //Creating the ArrayAdapter instance having the bank name list
                            ArrayAdapter aa = new ArrayAdapter(StudentDetailsActivity.this, R.layout.spinner_list, eduCategoryDatas);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
                            spinnerCategory.setAdapter(aa);


                        }
                    }

                    @Override
                    public void onFailure(Call<CollegeListResponseModel> call, Throwable t) {
                        t.printStackTrace();
                        System.out.println("============failure=====" + t.getMessage());
                        Toast.makeText(StudentDetailsActivity.this, "Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                    }
                });
            } else {
                Toast.makeText(StudentDetailsActivity.this, "Connect to internet to login", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
