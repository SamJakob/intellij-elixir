package org.elixir_lang.beam;

import com.ericsson.otp.erlang.OtpErlangDecodeException;
import com.google.common.io.Files;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import org.elixir_lang.PlatformTestCase;

import java.io.File;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DecompilerTest extends PlatformTestCase {
    /*
     * Tests
     */

    public void testIssue575() {
        String ebinDirectory = ebinDirectory();

        VfsRootAccess.allowRootAccess(getTestRootDisposable(), ebinDirectory);

        VirtualFile virtualFile = LocalFileSystem
                .getInstance()
                .findFileByIoFile(
                        new File(ebinDirectory + "Elixir.Bitwise.beam")
                );

        assertNotNull(virtualFile);

        Decompiler decompiler = new Decompiler();
        CharSequence decompiled = decompiler.decompile(virtualFile);

        assertEquals("# Source code recreated from a .beam file by IntelliJ Elixir\n" +
                        "defmodule Bitwise do\n" +
                        "  @moduledoc ~S\"\"\"\n" +
                        "  A set of functions that perform calculations on bits.\n" +
                        "\n" +
                        "  All bitwise functions work only on integers; otherwise an\n" +
                        "  `ArithmeticError` is raised. The functions `band/2`,\n" +
                        "  `bor/2`, `bsl/2`, and `bsr/2` also have operators,\n" +
                        "  respectively: `&&&/2`, `|||/2`, `<<</2`, and `>>>/2`.\n" +
                        "\n" +
                        "  ## Guards" +
                        "\n" +
                        "\n" +
                        "  All bitwise functions can be used in guards:\n" +
                        "\n" +
                        "      iex> odd? = fn\n" +
                        "      ...>   int when Bitwise.band(int, 1) == 1 -> true\n" +
                        "      ...>   _ -> false\n" +
                        "      ...> end\n" +
                        "      iex> odd?.(1)\n" +
                        "      true\n" +
                        "\n" +
                        "  All functions in this module are inlined by the compiler.\n" +
                        "\n" +
                        "  \"\"\"\n" +
                        "\n" +
                        "  # Macros\n" +
                        "\n\n" +
                        "  @deprecated \"\"\"\n" +
                        "  import Bitwise instead\n" +
                        "  \"\"\"" +
                        "\n" +
                        "\n" +
                        "  @doc false\n" +
                        "  defmacro __using__(options) do\n" +
                        "    (\n" +
                        "      except = cond() do\n" +
                        "        Keyword.get(options, :only_operators) ->\n" +
                        "          [bnot: 1, band: 2, bor: 2, bxor: 2, bsl: 2, bsr: 2]\n" +
                        "        Keyword.get(options, :skip_operators) ->\n" +
                        "          [~~~: 1, &&&: 2, |||: 2, ^^^: 2, <<<: 2, >>>: 2]\n" +
                        "        true ->\n" +
                        "          []\n" +
                        "      end\n" +
                        "      {:import, [context: Bitwise], [{:__aliases__, [alias: false], [:\"Bitwise\"]}, [except: except]]}\n" +
                        "    )\n" +
                        "  end\n" +
                        "\n" +
                        "  # Functions\n" +
                        "\n" +
                        "  @doc ~S\"\"\"\n" +
                        "  Bitwise AND operator.\n" +
                        "\n" +
                        "  Calculates the bitwise AND of its arguments.\n" +
                        "\n" +
                        "  Allowed in guard tests. Inlined by the compiler.\n" +
                        "\n" +
                        "  ## Examples\n" +
                        "\n" +
                        "      iex> 9 &&& 3\n" +
                        "      1\n" +
                        "\n" +
                        "\n" +
                        "  \"\"\"\n" +
                        "  def left &&& right do\n" +
                        "    Bitwise.band(left, right)\n" +
                        "  end\n" +
                        "\n" +
                        "  @doc ~S\"\"\"\n" +
                        "  Arithmetic left bitshift operator.\n" +
                        "\n" +
                        "  Calculates the result of an arithmetic left bitshift.\n" +
                        "\n" +
                        "  Allowed in guard tests. Inlined by the compiler.\n" +
                        "\n" +
                        "  ## Examples\n" +
                        "\n" +
                        "      iex> 1 <<< 2\n" +
                        "      4\n" +
                        "\n" +
                        "      iex> 1 <<< -2\n" +
                        "      0\n" +
                        "\n" +
                        "      iex> -1 <<< 2\n" +
                        "      -4\n" +
                        "\n" +
                        "      iex> -1 <<< -2\n" +
                        "      -1\n" +
                        "\n" +
                        "\n" +
                        "  \"\"\"\n" +
                        "  def left <<< right do\n" +
                        "    Bitwise.bsl(left, right)\n" +
                        "  end\n" +
                        "\n" +
                        "  @doc ~S\"\"\"\n" +
                        "  Arithmetic right bitshift operator.\n" +
                        "\n" +
                        "  Calculates the result of an arithmetic right bitshift.\n" +
                        "\n" +
                        "  Allowed in guard tests. Inlined by the compiler.\n" +
                        "\n" +
                        "  ## Examples\n" +
                        "\n" +
                        "      iex> 1 >>> 2\n" +
                        "      0\n" +
                        "\n" +
                        "      iex> 1 >>> -2\n" +
                        "      4\n" +
                        "\n" +
                        "      iex> -1 >>> 2\n" +
                        "      -1\n" +
                        "\n" +
                        "      iex> -1 >>> -2\n" +
                        "      -4\n" +
                        "\n" +
                        "\n" +
                        "  \"\"\"\n" +
                        "  def left >>> right do\n" +
                        "    Bitwise.bsr(left, right)\n" +
                        "  end\n" +
                        "\n" +
                        "  @doc false\n" +
                        "  def left ^^^ right do\n" +
                        "    Bitwise.bxor(left, right)\n" +
                        "  end\n" +
                        "\n" +
                        "  def __info__(p0) do\n" +
                        "    # body not decompiled\n" +
                        "  end\n" +
                        "\n" +
                        "  @doc ~S\"\"\"\n" +
                        "  Calculates the bitwise AND of its arguments.\n" +
                        "\n" +
                        "  Allowed in guard tests. Inlined by the compiler.\n" +
                        "\n" +
                        "  ## Examples\n" +
                        "\n" +
                        "      iex> band(9, 3)\n" +
                        "      1\n" +
                        "\n" +
                        "\n" +
                        "  \"\"\"\n" +
                        "  def band(left, right) do\n" +
                        "    Bitwise.band(left, right)\n" +
                        "  end\n" +
                        "\n" +
                        "  @doc ~S\"\"\"\n" +
                        "  Calculates the bitwise NOT of the argument.\n" +
                        "\n" +
                        "  Allowed in guard tests. Inlined by the compiler.\n" +
                        "\n" +
                        "  ## Examples\n" +
                        "\n" +
                        "      iex> bnot(2)\n" +
                        "      -3\n" +
                        "\n" +
                        "      iex> bnot(2) &&& 3\n" +
                        "      1\n" +
                        "\n" +
                        "\n" +
                        "  \"\"\"\n" +
                        "  def bnot(expr) do\n" +
                        "    :erlang.bnot(expr)\n" +
                        "  end\n" +
                        "\n" +
                        "  @doc ~S\"\"\"\n" +
                        "  Calculates the bitwise OR of its arguments.\n" +
                        "\n" +
                        "  Allowed in guard tests. Inlined by the compiler.\n" +
                        "\n" +
                        "  ## Examples\n" +
                        "\n" +
                        "      iex> bor(9, 3)\n" +
                        "      11\n" +
                        "\n" +
                        "\n" +
                        "  \"\"\"\n" +
                        "  def bor(left, right) do\n" +
                        "    Bitwise.bor(left, right)\n" +
                        "  end\n" +
                        "\n" +
                        "  @doc ~S\"\"\"\n" +
                        "  Calculates the result of an arithmetic left bitshift.\n" +
                        "\n" +
                        "  Allowed in guard tests. Inlined by the compiler.\n" +
                        "\n" +
                        "  ## Examples\n" +
                        "\n" +
                        "      iex> bsl(1, 2)\n" +
                        "      4\n" +
                        "\n" +
                        "      iex> bsl(1, -2)\n" +
                        "      0\n" +
                        "\n" +
                        "      iex> bsl(-1, 2)\n" +
                        "      -4\n" +
                        "\n" +
                        "      iex> bsl(-1, -2)\n" +
                        "      -1\n" +
                        "\n" +
                        "\n" +
                        "  \"\"\"\n" +
                        "  def bsl(left, right) do\n" +
                        "    Bitwise.bsl(left, right)\n" +
                        "  end\n" +
                        "\n" +
                        "  @doc ~S\"\"\"\n" +
                        "  Calculates the result of an arithmetic right bitshift.\n" +
                        "\n" +
                        "  Allowed in guard tests. Inlined by the compiler.\n" +
                        "\n" +
                        "  ## Examples\n" +
                        "\n" +
                        "      iex> bsr(1, 2)\n" +
                        "      0\n" +
                        "\n" +
                        "      iex> bsr(1, -2)\n" +
                        "      4\n" +
                        "\n" +
                        "      iex> bsr(-1, 2)\n" +
                        "      -1\n" +
                        "\n" +
                        "      iex> bsr(-1, -2)\n" +
                        "      -4\n" +
                        "\n" +
                        "\n" +
                        "  \"\"\"\n" +
                        "  def bsr(left, right) do\n" +
                        "    Bitwise.bsr(left, right)\n" +
                        "  end\n" +
                        "\n" +
                        "  @doc ~S\"\"\"\n" +
                        "  Calculates the bitwise XOR of its arguments.\n" +
                        "\n" +
                        "  Allowed in guard tests. Inlined by the compiler.\n" +
                        "\n" +
                        "  ## Examples\n" +
                        "\n" +
                        "      iex> bxor(9, 3)\n" +
                        "      10\n" +
                        "\n" +
                        "\n" +
                        "  \"\"\"\n" +
                        "  def bxor(left, right) do\n" +
                        "    Bitwise.bxor(left, right)\n" +
                        "  end\n" +
                        "\n" +
                        "  def module_info() do\n" +
                        "    # body not decompiled\n" +
                        "  end\n" +
                        "\n" +
                        "  def module_info(p0) do\n" +
                        "    # body not decompiled\n" +
                        "  end\n" +
                        "\n" +
                        "  @doc ~S\"\"\"\n" +
                        "  Bitwise OR operator.\n" +
                        "\n" +
                        "  Calculates the bitwise OR of its arguments.\n" +
                        "\n" +
                        "  Allowed in guard tests. Inlined by the compiler.\n" +
                        "\n" +
                        "  ## Examples\n" +
                        "\n" +
                        "      iex> 9 ||| 3\n" +
                        "      11\n" +
                        "\n" +
                        "\n" +
                        "  \"\"\"\n" +
                        "  def left ||| right do\n" +
                        "    Bitwise.bor(left, right)\n" +
                        "  end\n" +
                        "\n" +
                        "  @doc false\n" +
                        "  def ~~~(expr) do\n" +
                        "    :erlang.bnot(expr)\n" +
                        "  end\n" +
                        "\n" +
                        "  # Private Functions\n" +
                        "\n" +
                        "  defp unquote(:\"-inlined-__info__/1-\")(p0) do\n" +
                        "    # body not decompiled\n" +
                        "  end\n" +
                        "end\n",
                decompiled.toString()
        );
    }

    public void testIssue672() throws IOException, OtpErlangDecodeException {
        assertDecompiled("rebar3_hex_config");
    }

    public void testIssue683() throws IOException, OtpErlangDecodeException {
        assertDecompiled("orber_ifr");
    }

    public void testIssue703() throws IOException, OtpErlangDecodeException {
        assertDecompiled("Elixir.LDAPEx.ELDAPv3");
    }

    public void testIssue772() throws IOException, OtpErlangDecodeException {
        assertDecompiled("OTP20/Elixir.Kernel");
    }

    public void testIssue803() throws IOException, OtpErlangDecodeException {
        assertDecompiled("certifi_cacerts");
    }

    public void testIssue833() throws IOException, OtpErlangDecodeException {
        assertDecompiled("docgen_xmerl_xml_cb");
    }

    public void testIssue859() throws IOException, OtpErlangDecodeException {
        assertDecompiled("erl_syntax");
    }

    public void testIssue860() throws IOException, OtpErlangDecodeException {
        assertDecompiled("OTP-PUB-KEY");
    }

    public void testIssue878() throws IOException, OtpErlangDecodeException {
        assertDecompiled("gl");
    }

    public void testIssue883() throws IOException, OtpErlangDecodeException {
        assertDecompiled("fprof");
    }

    public void testElixir_1_5_0() throws IOException, OtpErlangDecodeException {
        assertDecompiled("OTP20/Elixir.AtU8Test");
    }

    public void testDocsElixirKernel() throws IOException {
        assertDecompiled("Docs/Elixir.Kernel");
    }

    public void testDocsElixirKernelSpecialForms() throws IOException {
        assertDecompiled("Docs/Elixir.Kernel.SpecialForms");
    }

    public void testDocsElixirRuntimeError() throws IOException {
        assertDecompiled("Docs/Elixir.RuntimeError");
    }

    public void testDocsErlang() throws IOException {
        assertDecompiled("Docs/erlang");
    }

    public void testIssue1882() throws IOException {
        assertDecompiled("Elixir.Ecto.Query");
    }

    public void testIssue1886() throws IOException {
        assertDecompiled("Elixir.Module");
    }

    public void testHyphenInKeywordKeys() throws IOException {
        assertDecompiled("Elixir.Phoenix.HTML.Tag");
    }

    public void testIssue2221() throws IOException {
        assertDecompiled("queue");
    }

    // Issues 2251 and 2263
    public void testSSHOptions() throws IOException {
        assertDecompiled("ssh_options");
    }

    public void testIssue2257() throws IOException {
        assertDecompiled("hipe_icode_call_elim");
    }

    // Issues 2285 and 2286
    public void testASN1CT() throws IOException {
        assertDecompiled("asn1ct");
    }

    public void testIssue2287() throws IOException {
        assertDecompiled("diameter_gen_acct_rfc6733");
    }

    public void testIssue2288() throws IOException {
        assertDecompiled("diameter_gen_base_accounting");
    }

    public void testIssue2289() throws IOException {
        assertDecompiled("diameter_gen_relay");
    }

    public void testIssue2306() throws IOException {
        assertDecompiled("dialyzer_callgraph");
    }

    public void testCode() throws IOException {
        assertDecompiled("code");
    }

    public void testIssue2328() throws IOException {
        assertDecompiled("dbg_wx_trace_win");
    }

    public void testIssue2329() throws IOException {
        assertDecompiled("ex_cursor");
    }

    public void testIssue2331() throws IOException {
        assertDecompiled("gb_sets");
    }

    public void testIssue2332() throws IOException {
        assertDecompiled("idna");
    }

    public void testIssue2401() throws IOException {
        assertDecompiled("elixir/1.13.0/Elixir.Kernel");
    }

    public void testIssue2403() throws IOException {
        assertDecompiled("Elixir.EExTestWeb.PageController");
    }

    public void testIssue2410() throws IOException {
        assertDecompiled("Elixir.Ecto.Changeset");
    }

    public void testIssue2386() throws IOException {
        assertDecompiled("Elixir.RabbitMq.Handler");
    }

    public void testIssue2976() throws IOException {
        assertDecompiled("inet_db");
    }

    /*
     * Instance Methods
     */

    /*
     * Protected Instance Methods
     */

    @Override
    protected String getTestDataPath() {
        return "testData/org/elixir_lang/beam/decompiler";
    }

    /*
     * Private Instance Methods
     */

    private void assertDecompiled(String name) throws IOException {
        String testDataPath = getTestDataPath();
        String prefix = testDataPath + "/" + name + ".";

        File expectedFile = new File(prefix + "ex");
        String expected = Files.toString(expectedFile, UTF_8);

        VfsRootAccess.allowRootAccess(getTestRootDisposable(), testDataPath);

        VirtualFile virtualFile = LocalFileSystem
                .getInstance()
                .findFileByIoFile(
                        new File(prefix + "beam")
                );

        assertNotNull(virtualFile);

        Decompiler decompiler = new Decompiler();
        CharSequence decompiled = decompiler.decompile(virtualFile);

        assertEquals(expected, decompiled.toString());
    }

    private String ebinDirectory() {
        String ebinDirectory = System.getenv("ELIXIR_EBIN_DIRECTORY");

        assertNotNull("ELIXIR_EBIN_DIRECTORY is not set", ebinDirectory);

        return ebinDirectory;
    }
}
