@file:Suppress("UnstableApiUsage")

package org.elixir_lang.lsp

import com.intellij.platform.lsp.api.customization.LspCompletionSupport
import org.eclipse.lsp4j.CompletionItem
import org.elixir_lang.Icons
import javax.swing.Icon

class ElixirLspCompletionSupport : LspCompletionSupport() {

    override fun getIcon(item: CompletionItem): Icon? {
        return when (item.detail) {
            "function" -> Icons.CALL_DEFINITION
            "module" -> Icons.MODULE_ITEM
            "macro" -> Icons.Time.RUN
            "alias" -> Icons.DELEGATION
            "struct" -> Icons.STRUCTURE
            "behaviour" -> Icons.BEHAVIOR
            "protocol" -> Icons.Protocol.Structure
            "exception" -> Icons.EXCEPTION
            else -> super.getIcon(item)
        }
    }

    override fun getTypeText(item: CompletionItem): String? {
        return item.labelDetails?.description
    }

}
