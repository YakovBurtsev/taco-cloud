package tacos.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
class RegistrationForm {

    @NotNull
    @Size(min = 5, message = "Username must be at least 5 characters long")
    private String username;

    @NotNull
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;

    private String confirm;

    private String fullname;

    @NotBlank(message="Street is required")
    private String street;

    @NotBlank(message="City is required")
    private String city;

    @NotBlank(message="State is required")
    private String state;

    @NotBlank(message="Zip code is required")
    private String zip;

    @NotBlank(message="Phone is required")
    private String phone;

    User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username,
                passwordEncoder.encode(password),
                fullname,
                street,
                city,
                state,
                zip,
                phone
        );
    }

}