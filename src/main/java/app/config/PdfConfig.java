package app.config;

import com.itextpdf.text.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class PdfConfig {

    @Bean
    @Scope("prototype")
    public Document document() {
        return new Document();
    }
}
