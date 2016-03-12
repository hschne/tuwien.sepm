package entities;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

abstract class AbstractEntity {

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
