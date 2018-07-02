package solution1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Sol1ParserTest {

    private Sol1Parser parser;

    @BeforeEach
    private void setup() {
        parser = new Sol1Parser();
    }

    @Test
    public void testLTL() {
        // Simple expressions
        assertTrue(accepts("[] true"));
        assertTrue(accepts("<> true"));
        assertTrue(accepts("X true"));
        assertTrue(accepts("true U true"));
        assertTrue(accepts("true W true"));
        assertTrue(accepts("true R true"));

        // Nesting
        assertTrue(accepts("[] true U true"));
        assertTrue(accepts("[] X true"));
        assertTrue(accepts("[] <> true U true"));
        assertTrue(accepts("true W true U true"));
        assertTrue(accepts("true W true U true R [] true"));
        assertTrue(accepts("true R [] true U true"));

        // Contains predicates
        assertTrue(accepts("[] 1==2+3"));
        assertTrue(accepts("<> true && false"));
        assertTrue(accepts("X (VAR || false) && OTHER_VAR"));
        assertTrue(accepts("VAR < 3 U VAR > 3"));
        assertTrue(accepts("X (VAR || false) && OTHER_VAR && TEST != 999"));
        assertTrue(accepts("1-3 < 5+6 W maybe"));
        assertTrue(accepts("4 != 5 R VAR"));
    }

    @Test
    public void testCTL() {
        // Simple expressions
        assertTrue(accepts("E true"));
        assertTrue(accepts("A true"));
        assertTrue(accepts("[] true"));
        assertTrue(accepts("<> true"));
        assertTrue(accepts("X true"));
        assertTrue(accepts("true U true"));

        // Nesting
        assertTrue(accepts("E E true"));
        assertTrue(accepts("A E A true"));
        assertTrue(accepts("[] A <> true"));
        assertTrue(accepts("<> true U true"));
        assertTrue(accepts("X <> true U true U true"));
        assertTrue(accepts("E true U E E true"));

        // Contains predicates
        assertTrue(accepts("E 1==2+3"));
        assertTrue(accepts("A true && false"));
        assertTrue(accepts("[] (VAR || false) && OTHER_VAR"));
        assertTrue(accepts("<> 1-3 < 5+6 || maybe"));
        assertTrue(accepts("X (VAR || false) && OTHER_VAR && TEST != 999"));
        assertTrue(accepts("4-5<4 U maybe U 1-5+4*3/2==VAR"));
    }

    @Test
    public void testMuCalc() {
        // Simple expressions
        assertTrue(accepts("mu VAR . true"));
        assertTrue(accepts("nu VAR . true"));
        assertTrue(accepts("[VAR] true"));
        assertTrue(accepts("<VAR> true"));
        assertTrue(accepts("X true"));
        assertTrue(accepts("E true"));
        assertTrue(accepts("A true"));

        // Nesting
        assertTrue(accepts("mu VAR . nu OTHER_VAR . X VAR"));
        assertTrue(accepts("nu VAR . [VAR] true"));
        assertTrue(accepts("[VAR] mu VAR . X true"));
        assertTrue(accepts("<VAR> nu VAR . E X A true"));
        assertTrue(accepts("X <VAR> X true"));
        assertTrue(accepts("E A A A E X A [VAR] mu VAR2 . true"));
        assertTrue(accepts("A nu VAR1 . mu VAR2 . VAR1"));

        // Contains predicates
        assertTrue(accepts("mu VAR . 1+3<5"));
        assertTrue(accepts("nu VAR . true && false || maybe"));
        assertTrue(accepts("[VAR] false"));
        assertTrue(accepts("<VAR> maybe"));
        assertTrue(accepts("X 1+3-5==VAR || VAR2 <= VAR3"));
        assertTrue(accepts("E E E A X A 1+2-3*4/5>=VAR"));
        assertTrue(accepts("A maybe || (true && false)"));

    }

    @Test
    public void testPred() {
        // Simple expressions
        assertTrue(accepts("VAR"));
        assertTrue(accepts("123456789"));
        assertTrue(accepts("0"));
        assertTrue(accepts("true"));
        assertTrue(accepts("false"));
        assertTrue(accepts("maybe"));
        assertTrue(accepts("\"This is a string\""));
        assertTrue(accepts("#0123456789ABCDEF#"));
        assertTrue(accepts("(1)"));
        assertTrue(accepts("1*1"));
        assertTrue(accepts("1/1"));
        assertTrue(accepts("1%1"));
        assertTrue(accepts("1+1"));
        assertTrue(accepts("1-1"));
        assertTrue(accepts("1<1"));
        assertTrue(accepts("1<=1"));
        assertTrue(accepts("1>1"));
        assertTrue(accepts("1>=1"));
        assertTrue(accepts("1??1"));
        assertTrue(accepts("1==1"));
        assertTrue(accepts("1!=1"));
        assertTrue(accepts("true&&true"));
        assertTrue(accepts("true||true"));
        assertTrue(accepts("!true"));
        assertTrue(accepts("true <-> true"));
        assertTrue(accepts("true -> true"));

        // Nesting is already tested in the other test methods
    }

    @Test
    public void testCombinations() {
        // Temporal logics with eachother
        assertTrue(accepts("mu VAR . [] true U true"));
        assertTrue(accepts("nu VAR . true W A true"));
        assertTrue(accepts("[] true W true R [VAR] true"));
        assertTrue(accepts("E A [] mu VAR . [VAR2] true"));
        assertTrue(accepts("[] A <> E true"));
        assertTrue(accepts("[] A <> E true W true U true"));
        assertTrue(accepts("E true W mu VAR . true"));
        assertTrue(accepts("X true W [VAR] true"));

        // Temporal logics with predicate expressions
        assertTrue(accepts("1 + E true"));
        assertTrue(accepts("E true < X true"));
        assertTrue(accepts("mu VAR . E 1 == nu VAR . 25"));
        assertTrue(accepts("[] true >= <> true"));
        assertTrue(accepts("A true / [VAR] true"));
        assertTrue(accepts("<VAR> true % X 4 * 8"));
        assertTrue(accepts("X 8 + A 5 - 3 > E true"));

        // Predicate expressions with eachother
        assertTrue(accepts("1 && true"));
        assertTrue(accepts("true - 5 * 3 || 2"));
        assertTrue(accepts("false / maybe > \"String\""));
        assertTrue(accepts("\"String\" + 3"));
        assertTrue(accepts("#1f2a2c# / 4"));
        assertTrue(accepts("true - false"));
        assertTrue(accepts("3 -> 4"));
    }

    private boolean accepts(String string) {
        parser.parse(string);
        return parser.getErrors().size() == 0;
    }

}