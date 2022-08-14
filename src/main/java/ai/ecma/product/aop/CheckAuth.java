package ai.ecma.product.aop;

import ai.ecma.lib.enums.PermissionEnum;
import ai.ecma.lib.enums.RoleTypeEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface CheckAuth {
    PermissionEnum[] permissions() default {};

    RoleTypeEnum[] roles() default {};
}
