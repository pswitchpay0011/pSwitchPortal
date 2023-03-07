package net.in.pSwitch.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "exam_type")
    private String examType;

    @CreationTimestamp
    @Column(name = "created_ts", nullable = false)
    private LocalDateTime createdTs;

    @UpdateTimestamp
    @Column(name = "last_updated_ts", nullable = false)
    private LocalDateTime lastUpdatedTs;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    public LocalDateTime getLastUpdatedTs() {
        return lastUpdatedTs;
    }

    public void setLastUpdatedTs(LocalDateTime lastUpdatedTs) {
        this.lastUpdatedTs = lastUpdatedTs;
    }
}
