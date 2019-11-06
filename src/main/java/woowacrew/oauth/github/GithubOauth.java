package woowacrew.oauth.github;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import woowacrew.user.domain.UserDto;

@Component
public class GithubOauth {
    private final GithubConfig githubConfig;

    public GithubOauth(GithubConfig githubConfig) {
        this.githubConfig = githubConfig;
    }

    public String getAccessToken(String code) {
        WebClient webClient = WebClient.builder()
                .baseUrl(githubConfig.getAccessTokenUri())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .build();

        String body = webClient.post()
                .body(BodyInserters.fromFormData("client_id", githubConfig.getClientId())
                        .with("client_secret", githubConfig.getClientSecret())
                        .with("code", code))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        AccessToken accessToken = new Gson().fromJson(body, AccessToken.class);
        return accessToken.getAccessToken();
    }

    public UserDto getUserInfo(String accessToken) {
        WebClient webClient = WebClient.builder()
                .baseUrl(githubConfig.getUserInfoUri())
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
