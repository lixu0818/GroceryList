package edu.gatech.seclass.glm.crosscuttting.validation;


/**
 * Interface for validation of all domain objects.
 *
 * @author Team 02
 * @since 10/17/2016
 * @version 1.0
 */
public interface Validator {

    /**
     * Validate the domain class
     *
     * @param object The domain class to validate
     * @return true if validation is successfull, false otherwise
     */
    boolean validate(Object object);
}
