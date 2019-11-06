package woowacrew.user;

import com.google.gson.annotations.SerializedName;

public class UserVo {
    @SerializedName("id")
    private String userId;
    private String url;

    public String getUserId() {
        return userId;
    }

    public String getUrl() {
        return url;
    }
}
