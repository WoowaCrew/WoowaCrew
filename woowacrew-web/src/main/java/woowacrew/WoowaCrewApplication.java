package woowacrew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WoowaCrewApplication {
    public static void main(String[] args) {
        SpringApplication.run(WoowaCrewApplication.class, args);
    }
}
