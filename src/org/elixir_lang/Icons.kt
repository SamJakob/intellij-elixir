package org.elixir_lang

import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.RowIcon
import com.intellij.util.PlatformIcons
import org.elixir_lang.structure_view.element.Timed
import javax.swing.Icon

// RowIcon on travis-ci does not have RowIcon(Icon...) constructor, so fake it
internal object RowIconFactory {
    fun create(vararg icons: Icon): RowIcon {
        val rowIcon = RowIcon(icons.size)

        for (i in icons.indices) {
            rowIcon.setIcon(icons[i], i)
        }

        return rowIcon
    }
}

/**
 * Created by zyuyou on 15/7/6.
 */
object Icons {
    object Time {
        @JvmField
        val COMPILE = AllIcons.Actions.Compile

        @JvmField
        val RUN = AllIcons.RunConfigurations.TestState.Run

        @JvmStatic
        fun from(time: Timed.Time): Icon =
            when (time) {
                Timed.Time.COMPILE -> COMPILE
                Timed.Time.RUN -> RUN
            }
    }

    object Visibility {
        @JvmField
        val PRIVATE: Icon = PlatformIcons.PRIVATE_ICON

        @JvmField
        val PUBLIC: Icon = PlatformIcons.PUBLIC_ICON

        @JvmStatic
        fun from(visibility: org.elixir_lang.call.Visibility?): Icon {
            val icon: Icon = if (visibility != null) {
                when (visibility) {
                    org.elixir_lang.call.Visibility.PRIVATE -> PRIVATE
                    org.elixir_lang.call.Visibility.PUBLIC -> PUBLIC
                }

            } else {
                UNKNOWN
            }

            return icon
        }
    }

    @JvmField
    val BEHAVIOR: Icon = IconLoader.getIcon("/icons/file/elixir/behavior.svg", Icons.javaClass)

    @JvmField
    val CALLBACK = AllIcons.Gutter.ImplementedMethod

    @JvmField
    val CALL_DEFINITION: Icon = PlatformIcons.FUNCTION_ICON

    @JvmField
    val CALL_DEFINITION_CLAUSE: Icon = RowIconFactory.create(CALL_DEFINITION, PlatformIcons.PACKAGE_LOCAL_ICON)

    @JvmField
    val DESCRIBE = AllIcons.Nodes.TestGroup

    @JvmField
    val DELEGATION: Icon =
        RowIconFactory.create(AllIcons.RunConfigurations.TestState.Run, PlatformIcons.PACKAGE_LOCAL_ICON)

    @JvmField
    val EXCEPTION: Icon = PlatformIcons.EXCEPTION_CLASS_ICON

    @JvmField
    val FIELD = AllIcons.Nodes.Field

    @JvmField
    val FILE = IconLoader.getIcon("/icons/file/elixir.svg", Icons.javaClass)

    @JvmField
    val MACRO: Icon = PlatformIcons.RECORD_ICON

    @JvmField
    val MIX_MODULE_CONFLICT = AllIcons.Actions.Cancel

    @JvmField
    val OVERRIDABLE = AllIcons.General.OverridenMethod

    @JvmField
    val OVERRIDE = AllIcons.General.OverridingMethod

    @JvmField
    val PARAMETER = AllIcons.Nodes.Parameter

    object Protocol {
        val Structure = IconLoader.getIcon("/icons/protocol.svg", Icons.javaClass)
        val GoToImplementations: Icon = AllIcons.Gutter.ImplementedMethod
    }

    object Implementation {
        @JvmField
        val Structure: Icon = AllIcons.Nodes.Interface

        @JvmField
        val GoToProtocols: Icon = IconLoader.getIcon("/icons/go_to_protocols.svg", Icons.javaClass)
    }

    @JvmField
    val STRUCTURE = AllIcons.Toolwindows.ToolWindowStructure

    @JvmField
    val TEST = AllIcons.Nodes.Test

    // same icon as intellij-erlang to match their look and feel
    @JvmField
    val TYPE = IconLoader.getIcon("/icons/type.png", Icons.javaClass)

    // MUST be after TYPE
    @JvmField
    val SPECIFICATION: Icon = RowIconFactory.create(CALL_DEFINITION, TYPE)


    // it is the unknown that is only a question mark
    @JvmField
    val UNKNOWN = AllIcons.Actions.Help

    @JvmField
    val VARIABLE = AllIcons.Nodes.Variable

    object File {
        @JvmField
        val APPLICATION = IconLoader.getIcon("/icons/file/elixir/application.svg", Icons.javaClass)

        @JvmField
        val GEN_EVENT = IconLoader.getIcon("/icons/file/elixir/gen-event.svg", Icons.javaClass)

        @JvmField
        val GEN_SERVER = IconLoader.getIcon("/icons/file/elixir/gen-server.svg", Icons.javaClass)

        @JvmField
        val SUPERVISOR = IconLoader.getIcon("/icons/file/elixir/supervisor.svg", Icons.javaClass)
    }

    @JvmField
    val LANGUAGE = IconLoader.getIcon("/icons/language/elixir.svg", Icons.javaClass)

    @JvmField
    val MODULE = IconLoader.getIcon("/icons/module/elixir.svg", Icons.javaClass)

    @JvmField
    val MODULE_ITEM: Icon = IconLoader.getIcon("/icons/file/elixir/module.svg", Icons.javaClass)
}

