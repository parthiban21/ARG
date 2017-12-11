package col.arg.org.arg.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arg on 23/9/17.
 */

public class FindCollegeRequestModel {
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("education_category_id")
    @Expose
    private String educationCategoryId;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getEducationCategoryId() {
        return educationCategoryId;
    }

    public void setEducationCategoryId(String educationCategoryId) {
        this.educationCategoryId = educationCategoryId;
    }
}
