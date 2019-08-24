package moe.uki.app.epub2markdown

import nl.siegmann.epublib.domain.Resource
import java.io.File

class MarkdownOutputer(val text: Resource, outputDirPath: String, val fileNameType: String) {
	val outputDirFile = File(outputDirPath)
	val htmlExtracter = HtmlExtracter(text.inputStream)

	//Get title
	val title: String = htmlExtracter.title
	val titleNum: String = TextProcessor(title).getMatcher("(\\d+)((\\.)(\\d+))*")
	val titleWithoutNum: String = TextProcessor(title).getRemoveMatcher("(\\d+)((\\.)(\\d+))*(\\s*)")

	//Configurate output file
	val outputFile: File
	val fileName: String
		get() {
			//If title is empty, use id as filename
			if (title != "") {
				//Choose filename type
				when (fileNameType) {
					"title" -> return title
					"titleNum" -> {
						if (titleNum != "") return TextProcessor(titleNum).getRemoveMatcher("^0*") //Remove 0 in head
						else return title
					}
					"titleWithoutNum" -> {
						if (titleWithoutNum != "") return titleWithoutNum
						else return title
					}
					else -> return text.id
				}
			} else return text.id
		}

	init {
		PathProcessor(outputDirFile).checkDir() //Create directory when it not exists
		outputFile = File(outputDirPath + "/" + fileName + ".md")  //Create the output file
	}

	fun outputText(text: String) {
		outputFile.appendText(text)
	}

	fun outputContent() {
		this.outputText(htmlExtracter.content)
	}
}