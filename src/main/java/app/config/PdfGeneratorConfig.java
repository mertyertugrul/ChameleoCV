package app.config;

import app.entity.CVSection;
import app.pdf.impl.CVPdfGenerator;
import app.pdf.renders.cv.PdfSectionRenderer;
import app.pdf.renders.cv.registory.CvRendererRegistry;
import app.pdf.renders.cv.registory.SectionRenderer;
import com.itextpdf.text.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PdfGeneratorConfig {

    @Bean
    public CvRendererRegistry rendererRegistry(List<PdfSectionRenderer<?>> renderers) {
        CvRendererRegistry registry = new CvRendererRegistry();
        for (PdfSectionRenderer<?> renderer : renderers) {
            addRendererWithWildcard(registry, renderer);
        }
        return registry;
    }


    @Bean
    public SectionRenderer sectionRenderer() {
        return new SectionRenderer();
    }


    private <T extends CVSection> void addRendererWithWildcard(
            CvRendererRegistry registry, PdfSectionRenderer<T> renderer) {
        registry.addRenderer(renderer.getSupportedClass(), renderer);
    }
}
