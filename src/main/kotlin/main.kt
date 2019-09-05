package moe.uki.app.epub2markdown

const val customText: String = "翻译: []()\n\n原帖: <>\n\n---\n\n"

/**
 * Get input file path, output directory path and output file's name type in command line
 */
fun main(args: Array<String>) {
	when (args.size) {
		0 -> println("Error, EPUB file path is empty")
		1 -> {
			val epubProcessor = EpubExtracter(args[0])
			epubProcessor.outputEachChapterAsMarkdown()
		}
		2 -> {
			val epubProcessor = EpubExtracter(args[0])
			epubProcessor.outputEachChapterAsMarkdown(args[1])
		}
		else -> {
			val epubProcessor = EpubExtracter(args[0])
			epubProcessor.outputEachChapterAsMarkdown(args[1], args[2])
		}
	}
}
