// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.json;

import com.intellij.icons.AllIcons;
import com.intellij.ide.scratch.RootType;
import com.intellij.lang.Language;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.LayeredIcon;
import com.intellij.util.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public final class BunchRootType extends RootType {
  @NotNull
  public static BunchRootType getInstance() {
    return findByClass(BunchRootType.class);
  }

  public BunchRootType() {
    super("bunches", "Bunches");
  }

  @Override
  public Language substituteLanguage(@NotNull Project project, @NotNull VirtualFile file) {
    return JavaLanguage.INSTANCE;
  }

  @Nullable
  @Override
  public Icon substituteIcon(@NotNull Project project, @NotNull VirtualFile file) {
    if (file.isDirectory()) return null;
    Icon icon = ObjectUtils.chooseNotNull(super.substituteIcon(project, file), BunchFileType.INSTANCE.getIcon());
    return LayeredIcon.create(icon, AllIcons.Actions.Scratch);
  }
}
