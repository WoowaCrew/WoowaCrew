package woowacrew.oauth.github;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import woowacrew.oauth.Oauth;
import woowacrew.oauth.OauthConfig;
import woowacrew.user.domain.UserDto;

@Component
public class GithubOauth implements Oauth {
    private final OauthConfig oauthConfig;

    public GithubOauth(OauthConfig oauthConfig) {
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
                .body(BodyInserters.fromFormData("client_id", oauthConfig.getClientId())
                        .with("client_secret", oauthConfig.getClientSecret())
                        .with("code", accessCode))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        AccessToken accessToken = new Gson().fromJson(body, AccessToken.class);
        return accessToken.getAccessToken();
    }

    @Override
    public UserDto getUserInfo(String accessToken) {
        WebClient webClient = WebClient.builder()
                .baseUrl(oauthConfig.getUserInfoUri())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .defaultHeader("Authorization", "token " + accessToken)
                .build();

        String body = webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return new Gson().fromJson(body, UserDto.class);
    }
}
