package col.arg.org.arg;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import col.arg.org.arg.bal.PreferenceConnector;
import col.arg.org.arg.bal.Utils;
import col.arg.org.arg.model.FindCollegeRequestModel;
import col.arg.org.arg.model.LoginRequestModel;
import col.arg.org.arg.model.response.CollegeListResponseModel;
import col.arg.org.arg.model.response.FindCollegeResponseModel;
import col.arg.org.arg.model.response.LoginRegResponseModel;
import col.arg.org.arg.nal.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by arg on 23/9/17.
 */

public class FindCollegeActivity extends AppCompatActivity {

//chngsesf


    private String CategoryId = "", locationId = "", mLocationApiId = "", mCategoryApiId = "";
    private Spinner spinnerLocation, spinnerCategory;
    private HashMap<String, String> mCategoryHashMap = new HashMap<>();
    private ArrayList<String> eduCategoryDatas = new ArrayList<>();
    private HashMap<String, String> mLocationHashMap = new HashMap<>();
    private ArrayList<String> mLocationList = new ArrayList<>();
    private TextView btnCollegeListNext;
    private ProgressDialog mProgressDialog;
    private RecyclerView recyclerVwFindCollege;
    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_college);
        getBundleValue();
        getViewCasting();
        getCollegeListAPICalling();
    }

    private void getCollegeListAPICalling() {
        try {
            System.out.println("==============Login=========");
            if (Utils.isNetworkAvailable(this)) {
                mProgressDialog = new ProgressDialog(FindCollegeActivity.this);
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
//                        if (mProgressDialog != null) {
//                            mProgressDialog.dismiss();
//                        }
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
                            ArrayAdapter aa = new ArrayAdapter(FindCollegeActivity.this, R.layout.spinner_list, eduCategoryDatas);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            spinnerCategory.setAdapter(aa);


                            ArrayAdapter location = new ArrayAdapter(FindCollegeActivity.this, R.layout.spinner_list, mLocationList);
                            location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            spinnerLocation.setAdapter(location);

                            System.out.println(CategoryId + "==========11========" + locationId);
                            if (!locationId.equals(null)) {
                                int spinnerPosition = location.getPosition(locationId);
                                spinnerLocation.setSelection(spinnerPosition);
                                mLocationApiId = mLocationHashMap.get(locationId);

                            }

                            if (!CategoryId.equals(null)) {
                                int spinnerPosition = aa.getPosition(CategoryId);
                                spinnerCategory.setSelection(spinnerPosition);
                                mCategoryApiId = mCategoryHashMap.get(CategoryId);
                            }

                            System.out.println(mLocationApiId + "=====Login====66==11=======" + mCategoryApiId);

                            findCollegeAPICalling();
                        }
                    }

                    @Override
                    public void onFailure(Call<CollegeListResponseModel> call, Throwable t) {
                        t.printStackTrace();
                        System.out.println("============failure=====" + t.getMessage());
                        Toast.makeText(FindCollegeActivity.this, "Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                    }
                });
            } else {
                Toast.makeText(FindCollegeActivity.this, "Connect to internet to login", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBundleValue() {

        Bundle b = getIntent().getExtras();
        CategoryId = b.getString("category");
        locationId = b.getString("location");
        System.out.println(CategoryId + "==================" + locationId);
    }


    private void findCollegeAPICalling() {
        try {
            System.out.println("==============Login=========");
            if (Utils.isNetworkAvailable(this)) {
                FindCollegeRequestModel userDatas = new FindCollegeRequestModel();
                System.out.println(mCategoryApiId + "==============Login====66==11===" + mLocationApiId);

                userDatas.setEducationCategoryId(mCategoryApiId);
                userDatas.setLocationId(mLocationApiId);
                Retrofit retrofit = Utils.callRetrofit();
                ApiInterface service = retrofit.create(ApiInterface.class);
                Call<FindCollegeResponseModel> call = service.getFindCollegeDetails("", userDatas);
                call.enqueue(new Callback<FindCollegeResponseModel>() {
                    @Override
                    public void onResponse(Call<FindCollegeResponseModel> call, Response<FindCollegeResponseModel> response) {
                        System.out.println("==========11======" + new Gson().toJson(response.body()));
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            System.out.println("=======success===========" + response.body().getStatus());
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FindCollegeActivity.this);
                            recyclerVwFindCollege.setLayoutManager(layoutManager);
                            recyclerVwFindCollege.setItemAnimator(new DefaultItemAnimator());


                            if (response.body().getCollegeDatas().size() > 0) {
                                UserReportViewHolder mAdapter = new UserReportViewHolder(FindCollegeActivity.this, (ArrayList<FindCollegeResponseModel.CollegeData>) response.body().getCollegeDatas());
                                recyclerVwFindCollege.setAdapter(mAdapter);
                            } else {

                            }
//                            ArrayList<FindCollegeResponseModel.CollegeData> eduCategoryDatas = new ArrayList<>();
//                            eduCategoryDatas.clear();
//                            for (int i = 0; i < response.body().getCollegeDatas().size(); i++) {
//                                FindCollegeResponseModel.CollegeData bean = new FindCollegeResponseModel.CollegeData();
//                                System.out.println("=eduCategoryDatas====id==========" + response.body().getCollegeDatas().get(i).getId());
//                                bean.setCollegeName(response.body().getCollegeDatas().get(i).getCollegeName());
//                                bean.setAddress(response.body().getCollegeDatas().get(i).getAddress());
//                                bean.setContactNum(response.body().getCollegeDatas().get(i).getContactNum());
//                                bean.setId(response.body().getCollegeDatas().get(i).getId());
//                                eduCategoryDatas.add(bean);
//                            }
//                            System.out.println("=====eduCategoryDatas==========" + eduCategoryDatas.size());
//                            for (int i = 0; i < eduCategoryDatas.size(); i++) {
//                                System.out.println("===eduCategoryDatas==id====11======" + eduCategoryDatas.get(i).getCollegeName());
//                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<FindCollegeResponseModel> call, Throwable t) {
                        t.printStackTrace();
                        System.out.println("============failure=====" + t.getMessage());
                        Toast.makeText(FindCollegeActivity.this, "Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                    }
                });
            } else {
                Toast.makeText(FindCollegeActivity.this, "Connect to internet to login", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
         * This method converts a String to an array of bytes
         */
    public byte[] convertStringToByteArray(String stringToConvert) {

        byte[] theByteArray = stringToConvert.getBytes();
        System.out.println(theByteArray.length);
        return theByteArray;
    }

    private void getViewCasting() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Find Colleges");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerVwFindCollege = (RecyclerView) findViewById(R.id.recyclerVwFindCollege);
        spinnerLocation = (Spinner) findViewById(R.id.spinnerLocation);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        btnCollegeListNext = (TextView) findViewById(R.id.btnCollegeListNext);
        btnCollegeListNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findCollegeAPICalling();
            }
        });

        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                tv.setText(tv.getText() + parent.getItemAtPosition(position).toString());
                mLocationApiId = mLocationHashMap.get(spinnerLocation.getSelectedItem().toString());
                System.out.println("==mLocationApiId====" + mLocationApiId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                tv.setText(tv.getText() + parent.getItemAtPosition(position).toString());
                mCategoryApiId = mCategoryHashMap.get(spinnerCategory.getSelectedItem().toString());
                System.out.println("==mLocationApiId====" + mCategoryApiId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

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

    //    /* * @Name Adapter
//                  * @Created_By Nandhini Mahendran
//                  * @Created_On 01-09-2017
//                  * @Updated_By
//                  * @Updated_On
//                  * @updated_Information
//                  * @Description  Adapter for Time sheet fragment
//                  * @last_Update_Content
//                  **/


    public class UserReportViewHolder extends RecyclerView.Adapter<UserReportViewHolder.ViewReportViewHolder> {

        private Context mContext;
        private ArrayList<FindCollegeResponseModel.CollegeData> adminAttendanceList = new ArrayList<>();

        /**
         * @param context
         * @param summaryListData
         */
        public UserReportViewHolder(Context context, ArrayList<FindCollegeResponseModel.CollegeData> summaryListData) {

            this.mContext = context;
            this.adminAttendanceList = summaryListData;

        }

        /**
         * @param viewGroup
         * @param i
         */


        @Override
        public UserReportViewHolder.ViewReportViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View itemView = null;

            try {
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_new_find_college, viewGroup, false);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new ViewReportViewHolder(itemView);
        }

        /**
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(final UserReportViewHolder.ViewReportViewHolder holder, final int position) {

            holder.txtVwCollegeName.setText(adminAttendanceList.get(position).getCollegeName());

            holder.txtVwLocation.setText(adminAttendanceList.get(position).getAddress());


            holder.txtVwCall.setText(adminAttendanceList.get(position).getContactNum());
            holder.txtVwCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
                    } else {
                        //Open call function
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + adminAttendanceList.get(position).getContactNum()));
                        startActivity(intent);
                    }
                }
            });
            Picasso.with(FindCollegeActivity.this)
                    .load(adminAttendanceList.get(position).getImageDatas())
                    .error(R.mipmap.ic_launcher)
                    .into(holder.imgCollege);

//            Bitmap bitmap = BitmapFactory.decodeByteArray(convertStringToByteArray(adminAttendanceList.get(position).getImageDatas()), 0,
//                    convertStringToByteArray(adminAttendanceList.get(position).getImageDatas()).length);
//            holder.imgCollege.setImageBitmap(bitmap);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(FindCollegeActivity.this, StudentDetailsActivity.class);
                    intent.putExtra("issue_id", adminAttendanceList.get(position).getId() + "");
                    startActivity(intent);
                }
            });
            holder.imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.txtVwCall.performLongClick();
                }
            });

        }

        /**
         * @return
         */
        @Override
        public int getItemCount() {
            return adminAttendanceList.size();
        }


        public class ViewReportViewHolder extends RecyclerView.ViewHolder {

            private TextView txtVwCollegeName, txtVwLocation, txtVwCall;
            private ImageView imgCollege, imgCall;

            /**
             * @param itemView
             */
            public ViewReportViewHolder(View itemView) {
                super(itemView);
                txtVwCollegeName = (TextView) itemView.findViewById(R.id.txtVwCollegeName);
                imgCollege = (ImageView) itemView.findViewById(R.id.imgCollege);
                imgCall = (ImageView) itemView.findViewById(R.id.imgCall);
                txtVwLocation = (TextView) itemView.findViewById(R.id.txtVwLocation);
                txtVwCall = (TextView) itemView.findViewById(R.id.txtVwCall);
//                textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
            }

        }
    }
}
