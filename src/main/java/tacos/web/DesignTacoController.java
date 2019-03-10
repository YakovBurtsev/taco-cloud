package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    private final TacoRepository designRepo;

    private final UserRepository userRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo,
                                TacoRepository designRepo,
                                UserRepository userRepository) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
        this.userRepository = userRepository;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute
    public void addAttributesToModel(Model model, Principal principal) {
        List<Ingredient> ingredients = ingredientRepo.findAll();

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
    }

    private static List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(toList());
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco taco,
                                Errors errors,
                                @ModelAttribute Order order) {

        if (errors.hasErrors()) {
            return "design";
        }

        Taco saved = designRepo.save(taco);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

}