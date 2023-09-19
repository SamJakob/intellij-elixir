package org.elixir_lang.mix

import org.elixir_lang.package_manager.DepGatherer

class PackageManagerImpl : org.elixir_lang.PackageManager {
    override val fileName: String = PackageManager.FILE_NAME
    override fun depGatherer(): DepGatherer = PackageManager.depGatherer()
}
