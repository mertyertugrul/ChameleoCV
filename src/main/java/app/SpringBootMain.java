package app;

import app.ui.FxApp;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class SpringBootMain {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure()
                .filename("secrets.env")
                .directory(System.getProperty("user.dir"))
                .ignoreIfMissing()
                .load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        ConfigurableApplicationContext context = SpringApplication.run(SpringBootMain.class, args);

        FxApp.setApplicationContext(context);
        javafx.application.Application.launch(FxApp.class, args);
    }
}