package com.university.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String title;

    private int credits;

    private int capacity;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    public Course() {
    }

    public Course(String code, String title, int credits, int capacity, Faculty faculty) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.capacity = capacity;
        this.faculty = faculty;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }

    @Override
    public String toString() {
        return "Course{id=" + id + ", code='" + code + "', title='" + title
                + "', credits=" + credits + ", capacity=" + capacity + "}";
    }
}
