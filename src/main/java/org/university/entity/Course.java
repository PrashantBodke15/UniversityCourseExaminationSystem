package org.university.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(nullable = false, length = 100)
    private String title;

    private int credits;

    @Column(nullable = false)
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Examination> examinations = new ArrayList<>();

    public Course() {
    }

    public Course(String code, String title,
                  int credits, int capacity) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public List<Examination> getExaminations() {
        return examinations;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", credits=" + credits +
                ", capacity=" + capacity +
                '}';
    }
}