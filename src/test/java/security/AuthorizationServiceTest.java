package security;

import com.fullteaching.backend.security.AuthorizationService;
import com.fullteaching.backend.user.User;
import com.fullteaching.backend.user.UserComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationServiceTest {

    @Mock
    private UserComponent userComponent ;
    @Mock
    private User user;

    @InjectMocks
    private AuthorizationService auth;

    private ResponseEntity<Object> response;

    @Test
    public void testUnauthorizedUser() {
        when(userComponent.isLoggedUser()).thenReturn(false);

        response = auth.checkBackendLogged();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testUserLogged() {
        when(userComponent.isLoggedUser()).thenReturn(true);

        response = auth.checkBackendLogged();
        assertNull(response);
    }

    @Test
    public void testCheckAuthorizationObjectNotFound() {
        response = auth.checkAuthorization(null, null);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCheckAuthorizationUserNotAuthorized() {
        Object object = new Object();
        User u = new User();

        when(userComponent.getLoggedUser()).thenReturn(user);
        when(user.getName()).thenReturn("pedro");

        response = auth.checkAuthorization(object, u);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testCheckAuthorizationUserAuthorized() {
        Object object = new Object();

        when(userComponent.getLoggedUser()).thenReturn(user);
        when(user.getName()).thenReturn("Jose");

        response = auth.checkAuthorization(object, user);
        assertNull(response);
    }



}
