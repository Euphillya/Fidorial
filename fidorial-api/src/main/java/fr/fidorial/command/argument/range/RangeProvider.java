package fr.fidorial.command.argument.range;

import com.google.common.collect.Range;

/**
 * A provider for a range of numbers
 *
 * @param <T> the type of number
 * @since 0.1.0
 */
public sealed interface RangeProvider<T extends Comparable<?>> permits DoubleRangeProvider, IntegerRangeProvider {

    /**
     * Provides the given range.
     *
     * @return range
     * @since 0.1.0
     */
    Range<T> range();
}
