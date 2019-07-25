package moe.uki.app.epub2markdown

import nl.siegmann.epublib.domain.Resource
import java.io.File

class MarkdownOutputer(val text: Resource, outputDirPath: String, val fileNameType: String) {
	val outputDirFile = File(outputDirPath)
	val htmlExtracter = HtmlExtracter(text.inputStream)

	//获取标题
	val title: String = htmlExtracter.title
	val titleNum: String = TextProcessor(title).getMatcher("\\d+")
	val titleWithoutNum: String = TextProcessor(title).getRemoveMatcher("\\d+\\s*")

	//配置输出文件
	val outputFile: File
	val fileName: String
		get() {
			//如果 Title 为空取 ID 为文件名
			if (title != "") {
				//选择文件名类型
				when (fileNameType) {
					"Title" -> return title
					"TitleNum" -> {
						if (titleNum == "") return title
						else return TextProcessor(titleNum).getRemoveMatcher("^0*") //去除首部 0
					}
					else -> return text.id
				}
			} else return text.id
		}

	init {
		PathProcessor(outputDirFile).checkDir()   //若目录不存在则创建
		outputFile = File(outputDirPath + fileName + ".md")  //创建输出文件
	}

	fun outputText(text: String) {
		outputFile.appendText(text)
	}

	fun outputContent() {
		this.outputText(htmlExtracter.content)
	}
}