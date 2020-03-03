package com.boss.demo.commons.annotations;

import java.lang.annotation.*;
import java.lang.annotation.RetentionPolicy;
/**
 * @Description:
 * @Target: 表示这个类可以作用在类/接口上，还可以用在方法上
 * @Retention: 表示这是一个运行时注解，即运行起来后才获取注解中的相关信息，而不像基本注解如@Override
 * @Inherited: 表示这个注解可以被子类继承
 * @Documented: 表示执行javadoc的时候，本注解会自动生成相关文档
 **/

@Target({ ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LoggerOperator {
    String description() default "";
}
