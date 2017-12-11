package col.arg.org.arg.nal;

import com.google.gson.JsonObject;


import col.arg.org.arg.model.FindCollegeRequestModel;
import col.arg.org.arg.model.LoginRequestModel;
//import io.reactivex.Observable;
import col.arg.org.arg.model.RegisterRequestModel;
import col.arg.org.arg.model.response.CollegeListResponseModel;
import col.arg.org.arg.model.response.FindCollegeResponseModel;
import col.arg.org.arg.model.response.LoginRegResponseModel;
import col.arg.org.arg.model.response.RegisterResponseModel;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by next on 30/8/17.
 */

public interface ApiInterface {

    // live server url - http://111.93.224.58:1111

    String BASE_URL = "http://192.168.1.9:8000";
    // String BASE_URL = "http://fms.nexttechnosolutions.com";

    @Headers("Content-Type: application/json")
    @POST("/")
    Call<LoginRegResponseModel> getUserLoginDetails(@Query("") String key, @Body LoginRequestModel body);

    @Headers("Content-Type: application/json")
    @POST("/register/")
    Call<RegisterResponseModel> setRegistrationDetails(@Query("") String key, @Body RegisterRequestModel body);
    @GET("/education/location_edu_datas/")
    Call<CollegeListResponseModel> setCollegeNameList();

    @Headers("Content-Type: application/json")
    @POST("/education/college_datas/")
    Call<FindCollegeResponseModel> getFindCollegeDetails(@Query("") String key, @Body FindCollegeRequestModel body);

//    @GET("/fm/mob_logout")
//    Call<StatusResponseFromServerModel> logOutCurrentUser();
//
//    @Headers("Content-Type: application/json")
//    @POST("/fm/reference_item_data/")
//    Call<FacilityCategoryResponseModel> getFacilityType(@Query("") String key, @Body FacilityCategoryTypeRequestModel body);
//
//    @Headers("Content-Type: application/json")
//    @POST("/fm/reference_item_data/")
//    Call<ReportCategoryResponseModel> getReportType(@Query("") String key, @Body FacilityCategoryTypeRequestModel body);
//
//    @Headers("Content-Type: application/json")
//    @POST("/fm/reference_item_data/")
//    Call<CountryListResponseModel> getCountryList(@Query("") String key, @Body FacilityCategoryTypeRequestModel body);
//
//    @Headers("Content-Type: application/json")
//    @POST("/fm/facility_data/")
//    Call<FacilityNameResponseModel> getFacilityName(@Query("") String key, @Body FacilityNameRequestModel body);
//
//    @Headers("Content-Type: application/json")
//    @POST("/fm/update_profile/")
//    Call<StatusResponseFromServerModel> uploadUserDetails(@Query("") String key, @Body UploadProfileRequestModel body);
//
//    @Headers("Content-Type: application/json")
//    @POST("/fm/report_issue_management/")
//    Call<StatusResponseFromServerModel> reportIssue(@Query("") String key, @Body ReportIssueRequestModel body);
//
//    @Headers("Content-Type: application/json")
//    @POST("/fm/report_issue_data/")
//    Call<ViewReportsListResponseModel> getViewReportsEndUserList(@Query("") String key, @Body ViewReportsListRequestModel body);
//
//    @Headers("Content-Type: application/json")
//    @POST("/fm/report_issue_data/")
//    Call<ViewReportsDetailsResponseModel> getViewReportsEndUserDetails(@Query("") String key, @Body ViewReportsDetailsRequestModel body);
//
//    @Headers("Content-Type: application/json")
//    @POST("/fm/report_issue_follow_up/")
//    Call<FollowUpResponseModel> getFollowUpDetails(@Query("") String key, @Body FollowUpRequestModel body);
//
//    @Headers("Content-Type: application/json")
//    @POST("/fm/report_issue_status_change/")
//    Call<FollowUpResponseModel> getVerifyDetails(@Query("") String key, @Body VerifyRequestModel body);
//
//    @Headers("Content-Type: application/json")
//    @POST("/fm/reference_item_data/")
//    Call<ReportStatusResponseModel> getReportStatus(@Query("") String key, @Body FacilityCategoryTypeRequestModel body);
//
//    @Headers("Content-Type: application/json")
//    @POST("/fm/report_issue_data/")
//    Observable<ViewReportsListResponseModel> getViewReportsMT(@Query("") String key, @Body ViewReportsListRequestModel body);
//
//    @Headers("Content-Type: application/json")
//    @POST("/fm/report_issue_data/")
//    Observable<ViewReportsDetailsResponseModel> getViewReportDetailsMT(@Query("") String key, @Body ViewReportsDetailsRequestModel body);
//
//    @Headers("Content-Type: application/json")
//    @POST("/fm/report_issue_resolved/")
//    Observable<StatusResponseFromServerModel> resolveIssueByMT(@Query("") String key, @Body MTResolveIssueRequestModel body);
//
//    @FormUrlEncoded
//    @POST("/fm/report_issue_management/")
//    Call<StatusResponseFromServerModel> function_name(@Field("datas") String datas);
//
//    @FormUrlEncoded
//    @POST("/")
//    Call<UserLoginRegResponseModel> testLogin(@Field("datas") String params);
//
//    @FormUrlEncoded
//    @POST("/fm/report_issue_resolved/")
//    Call<StatusResponseFromServerModel> resolveIssueMaintenanceTeam(@Field("datas") String params);


}
