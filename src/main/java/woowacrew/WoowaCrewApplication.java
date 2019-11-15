package woowacrew;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WoowaCrewApplication {
    private static final String PROPERTIES = "spring.config.location=classpath:/github.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(WoowaCrewApplication.class)
                .properties(PROPERTIES)
                .run(args);
    }
}
