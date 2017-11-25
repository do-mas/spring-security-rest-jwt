package api.repo;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="User")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private Date dob = new Date();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserRole",
               joinColumns = {@JoinColumn(name = "UserID")},
               inverseJoinColumns = {@JoinColumn(name = "RoleID")})
    private Set<Role> roles;

    public long getUserId() {
        return userId;
    }

    public UserEntity setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserEntity setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public Date getDob() {
        return dob;
    }

    public UserEntity setDob(Date dob) {
        this.dob = dob;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public UserEntity setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

}
