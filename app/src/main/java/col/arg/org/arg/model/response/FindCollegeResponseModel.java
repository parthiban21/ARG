
package col.arg.org.arg.model.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FindCollegeResponseModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("college_datas")
    @Expose
    private List<CollegeData> collegeDatas = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CollegeData> getCollegeDatas() {
        return collegeDatas;
    }

    public void setCollegeDatas(List<CollegeData> collegeDatas) {
        this.collegeDatas = collegeDatas;
    }

    public static class CollegeData {

        @SerializedName("contact_num")
        @Expose
        private String contactNum;
        @SerializedName("file_name")
        @Expose
        private String fileName;
        @SerializedName("image_datas")
        @Expose
        private String imageDatas;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("file_path")
        @Expose
        private String filePath;
        @SerializedName("college_name")
        @Expose
        private String collegeName;

        public String getContactNum() {
            return contactNum;
        }

        public void setContactNum(String contactNum) {
            this.contactNum = contactNum;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getImageDatas() {
            return imageDatas;
        }

        public void setImageDatas(String imageDatas) {
            this.imageDatas = imageDatas;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getCollegeName() {
            return collegeName;
        }

        public void setCollegeName(String collegeName) {
            this.collegeName = collegeName;
        }

    }

}
