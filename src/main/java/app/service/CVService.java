package app.service;

import app.cv.CV;
import app.entity.*;
import app.entity.common.Certification;
import app.pdf.impl.CVPdfGenerator;
import app.pdf.renders.cv.registory.CvRendererRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class CVService {

    private final CVPdfGenerator cvPdfGenerator;
    private final CvRendererRegistry cvRendererRegistry;

    public CVService(CVPdfGenerator cvPdfGenerator, CvRendererRegistry cvRendererRegistry) {
        this.cvPdfGenerator = cvPdfGenerator;
        this.cvRendererRegistry = cvRendererRegistry;
    }
    @PostConstruct
    public void validateRenderers() {
        cvRendererRegistry.validateRenderers(
                PersonalInfo.class,
                Summary.class,
                ProfessionalExperience.class,
                Education.class,
                Recommendation.class,
                Certification.class,
                Skills.class
        );
    }

    public void exportCvToPdf(CV cv, String filePath) {
        cvPdfGenerator.generateCV(filePath, cv);
    }
}
