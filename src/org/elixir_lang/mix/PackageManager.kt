package org.elixir_lang.mix

import org.elixir_lang.package_manager.DepGatherer

object PackageManager {
    const val FILE_NAME: String = "mix.exs"
    fun depGatherer(): DepGatherer = org.elixir_lang.mix.DepGatherer()
}
