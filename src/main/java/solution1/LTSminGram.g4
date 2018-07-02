grammar LTSminGram;

main_expr : PRED_EXIST main_expr
          | PRED_ALL main_expr
          | PRED_GLOBALLY main_expr
          | PRED_FUTURE main_expr
          | PRED_NEXT main_expr
          | main_expr PRED_UNTIL main_expr
          | main_expr LTL_WEAK_UNTIL main_expr
          | main_expr LTL_RELEASE main_expr
          | MUCALC_MU PRED_VAR MUCALC_DOT main_expr
          | MUCALC_NU PRED_VAR MUCALC_DOT main_expr
          | MUCALC_EDGE_EXIST_LEFT PRED_VAR MUCALC_EDGE_EXIST_RIGHT main_expr
          | MUCALC_EDGE_ALL_LEFT PRED_VAR MUCALC_EDGE_ALL_RIGHT main_expr
          | PRED_VAR
          | PRED_NUMBER
          | PRED_TRUE
          | PRED_FALSE
          | PRED_MAYBE
          | PRED_STRING
          | PRED_CHUNK
          | PRED_LPAR main_expr PRED_RPAR
          | main_expr PRED_MULT main_expr
          | main_expr PRED_DIV main_expr
          | main_expr PRED_REM main_expr
          | main_expr PRED_ADD main_expr
          | main_expr PRED_SUB main_expr
          | main_expr PRED_LT main_expr
          | main_expr PRED_LEQ main_expr
          | main_expr PRED_GT main_expr
          | main_expr PRED_GEQ main_expr
          | main_expr PRED_EN main_expr
          | main_expr PRED_EQ main_expr
          | main_expr PRED_NEQ main_expr
          | main_expr PRED_AND main_expr
          | main_expr PRED_OR main_expr
          | PRED_NOT main_expr
          | main_expr PRED_EQUIV main_expr
          | main_expr PRED_IMPLY main_expr
          ;


LTL_WEAK_UNTIL : 'W';
LTL_RELEASE : 'R';

MUCALC_MU : 'mu';
MUCALC_NU : 'nu';
MUCALC_EDGE_EXIST_LEFT : '<';
MUCALC_EDGE_EXIST_RIGHT : '>';
MUCALC_EDGE_ALL_LEFT : '[';
MUCALC_EDGE_ALL_RIGHT : ']';
MUCALC_DOT : '.';

PRED_STRING : '"' (~ [\\"] | '\\' .) * '"';
PRED_CHUNK : '#' ([0-9a-fA-F] [0-9a-fA-F]) * '#';
PRED_NUMBER : [0-9] +;
PRED_TRUE : 'true';
PRED_FALSE : 'false';
PRED_MAYBE : 'maybe';
PRED_VAR : '_' * ([a-zA-Z] | '\\' .) ([a-zA-Z0-9_'] | '\\' .) *;
PRED_LPAR : '(';
PRED_RPAR : ')';
PRED_MULT : '*';
PRED_DIV : '/';
PRED_REM : '%';
PRED_ADD : '+';
PRED_SUB : '-';
PRED_LT : '<';
PRED_LEQ : '<=';
PRED_GT : '>';
PRED_GEQ : '>=';
PRED_EQ : '==';
PRED_NEQ : '!=';
PRED_EN : '??';
PRED_NOT : '!';
PRED_AND : '&&';
PRED_OR : '||';
PRED_EQUIV : '<->';
PRED_IMPLY : '->';
PRED_GLOBALLY : '[]';
PRED_FUTURE : '<>';
PRED_NEXT : 'X';
PRED_EXIST : 'E';
PRED_ALL : 'A';
PRED_UNTIL : 'U';

WS : [ \t\n\r]+ -> skip; // This rule is added to support whitespaces
