package com.university.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "results",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "examination_id"}))
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "examination_id")
    private Examination examination;

    private double marksObtained;

    private String grade;

    public Result() {
    }

    public Result(Student student, Examination examination, double marksObtained, String grade) {
        this.student = student;
        this.examination = examination;
        this.marksObtained = marksObtained;
        this.grade = grade;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Examination getExamination() { return examination; }
    public void setExamination(Examination examination) { this.examination = examination; }
    public double getMarksObtained() { return marksObtained; }
    public void setMarksObtained(double marksObtained) { this.marksObtained = marksObtained; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return "Result{id=" + id + ", student=" + student.getName() + ", course="
                + examination.getCourse().getCode() + ", marks=" + marksObtained
                + "/" + examination.getMaxMarks() + ", grade=" + grade + "}";
    }
}
