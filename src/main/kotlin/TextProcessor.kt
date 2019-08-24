package moe.uki.app.epub2markdown

class TextProcessor(val string: String) {

	//Remove the extension in filename
	val noneExtension: String
		get() {
			val regex = Regex("\\..*")
			val result = regex.replace(string, "")

			return result
		}

	//Get matched string
	fun getMatcher(regexText: String): String {
		val regex = Regex(regexText)
		val result = regex.find(string)?.value

		//If none matched return empty string
		if (result != null)
			return result
		else
			return ""
	}

	//Remove mathced string
	fun getRemoveMatcher(regexText: String): String {
		val regex = Regex(regexText)
		val result = regex.replace(string, "")

		return result
	}
}