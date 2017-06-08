package net.xalcon.technolib.extensions

inline fun <reified T: Annotation> Any.hasAnnotation(declaredOnly:Boolean = true): Boolean = this.getAnnotation<T>(declaredOnly) != null

inline fun <reified T: Annotation> Any.getAnnotation(declaredOnly:Boolean = true): T? = when
{
    declaredOnly -> this.javaClass.getDeclaredAnnotation(T::class.java)
    else         -> this.javaClass.getAnnotation(T::class.java)
}