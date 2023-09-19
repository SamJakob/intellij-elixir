package org.elixir_lang.elixir_flex_lexer.sigil_modifiers.group;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.elixir_lang.ElixirFlexLexer;
import org.elixir_lang.elixir_flex_lexer.TokenTest;
import org.elixir_lang.psi.ElixirTypes;
import org.jetbrains.annotations.NotNull;
import org.junit.Ignore;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by luke.imhoff on 10/23/14.
 */
@Ignore("abstract")
public abstract class Test extends TokenTest {
    /*
     * Constructors
     */

    public Test(CharSequence charSequence, IElementType tokenType, int lexicalState) {
        super(charSequence, tokenType, lexicalState);
    }

    /*
     * Methods
     */

    @Parameterized.Parameters(
            name = "{index} \"{0}\" parses as {1} token and advances to state {2}"
    )
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][]{
                        { " ", TokenType.WHITE_SPACE, ElixirFlexLexer.ADDITION_OR_SUBTRACTION_MAYBE },
                        { ";", ElixirTypes.SEMICOLON, ElixirFlexLexer.MULTILINE_WHITE_SPACE_MAYBE },
                        { "A", ElixirTypes.SIGIL_MODIFIER, ElixirFlexLexer.SIGIL_MODIFIERS },
                        { "\n", ElixirTypes.EOL, ElixirFlexLexer.YYINITIAL },
                        { "\r\n", ElixirTypes.EOL, ElixirFlexLexer.YYINITIAL },
                        { "a", ElixirTypes.SIGIL_MODIFIER, ElixirFlexLexer.SIGIL_MODIFIERS }
                }
        );
    }

    protected abstract char promoter();
    protected abstract char terminator();

    @Override
    protected void start(@NotNull CharSequence charSequence) {
        // start to trigger SIGIL_MODIFIERS after GROUP
        CharSequence fullCharSequence = "~r" + promoter() + terminator() + charSequence;
        super.start(fullCharSequence);

        // consume ~
        assertEquals("~", lexer.getTokenText());
        lexer.advance();

        // consume r
        assertEquals("r", lexer.getTokenText());
        lexer.advance();

        // consume promoter
        assertEquals(String.valueOf(promoter()), lexer.getTokenText());
        lexer.advance();

        // consume terminator
        assertEquals(String.valueOf(terminator()), lexer.getTokenText());
        lexer.advance();
    }
}
