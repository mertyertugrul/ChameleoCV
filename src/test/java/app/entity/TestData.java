package app.entity;

import app.coverletter.CoverLetter;
import app.cv.CV;
import app.entity.*;
import app.entity.common.*;

import java.time.LocalDate;
import java.util.List;

public class TestData {

    public static CV createTestCV() {
        return new CV(
                new PersonalInfo(
                        "Alice", "B.", "Smith",
                        new Email("alice.smith@example.com"),
                        "+1 555-123-4567",
                        new Hyperlink("LinkedIn", "http://www.linkedin.com/in/alice-smith"),
                        new Hyperlink("GitHub", "https://github.com/alicesmith"),
                        new Address("123 Elm Street", "Suite 456", "Springfield", "United States", "12345"),
                        "Alice B. Smith" // Header
                ),
                new Summary(
                        "Certified Java Professional with over 5 years of experience in software development. Skilled in building scalable web applications using Java (Spring Boot), Python, and React. Expertise in microservices and cloud technologies including AWS, Docker, and Kubernetes.",
                        "Professional Summary"
                ),
                List.of(
                        new ProfessionalExperience(
                                "Tech Solutions Inc.",
                                new Position("Senior Software Developer", "Engineering"),
                                new Location("Seattle", "United States"),
                                new DateRange("March 2020", "Present"),
                                new Details(List.of(
                                        "Led a team of developers to create scalable web applications using Spring Boot.",
                                        "Implemented CI/CD pipelines to improve deployment efficiency."
                                )),
                                "Tech Solutions Inc."
                        ),
                        new ProfessionalExperience(
                                "Innovatech LLC",
                                new Position("Software Engineer", "Development"),
                                new Location("Austin", "United States"),
                                new DateRange("June 2017", "February 2020"),
                                new Details(List.of(
                                        "Developed RESTful APIs to support mobile and web applications.",
                                        "Collaborated with cross-functional teams to define software requirements."
                                )),
                                "Innovatech LLC"
                        )
                ),
                List.of(
                        new Education(
                                "University of Springfield", "School of Computer Science",
                                "MSc. in Software Engineering",
                                "Graduated with Distinction (GPA: 3.9/4.0)",
                                new Location("Springfield", "United States"),
                                new DateRange("2015", "2017"),
                                new Details(List.of(
                                        "Conducted research on distributed systems and cloud computing.",
                                        "Published a thesis on scalable microservices architecture."
                                )),
                                "Education"
                        ),
                        new Education(
                                "State University", "Department of Computer Science",
                                "BSc. in Computer Science",
                                "Graduated Cum Laude",
                                new Location("Columbus", "United States"),
                                new DateRange("2011", "2015"),
                                new Details(List.of(
                                        "Designed and implemented a capstone project on AI-based recommendation systems.",
                                        "Served as president of the Programming Club."
                                )),
                                "Education"
                        )
                ),
                new Skills(
                        List.of(
                                new Language("Java 17 with Spring Boot", "Advanced"),
                                new Language("Python", "Advanced")
                        ),
                        List.of(
                                new Framework("React.js", "Intermediate"),
                                new Framework("Kubernetes", "Intermediate")
                        ),
                        List.of(
                                new Tool("Docker", "Advanced"),
                                new Tool("AWS", "Intermediate")
                        ),
                        "Technical Skills"
                ),
                List.of(
                        new Certification(
                                "AWS Certified Solutions Architect – Associate",
                                "AWS-123456789",
                                new Hyperlink("Certification Link",
                                        "https://aws.amazon.com/certification/")
                        ),
                        new Certification(
                                "Oracle Certified Professional: Java SE 11 Developer",
                                "OCP-987654321",
                                new Hyperlink("Certification Link",
                                        "https://oracle.com/certification/")
                        )
                ),
                List.of(
                        new Recommendation("Jane Doe",
                                new Hyperlink("LinkedIn", "https://www.linkedin.com/in/jane-doe"),
                                "Recommendation"),
                        new Recommendation("Bob Johnson",
                                new Hyperlink("LinkedIn", "https://www.linkedin.com/in/bob-johnson"),
                                "Recommendation")
                )
        );
    }

    public static CoverLetter createTestCoverLetter() {
        return new CoverLetter(
                new PersonalInfo(
                        "Alice", "B.", "Smith",
                        new Email("alice.smith@example.com"),
                        "+1 555-123-4567",
                        new Hyperlink("LinkedIn", "http://www.linkedin.com/in/alice-smith"),
                        new Hyperlink("GitHub", "https://github.com/alicesmith"),
                        new Address("123 Elm Street", "Suite 456", "Springfield", "United States", "12345"), "Alice B. Smith"
                ),
                LocalDate.of(2024, 12, 8),
                "Hiring Manager",
                "Senior Software Engineer",
                """
                        I am thrilled to apply for the Senior Software Engineer position at Tech Innovators. With over five years of professional experience in software development and a proven track record in technical leadership, I am eager to contribute to Tech Innovators' mission of driving innovation through cutting-edge solutions.
                        
                        In my role as a Senior Software Developer at Tech Innovators Inc., I led a team of developers in designing and implementing scalable enterprise applications. My hands-on expertise in Java and Python, coupled with my deep understanding of object-oriented programming, has allowed me to create efficient and maintainable software systems. Additionally, I am proficient in front-end technologies like React, which aligns closely with the technical requirements of this role.
                        
                        Key Highlights of My Experience:
                        • Technical Leadership: Successfully led a team of developers, providing mentorship, conducting code reviews, and ensuring adherence to industry best practices. 
                        • Cloud Expertise: Designed and deployed cloud-based solutions on AWS and Azure, ensuring reliability and scalability. 
                        • Software Development Lifecycle: Extensive experience in SDLC, including requirements gathering, design, development, testing, and deployment. 
                        • Cross-Platform Development: Integrated third-party programs and developed secure, reusable components for large-scale systems.
                        
                        Tech Innovators' emphasis on curiosity, collaboration, and integrity resonates deeply with my professional values. I am particularly excited by the opportunity to work on innovative projects that deliver real-world impact. My prior experience in developing solutions for enterprise systems makes me well-prepared to contribute meaningfully to this team.
                        
                        I am confident that my technical expertise and leadership skills align with Tech Innovators' goals and values. I look forward to the opportunity to discuss how my experience can help drive innovation and success in this role. Please feel free to contact me at +1 555-123-4567 or via email at alice.smith@example.com to arrange an interview.
                        """
        );
    }
}
