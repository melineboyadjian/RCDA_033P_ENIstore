package tp.eni_store.api;

import org.springframework.web.bind.annotation.*;
import tp.eni_store.service.AuthService;
import tp.eni_store.service.ServiceResponse;

@RestController
public class AuthRestController {

    private final AuthService authService;

    public AuthRestController(AuthService authService, AuthService authService1) {
        this.authService = authService1;
    }

    @PostMapping("api/create-token")
    public ServiceResponse<String> createTokencreateToken(@RequestBody LoginRequest loginRequest)  {
        return authService.createToken(loginRequest.email, loginRequest.password);
    }

    @GetMapping("api/check-token")
    public ServiceResponse<Boolean> checkToken(@RequestHeader(value = "Authorization", required = true) String token){
        return authService.checkToken(token);
    }
}
