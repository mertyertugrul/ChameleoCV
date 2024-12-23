package app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import app.cv.CV;
import app.entity.TestData;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
class CVJsonServiceTest {

    private CVJsonService cvJsonService;
    private CV testCV;
    private final Path tempJsonFile = Path.of("src/test/resources/service/test_cv_json.json");
    private final Path expectedJsonFile = Path.of("src/test/resources/service/test_cv_json_expected.json");

    @BeforeEach
    void setUp() {
        cvJsonService = new CVJsonService();
        testCV = TestData.createTestCV();
    }

    @AfterEach
    void cleanUp() {
        try {
            Files.deleteIfExists(tempJsonFile);
        } catch (IOException e) {
            log.warn("Failed to delete temporary JSON file: " + tempJsonFile);
        }
    }


    @Test
    void testSerializedCVContent() {
        cvJsonService.saveToJsonFile(testCV, tempJsonFile);

        try {
            String actualJsonContent = Files.readString(tempJsonFile);
            String expectedJsonContent = Files.readString(expectedJsonFile);

            JSONAssert.assertEquals(expectedJsonContent, actualJsonContent, JSONCompareMode.STRICT);
        } catch (IOException | JSONException e) {
            fail("Failed to read, parse, or compare the JSON files: " + e.getMessage());
        }
    }

    @Test
    void testDeserializeCVFromFile() {
        // Serialize the test CV first to ensure the temporary JSON file is created
        cvJsonService.saveToJsonFile(testCV, tempJsonFile);

        try {
            // Deserialize the CV back from the temporary JSON file
            CV deserializedCV = cvJsonService.loadFromJsonFile(tempJsonFile);

            // Convert both the expected and deserialized CVs into JSON Strings
            String expectedJsonContent = Files.readString(expectedJsonFile);
            ObjectMapper objectMapper = new ObjectMapper();
            String actualJsonContent = objectMapper.writeValueAsString(deserializedCV);

            // Use JSONAssert to compare the JSON structures
            JSONAssert.assertEquals(expectedJsonContent, actualJsonContent, JSONCompareMode.STRICT);
        } catch (IOException | JSONException e) {
            fail("Failed to serialize, deserialize, or compare the CV JSON files: " + e.getMessage());
        }
    }
}