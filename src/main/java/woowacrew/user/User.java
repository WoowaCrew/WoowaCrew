package woowacrew.user;

import com.google.gson.annotations.SerializedName;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private Long id;

    private String userId;

    private String url;

    public User(String userId, String url) {
        this.userId = userId;
        this.url = url;
    }
}
