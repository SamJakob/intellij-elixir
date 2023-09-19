package org.elixir_lang.lsp.listeners

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.elixir_lang.lsp.services.ElixirLspDocumentVersionService
import org.elixir_lang.lsp.utils.ElixirLspNotifier

class ElixirLspFileEditorManagerListener(val project: Project) : FileEditorManagerListener {

    private fun getDocumentVersionService(): ElixirLspDocumentVersionService =
        project.getService(ElixirLspDocumentVersionService::class.java)

    override fun fileOpened(source: FileEditorManager, file: VirtualFile) {
        super.fileOpened(source, file)
        ElixirLspNotifier.notifyFileOpen(project, file)
        getDocumentVersionService().registerDocument(file)

    }

    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        super.fileClosed(source, file)
        ElixirLspNotifier.notifyFileClosed(project, file)
        getDocumentVersionService().unregisterDocument(file)
    }



}
