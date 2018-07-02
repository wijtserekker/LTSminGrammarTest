package solution2;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;

public class Sol2Parser extends BaseErrorListener {

    private ArrayList<String> errors = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            errors.add(msg);
    }

    public ParseTree parse(String formula) {
        errors = new ArrayList<>();
        CharStream charStream = CharStreams.fromString(formula);
        Lexer lexer = new LTSminGram2Lexer(charStream);

        lexer.removeErrorListeners();
        lexer.addErrorListener(this);
//        System.out.println(lexer.getAllTokens().toString());
        TokenStream tokens = new CommonTokenStream(lexer);
        LTSminGram2Parser parser = new LTSminGram2Parser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(this);

        LTSminGram2Parser.Main_exprContext result = parser.main_expr();
        return result;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }
}
