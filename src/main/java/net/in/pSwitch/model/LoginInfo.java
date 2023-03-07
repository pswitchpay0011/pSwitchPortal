package net.in.pSwitch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loginInfo")
public class LoginInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, name = "id", nullable = false)
    private long id;

    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_ip")
    private String userIp;

    @Column(name = "user_mac")
    private String userMac;

    @Column(name = "user_location")
    private String userLatLng;

    @Column(name = "remark")
    private String remark;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
}
