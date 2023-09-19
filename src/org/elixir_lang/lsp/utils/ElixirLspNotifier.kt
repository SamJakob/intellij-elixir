@file:Suppress("UnstableApiUsage")

package org.elixir_lang.lsp.utils

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServerManager
import org.eclipse.lsp4j.*
import org.elixir_lang.lsp.ElixirLspServerSupportProvider
import java.nio.file.Paths

/**
 * A utility class that provides convenience methods for notifying the LSP server of file events.
 */
object ElixirLspNotifier {

    /**
     * Notifies the LSP server of file events (specifically, for the workspace service's DidChangeWatchedFiles).
     * This is for the LSP workspace/didChangeWatchedFiles notification.
     * e.g., file creation, modification, deletion, etc.
     *
     * Given the list of [events] for a [project], the project's LSP server will be notified of the events.
     *
     * @param project The project with the LSP server to notify.
     * @param events The list of file events to notify the LSP server of.
     */
    fun notifyFileEvents(project: Project, events: List<FileEvent>) {
        LspServerManager.getInstance(project).getServersForProvider(ElixirLspServerSupportProvider::class.java).forEach {
            it.lsp4jServer.workspaceService.didChangeWatchedFiles(DidChangeWatchedFilesParams().apply {
                changes = events
            })
        }
    }

    /**
     * Notifies the LSP server of a file open event (specifically, for DidOpenTextDocument).
     *
     * @param project The project with the LSP server to notify.
     * @param file The file that was opened.
     */
    fun notifyFileOpen(project: Project, file: VirtualFile) {
        LspServerManager.getInstance(project).getServersForProvider(ElixirLspServerSupportProvider::class.java).forEach {
            it.lsp4jServer.textDocumentService.didOpen(DidOpenTextDocumentParams().apply {
                textDocument = TextDocumentItem(
                    Paths.get("/", file.path)
                        .normalize().toUri().toString(),
                    "elixir",
                    0,
                    VfsUtil.loadText(file)
                )
            })
        }
    }

    /**
     * Notifies the LSP server of a file close event (specifically, for DidCloseTextDocument).
     *
     * @param project The project with the LSP server to notify.
     * @param file The file that was closed.
     */
    fun notifyFileClosed(project: Project, file: VirtualFile) {
        LspServerManager.getInstance(project).getServersForProvider(ElixirLspServerSupportProvider::class.java).forEach {
            it.lsp4jServer.textDocumentService.didClose(DidCloseTextDocumentParams().apply {
                textDocument = TextDocumentIdentifier(
                    Paths.get("/", file.path)
                        .normalize().toUri().toString()
                )
            })
        }
    }

    /**
     * Handles dynamic content changes of a file. This is for textDocument/didChange.
     * This is used to notify the LSP server of changes to a file's content as the user types, i.e., before
     * the file is saved.
     *
     * This function is not currently used because the LSP server does not support this feature, however
     * when it does, this function will be used to notify the LSP server of changes to a file's content.
     *
     * Refer to `ElixirLspDocumentHandlerListener`.
     *
     * @param project The project with the LSP server to notify.
     * @param file The file that was changed.
     * @param version The version of the file.
     * @param changes The list of changes to the file.
     */
    fun notifyContentChange(project: Project, file: VirtualFile, version: Int, changes: List<TextDocumentContentChangeEvent>) {
        LspServerManager.getInstance(project).getServersForProvider(ElixirLspServerSupportProvider::class.java).forEach {
            it.lsp4jServer.textDocumentService.didChange(DidChangeTextDocumentParams().apply {
                textDocument = VersionedTextDocumentIdentifier(
                    Paths.get("/", file.path)
                        .normalize().toUri().toString(),
                    version
                )
                contentChanges = changes
            })
        }
    }
}
