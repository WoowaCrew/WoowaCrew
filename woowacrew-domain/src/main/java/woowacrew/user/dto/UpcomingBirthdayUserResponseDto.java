package woowacrew.user.dto;

import java.time.LocalDate;

public class UpcomingBirthdayUserResponseDto {
    private Long id;
    private int degree;
    private String nickname;
    private LocalDate birthday;

    public UpcomingBirthdayUserResponseDto(Long id, int degree, String nickname, LocalDate birthday) {
        this.id = id;
        this.degree = degree;
        this.nickname = nickname;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public int getDegree() {
        return degree;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
