package woowacrew.oauth.github;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import woowacrew.oauth.OauthConfig;
import woowacrew.oauth.OauthService;
import woowacrew.user.dto.UserOauthDto;

@Component
public class GithubOauthService implements OauthService {
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String CODE = "code";

    private final OauthConfig oauthConfig;

    public GithubOauthService(OauthConfig oauthConfig) {
        this.oauthConfig = oauthConfig;
    }

    @Override
    public String getAccessToken(String accessCode) {
        WebClient webClient = WebClient.builder()
                .baseUrl(oauthConfig.getAccessTokenUri())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .build();

        String body = webClient.post()
                .body(BodyInserters.fromFormData(CLIENT_ID, oauthConfig.getClientId())
                        .with(CLIENT_SECRET, oauthConfig.getClientSecret())
                        .with(CODE, accessCode))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        AccessToken accessToken = new Gson().fromJson(body, AccessToken.class);
        return accessToken.getAccessToken();
    }

    @Override
    public UserOauthDto getUserInfo(String accessToken) {
        WebClient webClient = WebClient.builder()
                .baseUrl(oauthConfig.getUserInfoUri())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .defaultHeader("Authorization", "token " + accessToken)
                .build();

        String body = webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return new Gson().fromJson(body, UserOauthDto.class);
    }

    @Override
    public String getAuthorizationUrl() {
        return oauthConfig.getUserAuthorizationUri() + "?client_id=" + oauthConfig.getClientId();
    }
}
