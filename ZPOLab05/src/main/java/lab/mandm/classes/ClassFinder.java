package lab.mandm.classes;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ClassFinder {
    String packageName;

    public ClassFinder(String packageName) {
        this.packageName = packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public ArrayList<ClassView> getClassViews() {
        ArrayList<ClassView> classes = new ArrayList<>();
        try {
            URL packageURL = new URL(this.getClass().getProtectionDomain().getCodeSource().getLocation().toString() + packageName.replace(".", "/"));

            File[] files = new File(packageURL.getFile()).listFiles((dir, name) -> name.endsWith(".class"));

            for (File f : files) {
                String fileName = f.getName();
                Class<?> clazz = Class.forName(packageName + "." + fileName.replace(".class", ""));
                if (!clazz.isInterface() && !clazz.isEnum() && !clazz.isAnnotation())
                    classes.add(new ClassView(clazz));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            return classes;
        }
    }
}
