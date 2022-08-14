package ai.ecma.product.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Osiyo Adilova
 * @project app-eticket-server
 * @since 12/16/2021
 */

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Value("${spring.sql.init.mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {

        } else {

        }

        System.err.printf("Sql init mode is %s \n", initialMode);
    }
}
