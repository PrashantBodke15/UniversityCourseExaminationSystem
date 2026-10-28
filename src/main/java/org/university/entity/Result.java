package org.university.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "results",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_enrollment_examination",
                columnNames = {"enrollment_id", "examination_id"}
        )
)
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "examination_id", nullable = false)
    private Examination examination;

    @Column(nullable = false)
    private double marksObtained;

    @Column(nullable = false, length = 5)
    private String grade;

    @Column(nullable = false)
    private boolean passed;

    public Result() {
    }

    public Result(Enrollment enrollment, Examination examination,
                  double marksObtained, String grade,
                  boolean passed) {
        this.enrollment = enrollment;
        this.examination = examination;
        this.marksObtained = marksObtained;
        this.grade = grade;
        this.passed = passed;
    }

    public Long getId() {
        return id;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public Examination getExamination() {
        return examination;
    }

    public double getMarksObtained() {
        return marksObtained;
    }

    public String getGrade() {
        return grade;
    }

    public boolean isPassed() {
        return passed;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", student=" + enrollment.getStudent().getName() +
                ", examination=" + examination.getTitle() +
                ", marksObtained=" + marksObtained +
                ", grade='" + grade + '\'' +
                ", passed=" + passed +
                '}';
    }
}