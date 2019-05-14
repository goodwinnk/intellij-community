// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.json;

import com.intellij.json.psi.impl.JsonFileImpl;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.file.impl.FileManager;
import com.intellij.psi.impl.file.impl.FileManagerImpl;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import static com.intellij.json.JsonElementTypes.*;

//public class BunchParserDefinition implements ParserDefinition {
//  public static final TokenSet WHITE_SPACES = TokenSet.WHITE_SPACE;
//
//  public static final IFileElementType FILE = new IFileElementType(BunchLanguage.INSTANCE);
//
//  @NotNull
//  @Override
//  public Lexer createLexer(Project project) {
//    return new BunchLexer();
//  }
//
//  @Override
//  public PsiParser createParser(Project project) {
//    return new BunchParser();
//  }
//
//  @Override
//  public IFileElementType getFileNodeType() {
//    return FILE;
//  }
//
//  @NotNull
//  @Override
//  public TokenSet getWhitespaceTokens() {
//    return WHITE_SPACES;
//  }
//
//  @NotNull
//  @Override
//  public TokenSet getCommentTokens() {
//    return TokenSet.EMPTY;
//  }
//
//  @NotNull
//  @Override
//  public TokenSet getStringLiteralElements() {
//    return TokenSet.EMPTY;
//  }
//
//  @NotNull
//  @Override
//  public PsiElement createElement(ASTNode astNode) {
//    return Factory.createElement(astNode);
//  }
//
//  @Override
//  public PsiFile createFile(FileViewProvider fileViewProvider) {
//    return new JsonFileImpl(fileViewProvider, JsonLanguage.INSTANCE);
//  }
//
//  @Override
//  public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode astNode, ASTNode astNode2) {
//    return SpaceRequirements.MAY;
//  }
//}
