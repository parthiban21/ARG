
package col.arg.org.arg.model.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoursesResponseModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("course_list")
    @Expose
    private List<CourseList> courseList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CourseList> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseList> courseList) {
        this.courseList = courseList;
    }

    public class CourseList {

        @SerializedName("educational_courses_name")
        @Expose
        private String educationalCoursesName;
        @SerializedName("id")
        @Expose
        private Integer id;

        public String getEducationalCoursesName() {
            return educationalCoursesName;
        }

        public void setEducationalCoursesName(String educationalCoursesName) {
            this.educationalCoursesName = educationalCoursesName;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }

}
