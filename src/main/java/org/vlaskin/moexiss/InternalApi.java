package org.vlaskin.moexiss;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Помечает технический API SDK, который не входит в гарантии совместимости публичных релизов.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.PACKAGE})
public @interface InternalApi
{
}
