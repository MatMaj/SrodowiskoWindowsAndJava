package lab.mandm.classes;

import lab.mandm.annotations.Named;

import java.lang.reflect.Method;

public class BecauseAnnotation {
    @Named(name = "XD")
    public String getName(){
        Method declaredMethod;
        Named named;
        String newColumnHeader = "";

        try {
            declaredMethod = BecauseAnnotation.class.getDeclaredMethod("getName");
            named = declaredMethod.getAnnotation(Named.class);
            newColumnHeader = named.name();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return newColumnHeader;
    }
}
