package app.pdf.renders.cv.registory;

import app.entity.CVSection;
import app.pdf.renders.cv.PdfSectionRenderer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CvRendererRegistry {
    private final Map<Class<? extends CVSection>, PdfSectionRenderer<? extends CVSection>> renderers = new HashMap<>();

    public <T extends CVSection> void addRenderer(Class<T> sectionClass, PdfSectionRenderer<T> renderer) {
        renderers.put(sectionClass, renderer);
    }

    public <T extends CVSection> PdfSectionRenderer<T> getRenderer(Class<T> sectionClass) {
        @SuppressWarnings("unchecked")
        PdfSectionRenderer<T> renderer = (PdfSectionRenderer<T>) renderers.get(sectionClass);
        if (renderer == null) {
            throw new RuntimeException("No renderer found for section: " + sectionClass.getSimpleName());
        }
        return renderer;
    }
    public Collection<PdfSectionRenderer<?>> getAllRenderers() {
        return renderers.values();
    }

    @SafeVarargs
    public final void validateRenderers(Class<? extends CVSection>... requiredSections) {
        for (Class<? extends CVSection> section : requiredSections) {
            if (!renderers.containsKey(section)) {
                throw new RuntimeException("No renderer found for section: " + section.getSimpleName());
            }
        }
    }
}
