
package col.arg.org.arg.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoursesRequestModel {

    @SerializedName("college_id")
    @Expose
    private String collegeId;

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

}
