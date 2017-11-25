package api._security.core.user;

import api._security.bean.LoginResponse;
import api._security.bean.User;
import api._security.core.token.TokenUtil;
import api.repo.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import api.repo.UserEntity;

@Component
public class SecurityService implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private TokenUtil tokenUtils;
    @Autowired private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserName(userName);;

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userName));
        } else {
            return SecurityUserFactory.create(user);
        }
    }

    public void saveNewPassword(String userName, String psw) {
        UserEntity user = userRepository.findByUserName(userName);
        user.setPassword(encodePsw(psw));
        userRepository.save(user);
    }

    public LoginResponse login(Device device, String authorizationHeader) {
        Authentication auth = authenticate(authorizationHeader);
        AuthorizedUser user = (AuthorizedUser) auth.getPrincipal();
        return getLoginResponse(user, device);
    }

    public Authentication authenticate(String authorizationHeader) {

        byte[] credentialsBytes = Base64.decodeBase64(authorizationHeader);
        String credentials = new String(credentialsBytes);

        String username = credentials.split(":")[0];
        String password = credentials.split(":")[1];

        byte[] pswBytes = Base64.decodeBase64(password);
        String decodedPassword = new String(pswBytes);

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        decodedPassword
                )
        );

    }

    private String encodePsw(String rawPsw) {
        BCryptPasswordEncoder en = new BCryptPasswordEncoder();
        return en.encode(rawPsw);
    }

    private LoginResponse getLoginResponse(AuthorizedUser user, Device device) {
        String token = tokenUtils.generateToken(user, device);
        return new LoginResponse(token,
                new User(user.getUsername(),
                        user.getFirstname(),
                        user.getLastname()));
    }

}
