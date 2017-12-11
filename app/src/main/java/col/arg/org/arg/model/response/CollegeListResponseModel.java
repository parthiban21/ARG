
package col.arg.org.arg.model.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CollegeListResponseModel {

    @SerializedName("location_datas")
    @Expose
    private List<LocationData> locationDatas = null;
    @SerializedName("edu_category_datas")
    @Expose
    private List<EduCategoryData> eduCategoryDatas = null;

    public List<LocationData> getLocationDatas() {
        return locationDatas;
    }

    public void setLocationDatas(List<LocationData> locationDatas) {
        this.locationDatas = locationDatas;
    }

    public List<EduCategoryData> getEduCategoryDatas() {
        return eduCategoryDatas;
    }

    public void setEduCategoryDatas(List<EduCategoryData> eduCategoryDatas) {
        this.eduCategoryDatas = eduCategoryDatas;
    }

    public static class EduCategoryData {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("educational_category_name")
        @Expose
        private String educationalCategoryName;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getEducationalCategoryName() {
            return educationalCategoryName;
        }

        public void setEducationalCategoryName(String educationalCategoryName) {
            this.educationalCategoryName = educationalCategoryName;
        }

    }

    public class LocationData {

        @SerializedName("location_name")
        @Expose
        private String locationName;
        @SerializedName("id")
        @Expose
        private Integer id;

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }

}
