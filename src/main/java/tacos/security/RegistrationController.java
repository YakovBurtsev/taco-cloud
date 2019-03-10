package tacos.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.User;
import tacos.data.UserRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @ModelAttribute(name = "registrationForm")
    public RegistrationForm registrationForm() {
        return new RegistrationForm();
    }

    @PostMapping
    public String processRegistration(@Valid RegistrationForm form, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "registration";
        }

        //todo: check password == confirm
        User user = form.toUser(passwordEncoder);

        if (userRepo.findByUsername(user.getUsername()) != null) {
            model.addAttribute("not_unique_username", true);
            return "registration";
        }

        userRepo.save(user);

        return "redirect:/login";
    }

}