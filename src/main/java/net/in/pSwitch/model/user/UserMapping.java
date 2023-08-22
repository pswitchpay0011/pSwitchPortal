package net.in.pSwitch.model.user;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_mapping")
public class UserMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, name = "id", nullable = false)
    private Integer id;

    @JoinColumn(name = "parent_user_id", nullable = false)
    private Integer parentUser;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "updatedBy", nullable = false)
    private Integer updatedBy;

    @CreationTimestamp
    @Column(name = "createdOn", nullable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updatedOn", nullable = false)
    private LocalDateTime updatedOn;
}
