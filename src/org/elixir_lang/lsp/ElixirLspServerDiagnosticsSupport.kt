@file:Suppress("UnstableApiUsage")

package org.elixir_lang.lsp

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.openapi.util.TextRange
import com.intellij.platform.lsp.api.customization.LspDiagnosticsSupport
import com.intellij.psi.PsiElement
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset
import org.eclipse.lsp4j.Diagnostic

class ElixirLspServerDiagnosticsSupport : LspDiagnosticsSupport() {
    override fun createAnnotation(
        holder: AnnotationHolder,
        diagnostic: Diagnostic,
        textRange: TextRange,
        quickFixes: List<IntentionAction>
    ) {
        super.createAnnotation(
            holder,
            diagnostic,
            traverseForToken(
                holder.currentAnnotationSession.file.children,
                TextRange(textRange.startOffset, textRange.endOffset)
            ),
            quickFixes
        )
    }

    /**
     * Elixir-LSP doesn't use tags (possibly because it inherits its messages from Dialyzer),
     * so our best approach seems to be to guess the highlight type using heuristics from the
     * message.
     */
    override fun getSpecialHighlightType(diagnostic: Diagnostic): ProblemHighlightType? {
        return when {
            diagnostic.message.matches(Regex("^variable \"(.*)\" is unused.*$")) ->
                ProblemHighlightType.LIKE_UNUSED_SYMBOL

            else -> super.getSpecialHighlightType(diagnostic)
        }
    }

    /**
     * The LSP for Elixir only returns the start character for the TextRange pertaining to a
     * diagnostic, so we trivially overcome this by traversing the list of [PsiElement]s
     * recursively until we optimize (minimize) the offset into an optimal range.
     */
    private fun traverseForToken(elements: Array<PsiElement>, range: TextRange): TextRange {
        for (it in elements) {
            if (it.startOffset <= range.startOffset - 1 && it.endOffset >= range.endOffset) {
                if (it.children.isNotEmpty()) {
                    return traverseForToken(it.children, range)
                }

                return TextRange(it.startOffset, it.endOffset)
            }
        }

        return range
    }
}
