package springproject.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import springproject.project.model.bundle.ClassicBundle;
import springproject.project.model.bundle.ABundle;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Entity
@Table(name = "\"user\"")
@EqualsAndHashCode(exclude="images")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name = "email")
    @Email(message = "Please provide a valid email")
    @NotEmpty(message = "Please provide an email")
    private String email;

    @Column(name = "password")
    @Length(min = 8, message = "Your password must have at least 8 characters")
    @NotEmpty(message = "Provide your password please")
    private String password;

    @Column(name = "first_name")
    @NotEmpty(message = "Provide your firstname please")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Provide your lastname please")
    private String lastName;

    @Column(name = "active")
    private int active;

    @Column(name="city")
    @NotEmpty(message= "Provide your city please")
    private String city;

    @Column(name= "username")
    @NotEmpty(message= "Provide an username please")
    private String username;

    @Column(name= "bundle")
    private String bundle = "classic";

    @Transient
    private ABundle bundleObject = ABundle.create(bundle);

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;

    private void bundleReset() {
        if (!bundleObject.getName().equals(bundle))
            bundleObject = ABundle.create(bundle);
    }

    public String getBundleName() {
        bundleReset();
        return bundleObject.getName();
    }

    public int imageCounter() {
        bundleReset();
        return images.size();
    }
}
