// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.json;

import com.intellij.lang.Language;

public class BunchLanguage extends Language {
  public static final BunchLanguage INSTANCE = new BunchLanguage();

  protected BunchLanguage(String ID, String... mimeTypes) {
    super(INSTANCE, ID, mimeTypes);
  }

  private BunchLanguage() {
    super("BUNCH");
  }

  @Override
  public boolean isCaseSensitive() {
    return true;
  }

  public boolean hasPermissiveStrings() { return false; }
}
