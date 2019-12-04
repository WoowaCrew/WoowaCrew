package woowacrew.user.domain;

import java.time.LocalDate;

public class UserContext {
    private Long id;

    private String oauthId;

    private String nickname;

    private LocalDate birthday;

    private UserContext() {
    }

    public UserContext(String oauthId) {
        this.oauthId = oauthId;
    }

    public UserContext(Long id, String oauthId, String nickname, LocalDate birthday) {
        this.id = id;
        this.oauthId = oauthId;
        this.nickname = nickname;
        this.birthday = birthday;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
