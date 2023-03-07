package net.in.pSwitch.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "pwd")
    private String pwd;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Role roles;

    @NotNull(message = "First Name cannot be blank")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 50, message = "Last Name must be between 2 and 50 characters")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_active")
    private Long isActive = 0l;

    @Column(name = "is_verified")
    private Long isVerified = 0l;

    @NotNull(message = "Contact Number cannot be blank")
    @Column(name = "mobileNumber")
    private String mobileNumber;

    //	@NotNull(message = "Address cannot be blank")
    @Column(name = "address")
    private String address = "";

    //	@NotNull(message = "City cannot be blank")
    @Column(name = "city")
    private Long city = 0l;

    //	@NotNull(message = "State cannot be blank")
    @Column(name = "state")
    private Long state = 0l;

    @Column(name = "country")
    private Long country = 101l;

    //	@NotNull(message = "Zipcode cannot be blank")
    @Column(name = "zipcode")
    private String zipcode = "";

    @Column(name = "verification_code")
    private String verificationCode;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "user_pswitch_id")
    private String userPSwitchId;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "aadhaar_number")
    private String aadhaarNumber;

    @Column(name = "pancard_picture")
    private String pancardNumber;

    @Column(name = "gst_no")
    private String gstNo;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "agreement_accept")
    private Long agreementAccept = 0l;

    @Column(name = "e_sign_agreement")
    private Long eSignAgreement = 0l;

    @Column(name = "contact_otp")
    private String contactOTP = null;

    @Column(name = "password_reset_otp")
    private Long passwordResetOTP = 0l;

    @Column(name = "contact_verified")
    private Long contactVerified = 0l;

    @OneToOne
    @JoinColumn(name = "bank_details_id")
    private UserBankDetails bankDetails;

    @OneToOne
    @JoinColumn(name = "business_details")
    private BusinessDetails businessDetails;

    @OneToMany(mappedBy = "userInfo")
    private List<Attachment> attachments;

    @Column(name = "account_state")
    private String accountState = "";

//	@Column(name = "parent_user_id")
//	private Long parentUserId;

//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "parent_user_id")
//	private List<UserInfo> associate;

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "parent_user_id")
//	private UserInfo parent;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_user_id")
    protected UserInfo parent;

    @OneToMany(mappedBy = "parent")
    protected List<UserInfo> associate;

    @Column(name = "account_non_locked")
    private Boolean accountNonLocked = true;

    @Column(name = "failed_attempt")
    private Integer failedAttempt;

    @Column(name = "lock_time")
    private Date lockTime;

    @Column(name = "kyc_completed")
    private Boolean kycCompleted = false;

    public Boolean isAccountNonLocked() {
        return accountNonLocked == null ? true : accountNonLocked;
    }

    public Integer getFailedAttempt() {
        return failedAttempt == null ? 0 : failedAttempt;
    }

    public boolean isEnabled() {
        return isActive == 1;
    }

    public Boolean isKycCompleted() {
        return kycCompleted;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

}