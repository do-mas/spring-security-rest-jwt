package api._security;

import api._security.bean.AuthFailedResponse;
import api._security.bean.ChangePasswordRequest;
import api._security.core.token.TokenUtil;
import api._security.core.user.SecurityService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class AuthenticationController {

    private static String authFailedStatus = "failed";

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenUtil tokenUtils;
    @Autowired
    private SecurityService userSecurityService;

    @RequestMapping(value = "change-password", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) throws AuthenticationException {

        String psw1 = new String(Base64.decodeBase64(request.getNewPassword()));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String decodedPassword = new String(Base64.decodeBase64(request.getOldPassword()));

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            decodedPassword
                    )
            );

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new AuthFailedResponse(authFailedStatus, "Wrong Old Password"));
        } catch (InternalAuthenticationServiceException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new AuthFailedResponse(authFailedStatus, e.getMessage()));
        }

        userSecurityService.saveNewPassword(username, psw1);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(Device device, HttpServletRequest request) throws AuthenticationException {

        String authorizationHeader = request.getHeader("Authorization");

        if (StringUtils.isBlank(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getStatusWrongCredentials());
        }

        try {

            return ResponseEntity.ok(userSecurityService.login(device, authorizationHeader));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getStatusWrongCredentials());
        }

    }

    private AuthFailedResponse getStatusWrongCredentials() {
        return new AuthFailedResponse(authFailedStatus, "Username or Password is incorrect!");
    }

}
