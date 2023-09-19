package org.elixir_lang.structure_view.element.structure;

import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.ElementDescriptionLocation;
import com.intellij.psi.PsiElement;
import com.intellij.usageView.UsageViewTypeLocation;
import org.elixir_lang.navigation.item_presentation.Parent;
import org.elixir_lang.psi.*;
import org.elixir_lang.psi.call.Call;
import org.elixir_lang.structure_view.element.Element;
import org.elixir_lang.structure_view.element.modular.Modular;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static org.elixir_lang.psi.call.name.Function.DEFSTRUCT;
import static org.elixir_lang.psi.call.name.Module.KERNEL;
import static org.elixir_lang.psi.impl.PsiElementImplKt.stripAccessExpression;
import static org.elixir_lang.psi.impl.call.CallImplKt.finalArguments;

public class Structure extends Element<Call> {
    /*
     * Fields
     */

    @NotNull
    private final Modular modular;

    /*
     * Static Methods
     */

    public static String elementDescription(Call call, ElementDescriptionLocation location) {
        String elementDescription = null;

        if (location == UsageViewTypeLocation.INSTANCE) {
            elementDescription = "struct";
        }

        return elementDescription;
    }

    public static boolean is(Call call) {
        return call.isCalling(KERNEL, DEFSTRUCT, 1);
    }

    /*
     * Constructors
     */

    public Structure(@NotNull Modular modular, @NotNull Call call) {
        super(call);
        this.modular = modular;
    }

    /*
     * Instance Methods
     */

    /**
     * Returns the list of children of the tree element.
     *
     * @return the list of children.
     */
    @NotNull
    @Override
    public TreeElement @NotNull [] getChildren() {
        PsiElement[] finalArguments = finalArguments(navigationItem);
        List<TreeElement> childList = new ArrayList<>();

        assert finalArguments != null;
        assert finalArguments.length == 1;

        PsiElement finalArgument = finalArguments[0];

        if (finalArgument instanceof QuotableKeywordList) {
            addDefaultValueByField(childList, (QuotableKeywordList) finalArgument);
        } else if (finalArgument instanceof ElixirAccessExpression) {
            PsiElement accessExpressionChild = stripAccessExpression(finalArgument);

            if (accessExpressionChild instanceof ElixirList) {
                ElixirList list = (ElixirList) accessExpressionChild;
                PsiElement[] listChildren = list.getChildren();

                for (PsiElement listChild : listChildren) {
                    if (listChild instanceof QuotableKeywordList) {
                        addDefaultValueByField(childList, (QuotableKeywordList) listChild);
                    } else if (listChild instanceof ElixirAccessExpression) {
                        PsiElement listChildAccessExpressionChild = stripAccessExpression(listChild);

                        if (listChildAccessExpressionChild instanceof ElixirAtom) {
                            childList.add(
                                    new Field(this, (ElixirAtom) listChildAccessExpressionChild)
                            );
                        }
                    }
                }
            }
        } else {
            // TODO handle metaprogramming like {@code defstruct keys}
        }

        return childList.toArray(new TreeElement[childList.size()]);
    }

    /**
     * Returns the presentation of the tree element.
     *
     * @return the element presentation.
     */
    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        Parent parentPresentation = (Parent) modular.getPresentation();
        String location = parentPresentation.getLocatedPresentableText();
        int lastIndex = location.lastIndexOf('.');
        String parentLocation;
        String name;

        if (lastIndex != -1) {
            parentLocation = location.substring(0, lastIndex);
            name = location.substring(lastIndex + 1, location.length());
        } else {
            parentLocation = null;
            name = location;
        }

        return new org.elixir_lang.navigation.item_presentation.structure.Structure(parentLocation, name);
    }

    /*
     * Private Instance Methods
     */

    private void addDefaultValueByField(List<TreeElement> treeElementList, QuotableKeywordList defaultValueByField) {
        List<QuotableKeywordPair> fieldDefaultValuePairs = defaultValueByField.quotableKeywordPairList();

        for (QuotableKeywordPair fieldDefaultValuePair : fieldDefaultValuePairs) {
            treeElementList.add(new FieldWithDefaultValue(this, fieldDefaultValuePair));
        }
    }
}
