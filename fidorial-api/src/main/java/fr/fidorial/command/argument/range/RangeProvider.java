package fr.fidorial.command.argument.range;

import com.google.common.collect.Range;

/**
 * A provider for a range of numbers
 *
 * @param <T> the type of number
 */
public sealed interface RangeProvider<T extends Comparable<?>> permits DoubleRangeProvider, IntegerRangeProvider {

    /**
     * Provides the given range.
     *
     * @return range
     */
    Range<T> range();
}
