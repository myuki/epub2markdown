package moe.uki.app.epub2markdown

import java.io.InputStream
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class HtmlExtracter(htmlStream: InputStream) {
	val document: Document = Jsoup.parse(htmlStream, "UTF-8", "")
	val titleElements: Elements = document.getElementsByTag("title")
	val h1Elements: Elements = document.getElementsByTag("h1")
	val h2Elements: Elements = document.getElementsByTag("h2")
	val h3Elements: Elements = document.getElementsByTag("h3")
	val h4Elements: Elements = document.getElementsByTag("h4")
	val h5Elements: Elements = document.getElementsByTag("h5")
	val h6Elements: Elements = document.getElementsByTag("h6")
	val hElementsList = listOf(h1Elements, h2Elements, h3Elements, h4Elements, h5Elements, h6Elements)

	val pElements: Elements = document.getElementsByTag("p")
	val pText: List<String> = pElements.eachText()
	var pIndex: Int = 0

	val title: String
		get() {
			//Title 不为空，返回 Title
			if (titleElements.text() != "")
				return titleElements.text()
			else//Title 为空，返回 h1-h6 的第一个非空值
				for (i in hElementsList) {
					if (i.text() != "") return i.first().text()
				}
			//Title 与 h1-h6 均为空，取第一个 p
			pIndex = 1
			return pText.first()
		}

	//取内容
	val content: String
		get() {
			val size: Int = pText.size
			var content = ""

			//判断内容是否为空
			if (size != 0) {
				for (i in pIndex..size - 2) {
					content += pText.get(i) + "\n\n"
				}
				content += pText.get(size - 1) + "\n" //去除尾部多于换行
			}

			return content
		}
}