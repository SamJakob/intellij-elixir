// This is a generated file. Not intended for manual editing.
package org.elixir_lang.psi.impl;

import com.ericsson.otp.erlang.OtpErlangObject;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.tree.IElementType;
import org.elixir_lang.psi.ElixirCapturePrefixOperator;
import org.elixir_lang.psi.ElixirVisitor;
import org.jetbrains.annotations.NotNull;

public class ElixirCapturePrefixOperatorImpl extends ASTWrapperPsiElement implements ElixirCapturePrefixOperator {

  public ElixirCapturePrefixOperatorImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ElixirVisitor) ((ElixirVisitor)visitor).visitCapturePrefixOperator(this);
    else super.accept(visitor);
  }

  @NotNull
  public IElementType operatorTokenType() {
    return ElixirPsiImplUtil.operatorTokenType(this);
  }

  @NotNull
  public OtpErlangObject quote() {
    return ElixirPsiImplUtil.quote(this);
  }

}
