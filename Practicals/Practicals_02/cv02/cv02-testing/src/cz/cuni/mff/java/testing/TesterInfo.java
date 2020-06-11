package cz.cuni.mff.java.testing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TesterInfo {
    String createdBy() default "N/A";
    String lastModified() default "N/A";
    Priority priority() default Priority.LOW;
}
