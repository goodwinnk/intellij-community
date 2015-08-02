/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package formatter

import com.intellij.formatting.CoreFormatterUtil
import com.intellij.formatting.FormatTextRanges
import com.intellij.formatting.FormatterImpl
import com.intellij.formatting.FormattingMode
import com.intellij.lang.Language
import com.intellij.mock.MockDocument
import com.intellij.mock.MockPsiDocumentManager
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.codeStyle.*
import com.intellij.psi.formatter.DocumentBasedFormattingModel
import com.intellij.testFramework.ParsingTestCase
import com.intellij.testFramework.TestDataFile
import com.intellij.testFramework.UsefulTestCase
import org.jetbrains.annotations.NonNls
import org.jetbrains.kotlin.idea.core.formatter.JetCodeStyleSettings
import org.jetbrains.kotlin.idea.formatter.JetFormattingModelBuilder
import org.jetbrains.kotlin.parsing.JetParserDefinition
import org.jetbrains.kotlin.psi.JetFile
import org.junit.Assert
import java.io.File
import java.io.IOException

public abstract class AbstractJetFormatterTest : ParsingTestCase("", "kt", JetParserDefinition()) {
  var testDir = ""

  override fun setUp() {
    super.setUp()

    registerExtensionPoint(FileTypeIndentOptionsProvider.EP_NAME, javaClass<FileTypeIndentOptionsProvider>())

    registerApplicationService(javaClass<CodeStyleSchemes>(), object : CodeStyleSchemes() {
      override fun getSchemes() = throw UnsupportedOperationException()
      override fun getCurrentScheme() = getDefaultScheme()
      override fun setCurrentScheme(scheme: CodeStyleScheme?) {}
      override fun createNewScheme(preferredName: String?, parentScheme: CodeStyleScheme?) = throw UnsupportedOperationException()
      override fun deleteScheme(scheme: CodeStyleScheme?) {}
      override fun findSchemeByName(name: String) = null
      override fun getDefaultScheme() = object : CodeStyleScheme {
        override fun getName() = "Default"
        override fun isDefault() = true
        override fun getCodeStyleSettings() = getSettingsWithKotlin()
      }
      override fun addScheme(currentScheme: CodeStyleScheme) = throw UnsupportedOperationException()
      override fun setSchemes(schemes: MutableList<CodeStyleScheme>) = throw UnsupportedOperationException()
    })

    myProject.registerService(javaClass<ProjectCodeStyleSettingsManager>(), ProjectCodeStyleSettingsManager())
  }

  override fun tearDown() {
    super.tearDown()
    testDir = ""
  }

  override fun getTestDataPath() = testDir

  public fun doTestInverted(path: String) {
    // Do nothing
  }

  protected override fun loadFile(NonNls TestDataFile name: String): String {
    return ParsingTestCase.loadFileDefault(testDir, name)
  }

  public override fun doTest(path: String) {
    testDir = path.replace("idea/testData/formatter", "platform/kotlin-formatter/testData").substringBeforeLast("/")

    doTest(false)
    Assert.assertTrue(myFile is JetFile)

    val formatter = FormatterImpl()

    val document = MockDocument(myFile.getText())
    (PsiDocumentManager.getInstance(project) as MockPsiDocumentManager).setDocument(document)

    val settings = CodeStyleSettingsManager.getSettings(getProject())

    val formattingModel = CoreFormatterUtil.buildModel(
        JetFormattingModelBuilder(), myFile,
        settings,
        FormattingMode.REFORMAT)

    val model = DocumentBasedFormattingModel(formattingModel,
        document,
        project, settings, myFile.getFileType(), myFile)

    val ranges = FormatTextRanges(TextRange.create(0, myFile.getText().length() - 1), true)

    val indentOptions = CommonCodeStyleSettings.IndentOptions()

    formatter.format(model, settings, indentOptions, ranges, false)

    val actual = document.getText()

    val fileName = "${getTestName(false)}.after.kt"
    val expectedFilePath = "$testDir/$fileName"

    UsefulTestCase.assertSameLinesWithFile(expectedFilePath, actual)
  }

  private fun getSettingsWithKotlin(): CodeStyleSettings {
    val settings = object : CodeStyleSettings(false) {
      override fun getCommonSettings(lang: Language?): CommonCodeStyleSettings? {
        return CommonCodeStyleSettings(lang)
      }
    }

    val method = javaClass<CodeStyleSettings>().getDeclaredMethod("addCustomSettings", javaClass<CustomCodeStyleSettings>())
    method.setAccessible(true);
    method.invoke(settings, JetCodeStyleSettings(settings))

    return settings
  }
}
