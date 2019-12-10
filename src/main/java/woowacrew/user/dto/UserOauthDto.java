package woowacrew.user.dto;

import com.google.gson.annotations.SerializedName;

public class UserOauthDto {
    @SerializedName("id")
    private String oauthId;

    public UserOauthDto(String oauthId) {
        this.oauthId = oauthId;
    }

    public String getOauthId() {
        return oauthId;
    }
}
