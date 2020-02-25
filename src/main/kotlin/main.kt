package moe.uki.app.epub2markdown

const val customText1: String = ""
const val customText2: String = ""

/**
 * Parameter: inputFilePath, outputDirectoryPath, outputFileNameType[titleNum, titleWithoutNum, title]
 */
fun main(args: Array<String>) {
	when (args.size) {
		0 -> println("Error, EPUB file path is empty")
		1 -> {
			val epubConverter = EpubConverter(args[0])
			epubConverter.outputEachChapterTextOnly()
		}
		2 -> {
			val epubConverter = EpubConverter(args[0])
			epubConverter.outputEachChapterTextOnly(args[1])
		}
		else -> {
			val epubConverter = EpubConverter(args[0])
			epubConverter.outputEachChapterTextOnly(args[1], args[2])
		}
	}
}
