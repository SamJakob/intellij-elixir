package org.elixir_lang

import com.intellij.lang.Language

object ElixirLanguage: Language("Elixir") {
    private fun readResolve(): Any = ElixirLanguage
    override fun isCaseSensitive(): Boolean = true
}
