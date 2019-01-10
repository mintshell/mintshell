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

CHARACTER: SIGN;

UNQUOTED: SIGN+ (SIGN | '-')*;

QUOTED: '"' (~["\\\r\n] | ESC_SEQUENCE)* '"';

SPACE: [ \t];

fragment ESC_SEQUENCE : '\\' [btnfr"'\\];

fragment SIGN: [\u0021\u0023-\u0026\u0028-\u002C\u002E-\u007E\u00A1-\u00FF];







