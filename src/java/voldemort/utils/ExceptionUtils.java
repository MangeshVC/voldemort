package voldemort.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Static utility functions to deal with exceptions.
 */
public class ExceptionUtils {
    /**
     * Inspects a given {@link Throwable} as well as its nested causes, in order to look
     * for a specific set of exception classes. The function also detects if the throwable
     * to inspect is a subclass of one of the classes you look for, but not the other way
     * around (i.e.: if you're looking for the subclass but the throwableToInspect is the
     * parent class, then this function returns false).
     *
     * @return true if a the throwableToInspect corresponds to or is caused by any of the throwableClassesToLookFor
     */
    public static boolean recursiveClassEquals(Throwable throwableToInspect, Class... throwableClassesToLookFor) {
        for (Class clazz: throwableClassesToLookFor) {
            Class classToInspect = throwableToInspect.getClass();
            while (classToInspect != null) {
                if (classToInspect.equals(clazz)) {
                    return true;
                }
                classToInspect = classToInspect.getSuperclass();
            }
        }
        Throwable cause = throwableToInspect.getCause();
        return cause != null && recursiveClassEquals(cause, throwableClassesToLookFor);
    }

    /**
     * @return a String representation of the provided throwable's stacktrace.
     */
    public static String stackTraceToString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString(); // stack trace as a string
    }
}
