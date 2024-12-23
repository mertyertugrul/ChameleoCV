package app.cv;

import app.entity.*;
import app.entity.common.Certification;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CV(
        @JsonProperty("personalInfo") PersonalInfo personalInfo,
        @JsonProperty("summary") Summary summary,
        @JsonProperty("professionalExperience") List<ProfessionalExperience> professionalExperience,
        @JsonProperty("education") List<Education> education,
        @JsonProperty("skills") Skills skills,
        @JsonProperty("certifications") List<Certification> certifications,
        @JsonProperty("recommendations") List<Recommendation> recommendations
) {}