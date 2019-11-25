package woowacrew.keyword.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    private Long views;

    public Keyword() {
    }

    public Keyword(String content) {
        this.content = content;
        this.views = 0L;
    }

    public void increaseViews() {
        this.views++;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getViews() {
        return views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyword keyword = (Keyword) o;
        return Objects.equals(id, keyword.id) &&
                Objects.equals(content, keyword.content) &&
                Objects.equals(views, keyword.views);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, views);
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", views=" + views +
                '}';
    }
}
