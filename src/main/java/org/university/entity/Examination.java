package org.university.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "examinations")
public class Examination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 30)
    private String examinationType;

    @Column(nullable = false)
    private LocalDate examinationDate;

    @Column(nullable = false)
    private double maximumMarks;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public Examination() {
    }

    public Examination(String title, String examinationType,
                       LocalDate examinationDate,
                       double maximumMarks, Course course) {
        this.title = title;
        this.examinationType = examinationType;
        this.examinationDate = examinationDate;
        this.maximumMarks = maximumMarks;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getExaminationType() {
        return examinationType;
    }

    public LocalDate getExaminationDate() {
        return examinationDate;
    }

    public double getMaximumMarks() {
        return maximumMarks;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "Examination{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", examinationType='" + examinationType + '\'' +
                ", examinationDate=" + examinationDate +
                ", maximumMarks=" + maximumMarks +
                ", course=" + course.getTitle() +
                '}';
    }
}