package org.elixir_lang.jps;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.testFramework.UsefulTestCase;
import com.intellij.util.containers.CollectionFactory;
import com.intellij.util.containers.MultiMap;
import org.jetbrains.jps.builders.impl.logging.ProjectBuilderLoggerBase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by zyuyou on 15/7/17.
 */
public class TestProjectBuilderLogger extends ProjectBuilderLoggerBase {
  private MultiMap<String, String> myCompiledFiles = new MultiMap<>();
  private Set<String> myDeletedFiles = CollectionFactory.createFilePathSet();

  @Override
  public void logDeletedFiles(Collection<String> paths) {
    myDeletedFiles.addAll(paths);
  }

  @Override
  public void logCompiledFiles(Collection<File> files, String builderName, String description) throws IOException {
    myCompiledFiles.putValues(builderName, files.stream()
            .map(File::getPath)
            .map(FileUtil::toCanonicalPath)
            .collect(Collectors.toList()));
  }

  @Override
  protected void logLine(String message) {
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public void clear(){
    myCompiledFiles.clear();
    myDeletedFiles.clear();
  }

  public void assertCompiled(String builderName, File[] baseDirs, String... paths){
    assertRelativePaths(baseDirs, myCompiledFiles.get(builderName), paths);
  }

  public void assertDeleted(File[] baseDirs, String... paths){
    assertRelativePaths(baseDirs, myDeletedFiles, paths);
  }

  private static void assertRelativePaths(File[] baseDirs, Collection<String> files, String[] expected){
    List<String> relativePaths = new ArrayList<>();
    for (String filePath: files){
      File file = new File(filePath);
      String path = file.getAbsolutePath();

      for(File baseDir : baseDirs){
        if(baseDir != null && FileUtil.isAncestor(baseDir, file, false)){
          path = FileUtil.getRelativePath(baseDir, file);
          break;
        }
      }

        assert path != null;
        relativePaths.add(FileUtil.toSystemIndependentName(path));
    }

    UsefulTestCase.assertSameElements(relativePaths, expected);
  }
}
