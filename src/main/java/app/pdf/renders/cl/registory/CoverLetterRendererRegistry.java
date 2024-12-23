package app.pdf.renders.cl.registory;

import app.coverletter.CoverLetter;
import app.pdf.renders.cl.CoverLetterRenderer;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CoverLetterRendererRegistry {
    private final Map<Class<? extends CoverLetter>, CoverLetterRenderer<? extends CoverLetter>> renderers = new HashMap<>();
    private final List<CoverLetterRenderer<? extends CoverLetter>> rendererList;

    @Autowired
    public CoverLetterRendererRegistry(List<CoverLetterRenderer<? extends CoverLetter>> rendererList) {
        this.rendererList = rendererList;
        initialize();
    }

    public void initialize() {
        for (CoverLetterRenderer<? extends CoverLetter> renderer : rendererList) {
            renderers.put(renderer.getSupportedClass(), renderer);
        }
        log.info("CoverLetterRendererRegistry initialized with {} renderers.", renderers.size());
    }

    public <T extends CoverLetter> void addRenderer(Class<T> coverLetterClass, CoverLetterRenderer<T> renderer) {
        renderers.put(coverLetterClass, renderer);
    }

    public <T extends CoverLetter> CoverLetterRenderer<T> getRenderer(Class<T> coverLetterClass) {
        @SuppressWarnings("unchecked")
        CoverLetterRenderer<T> renderer = (CoverLetterRenderer<T>) renderers.get(coverLetterClass);
        if (renderer != null) {
            return renderer;
        }

        for (Map.Entry<Class<? extends CoverLetter>, CoverLetterRenderer<? extends CoverLetter>> entry : renderers.entrySet()) {
            if (entry.getKey().isAssignableFrom(coverLetterClass)) {
                @SuppressWarnings("unchecked")
                CoverLetterRenderer<T> compatibleRenderer = (CoverLetterRenderer<T>) entry.getValue();
                return compatibleRenderer;
            }
        }

        throw new RuntimeException("No renderer found for: " + coverLetterClass.getSimpleName());
    }
}