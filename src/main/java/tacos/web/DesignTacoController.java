package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static tacos.Ingredient.Type.CHEESE;
import static tacos.Ingredient.Type.PROTEIN;
import static tacos.Ingredient.Type.SAUCE;
import static tacos.Ingredient.Type.VEGGIES;
import static tacos.Ingredient.Type.WRAP;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", WRAP),
                new Ingredient("COTO", "Corn Tortilla", WRAP),
                new Ingredient("GRBF", "Ground Beef", PROTEIN),
                new Ingredient("CARN", "Carnitas", PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", VEGGIES),
                new Ingredient("LETC", "Lettuce", VEGGIES),
                new Ingredient("CHED", "Cheddar", CHEESE),
                new Ingredient("JACK", "Monterrey Jack", CHEESE),
                new Ingredient("SLSA", "Salsa", SAUCE),
                new Ingredient("SRCR", "Sour Cream", SAUCE)
        );

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        model.addAttribute("design", new Taco());

        return "design";
    }

    private static List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(toList());
    }

    @PostMapping
    public String processDesign(Taco design) {
        //todo: Save the taco design...
        log.info("Processing design: " + design);

        return "redirect:/orders/current";
    }

}