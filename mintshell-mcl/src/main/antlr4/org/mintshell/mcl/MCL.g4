grammar MCL;

commandLine: command (SPACE+ commandParameter)*;

command: QUOTED | UNQUOTED;

commandParameter: ((shortCommandParameter | longCommandParameter) (SPACE+ commandParameterValue)?) | commandParameterValue;

commandParameterValue: UNQUOTED | QUOTED;

shortCommandParameter: SHORT_COMMAND_PARAMETER_PREFIX shortCommandParameterName;

shortCommandParameterName: CHARACTER;

longCommandParameter: LONG_COMMAND_PARAMETER_PREFIX longCommandParameterName;

longCommandParameterName: UNQUOTED;

SHORT_COMMAND_PARAMETER_PREFIX: '-';

LONG_COMMAND_PARAMETER_PREFIX: '--';

CHARACTER: LETTER_OR_DIGIT;

UNQUOTED: LETTER_OR_DIGIT LETTER_OR_DIGIT+;

QUOTED: '"' (LETTER_OR_DIGIT | SYMBOL | SPACE)* '"'; 

SPACE: [ \t];

fragment LETTER_OR_DIGIT: [a-zA-Z0-9];

fragment SYMBOL: [-,\.];





