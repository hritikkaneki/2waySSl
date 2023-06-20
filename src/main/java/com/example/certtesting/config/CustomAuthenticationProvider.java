package com.example.certtesting.config;

import com.example.certtesting.entity.AuthUser;
import com.example.certtesting.repo.AuthUserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final HttpServletRequest request;
    private final PasswordEncoder encoder;
    private final AuthUserRepo authUserRepo;

    public CustomAuthenticationProvider(HttpServletRequest request, PasswordEncoder encoder, AuthUserRepo authUserRepo) {
        this.request = request;
        this.encoder = encoder;
        this.authUserRepo = authUserRepo;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("hello");
        X509Certificate[] clientCertificates = (X509Certificate[]) request.getAttribute("jakarta.servlet.request.X509Certificate");
        if (clientCertificates != null) {
//        TODO:validate certificate with trust-store
            System.out.println(clientCertificates.length);
//            System.out.println(clientCertificates[1]);
            return new UsernamePasswordAuthenticationToken("test", "test", new ArrayList<>());
        }

//      form login
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        AuthUser authUser = authUserRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user " + email + " not found"));
        List<GrantedAuthority> tempAuthUserRoles = new ArrayList<>();
        authUser.getRoles().forEach(e -> tempAuthUserRoles.add(new SimpleGrantedAuthority(e.getRole())));

        if (encoder.matches(password, authUser.getPassword()))
            return new UsernamePasswordAuthenticationToken(email, password, tempAuthUserRoles);

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
