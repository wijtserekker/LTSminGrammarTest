package solution1;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;

public class Sol1Parser extends BaseErrorListener {

    private ArrayList<String> errors = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        errors.add(msg);
    }

    public ParseTree parse(String formula) {
        errors = new ArrayList<>();
        CharStream charStream = CharStreams.fromString(formula);
        Lexer lexer = new LTSminGramLexer(charStream);

        lexer.removeErrorListeners();
        lexer.addErrorListener(this);

        TokenStream tokens = new CommonTokenStream(lexer);
        LTSminGramParser parser = new LTSminGramParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(this);

        return parser.main_expr();
    }

    public ArrayList<String> getErrors() {
        return errors;
    }
}
