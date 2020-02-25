package moe.uki.app.epub2markdown

import java.io.InputStream

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class HtmlExtracter(htmlStream: InputStream) {
	val document: Document = Jsoup.parse(htmlStream, "UTF-8", "")

	val pValue: List<String> = document.getElementsByTag("p").eachText()
	var pIndex: Int = 0

	/**
	 * Title selection priority: title->h1->...->h6->p(first)
	 */
	val title: String
		get() {
			val titleValue: String = document.getElementsByTag("title").text()
			if (titleValue != "") return titleValue
			for (h in 1..6) {
				val hTag: Elements = document.getElementsByTag("h$h")
				if (hTag.text() != "") return hTag.eachText().first()
			}
			pIndex = 1
			return pValue.first()
		}

	/**
	 * Get content in p tag
	 */
	val content: String
		get() {
			val size: Int = pValue.size
			var content = ""

			// Check if the p tag exists
			if (size != 0) {
				// Traversing all p tag, splice all p tag as content when it exists
				for (i in pIndex..size - 2) {
					content += "${pValue.get(i)}\n\n"
				}
				content += "${pValue.get(size - 1)}\n" // Remove the last \n in content
			}
			return content
		}
}
