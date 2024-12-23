package app.service;

import app.coverletter.CoverLetter;
import app.entity.*;
import app.entity.common.Certification;
import app.integrations.IChatModelClient;
import app.integrations.IPromptBuilder;
import app.integrations.IResponseParser;
import app.integrations.OpenAIModel;
import app.integrations.impl.DefaultPromptBuilder;
import app.integrations.impl.JsonResponseParser;
import app.pdf.impl.CVPdfGenerator;
import app.pdf.impl.CoverLetterPdfGenerator;
import app.pdf.renders.cl.DefaultCoverLetterRenderer;
import app.pdf.renders.cl.registory.CoverLetterRendererRegistry;
import app.pdf.renders.cv.*;
import app.pdf.renders.cv.registory.CvRendererRegistry;
import app.repositories.ICVRepository;
import app.repositories.JsonCVRepository;
import app.service.impl.JsonSerializerService;
import app.mock.MockChatModelClient;
import app.util.impl.DefaultFileNameGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CVProcessingServiceTest {

    private static Path testCVPath;
    private static Path tailoredCVPdfPath;
    private static Path tailoredCoverLetterPdfPath;
    private static Path tailoredOutputDir;
    private static String mockResponsePath;

    private static CVProcessingService service;

    @BeforeAll
    static void setupPaths() {
        testCVPath = Path.of("src/test/resources/cv/cv.json");
        tailoredOutputDir = Path.of("src/test/resources/integrations/");
        mockResponsePath = "src/test/resources/mock_responses/tailored_response.json";
    }

    @BeforeAll
    static void setup() {
        // Initialize paths
        testCVPath = Path.of("src/test/resources/cv/cv.json");
        tailoredCVPdfPath = Path.of("src/test/resources/integrations/Tech Innovators/Alice Smith - CV - Tech Innovators - Senior Software Engineer - 23 12 2024.pdf");
        tailoredCoverLetterPdfPath = Path.of("src/test/resources/integrations/Tech Innovators/Alice Smith - Cover Letter - Tech Innovators - Senior Software Engineer - 23 12 2024.pdf");
        mockResponsePath = "src/test/resources/mock_responses/tailored_response.json";

        // Repository and JSON handling
        ICVRepository cvRepository = new JsonCVRepository(new JsonSerializerService<>());
        IPromptBuilder promptBuilder = new DefaultPromptBuilder();
        IChatModelClient mockChatClient = new MockChatModelClient(mockResponsePath);
        IResponseParser responseParser = new JsonResponseParser();
        DefaultFileNameGenerator fileNameGenerator = new DefaultFileNameGenerator();

        // Renderer Registry for CV
        CvRendererRegistry cvRendererRegistry = new CvRendererRegistry();
        cvRendererRegistry.addRenderer(PersonalInfo.class, new PersonalInfoRenderer());
        cvRendererRegistry.addRenderer(Summary.class, new SummaryRenderer());
        cvRendererRegistry.addRenderer(ProfessionalExperience.class, new ProfessionalExperienceRenderer());
        cvRendererRegistry.addRenderer(Education.class, new EducationRenderer());
        cvRendererRegistry.addRenderer(Recommendation.class, new RecommendationsRenderer());
        cvRendererRegistry.addRenderer(Certification.class, new CertificationsRenderer());
        cvRendererRegistry.addRenderer(Skills.class, new SkillsRenderer());

        CVPdfGenerator cvPdfGenerator = new CVPdfGenerator(new com.itextpdf.text.Document(), cvRendererRegistry);

        // Renderer Registry for Cover Letter
        CoverLetterRendererRegistry clRendererRegistry = new CoverLetterRendererRegistry(
                List.of(new DefaultCoverLetterRenderer())
        );
        clRendererRegistry.addRenderer(CoverLetter.class, new DefaultCoverLetterRenderer());

        CoverLetterPdfGenerator clPdfGenerator = new CoverLetterPdfGenerator(clRendererRegistry);

        // Initialize CVProcessingService
        service = new CVProcessingService(
                cvRepository,
                promptBuilder,
                mockChatClient,
                responseParser,
                cvPdfGenerator,
                clPdfGenerator,
                fileNameGenerator
        );
    }

    @Test
    void testCreateTailoredCVAndCoverLetterWithFileBackedMock() {
        String jobDescription = "Senior Java Developer at a FinTech company.";

        service.createTailoredCVAndCoverLetter(
                testCVPath,
                jobDescription,
                tailoredOutputDir,
                OpenAIModel.GPT_4_TURBO
        );

        assertTrue(tailoredCVPdfPath.toFile().exists(), "Tailored CV PDF should be generated by mock response");
        assertTrue(tailoredCoverLetterPdfPath.toFile().exists(), "Tailored Cover Letter PDF should be generated by mock response");
    }
}