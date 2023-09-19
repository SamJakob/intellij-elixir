@file:Suppress("UnstableApiUsage")

package org.elixir_lang.lsp

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor
import com.intellij.platform.lsp.api.customization.LspCodeActionsSupport
import com.intellij.platform.lsp.api.customization.LspCompletionSupport
import com.intellij.platform.lsp.api.customization.LspDiagnosticsSupport
import com.intellij.util.io.BaseOutputReader
import org.eclipse.lsp4j.ClientCapabilities
import org.eclipse.lsp4j.DidChangeWatchedFilesCapabilities
import org.eclipse.lsp4j.SynchronizationCapabilities
import org.eclipse.lsp4j.TextDocumentClientCapabilities
import org.eclipse.lsp4j.WorkspaceClientCapabilities
import org.elixir_lang.ElixirFileType
import org.elixir_lang.ElixirScriptFileType
import java.io.File

class ElixirLspServerDescriptor(project: Project) : ProjectWideLspServerDescriptor(project, "Elixir") {

    companion object {
        val launcher = File(GeneralCommandLine().parentEnvironment["ELIXIR_LS_DIRECTORY"], "launch.sh")
        val isValid = launcher.exists()

        fun isSupportedFile(file: VirtualFile): Boolean =
            file.fileType == ElixirFileType.INSTANCE || file.fileType == ElixirScriptFileType.INSTANCE
    }

    override fun isSupportedFile(file: VirtualFile): Boolean =
        ElixirLspServerDescriptor.isSupportedFile(file)

    override fun createCommandLine(): GeneralCommandLine =
        GeneralCommandLine(launcher.absolutePath)
            .withWorkDirectory(project.basePath)
            .withEnvironment("ELS_MODE", "language_server")

    override fun startServerProcess(): OSProcessHandler {
        val startingCommandLine = createCommandLine()
        LOG.info("$this: starting LSP server: $startingCommandLine")
        return ElixirLspOSProcessHandler(startingCommandLine)
    }

    override val lspCompletionSupport: LspCompletionSupport
        get() = ElixirLspCompletionSupport()

    override val lspDiagnosticsSupport: LspDiagnosticsSupport
        get() = ElixirLspServerDiagnosticsSupport()

    // elixir-ls does not currently support Code Actions.
    override val lspCodeActionsSupport: LspCodeActionsSupport?
        get() = null

    override val clientCapabilities: ClientCapabilities
        get() = ClientCapabilities().apply {
            WorkspaceClientCapabilities().apply {
                DidChangeWatchedFilesCapabilities().apply {
                    dynamicRegistration = true
                }
            }
            TextDocumentClientCapabilities().apply {
                SynchronizationCapabilities().apply {
                    didSave = true
                    dynamicRegistration = true
                }
            }
        }

}

class ElixirLspOSProcessHandler(startingCommandLine: GeneralCommandLine) : OSProcessHandler(startingCommandLine) {
    override fun readerOptions(): BaseOutputReader.Options = BaseOutputReader.Options.forMostlySilentProcess()
}
