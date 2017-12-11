package col.arg.org.arg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
 * Created by arg on 23/9/17.
 */

public class CollegeNameListActivity extends AppCompatActivity {

    private Spinner spinnerLocation, spinnerCategory;
    private HashMap<String, String> mCategoryHashMap = new HashMap<>();
    private ArrayList<String> eduCategoryDatas = new ArrayList<>();
    private HashMap<String, String> mLocationHashMap = new HashMap<>();
    private ArrayList<String> mLocationList = new ArrayList<>();
    private TextView btnCollegeListNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_name_list);
        getViewCasting();
    }

    private void getViewCasting() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Find Colleges");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinnerLocation = (Spinner) findViewById(R.id.spinnerLocation);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        btnCollegeListNext = (TextView) findViewById(R.id.btnCollegeListNext);
        btnCollegeListNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollegeNameListActivity.this, FindCollegeActivity.class);
                intent.putExtra("category", spinnerCategory.getSelectedItem().toString());
                intent.putExtra("location", spinnerLocation.getSelectedItem().toString());
                startActivity(intent);
            }
        });
        getCollegeListAPICalling();
    }

    private void getCollegeListAPICalling() {
        try {
            System.out.println("==============Login=========");
            if (Utils.isNetworkAvailable(this)) {
                final ProgressDialog mProgressDialog = new ProgressDialog(CollegeNameListActivity.this);
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
//                            ArrayList<CollegeListResponseModel.EduCategoryData> eduCategoryDatas = new ArrayList<>();
//                            eduCategoryDatas.clear();
//                            for (int i = 0; i < response.body().getEduCategoryDatas().size(); i++) {
//                                CollegeListResponseModel.EduCategoryData bean = new CollegeListResponseModel.EduCategoryData();
//                                bean.setEducationalCategoryName(response.body().getEduCategoryDatas().get(i).getEducationalCategoryName());
//                                bean.setId(response.body().getEduCategoryDatas().get(i).getId());
//                                eduCategoryDatas.add(bean);
//                            }
                            eduCategoryDatas = new ArrayList<>();
                            eduCategoryDatas.clear();
                            mCategoryHashMap.clear();
                            mCategoryHashMap = new HashMap<>();
                            for (int i = 0; i < response.body().getEduCategoryDatas().size(); i++) {
                                eduCategoryDatas.add(response.body().getEduCategoryDatas().get(i).getEducationalCategoryName());
                                mCategoryHashMap.put(response.body().getEduCategoryDatas().get(i).getEducationalCategoryName(),
                                        response.body().getEduCategoryDatas().get(i).getId().toString());
                            }
                            mLocationList = new ArrayList<>();
                            mLocationList.clear();
                            mLocationHashMap.clear();
                            mLocationHashMap = new HashMap<>();
                            for (int i = 0; i < response.body().getLocationDatas().size(); i++) {
                                mLocationList.add(response.body().getLocationDatas().get(i).getLocationName());
                                mLocationHashMap.put(response.body().getLocationDatas().get(i).getLocationName(),
                                        response.body().getLocationDatas().get(i).getId().toString());
                            }
                            System.out.println("====eduCategoryDatas========" + eduCategoryDatas.size());
                            //Creating the ArrayAdapter instance having the bank name list
                            ArrayAdapter aa = new ArrayAdapter(CollegeNameListActivity.this, R.layout.spinner_list, eduCategoryDatas);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
                            spinnerCategory.setAdapter(aa);


                            ArrayAdapter location = new ArrayAdapter(CollegeNameListActivity.this, R.layout.spinner_list, mLocationList);
                            location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
                            spinnerLocation.setAdapter(location);

                        }
                    }

                    @Override
                    public void onFailure(Call<CollegeListResponseModel> call, Throwable t) {
                        t.printStackTrace();
                        System.out.println("============failure=====" + t.getMessage());
                        Toast.makeText(CollegeNameListActivity.this, "Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                    }
                });
            } else {
                Toast.makeText(CollegeNameListActivity.this, "Connect to internet to login", Toast.LENGTH_SHORT).show();
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
    //Performing action onItemSelected and onNothing selected
//    @Override
//    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        mCategoryHashMap.get(eduCategoryDatas.get(position));
//        Toast.makeText(getApplicationContext(), eduCategoryDatas.get(position), Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> arg0) {
//
//    }
}
