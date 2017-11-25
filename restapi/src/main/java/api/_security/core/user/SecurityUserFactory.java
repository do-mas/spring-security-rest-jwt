package api._security.core.user;

import api.repo.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import api.repo.Role;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityUserFactory {


    public static AuthorizedUser create(UserEntity user) {

        return new AuthorizedUser(
                user.getUserId(),
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoles()),
                true,
                new Date()
        );

    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole()))
                .collect(Collectors.toList());
    }
}
