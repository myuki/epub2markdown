package moe.uki.app.epub2markdown

import java.io.File

import nl.siegmann.epublib.domain.Resource

class MarkdownOutputer(val resource: Resource, outputDirPath: String, val fileNameType: String) {
	val outputDirFile = File(outputDirPath)
	val htmlExtracter = HtmlExtracter(resource.inputStream)

	val title: String = htmlExtracter.title
	val titleNum: String = TextProcessor(title).getMatcher("^(\\d+)([\\.\\-_](\\d+))*")
	val titleWithoutNum: String = TextProcessor(title).removeMatcher("^(\\d+)([\\.\\-_](\\d+))*(\\s)*")

	// Configurate output file
	val outputFile: File
	val fileName: String
		get() {
			// If HTML's title is empty, use id as filename
			if (title != "") {
				// Choose filename type
				when (fileNameType) {
					"title" -> return title
					"titleNum" -> {
						if (titleNum != "") return TextProcessor(titleNum).removeMatcher("^0*") // Remove 0 in head
						else return title
					}
					"titleWithoutNum" -> {
						if (titleWithoutNum != "") return titleWithoutNum
						else return title
					}
					else -> return resource.id
				}
			} else return resource.id
		}

	init {
		PathProcessor(outputDirFile).checkDir() // Create directory when it not exists
		outputFile = File("$outputDirPath/$fileName.md") // Create the output file
	}

	fun outputText(text: String) {
		outputFile.appendText(text)
	}

	fun outputContent() {
		outputText(htmlExtracter.content)
	}
}
