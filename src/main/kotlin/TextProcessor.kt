package moe.uki.app.epub2markdown

class TextProcessor() {

  /**
   * Get matched string
   */
  fun getMatcher(string: String, regexText: String): String {
    val result = Regex(regexText).find(string)?.value

    // If none matched return empty string
    return if (result != null) result else ""
  }

  /**
   * Remove mathced string
   */
  fun removeMatcher(string: String, regexText: String): String {
    return string.replace(Regex(regexText), "")
  }

  /**
   * Remove the extension in filename
   */
  fun removeExtension(string: String): String {
    return removeMatcher(string, "\\..*")
  }
}
