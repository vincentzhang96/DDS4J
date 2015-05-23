package co.phoenixlab.dds;

import sun.misc.SharedSecrets;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.IntSupplier;
import java.util.function.Predicate;

/**
 * Internal util class.
 * Uses undocumented internal Sun APIs - may break on other platforms or in future Java releases.
 */
class InternalUtils {

    private enum Dummy {
        FOO,
        BAR;
    }

    private static boolean useSharedSecrets = true;

    static {
        try {
            Dummy[] dummies = SharedSecrets.getJavaLangAccess().getEnumConstantsShared(Dummy.class);
        } catch (Exception e) {
            useSharedSecrets = false;
        }
    }

    private InternalUtils() {
    }

    static <T extends Enum<T> & IntSupplier> Set<T> bitsToSet(int bits, Class<T> tClass) {
        EnumSet<T> ret = EnumSet.noneOf(tClass);
        //  Internal/Unsafe
        T[] enums;
        if (useSharedSecrets) {
            enums = SharedSecrets.getJavaLangAccess().getEnumConstantsShared(tClass);
        } else {
            enums = tClass.getEnumConstants();
        }
        tClass.getEnumConstants();
        for (T t : enums) {
            if ((bits & t.getAsInt()) != 0) {
                ret.add(t);
            }
        }
        return ret;
    }

    static <T extends Enum<T> & IntSupplier> Set<T> bitsToUnmodifiableSet(int bits, Class<T> tClass) {
        return Collections.unmodifiableSet(bitsToSet(bits, tClass));
    }

    static <T> void verifyThat(T t, Predicate<T> predicate, String message) throws InvalidDdsException {
        verifyThatNot(t, predicate.negate(), message);
    }
    static <T> void verifyThatNot(T t, Predicate<T> predicate, String message) throws InvalidDdsException {
        if (predicate.test(t)) {
            throw new InvalidDdsException(message);
        }
    }
}
