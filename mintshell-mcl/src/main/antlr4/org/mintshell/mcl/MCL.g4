grammar MCL;

commandLine: command (pipedCommand)*;

pipedCommand: SPACE+ PIPE SPACE+ command;

command: commandName (SPACE+ commandParameter)*;

commandName: CHARACTER | UNQUOTED | QUOTED;

commandParameter: ((shortCommandParameter | longCommandParameter) (SPACE+ commandParameterValue)?) | commandParameterValue;

commandParameterValue: CHARACTER | UNQUOTED | QUOTED;

shortCommandParameter: SHORT_COMMAND_PARAMETER_PREFIX shortCommandParameterName;

shortCommandParameterName: CHARACTER;

longCommandParameter: LONG_COMMAND_PARAMETER_PREFIX longCommandParameterName;

longCommandParameterName: CHARACTER | UNQUOTED;

PIPE: '|';

SHORT_COMMAND_PARAMETER_PREFIX: '-';

LONG_COMMAND_PARAMETER_PREFIX: '--';

CHARACTER: LETTER_OR_DIGIT;

UNQUOTED: LETTER_OR_DIGIT+;

QUOTED: '"' (~["\\\r\n] | ESC_SEQUENCE)* '"';

SPACE: [ \t];

fragment ESC_SEQUENCE : '\\' [btnfr"'\\];

fragment LETTER_OR_DIGIT: LETTER | [0-9];

fragment LETTER: [a-zA-Z$_];






