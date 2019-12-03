package woowacrew.user.domain;

import com.google.gson.annotations.SerializedName;

public class UserOauthDto {
    @SerializedName("id")
    private String userId;

    public UserOauthDto(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
