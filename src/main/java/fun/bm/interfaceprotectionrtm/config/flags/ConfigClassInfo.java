package fun.bm.interfaceprotectionrtm.config.flags;

import fun.bm.interfaceprotectionrtm.enums.EnumConfigCategory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigClassInfo {
    EnumConfigCategory category();

    String[] directory() default {};

    String comments() default "";
}
