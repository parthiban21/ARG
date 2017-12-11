
package col.arg.org.arg.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRegResponseModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_datas")
    @Expose
    private UserDatas userDatas;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserDatas getUserDatas() {
        return userDatas;
    }

    public void setUserDatas(UserDatas userDatas) {
        this.userDatas = userDatas;
    }

    public class UserDatas {

        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("id")
        @Expose
        private Integer id;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }
}
