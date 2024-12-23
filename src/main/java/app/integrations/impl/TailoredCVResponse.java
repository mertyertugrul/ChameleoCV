package app.integrations.impl;

import app.coverletter.CoverLetter;
import app.cv.CV;

public class TailoredCVResponse {
    private final CV tailoredCV;
    private final CoverLetter coverLetter;

    public TailoredCVResponse(CV tailoredCV, CoverLetter coverLetter) {
        this.tailoredCV = tailoredCV;
        this.coverLetter = coverLetter;
    }

    public CV getTailoredCV() { return tailoredCV; }
    public CoverLetter getCoverLetter() { return coverLetter; }
}
