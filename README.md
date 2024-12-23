# 🌟 **ChameleoCV🦎** 🌟
Revolutionize your job applications with AI-powered CV and cover letter generation!

---

## **🚀 About ChameleoCV**
ChameleoCV is an intelligent application designed to transform the way you approach job applications. With the power of the OpenAI API, it generates personalized resumes and cover letters tailored to specific job descriptions.

### **Key Features**
- ✍️ **Custom Resumes**: Transform your CV to match any job posting.
- 💌 **Tailored Cover Letters**: Craft professional, job-specific cover letters automatically.
- ⚡ **ATS-Friendly**: Ensures optimal compatibility with Applicant Tracking Systems.
- 🕒 **Time-Saving**: Automates the tedious task of document preparation.

---

## **🎯 Getting Started**

### **Prerequisites**
Ensure you have the following installed:
- Java 17 or higher
- Maven

## **🎯 Getting Started**

### **Prerequisites**
Ensure you have the following installed:
- Java 17 or higher
- Maven

### **Clone the Repository**
```bash
git clone https://github.com/mertyertugrul/ChameleoCV.git
cd ChameleoCV
```

### **Build the Project**
Use Maven to build the project:
```bash
mvn clean install
```

### **Run the Application**
```bash
java -jar target/ChameleoCV-1.0.0.jar
```

### **Environment Variables**
Set up your `.env` file or provide environment variables with the following keys:
- `OPENAI_API_KEY`: Your OpenAI API key.
- `SERVER_PORT`: Port for the application (default: `8080`).

---

## **🛠️ How It Works**
1. 📥 Upload your current CV and target job description.
2. 🔄 ChameleoCV processes your documents using OpenAI.
3. 📤 Save the tailored CV and cover letter for the specific job description.

---

## **📋 Example JSON**
Here’s a snippet of the JSON structure used by ChameleoCV:
```json
{
  "personalInfo": {
    "firstName": "Alice",
    "middleName": "B.",
    "lastName": "Smith",
    "email": "alice.smith@example.com",
    "telNumber": "+1 555-123-4567",
    "linkedin": {
      "text": "LinkedIn",
      "url": "http://www.linkedin.com/in/alice-smith"
    },
    "github": {
      "text": "GitHub",
      "url": "https://github.com/alicesmith"
    },
    "address": {
      "street": "123 Elm Street",
      "flatOrHouseNumber": "Suite 456",
      "city": "Springfield",
      "country": "United States",
      "zipCode": "12345"
    },
    "header": "Alice B. Smith"
  },
  "summary": {
    "summaryText": "Certified Java Professional with over 5 years of experience in software development. Skilled in building scalable web applications using Java (Spring Boot), Python, and React. Expertise in microservices and cloud technologies including AWS, Docker, and Kubernetes.",
    "header": "Professional Summary"
  },
  "professionalExperience": [
    {
      "company": "Tech Solutions Inc.",
      "position": {
        "title": "Senior Software Developer",
        "department": "Engineering"
      },
      "location": {
        "city": "Seattle",
        "country": "United States"
      },
      "dateRange": {
        "startDate": "March 2020",
        "endDate": "Present"
      },
      "details": {
        "details": [
          "Led a team of developers to create scalable web applications using Spring Boot.",
          "Implemented CI/CD pipelines to improve deployment efficiency."
        ]
      },
      "header": "Tech Solutions Inc."
    },
    {
      "company": "Innovatech LLC",
      "position": {
        "title": "Software Engineer",
        "department": "Development"
      },
      "location": {
        "city": "Austin",
        "country": "United States"
      },
      "dateRange": {
        "startDate": "June 2017",
        "endDate": "February 2020"
      },
      "details": {
        "details": [
          "Developed RESTful APIs to support mobile and web applications.",
          "Collaborated with cross-functional teams to define software requirements."
        ]
      },
      "header": "Innovatech LLC"
    }
  ],
  "education": [
    {
      "school": "University of Springfield",
      "institution": "School of Computer Science",
      "degree": "MSc. in Software Engineering",
      "grade": "Graduated with Distinction (GPA: 3.9/4.0)",
      "location": {
        "city": "Springfield",
        "country": "United States"
      },
      "dateRange": {
        "startDate": "2015",
        "endDate": "2017"
      },
      "details": {
        "details": [
          "Conducted research on distributed systems and cloud computing.",
          "Published a thesis on scalable microservices architecture."
        ]
      },
      "header": "Education"
    },
    {
      "school": "State University",
      "institution": "Department of Computer Science",
      "degree": "BSc. in Computer Science",
      "grade": "Graduated Cum Laude",
      "location": {
        "city": "Columbus",
        "country": "United States"
      },
      "dateRange": {
        "startDate": "2011",
        "endDate": "2015"
      },
      "details": {
        "details": [
          "Designed and implemented a capstone project on AI-based recommendation systems.",
          "Served as president of the Programming Club."
        ]
      },
      "header": "Education"
    }
  ],
  "certifications": [
    {
      "certifications": [
        {
          "title": "AWS Certified Solutions Architect \u2013 Associate",
          "certificationId": "AWS-123456789",
          "link": {
            "text": "Certification Link",
            "url": "https://aws.amazon.com/certification/"
          }
        },
        {
          "title": "Oracle Certified Professional: Java SE 11 Developer",
          "certificationId": "OCP-987654321",
          "link": {
            "text": "Certification Link",
            "url": "https://oracle.com/certification/"
          }
        }
      ],
      "header": "Certifications"
    }
  ],
  "recommendations": [
    {
      "name": "Jane Doe",
      "link": {
        "text": "LinkedIn",
        "url": "https://www.linkedin.com/in/jane-doe"
      },
      "header": "Recommendation"
    },
    {
      "name": "Bob Johnson",
      "link": {
        "text": "LinkedIn",
        "url": "https://www.linkedin.com/in/bob-johnson"
      },
      "header": "Recommendation"
    }
  ],
  "skills": {
    "languages": [
      {
        "name": "Java 17 with Spring Boot",
        "level": "Advanced"
      },
      {
        "name": "Python",
        "level": "Advanced"
      }
    ],
    "frameworks": [
      {
        "name": "React.js",
        "level": "Intermediate"
      },
      {
        "name": "Kubernetes",
        "level": "Intermediate"
      }
    ],
    "tools": [
      {
        "name": "Docker",
        "level": "Advanced"
      },
      {
        "name": "AWS",
        "level": "Intermediate"
      }
    ],
    "header": "Technical Skills"
  }
}
```

---

## **🤝 Contributing**
We welcome contributions! Follow these steps:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/YourFeature`).
3. Commit changes (`git commit -m "Add YourFeature"`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Open a Pull Request.

---

## **📄 License**
This project is licensed under the [MIT License](LICENSE).
---

