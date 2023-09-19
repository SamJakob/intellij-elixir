package org.elixir_lang.beam.psi.stubs;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LighterAST;
import com.intellij.lang.LighterASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @param <S> The stub element that {@link #serialize(StubElement, StubOutputStream)} and
 *            {@link #indexStub(StubElement, IndexSink)} should accept.  These stubs should be created by
 *            {@link org.elixir_lang.beam.psi.BeamFileImpl#buildFileStub(byte[], String)}.
 * @param <P> The PSI element that should be returned by {@link #createPsi(StubElement)} in subclasses
 */
public abstract class ModuleElementType<S extends StubElement<?>, P extends PsiElement>
        extends ModuleStubElementType<S, P> {

    /**
     * @throws IllegalArgumentException stubs should never be created from {@link LighterAST} and
     *                                  {@link LighterASTNode}:  Stubs should be created by
     *                                  {@link org.elixir_lang.beam.psi.BeamFileImpl#buildFileStub(byte[], String)}.
     */
    @Override
    public @NotNull S createStub(@NotNull LighterAST tree, @NotNull LighterASTNode node, @NotNull StubElement parentStub) {
        throw new IllegalArgumentException(
                "ModuleElementType should never create stubs from LighterAST tree as they are only create from " +
                        "BeamFileImpl#buildFileStub and ModuleElementType#deserialize"
        );
    }

    /**
     * @param id Unique ID in {@link ModuleStubElementTypes}.  Used to lookup the subclass by {@link #getExternalId()}
     *           when calling {{@link #deserialize(StubInputStream, Stub)}}.
     */
    ModuleElementType(@NotNull String id) {
        super(id);
    }

    /**
     * @throws IllegalArgumentException {@link PsiElement} should never be created from {@link ASTNode} because
     *                                  {@link S} is for binary {@link org.elixir_lang.beam.psi.BeamFileImpl}.  Only
     *                                  {{@link #createStub(PsiElement, StubElement)}} should be used to create {@link PsiElement} from {@link S}.
     */
    @Override
    public P createPsi(@NotNull ASTNode node) {
        throw new IllegalArgumentException(
                "ModuleElementType stubs should never have psi created from ASTNodes as they are only created from " +
                        "BeamFileImpl#buildFileStub and ModuleElementType#deserialize"
        );
    }

}
