package entities;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Base entity, provides better toString() for subclasses
 */
abstract class Dto{

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
