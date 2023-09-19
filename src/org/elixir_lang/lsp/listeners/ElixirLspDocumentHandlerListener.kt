package org.elixir_lang.lsp.listeners

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectLocator
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.refactoring.suggested.newRange
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.Range
import org.eclipse.lsp4j.TextDocumentContentChangeEvent
import org.elixir_lang.lsp.services.ElixirLspDocumentVersionService
import org.elixir_lang.lsp.utils.ElixirLspNotifier

/**
 * Listens to document changes and dispatches them to the LSP server.
 *
 * These are for textDocument/didChange notifications (for changes made without a document
 * save). This class dispatches the changes to the LSP server for the respective project
 * using [ElixirLspNotifier] and hence is self-contained in that regard. (That is, you can
 * enable this listener to enable the feature).
 *
 * This class is currently not activated in plugin.xml as the LSP server does not currently
 * support this notification (it won't fail or crash with it, but it won't do anything useful).
 * So, to avoid unnecessary computations, this listener is commented out. However, you can
 * uncomment it and the feature will be activated.
 */
class ElixirLspDocumentHandlerListener : DocumentListener {

    private fun getDocumentVersionService(project: Project): ElixirLspDocumentVersionService =
        project.getService(ElixirLspDocumentVersionService::class.java)

    /**
     * Processes an event and enumerates the projects that reference the file. For each project,
     * the event is processed and dispatched with [dispatchDocumentChanged].
     */
    override fun documentChanged(event: DocumentEvent) {
        super.documentChanged(event)

        val file = FileDocumentManager.getInstance().getFile(event.document) ?: return
        val projects = ProjectLocator.getInstance().getProjectsForFile(file)

        projects.forEach {
            if (it != null) dispatchDocumentChanged(it, event, file)
        }
    }

    /**
     * Actually handles the list of events in the context of a specific project.
     *
     * Uses the [ElixirLspDocumentVersionService] to get the document version and
     * converts the IntelliJ Document Event to an LSP TextDocumentContentChangeEvent,
     * and dispatches the event in question to the LSP for the project.
     */
    private fun dispatchDocumentChanged(project: Project, event: DocumentEvent, file: VirtualFile) {
        val version = getDocumentVersionService(project).getAndIncrementDocumentVersion(file)

        // Get the text fragment from the event.
        val text = event.newFragment

        // Get the IntelliJ newRange object and convert it to LSP
        // form.
        val ideaNewRange = event.newRange
        val ideaStartOffset = StringUtil.offsetToLineColumn(
            event.document.text,
            ideaNewRange.startOffset
        )
        val ideaEndOffset = StringUtil.offsetToLineColumn(
            event.document.text,
            ideaNewRange.endOffset
        )

        val range: Range? = if (event.isWholeTextReplaced) null
        else Range(
            // Start Position
            Position(ideaStartOffset.line, ideaStartOffset.column),
            // End Position
            Position(ideaEndOffset.line, ideaEndOffset.column)
        )

        if (range == null) return

        ElixirLspNotifier.notifyContentChange(
            project,
            file,
            version,
            listOf(
                TextDocumentContentChangeEvent(range, text.toString())
            )
        )
    }

}
