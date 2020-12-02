package lt.reviewapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ReviewAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewAppApplication.class, args);
    }
}
