package app.config;

import app.coverletter.CoverLetter;
import app.pdf.renders.cl.DefaultCoverLetterRenderer;
import app.pdf.renders.cl.registory.CoverLetterRendererRegistry;
import com.itextpdf.text.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CoverLetterRendererConfig {

    @Bean
    public DefaultCoverLetterRenderer defaultCoverLetterRenderer() {
        return new DefaultCoverLetterRenderer();
    }

}
