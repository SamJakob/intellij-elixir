package org.elixir_lang.facet

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.projectRoots.impl.ProjectJdkImpl
import com.intellij.openapi.roots.ui.configuration.projectRoot.ProjectSdksModel

@Service(Service.Level.APP)
class SdksService {
    private var model: ProjectSdksModel? = null

    companion object {
        fun getInstance(): SdksService? = ApplicationManager.getApplication().getService(SdksService::class.java)
    }

    fun <T> projectJdkImplList(clazz: Class<T>) =
        getModel().sdks.filter { clazz.isInstance(it.sdkType) }.map { it as ProjectJdkImpl }

    fun getModel(): ProjectSdksModel {
        val model = this.model ?: initModel()
        this.model = model

        return model
    }

    private fun initModel(): ProjectSdksModel {
        var model: ProjectSdksModel?

        do {
            model = try {
                ProjectSdksModel().apply { reset(null) }
            } catch (e: AssertionError) {
                null
            }
        } while (model == null)

        return model
    }
}
