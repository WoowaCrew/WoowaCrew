package woowacrew.user.dto;

import com.google.gson.annotations.SerializedName;

public class UserOauthDto {
    @SerializedName("id")
    private String oauthId;

    @SerializedName("login")
    private String githubId;

    public UserOauthDto(String oauthId, String githubId) {
        this.oauthId = oauthId;
        this.githubId = githubId;
    }

    public String getOauthId() {
        return oauthId;
    }

    public String getGithubId() {
        return githubId;
    }

    @Override
    public String toString() {
        return "UserOauthDto{" +
                "oauthId='" + oauthId + '\'' +
                ", githubId='" + githubId + '\'' +
                '}';
    }
}
