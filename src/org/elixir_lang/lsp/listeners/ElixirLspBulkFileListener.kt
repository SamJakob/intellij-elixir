package org.elixir_lang.lsp.listeners

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.vfs.newvfs.events.*
import org.eclipse.lsp4j.FileChangeType
import org.eclipse.lsp4j.FileEvent
import org.elixir_lang.lsp.utils.ElixirLspNotifier
import java.nio.file.Paths

class ElixirLspBulkFileListener(val project: Project) : BulkFileListener {
    private fun dispatch(events: List<FileEvent>) {
        ElixirLspNotifier.notifyFileEvents(project, events)
    }

    override fun before(events: MutableList<out VFileEvent>) {
        super.before(events)

        dispatch(events.mapNotNull { vFileEvent ->
            when (vFileEvent) {
                // FileChangeType.Changed
                is VFileMoveEvent -> {
                    val fileUri = Paths.get("/", vFileEvent.oldPath)
                        .normalize().toUri().toString()

                    FileEvent().apply {
                        type = FileChangeType.Deleted
                        uri = fileUri
                    }
                }

                else -> null
            }
        })
    }

    override fun after(events: MutableList<out VFileEvent>) {
        super.after(events)

        dispatch(events.mapNotNull { vFileEvent ->
            val fileUri = Paths.get("/", vFileEvent.path).normalize().toUri().toString()

            when (vFileEvent) {
                // FileChangeType.Created
                is VFileCreateEvent,
                is VFileCopyEvent,
                is VFileMoveEvent -> {
                    FileEvent().apply {
                        type = FileChangeType.Created
                        uri = fileUri
                    }
                }

                // FileChangeType.Changed
                is VFileContentChangeEvent -> {
                    FileEvent().apply {
                        type = FileChangeType.Changed
                        uri = fileUri
                    }
                }
                // FileChangeType.Deleted
                is VFileDeleteEvent -> {
                    FileEvent().apply {
                        type = FileChangeType.Deleted
                        uri = fileUri
                    }
                }

                else -> null
            }
        })
    }

}
