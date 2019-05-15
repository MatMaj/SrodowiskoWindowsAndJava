package lab.mandm.classes;

public class ClassView {
    private Class<?> clazz;

    public ClassView(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public String toString(){
        return clazz.getSimpleName();
    }
}
