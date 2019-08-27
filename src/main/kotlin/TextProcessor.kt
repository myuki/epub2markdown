package moe.uki.app.epub2markdown

class TextProcessor(val string: String) {

	/**
	 * Get matched string
	 */
	fun getMatcher(regexText: String): String {
		val result = Regex(regexText).find(string)?.value

		// If none matched return empty string
		return if (result != null) result else ""
	}

	/**
	 * Remove mathced string
	 */
	fun removeMatcher(regexText: String): String {
		return string.replace(Regex(regexText), "")
	}

	/**
	 * Remove the extension in filename
	 */
	fun removeExtension(): String {
		return removeMatcher("\\..*")
	}
}
