package security;

import com.fullteaching.backend.security.AuthorizationService;
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
    private UserComponent user ;

    @InjectMocks
    private AuthorizationService auth;

    private ResponseEntity<Object> response;

    @Test
    public void testUnauthorizedUser() {
        when(user.isLoggedUser()).thenReturn(false);

        response = auth.checkBackendLogged();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testUserLogged() {
        when(user.isLoggedUser()).thenReturn(true);

        response = auth.checkBackendLogged();
        assertNull(response);
    }

    @Test
    public void testCheckAuthorizationObjectNotFound() {
        response = auth.checkAuthorization(null, null);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
