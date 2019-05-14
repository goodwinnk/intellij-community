// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.json;

import com.intellij.lang.Language;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.lang.properties.PropertiesLanguage;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.LanguageSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BunchLanguageSubstitutor extends LanguageSubstitutor {
  @Nullable
  @Override
  public Language getLanguage(@NotNull VirtualFile file, @NotNull Project project) {
    return substituteLanguage(file);
  }

  @Nullable
  public static Language substituteLanguage(@NotNull VirtualFile file) {
    String name = file.getName();

    String extension = StringUtil.substringAfterLast(name, ".");
    if (extension == null || (!"183".equals(extension) && !"182".equals(extension))) {
      return null;
    }

    String nameWithoutExtension = StringUtil.substringBeforeLast(name, ".");
    String secondExtension = StringUtil.substringAfterLast(nameWithoutExtension, ".");
    if (secondExtension == null) {
      return null;
    }

    if ("java".equals(secondExtension)) {
      return JavaLanguage.INSTANCE;
    }

    if ("properties".equals(secondExtension)) {
      return PropertiesLanguage.INSTANCE;
    }

    return null;
  }
}
