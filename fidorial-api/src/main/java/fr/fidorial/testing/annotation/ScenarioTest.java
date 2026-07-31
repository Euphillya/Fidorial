package fr.fidorial.testing.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ScenarioTest {

    String group() default "fidorial.builtin";

    int timeoutTicks() default 200;

    boolean required() default true;
}
