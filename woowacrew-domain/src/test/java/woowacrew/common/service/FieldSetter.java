package woowacrew.common.service;

import org.junit.jupiter.api.Test;
import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.User;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldSetter {

    private FieldSetter() {
    }

    @Test
    void set_Test() {
        User user = new User("testId", new Degree());
        assertThat(user.getId()).isEqualTo(null);

        set(user, "id", 1L);
        assertThat(user.getId()).isEqualTo(1L);
    }

    public static <T> T set(T object, String fieldName, Object fieldValue) {
        try {
            Class<?> clazz = object.getClass();
            Field idField = clazz.getDeclaredField(fieldName);

            idField.setAccessible(true);
            idField.set(object, fieldValue);
            return object;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
