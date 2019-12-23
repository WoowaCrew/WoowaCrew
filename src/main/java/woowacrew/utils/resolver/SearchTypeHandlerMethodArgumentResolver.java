package woowacrew.utils.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacrew.search.domain.SearchSpec;
import woowacrew.search.domain.SearchType;
import woowacrew.utils.annotation.AllowedSearchType;

import java.util.Objects;

public class SearchTypeHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String TYPE = "type";
    private static final String CONTENT = "content";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Specification.class) &&
                parameter.hasParameterAnnotation(AllowedSearchType.class);
    }

    @Override
    public Specification<?> resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String searchType = webRequest.getParameter(TYPE);
        String searchContent = webRequest.getParameter(CONTENT);
        SearchType[] searchTypes = Objects.requireNonNull(parameter.getParameterAnnotation(AllowedSearchType.class)).type();

        SearchSpec<?> searchSpec = new SearchSpec<>(searchTypes);
        return searchSpec.getSpecification(searchType, searchContent);
    }
}
