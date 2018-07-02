package solution2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Sol2ParserTest {

    private Sol2Parser parser;

    @BeforeEach
    private void setup() {
        parser = new Sol2Parser();
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
        assertTrue(accepts("X VAR==4 || false && OTHER_VAR==\"string\""));
        assertTrue(accepts("VAR < 3 U VAR > 3"));
        assertTrue(accepts("X (VAR==\"string\" || false) && OTHER_VAR < 4 && TEST != 999"));
        assertTrue(accepts("1-3 < 5+6 W maybe"));
        assertTrue(accepts("4 != 5 R VAR != 9"));
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
        assertTrue(accepts("[] (VAR==0 || false) && OTHER_VAR<9"));
        assertTrue(accepts("<> 1-3 < 5+6 || maybe"));
        assertTrue(accepts("X (VAR!=5 || false) && OTHER_VAR>=10 && TEST != 999"));
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
        assertTrue(accepts("mu VAR . nu OTHER_VAR . X 9<VAR"));
        assertTrue(accepts("nu VAR . [VAR] true"));
        assertTrue(accepts("[VAR] mu VAR . X true"));
        assertTrue(accepts("<VAR> nu VAR . E X A true"));
        assertTrue(accepts("X <VAR> X true"));
        assertTrue(accepts("E A A A E X A [VAR] mu VAR2 . true"));
        assertTrue(accepts("A nu VAR1 . mu VAR2 . VAR1==#1b2b3b4c#"));

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
        assertTrue(accepts("VAR==1"));
        assertTrue(accepts("123456789==1"));
        assertTrue(accepts("0==1"));
        assertTrue(accepts("true"));
        assertTrue(accepts("false"));
        assertTrue(accepts("maybe"));
        assertTrue(accepts("\"This is a string\"==VAR"));
        assertTrue(accepts("#0123456789ABCDEF#==VAR"));
        assertTrue(accepts("(1)==3"));
        assertTrue(accepts("1*1<1"));
        assertTrue(accepts("1/1<1"));
        assertTrue(accepts("1%1<1"));
        assertTrue(accepts("1+1<1"));
        assertTrue(accepts("1-1<1"));
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
        assertFalse(accepts("mu VAR . [] true U true"));
        assertFalse(accepts("nu VAR . true W A true"));
        assertFalse(accepts("[] true W true R [VAR] true"));
        assertFalse(accepts("E A [] mu VAR . [VAR2] true"));
        assertFalse(accepts("[] A <> E true W true U true"));
        assertFalse(accepts("E true W mu VAR . true"));
        assertFalse(accepts("X true W [VAR] true"));

        // Temporal logics with predicate expressions
        assertFalse(accepts("1 + E true"));
        assertFalse(accepts("E true < X true"));
        assertFalse(accepts("mu VAR . E 1 == nu VAR . 25"));
        assertFalse(accepts("[] true >= <> true"));
        assertFalse(accepts("A true / [VAR] true"));
        assertFalse(accepts("<VAR> true % X 4 * 8"));
        assertFalse(accepts("X 8 + A 5 - 3 > E true"));

        // Predicate expressions with eachother
        assertFalse(accepts("1 && true"));
        assertFalse(accepts("true - 5 * 3 || 2"));
        assertFalse(accepts("false / maybe > \"String\""));
        assertFalse(accepts("\"String\" + 3"));
        assertFalse(accepts("#1f2a2c# / 4"));
        assertFalse(accepts("true - false"));
        assertFalse(accepts("3 -> 4"));
    }

    private boolean accepts(String string) {
        parser.parse(string);
        return parser.getErrors().size() == 0;
    }
}