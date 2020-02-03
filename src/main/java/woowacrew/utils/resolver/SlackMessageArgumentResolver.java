package woowacrew.utils.resolver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacrew.article.slack.dto.SlackMessageRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Optional;

public class SlackMessageArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String EVENT = "event";
    private static final String CHANNEL = "channel";
    private static final String AUTHOR = "user";
    private static final String CONTENT = "text";
    private static final String FILE = "files";
    private static final String DOWNLOAD_LINK = "url_private_download";
    private static final String DOWNLOAD_LINK_FROM_SLACK = "permalink";
    private static final String BOT = "bot_profile";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(SlackMessageRequestDto.class);
    }

    @Override
    public SlackMessageRequestDto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        JsonNode request = createJsonNode(webRequest);
        if (request.get("challenge") != null) {
            return new SlackMessageRequestDto(request.get("challenge").asText());
        }
        JsonNode event = request.get(EVENT);

        JsonNode channel = event.get(CHANNEL);
        JsonNode author = event.get(AUTHOR);
        JsonNode content = event.get(CONTENT);
        JsonNode file = event.get(FILE);
        boolean isBot = event.get(BOT) != null;

        return createSlackMessageRequestDto(channel, author, content, file, isBot);
    }

    private JsonNode createJsonNode(NativeWebRequest webRequest) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        Iterator<String> requestBodyReader = request.getReader().lines().iterator();
        StringBuilder result = new StringBuilder();

        while (requestBodyReader.hasNext()) {
            result.append(requestBodyReader.next());
        }
        return objectMapper.readTree(result.toString());
    }

    private SlackMessageRequestDto createSlackMessageRequestDto(JsonNode channel, JsonNode author, JsonNode content, JsonNode file, boolean isBot) {
        if (Optional.ofNullable(file).isPresent()) {
            String downloadLink = file.findValue(DOWNLOAD_LINK).asText();
            String downloadLinkFromSlack = file.findValue(DOWNLOAD_LINK_FROM_SLACK).asText();
            return new SlackMessageRequestDto(channel.asText(), author.asText(), content.asText(), downloadLink, downloadLinkFromSlack, isBot);
        }
        return new SlackMessageRequestDto(channel.asText(), author.asText(), content.asText(), isBot);
    }
}
