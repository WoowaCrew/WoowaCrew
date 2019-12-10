package woowacrew.user.dto;

import woowacrew.user.domain.UserRole;

public class UserContext {
    private Long id;

    private String oauthId;

    private String nickname;

    private UserRole role;

    private UserContext() {
    }

    public UserContext(Long id, String oauthId, String nickname, UserRole role) {
        this.id = id;
        this.oauthId = oauthId;
        this.nickname = nickname;
        this.role = role;
    }

    public UserContext(String oauthId) {
        this.oauthId = oauthId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
