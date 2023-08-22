package net.in.pSwitch.model;

import lombok.Getter;
import lombok.Setter;
import net.in.pSwitch.model.user.UserInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "business_details")
public class BusinessDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull(message = "Business name cannot be blank")
    @Column(name = "business_name", nullable = false)
    private String businessName;

    @NotNull(message = "address cannot be blank")
    @Column(name = "address")
    private String address = "";

    @NotNull(message = "City cannot be blank")
    @Column(name = "city")
    private Long city = 0l;

    @NotNull(message = "State cannot be blank")
    @Column(name = "state")
    private Long state = 0l;

    @NotNull(message = "Country cannot be blank")
    @Column(name = "country")
    private Long country = 101l;

    @Column(name = "type_of_mcc")
    private Long typeOfMcc = 0l;

    @Column(name = "type_of_shop")
    private Long typeOfShop = 0l;
    @NotNull(message = "Pincode cannot be blank")
    @Column(name = "zipcode")
    private String zipcode = "";

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserInfo userInfo;


}