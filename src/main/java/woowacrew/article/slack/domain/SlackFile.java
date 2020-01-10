package woowacrew.article.slack.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class SlackFile {
    private String downloadLink;

    private String downloadLinkFromSlack;

    private SlackFile() {
    }

    public SlackFile(String downloadLink, String downloadLinkFromSlack) {
        this.downloadLink = downloadLink;
        this.downloadLinkFromSlack = downloadLinkFromSlack;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public String getDownloadLinkFromSlack() {
        return downloadLinkFromSlack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlackFile that = (SlackFile) o;
        return Objects.equals(downloadLink, that.downloadLink) &&
                Objects.equals(downloadLinkFromSlack, that.downloadLinkFromSlack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(downloadLink, downloadLinkFromSlack);
    }
}
