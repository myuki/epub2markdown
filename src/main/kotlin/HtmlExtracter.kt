package moe.uki.app.epub2markdown

import java.io.InputStream

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class HtmlExtracter(htmlStream: InputStream) {
	val document: Document = Jsoup.parse(htmlStream, "UTF-8", "")

	val pText: List<String> = document.getElementsByTag("p").eachText()
	var pIndex: Int = 0

	val title: String
		get() {
			//Title selection priority: title->h1->h2->h3->h4->h5->h6->p(first)
			val title: String = document.getElementsByTag("title").text()
			if (title != "") return title
			val h1: String = document.getElementsByTag("h1").text()
			if (h1 != "") return h1
			val h2: String = document.getElementsByTag("h1").text()
			if (h2 != "") return h2
			val h3: String = document.getElementsByTag("h1").text()
			if (h3 != "") return h3
			val h4: String = document.getElementsByTag("h1").text()
			if (h4 != "") return h4
			val h5: String = document.getElementsByTag("h1").text()
			if (h5 != "") return h5
			val h6: String = document.getElementsByTag("h1").text()
			if (h6 != "") return h6

			pIndex = 1
			return pText.first()
		}

	//Get content in p tag
	val content: String
		get() {
			val size: Int = pText.size
			var content = ""

			//Check if the p tag exists, splice all p tag as content when it exists
			if (size != 0) {
				for (i in pIndex..size - 2) {
					content += "${pText.get(i)}\n\n"
				}
				content += "${pText.get(size - 1)}\n" //Remove the last \n in content
			}
			return content
		}
}
