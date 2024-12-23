package app.config.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DebugFileWriter {

    private static final Path DEBUG_DIRECTORY = Paths.get("logs/prompts");
    private static StringBuilder promptAndResponse = new StringBuilder();
    private static boolean isPromptAdded = false;
    private static boolean isResponseAdded = false;


    public static void addPromptToLogFile(String prompt) {
        promptAndResponse.append("-------------Prompt-------------\n");
        promptAndResponse.append(prompt).append("------------------------\n");
        isPromptAdded = true;
        checkAndSaveToFile();
    }
    public static void addResponseToLogFile(String response) {
        promptAndResponse.append("-------------Response-------------\n");
        promptAndResponse.append(response).append("---------------------------\n");
        isResponseAdded = true;
        checkAndSaveToFile();
    }
    private static void checkAndSaveToFile() {
        if (isPromptAdded && isResponseAdded) {
            try {
                savePromptAndResponse();
            } catch (IOException e) {
                System.err.println("Failed to save prompt and response to file: " + e.getMessage());
            }
            promptAndResponse.setLength(0);
            isPromptAdded = false;
            isResponseAdded = false;
        }
    }

    private static void savePromptAndResponse() throws IOException {
        if (!DEBUG_DIRECTORY.toFile().exists()) {
            DEBUG_DIRECTORY.toFile().mkdirs();
        }

        String fileName = "prompt_response_" + System.currentTimeMillis() + ".log";
        Path filePath = DEBUG_DIRECTORY.resolve(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            writer.write(promptAndResponse.toString());
        }
    }
}
