package moe.uki.app.epub2markdown

class TextProcessor(val string: String) {

	//去除文件扩展名
	val noneExtension: String
		get() {
			val regex = Regex("\\..*")
			val result = regex.replace(string, "")

			return result
		}

	//取匹配正则的字符串
	fun getMatcher(regexText: String): String {
		val regex = Regex(regexText)
		val result = regex.find(string)?.value

		//如果无匹配，返回空字符串
		if (result != null)
			return result
		else
			return ""
	}

	//移除匹配正则的字符串
	fun getRemoveMatcher(regexText: String): String {
		val regex = Regex(regexText)
		val result = regex.replace(string, "")

		return result
	}
}