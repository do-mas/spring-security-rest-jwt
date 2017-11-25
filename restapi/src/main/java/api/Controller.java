package api;

import api._security.core.token.TokenUtil;
import api._security.core.user.AuthorizedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Controller {

    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public AuthorizedUser getAuthenticatedUser(HttpServletRequest request) {
        String username = tokenUtil.getUsernameFromToken(request.getHeader("Authorization"));
        return (AuthorizedUser) userDetailsService.loadUserByUsername(username);
    }

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public String getProtectedGreeting() {
        return "Greetings from admin protected method!";
    }
}
