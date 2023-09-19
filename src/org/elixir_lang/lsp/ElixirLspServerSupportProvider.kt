@file:Suppress("UnstableApiUsage")

package org.elixir_lang.lsp

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServerSupportProvider

class ElixirLspServerSupportProvider : LspServerSupportProvider {

    /**
     * Used to memoize whether the error notification ("Failed to activate language server") has been
     * displayed.
     *
     * Once it has, this is set to true until/unless it becomes valid again. This avoids spamming the
     * user every time they open a file.
     */
    private var memoizeErrorDisplayed: Boolean = false

    override fun fileOpened(
        project: Project,
        file: VirtualFile,
        serverStarter: LspServerSupportProvider.LspServerStarter
    ) {
        if (ElixirLspServerDescriptor.isSupportedFile(file)) {
            // If the LSP Server environment variable is valid, and the file is supported,
            // prepare the LSP Server.
            if (ElixirLspServerDescriptor.isValid) {
                serverStarter.ensureServerStarted(ElixirLspServerDescriptor(project))
                memoizeErrorDisplayed = false
            } else {
                if (!memoizeErrorDisplayed) {
                    // If the LSP server is not valid, display a notification and activate the fallback
                    // completion contributor.
                    ApplicationManager.getApplication().invokeLater {
                        NotificationGroupManager.getInstance()
                            .getNotificationGroup("Elixir")
                            .createNotification(
                                "Failed to activate language server",
                                "The language server couldn't be located. " +
                                        "We checked for a launcher at '${ElixirLspServerDescriptor.launcher.absolutePath}', " +
                                        "if this is incorrect try updating 'ELIXIR_LS_DIRECTORY' " +
                                        "or checking your installation and file permissions " +
                                        "and then restart the IDE.",
                                NotificationType.ERROR
                            )
                            .notify(project)
                    }

                    // Register the legacy completions as a fallback if the LSP is unavailable.
                    CompletionContributor.forLanguage(org.elixir_lang.ElixirLanguage).add(
                        org.elixir_lang.code_insight.completion.contributor.CallDefinitionClause()
                    )
                    memoizeErrorDisplayed = true
                }
            }
        }
    }

}
