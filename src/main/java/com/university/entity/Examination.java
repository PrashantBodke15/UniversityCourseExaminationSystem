package com.university.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "examinations")
public class Examination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDate examDate;

    private int maxMarks;

    private String venue;

    public Examination() {
    }

    public Examination(Course course, LocalDate examDate, int maxMarks, String venue) {
        this.course = course;
        this.examDate = examDate;
        this.maxMarks = maxMarks;
        this.venue = venue;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }
    public int getMaxMarks() { return maxMarks; }
    public void setMaxMarks(int maxMarks) { this.maxMarks = maxMarks; }
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    @Override
    public String toString() {
        return "Examination{id=" + id + ", course=" + course.getCode() + ", date=" + examDate
                + ", maxMarks=" + maxMarks + ", venue='" + venue + "'}";
    }
}
