package woowacrew.user.domain;

import org.springframework.core.convert.converter.Converter;

public class UserRoleConverter implements Converter<String, UserRole> {

    @Override
    public UserRole convert(String source) {
        return UserRole.valueOf(source);
    }
}
