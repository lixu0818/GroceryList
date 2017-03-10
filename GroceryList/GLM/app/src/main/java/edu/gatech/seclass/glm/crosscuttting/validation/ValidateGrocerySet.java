package edu.gatech.seclass.glm.crosscuttting.validation;

import edu.gatech.seclass.glm.domain.GrocerySet;

/**
 * Represents the presenter implementation for Add {@link edu.gatech.seclass.glm.domain.GrocerySet}
 *
 * @author Team 02
 * @since 10/14/2016
 * @version 1.0
 */
public class ValidateGrocerySet implements Validator {

    private static final ValidateGrocerySet INSTANCE = new ValidateGrocerySet();

    public boolean validate(final Object object) {
        if (object != null) {
            final GrocerySet set  = (GrocerySet) object;
            if (set != null && set.getName() != null && !set.getName().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the singleton instance.
     *
     * @return The {@link ValidateGrocerySet}
     */
    public static ValidateGrocerySet get() {
        return INSTANCE;
    }
}
