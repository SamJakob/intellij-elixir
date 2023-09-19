package org.elixir_lang.lsp.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.vfs.VirtualFile
import java.nio.file.Paths

/**
 * A project-level service that keeps track of the version of a document.
 *
 * As the document is updated by the user, the version is incremented for LSP.
 * As documents are opened and closed, the version is tracked with [registerDocument]
 * and [unregisterDocument].
 *
 * When a document is registered, it is given an entry in this service with an associated
 * version number. As changes are logged with [getAndIncrementDocumentVersion], the
 * version number is returned and incremented. When a document is unregistered, its
 * entry is removed from the service.
 *
 * This allows for the version numbers of files associated with each project to be efficiently
 * tracked and, being a project-level service, means that version numbers are consistent to
 * each respective project's LSP server and can be disposed all at once when the project is
 * closed.
 */
@Service(Service.Level.PROJECT)
class ElixirLspDocumentVersionService {

    companion object {
        // Given a file, normalizes the path to it and returns it as a URI for LSP.
        private fun normalizePathToFile(file: VirtualFile): String {
            return Paths.get("/", file.path)
                .normalize().toUri().toString()
        }
    }

    private val fileVersions: MutableMap<String, Int> = mutableMapOf()

    /**
     * Registers a document (should be called on file open).
     *
     * This sets up an entry in the service for the document with a version
     * number of 0.
     */
    fun registerDocument(file: VirtualFile) {
        val path = normalizePathToFile(file)
        if (fileVersions.containsKey(path)) return
        fileVersions[path] = 0
    }

    /**
     * Unregisters a document (should be called on file close).
     *
     * This cleans up the entry for a given file in the service. It cannot be
     * used after that until [registerDocument] is called for that file.
     */
    fun unregisterDocument(file: VirtualFile) {
        fileVersions.remove(normalizePathToFile(file))
    }

    /**
     * Returns the current version of a document (associated with [file]).
     *
     * If [delta] is 0, the current version is returned. Otherwise, the
     * current version will be returned but subsequently incremented by
     * [delta].
     */
    fun getDocumentVersion(file: VirtualFile, delta: Int = 0): Int {
        // Obtain the file from fileVersions and assert that it has an
        // entry.
        val path = normalizePathToFile(file)
        if (!fileVersions.containsKey(path)) {
            throw RuntimeException("Attempted to get version of document that has not been claimed!")
        }

        // Get the current version value of the file. If the delta is 0
        // return it directly.
        val current: Int = fileVersions[path]!!
        if (delta == 0) return current

        // Otherwise, we have non-zero delta, so apply it and return the
        // updated value.
        fileVersions[path] = current + delta
        return current
    }

    /**
     * Simple alias for [getDocumentVersion] with a delta of 1 (increment).
     *
     * This has equivalent behavior to the post-increment operator.
     */
    fun getAndIncrementDocumentVersion(file: VirtualFile): Int
        = getDocumentVersion(file, 1)

}
