package org.elixir_lang.navigation.item_presentation;

import com.intellij.navigation.ItemPresentation;
import org.elixir_lang.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class Exception implements ItemPresentation {
    /*
     * Fields
     */

    @Nullable
    private final String location;
    private final @NotNull String name;

    /*
     * Constructors
     */

    public Exception(@Nullable String location, @NotNull String name) {
        this.location = location;
        this.name = name;
    }

    /*
     * Instance Methods
     */

    /**
     * Returns the icon representing the object.
     *
     * @param unused Used to mean if open/close icons for tree renderer. No longer in use. The parameter is only there for API compatibility reason.
     */
    @NotNull
    @Override
    public Icon getIcon(boolean unused) {
        return Icons.EXCEPTION;
    }

    /**
     * Returns the location of the object (for example, the package of a class). The location
     * string is used by some renderers and usually displayed as grayed text next to the item name.
     *
     * @return the location description, or null if none is applicable.
     */
    @Nullable
    @Override
    public String getLocationString() {
        return location;
    }

    /**
     * Returns the name of the object to be presented in most renderers across the program.
     *
     * @return the object name.
     */
    @Nullable
    @Override
    public String getPresentableText() {
        return name;
    }
}
