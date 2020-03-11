package woowacrew.user.utils;

import org.springframework.core.convert.converter.Converter;
import woowacrew.user.domain.UserRole;

public class UserRoleConverter implements Converter<String, UserRole> {

    @Override
    public UserRole convert(String source) {
        return UserRole.valueOf(source);
    }
}
