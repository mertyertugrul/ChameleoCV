package app.pdf;

import app.cv.CV;

public interface ICVExporter {
    void generateCV(String fileName, CV cv);
}

