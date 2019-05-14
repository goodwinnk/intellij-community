// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.json;

import com.intellij.codeInsight.daemon.impl.analysis.FileHighlightingSetting;
import com.intellij.codeInsight.daemon.impl.analysis.HighlightLevelUtil;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class BunchProjectComponent implements EditorFactoryListener {
  private Project myProject;

  public BunchProjectComponent(@NotNull Project project) {
    myProject = project;
    EditorFactory.getInstance().addEditorFactoryListener(this, project);
  }

  @Override
  public void editorCreated(@NotNull EditorFactoryEvent event) {
    Editor editor = event.getEditor();
    if (!(editor instanceof EditorImpl)) {
      return;
    }

    Document document = editor.getDocument();
    PsiFile file = PsiDocumentManager.getInstance(myProject).getPsiFile(document);
    if (file == null) return;

    VirtualFile virtualFile = file.getVirtualFile();
    if (virtualFile == null) return;

    boolean isBunchFile = BunchLanguageSubstitutor.substituteLanguage(virtualFile) != null;
    if (!isBunchFile) return;

    HighlightLevelUtil.forceRootHighlighting(file, FileHighlightingSetting.SKIP_HIGHLIGHTING);
  }
}
