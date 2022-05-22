package wtf.azure.util.internal;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Uses reflection to gather fields/methods/annoations/etc
 *
 * @author aesthetical
 * @since 5/22/22
 */
public class ReflectionUtil {

    /**
     * Gets all fields with this type
     *
     * @param parent the parent class
     * @param fieldKlass the class the field has to be
     * @return a list of the fields with this type
     */
    public static List<Field> allFieldsWithType(Object parent, Class<?> fieldKlass) {
        return Arrays.stream(parent.getClass().getDeclaredFields())
                .filter((field) -> fieldKlass.isAssignableFrom(field.getType())).toList();
    }

    /**
     * Gets all classes in a package
     *
     * @param path the package
     * @param klass the class type
     * @param <T> the type
     * @return a list of ClassInfo
     *
     * @throws IOException if something happens idk
     */
    public static <T> List<Class<T>> getClasses(String path, Class<T> klass) throws IOException {
        String fixedPackageName = path.replaceAll("[.]", "/");

        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(fixedPackageName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        List<Class<T>> classes = new CopyOnWriteArrayList<>();

        String t;
        while ((t = reader.readLine()) != null) {

            // this is a package, we'll go ahead and add all the classes from this package
            if (!t.endsWith(".class")) {
                classes.addAll(getClasses(path + "." + t, klass));
                continue;
            }

            Class<?> clazz = getClassByName(path, t);
            if (klass.isAssignableFrom(clazz)) {
                classes.add((Class<T>) clazz);
            }
        }

        return classes;
    }

    /**
     * Gets a class by its name
     * @param path the package the class should be in
     * @param name the name of the class
     * @return the class
     */
    @SneakyThrows
    public static Class<?> getClassByName(String path, String name) {
        return Class.forName(path + "." + name.substring(0, name.lastIndexOf(".")));
    }

    /**
     * Checks if one classes super class is equal to another
     *
     * Useful for looping through a package where you have a base class (Module) and then you have other packages that
     * extend off of that base class
     *
     * @param to the class to compare
     * @param base the base class
     * @return if the "to" class is the super class of base
     */
    public static boolean isSameBaseClass(Class<?> to, Class<?> base) {
        return to.getSuperclass().isAssignableFrom(base);
    }
}
