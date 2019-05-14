// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.json;

import com.intellij.openapi.fileTypes.InternalFileType;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import com.intellij.openapi.fileTypes.ex.FileTypeIdentifiableByVirtualFile;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class BunchFileType extends LanguageFileType implements FileTypeIdentifiableByVirtualFile, InternalFileType {
  public static final LanguageFileType INSTANCE = new BunchFileType();

  BunchFileType() {
    super(PlainTextLanguage.INSTANCE);
  }

  @Override
  public boolean isMyFileType(@NotNull VirtualFile file) {
    String extension = file.getExtension();
    return ("183".equals(extension) || "182".equals(extension));
  }

  @NotNull
  @Override
  public String getName() {
    return "Bunch";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Bunch";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return "";
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return PlainTextFileType.INSTANCE.getIcon();
  }

  @Override
  public boolean isReadOnly() {
    return true;
  }

  @Nullable
  @Override
  public String getCharset(@NotNull VirtualFile file, @NotNull byte[] content) {
    return null;
  }
}