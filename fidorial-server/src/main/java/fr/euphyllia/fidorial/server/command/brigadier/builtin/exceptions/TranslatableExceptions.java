package fr.euphyllia.fidorial.server.command.brigadier.builtin.exceptions;

import com.mojang.brigadier.exceptions.BuiltInExceptionProvider;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import fr.euphyllia.fidorial.server.command.brigadier.argument.util.ExceptionFactory;

public final class TranslatableExceptions implements BuiltInExceptionProvider {

    private static final Dynamic2CommandExceptionType DOUBLE_TOO_SMALL = ExceptionFactory.dynamic2Reversed("argument.double.low");
    private static final Dynamic2CommandExceptionType DOUBLE_TOO_BIG = ExceptionFactory.dynamic2Reversed("argument.double.big");
    private static final Dynamic2CommandExceptionType FLOAT_TOO_SMALL = ExceptionFactory.dynamic2Reversed("argument.float.low");
    private static final Dynamic2CommandExceptionType FLOAT_TOO_BIG = ExceptionFactory.dynamic2Reversed("argument.float.big");
    private static final Dynamic2CommandExceptionType INTEGER_TOO_SMALL = ExceptionFactory.dynamic2Reversed("argument.integer.low");
    private static final Dynamic2CommandExceptionType INTEGER_TOO_BIG = ExceptionFactory.dynamic2Reversed("argument.integer.big");
    private static final Dynamic2CommandExceptionType LONG_TOO_SMALL = ExceptionFactory.dynamic2Reversed("argument.long.low");
    private static final Dynamic2CommandExceptionType LONG_TOO_BIG = ExceptionFactory.dynamic2Reversed("argument.long.big");

    private static final DynamicCommandExceptionType LITERAL_INCORRECT = ExceptionFactory.dynamic("argument.literal.incorrect");

    private static final SimpleCommandExceptionType READER_EXPECTED_START_OF_QUOTE = ExceptionFactory.simple("parsing.quote.expected.start");
    private static final SimpleCommandExceptionType READER_EXPECTED_END_OF_QUOTE = ExceptionFactory.simple("parsing.quote.expected.end");
    private static final DynamicCommandExceptionType READER_INVALID_ESCAPE = ExceptionFactory.dynamic("parsing.quote.escape");
    private static final DynamicCommandExceptionType READER_INVALID_BOOL = ExceptionFactory.dynamic("parsing.bool.invalid");
    private static final DynamicCommandExceptionType READER_INVALID_INT = ExceptionFactory.dynamic("parsing.int.invalid");
    private static final SimpleCommandExceptionType READER_EXPECTED_INT = ExceptionFactory.simple("parsing.int.expected");
    private static final DynamicCommandExceptionType READER_INVALID_LONG = ExceptionFactory.dynamic("parsing.long.invalid");
    private static final SimpleCommandExceptionType READER_EXPECTED_LONG = ExceptionFactory.simple("parsing.long.expected");
    private static final DynamicCommandExceptionType READER_INVALID_DOUBLE = ExceptionFactory.dynamic("parsing.double.invalid");
    private static final SimpleCommandExceptionType READER_EXPECTED_DOUBLE = ExceptionFactory.simple("parsing.double.expected");
    private static final DynamicCommandExceptionType READER_INVALID_FLOAT = ExceptionFactory.dynamic("parsing.float.invalid");
    private static final SimpleCommandExceptionType READER_EXPECTED_FLOAT = ExceptionFactory.simple("parsing.float.expected");
    private static final SimpleCommandExceptionType READER_EXPECTED_BOOL = ExceptionFactory.simple("parsing.bool.expected");
    private static final DynamicCommandExceptionType READER_EXPECTED_SYMBOL = ExceptionFactory.dynamic("parsing.expected");

    public static final SimpleCommandExceptionType DISPATCHER_UNKNOWN_COMMAND = ExceptionFactory.simple("command.unknown.command");
    public static final SimpleCommandExceptionType DISPATCHER_UNKNOWN_ARGUMENT = ExceptionFactory.simple("command.unknown.argument");
    private static final SimpleCommandExceptionType DISPATCHER_EXPECTED_ARGUMENT_SEPARATOR = ExceptionFactory.simple("command.expected.separator");
    private static final DynamicCommandExceptionType DISPATCHER_PARSE_EXCEPTION = ExceptionFactory.dynamic("command.exception");

    @Override
    public Dynamic2CommandExceptionType doubleTooLow() {
        return DOUBLE_TOO_SMALL;
    }

    @Override
    public Dynamic2CommandExceptionType doubleTooHigh() {
        return DOUBLE_TOO_BIG;
    }

    @Override
    public Dynamic2CommandExceptionType floatTooLow() {
        return FLOAT_TOO_SMALL;
    }

    @Override
    public Dynamic2CommandExceptionType floatTooHigh() {
        return FLOAT_TOO_BIG;
    }

    @Override
    public Dynamic2CommandExceptionType integerTooLow() {
        return INTEGER_TOO_SMALL;
    }

    @Override
    public Dynamic2CommandExceptionType integerTooHigh() {
        return INTEGER_TOO_BIG;
    }

    @Override
    public Dynamic2CommandExceptionType longTooLow() {
        return LONG_TOO_SMALL;
    }

    @Override
    public Dynamic2CommandExceptionType longTooHigh() {
        return LONG_TOO_BIG;
    }

    @Override
    public DynamicCommandExceptionType literalIncorrect() {
        return LITERAL_INCORRECT;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedStartOfQuote() {
        return READER_EXPECTED_START_OF_QUOTE;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedEndOfQuote() {
        return READER_EXPECTED_END_OF_QUOTE;
    }

    @Override
    public DynamicCommandExceptionType readerInvalidEscape() {
        return READER_INVALID_ESCAPE;
    }

    @Override
    public DynamicCommandExceptionType readerInvalidBool() {
        return READER_INVALID_BOOL;
    }

    @Override
    public DynamicCommandExceptionType readerInvalidInt() {
        return READER_INVALID_INT;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedInt() {
        return READER_EXPECTED_INT;
    }

    @Override
    public DynamicCommandExceptionType readerInvalidLong() {
        return READER_INVALID_LONG;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedLong() {
        return READER_EXPECTED_LONG;
    }

    @Override
    public DynamicCommandExceptionType readerInvalidDouble() {
        return READER_INVALID_DOUBLE;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedDouble() {
        return READER_EXPECTED_DOUBLE;
    }

    @Override
    public DynamicCommandExceptionType readerInvalidFloat() {
        return READER_INVALID_FLOAT;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedFloat() {
        return READER_EXPECTED_FLOAT;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedBool() {
        return READER_EXPECTED_BOOL;
    }

    @Override
    public DynamicCommandExceptionType readerExpectedSymbol() {
        return READER_EXPECTED_SYMBOL;
    }

    @Override
    public SimpleCommandExceptionType dispatcherUnknownCommand() {
        return DISPATCHER_UNKNOWN_COMMAND;
    }

    @Override
    public SimpleCommandExceptionType dispatcherUnknownArgument() {
        return DISPATCHER_UNKNOWN_ARGUMENT;
    }

    @Override
    public SimpleCommandExceptionType dispatcherExpectedArgumentSeparator() {
        return DISPATCHER_EXPECTED_ARGUMENT_SEPARATOR;
    }

    @Override
    public DynamicCommandExceptionType dispatcherParseException() {
        return DISPATCHER_PARSE_EXCEPTION;
    }
}
