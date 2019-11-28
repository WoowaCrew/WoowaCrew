package woowacrew.user.domain;

import com.google.gson.annotations.SerializedName;

public class UserDto {
    @SerializedName("login")
    private String userId;
    private String url;

    private UserDto() {
    }

    public UserDto(String userId, String url) {
        this.userId = userId;
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public String getUrl() {
        return url;
    }
}
