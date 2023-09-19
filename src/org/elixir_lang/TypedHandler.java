package org.elixir_lang;

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.elixir_lang.psi.ElixirFile;
import org.elixir_lang.psi.ElixirTypes;
import org.jetbrains.annotations.NotNull;

import static org.elixir_lang.psi.ElixirTypes.LINE_PROMOTER;

public class TypedHandler extends TypedHandlerDelegate {
    /*
     * Instance Methods
     */

    /**
     * Called after the specified character typed by the user has been inserted in the editor.
     *
     * @param charTyped the character that was typed
     * @param project the project in which the {@code file} exists
     * @param editor the editor that has the {@code file} open
     * @param file the file into which the {@code charTyped} was typed
     */
    @Override
    public @NotNull Result charTyped(char charTyped, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        Result result = Result.CONTINUE;

        if (file instanceof ElixirFile) {
            if (charTyped == ' ') {
                int caret = editor.getCaretModel().getOffset();

                if (caret > 2) { // "(do|fn)<space><caret>"
                    final EditorHighlighter highlighter = ((EditorEx)editor).getHighlighter();
                    HighlighterIterator iterator = highlighter.createIterator(caret - 2);
                    IElementType tokenType = iterator.getTokenType();

                    if (tokenType == ElixirTypes.DO || tokenType == ElixirTypes.FN) {
                        editor.getDocument().insertString(caret, " end");
                        result = Result.STOP;
                    }
                }
            } else if (charTyped == '<') {
                int caret = editor.getCaretModel().getOffset();

                if (caret > 2) { // "~<sigil_name><"
                    final EditorHighlighter highlighter = ((EditorEx)editor).getHighlighter();
                    HighlighterIterator iterator = highlighter.createIterator(caret - 1);
                    IElementType tokenType = iterator.getTokenType();

                    if (tokenType == LINE_PROMOTER) {
                        editor.getDocument().insertString(caret, ">");
                        result = Result.STOP;
                    }
                }

                if (result == Result.CONTINUE && caret > 1) { // "<<"
                    final EditorHighlighter highlighter = ((EditorEx)editor).getHighlighter();
                    HighlighterIterator iterator = highlighter.createIterator(caret - 2);

                    if (iterator.getTokenType() == ElixirTypes.OPENING_BIT) {
                        editor.getDocument().insertString(caret, ">>");
                        result = Result.STOP;
                    }
                }
            } else if (charTyped == '/' || charTyped == '|') {
                int caret = editor.getCaretModel().getOffset();

                if (caret > 2) { // "~<sigil_name>(/|\|)"
                    final EditorHighlighter highlighter = ((EditorEx)editor).getHighlighter();
                    HighlighterIterator iterator = highlighter.createIterator(caret - 1);
                    IElementType tokenType = iterator.getTokenType();

                    if (tokenType == LINE_PROMOTER) {
                        editor.getDocument().insertString(caret, String.valueOf(charTyped));
                        result = Result.STOP;
                    }
                }
            } else if (charTyped == '{') { // #{
                int caret = editor.getCaretModel().getOffset();

                if (caret > 1) {
                    final EditorHighlighter highlighter = ((EditorEx) editor).getHighlighter();
                    HighlighterIterator iterator = highlighter.createIterator(caret - 1);
                    IElementType tokenType = iterator.getTokenType();

                    if (tokenType == ElixirTypes.INTERPOLATION_START) {
                        editor.getDocument().insertString(caret, "}");
                        result = Result.STOP;
                    }
                }
            }
        }

        return result;
    }
}
