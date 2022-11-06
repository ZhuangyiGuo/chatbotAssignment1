package com.example.chatbotassignment1;

public class Course {
    String title;
    String course_code;
    String instructor;

    public Course(String title, String code, String professor) {
        this.title = title;
        this.course_code = code;
        this.instructor = professor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String code) {
        this.course_code = code;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String professor) {
        this.instructor = professor;
    }
}
