package app.pdf;

import app.coverletter.CoverLetter;

public interface ICoverLetterExporter {
    void generateCoverLetter(String fileName, CoverLetter coverLetter);
}

