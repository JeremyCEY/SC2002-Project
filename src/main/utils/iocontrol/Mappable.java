package main.utils.iocontrol;

import main.utils.parameters.EmptyID;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Mappable} interface provides methods to convert objects to and from {@code Map<String, String>}.
 * Objects implementing this interface can be mapped to a map representation and vice versa.
 */
public interface Mappable {
    /**
     * Converts the current object to a {@code Map<String, String>}.
     *
     * @return A map representation of the object, where keys are field names and values are their string representations.
     */
    default Map<String, String> toMap() {

        Map<String, String> map = new HashMap<>();
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {

            try {
                field.setAccessible(true);
                try {
                    map.put(field.getName(), field.get(this).toString());
                } catch (NullPointerException e) {
                    map.put(field.getName(), EmptyID.EMPTY_ID);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;

    }
    
    /**
     * Populates the fields of the current object from the provided {@code Map<String, String>}.
     *
     * @param map The map containing field names as keys and their string representations as values.
     */
    default void fromMap(Map<String, String> map) {
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.getType().isEnum()) {
                    @SuppressWarnings("unchecked")
                    Enum<?> enumValue = Enum.valueOf((Class<Enum>) field.getType(), map.get(field.getName()));
                    field.set(this, enumValue);
                } else if (field.getType().equals(Integer.TYPE) || field.getType().equals(Integer.class)) {

                    if (EmptyID.isEmptyID(map.get(field.getName()))) {
                        field.set(this, 0);
                    } else {
                        int intValue = Integer.parseInt(map.get(field.getName()));
                        field.set(this, intValue);
                    }
                } else {
                    field.set(this, map.get(field.getName()));
                }
            } catch (IllegalAccessException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

}
