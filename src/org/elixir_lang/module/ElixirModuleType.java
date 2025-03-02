package org.elixir_lang.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectJdkForModuleStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import org.elixir_lang.Icons;
import org.elixir_lang.sdk.elixir.Type;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by zyuyou on 2015/5/26.
 */
public class ElixirModuleType extends ModuleType<ElixirModuleBuilder>{
  public static final String MODULE_TYPE_ID = "ELIXIR_MODULE";

  public ElixirModuleType() {
    super(MODULE_TYPE_ID);
  }

  public static ElixirModuleType getInstance(){
    return (ElixirModuleType) ModuleTypeManager.getInstance().findByID(MODULE_TYPE_ID);
  }

  @NotNull
  @Override
  public ElixirModuleBuilder createModuleBuilder() {
    return new ElixirModuleBuilder();
  }

  @NotNull
  @Override
  public String getName() {
    return "Elixir Module";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Elixir modules are used for developing <b>Elixir</b> applications.";
  }

//  @Override
  public Icon getBigIcon() {
    return Icons.MODULE;
  }

  @Override
  public @NotNull Icon getNodeIcon(boolean isOpened) {
    return Icons.MODULE;
  }

  @NotNull
  @Override
  public ModuleWizardStep @NotNull [] createWizardSteps(@NotNull WizardContext wizardContext,
                                                        @NotNull final ElixirModuleBuilder moduleBuilder,
                                                        @NotNull ModulesProvider modulesProvider) {
    return new ModuleWizardStep[]{
        new ProjectJdkForModuleStep(wizardContext, Type.Util.getInstance()){
          public void updateDataModel(){
            super.updateDataModel();
            moduleBuilder.setModuleJdk(getJdk());
          }
        }
    };
  }
}
