import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Implement {
    Pizza findCheapestSpicy(){
        return Stream.of(Pizza.values()).filter(a->a.getIngredients().stream().anyMatch(Ingredient::isSpicy))
                .min(Comparator.comparing(b->b.getIngredients().stream().mapToInt(Ingredient::getPrice).sum())).get();
    }
    Pizza findMostExpensiveVegetarian(){
        return Stream.of(Pizza.values()).filter(a->a.getIngredients().stream().noneMatch(Ingredient::isMeat))
                .max(Comparator.comparing(b->b.getIngredients().stream().mapToInt(Ingredient::getPrice).sum())).get();
    }
    public List iLikeMeat(){
        return Stream.of(Pizza.values()).filter(pizza -> pizza.getIngredients().stream().filter(ingredient -> ingredient.isMeat()).count() > 0)
                .sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    public Map groupByPrice(){
        Map<Integer, List<Pizza>> mapka = Stream.of(Pizza.values()).collect(Collectors.groupingBy(pizza -> pizza.getIngredients()
                .stream().mapToInt(Ingredient::getPrice).sum()));
        return mapka;
    }
    String formatedMenu(){
        StringBuilder sb = new StringBuilder();
        Stream.of(Pizza.values()).forEach(pizza -> {
            sb.append(pizza.getName());
            sb.append(": ");
            sb.append(pizza.getIngredients().stream().map(Objects::toString).collect(Collectors.joining(", ")));
            sb.append(" - ");
            sb.append(pizza.getIngredients().stream().mapToInt(Ingredient::getPrice).sum());
            sb.append('\n');
        });
        return sb.toString();
    }
}
