package br.com.juliogriebeler.pingapp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DatabaseColumn {

    String name();

    String type();

    boolean isPrimaryKey() default false;

    boolean isNullable() default false;

    boolean isIndex() default false;

    boolean isAutoIncrement() default false;


}
