import java.util.Arrays;
import java.util.List;
public enum Pizza {
        MARGHERITA("Marrgherita", Ingredient.THIN_CRUST, Ingredient.TOMATO_SUACE,
                Ingredient.CHEESE),
        CAPRI("Capri", Ingredient.THIN_CRUST, Ingredient.TOMATO_SUACE, Ingredient.CHEESE, Ingredient.HAM,
                Ingredient.MUSHROOMS),
        HAVAI("Havai", Ingredient.THIN_CRUST, Ingredient.TOMATO_SUACE, Ingredient.CHEESE,
                Ingredient.HAM,Ingredient.PINEAPPLE),
        CARUSO("Caruso", Ingredient.THIN_CRUST, Ingredient.TOMATO_SUACE, Ingredient.SUASAGE,
                Ingredient.PEPERONI),
        MAMA_MIA("Mama Mia", Ingredient.THIN_CRUST, Ingredient.TOMATO_SUACE, Ingredient.CHEESE,
                Ingredient.ONION, Ingredient.MUSHROOMS, Ingredient.BECON),
        SOPRANO("Soprano", Ingredient.THICK_CRUST, Ingredient.TOMATO_SUACE, Ingredient.CHEESE, Ingredient.HAM,
                Ingredient.MUSHROOMS, Ingredient.ONION, Ingredient.BECON, Ingredient.PEPPER),
        CALABRESE("Calabrese", Ingredient.THICK_CRUST, Ingredient.TOMATO_SUACE, Ingredient.CHEESE,
                Ingredient.HAM, Ingredient.MUSHROOMS, Ingredient.SUASAGE, Ingredient.ONION, Ingredient.OLIVES),
        VEGETARIANA("Vegetariana", Ingredient.THIN_CRUST, Ingredient.TOMATO_SUACE,
                Ingredient.CHEESE,
                Ingredient.ONION, Ingredient.BEAN, Ingredient.CORN, Ingredient.BROCCOLI, Ingredient.ARUGULA),
        CAPRESE("Caprese", Ingredient.THICK_CRUST, Ingredient.TOMATO_SUACE,
                Ingredient.MOZARELLA,Ingredient.FETA, Ingredient.TOMATO, Ingredient.BASIL),
        PASCETORE("Pascetore", Ingredient.THIN_CRUST, Ingredient.TOMATO_SUACE, Ingredient.CHEESE,
                Ingredient.TUNA, Ingredient.ONION),
        FOUR_CHEESE("Cztery sery", Ingredient.THIN_CRUST, Ingredient.TOMATO_SUACE,
                Ingredient.CHEESE,Ingredient.MOZARELLA, Ingredient.FETA, Ingredient.BLUE_CHEESE),
        TABASCO("Tabasco", Ingredient.THICK_CRUST, Ingredient.TOMATO_SUACE, Ingredient.CHEESE, Ingredient.HAM,
                Ingredient.SALAMI, Ingredient.PEPERONI, Ingredient.CORN, Ingredient.TABASCO_SUACE),
        AMORE("Amore", Ingredient.THIN_CRUST, Ingredient.TOMATO_SUACE, Ingredient.CHEESE,
                Ingredient.CHICKEN,Ingredient.TOMATO),
        FARMER("Farmerska", Ingredient.THICK_CRUST, Ingredient.TOMATO_SUACE, Ingredient.CHEESE,
                Ingredient.CHICKEN, Ingredient.BECON, Ingredient.ONION, Ingredient.CORN);
        private final String name;
        private final List<Ingredient> ingredients;
        private Pizza(String name, Ingredient... ingredients) {
            this.name = name;
            this.ingredients = Arrays.asList(ingredients);
        }
        public String getName() {
            return name;
        }
        public List<Ingredient> getIngredients() {
            return ingredients;
        }
        @Override
        public String toString() {
            return name;
        }
    }
