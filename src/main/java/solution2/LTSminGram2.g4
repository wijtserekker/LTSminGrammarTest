grammar LTSminGram2;

main_expr : ltl_expr EOF // EOF added to make sure it parses the full string
          | ctl_expr EOF
          | mucalc_expr EOF
          ;

ctl_expr : PRED_EXIST ctl_expr
         | PRED_ALL ctl_expr
         | PRED_GLOBALLY ctl_expr
         | PRED_FUTURE ctl_expr
         | PRED_NEXT ctl_expr
         | ctl_expr PRED_UNTIL ctl_expr
         | pred_comp_expr
         | ctl_expr BOOL_AND ctl_expr
         | ctl_expr BOOL_OR ctl_expr
         | BOOL_NOT ctl_expr
         | BOOL_TRUE
         | BOOL_FALSE
         | BOOL_MAYBE
         | BOOL_LPAR ctl_expr BOOL_RPAR
         | ctl_expr BOOL_EQUIV ctl_expr
         | ctl_expr BOOL_IMPLY ctl_expr
         ;

ltl_expr : PRED_GLOBALLY ltl_expr
         | PRED_FUTURE ltl_expr
         | PRED_NEXT ltl_expr
         | ltl_expr PRED_UNTIL ltl_expr
         | ltl_expr LTL_WEAK_UNTIL ltl_expr
         | ltl_expr LTL_RELEASE ltl_expr
         | pred_comp_expr
         | ltl_expr BOOL_AND ltl_expr
         | ltl_expr BOOL_OR ltl_expr
         | BOOL_NOT ltl_expr
         | BOOL_TRUE
         | BOOL_FALSE
         | BOOL_MAYBE
         | BOOL_LPAR ltl_expr BOOL_RPAR
         | ltl_expr BOOL_EQUIV ltl_expr
         | ltl_expr BOOL_IMPLY ltl_expr
         ;
LTL_WEAK_UNTIL : 'W';
LTL_RELEASE : 'R';

mucalc_expr : MUCALC_MU PRED_VAR MUCALC_DOT mucalc_expr
            | MUCALC_NU PRED_VAR MUCALC_DOT mucalc_expr
            | PRED_LT PRED_VAR PRED_GT mucalc_expr
            | MUCALC_EDGE_ALL_LEFT PRED_VAR MUCALC_EDGE_ALL_RIGHT mucalc_expr
            | PRED_NEXT mucalc_expr
            | PRED_EXIST mucalc_expr
            | PRED_ALL mucalc_expr
            | pred_comp_expr
            | mucalc_expr BOOL_AND mucalc_expr
            | mucalc_expr BOOL_OR mucalc_expr
            | BOOL_NOT mucalc_expr
            | BOOL_TRUE
            | BOOL_FALSE
            | BOOL_MAYBE
            | BOOL_LPAR mucalc_expr BOOL_RPAR
            ;
MUCALC_MU : 'mu';
MUCALC_NU : 'nu';
MUCALC_EDGE_ALL_LEFT : '[';
MUCALC_EDGE_ALL_RIGHT : ']';
MUCALC_DOT : '.';

pred_comp_expr : pred_calc_expr PRED_EQ pred_calc_expr
               | pred_calc_expr PRED_NEQ pred_calc_expr
               | pred_calc_expr PRED_LT pred_calc_expr
               | pred_calc_expr PRED_LEQ pred_calc_expr
               | pred_calc_expr PRED_GT pred_calc_expr
               | pred_calc_expr PRED_GEQ pred_calc_expr
               | pred_calc_expr PRED_EN pred_calc_expr
               ;
pred_calc_expr : PRED_VAR
               | PRED_NUMBER
               | PRED_STRING
               | PRED_CHUNK
               | BOOL_LPAR pred_calc_expr BOOL_RPAR
               | pred_calc_expr PRED_MULT pred_calc_expr
               | pred_calc_expr PRED_DIV pred_calc_expr
               | pred_calc_expr PRED_REM pred_calc_expr
               | pred_calc_expr PRED_ADD pred_calc_expr
               | pred_calc_expr PRED_SUB pred_calc_expr
               ;
PRED_STRING : '"' (~ [\\"] | '\\' .) * '"';
PRED_CHUNK : '#' ([0-9a-fA-F] [0-9a-fA-F]) * '#';
PRED_NUMBER : [0-9] +;
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
PRED_GLOBALLY : '[]';
PRED_FUTURE : '<>';
PRED_NEXT : 'X';
PRED_EXIST : 'E';
PRED_ALL : 'A';
PRED_UNTIL : 'U';

BOOL_LPAR : '(';
BOOL_RPAR : ')';
BOOL_TRUE : 'true';
BOOL_FALSE : 'false';
BOOL_MAYBE : 'maybe';
BOOL_NOT : '!';
BOOL_AND : '&&';
BOOL_OR : '||';
BOOL_EQUIV : '<->';
BOOL_IMPLY : '->';
PRED_VAR : '_' * ([a-zA-Z] | '\\' .) ([a-zA-Z0-9_'] | '\\' .) *; // This rule is moved to the bottom to ensure that the operaters are lexed first

WS : [ \t\n\r]+ -> skip; // This rule is added to support whitespaces
