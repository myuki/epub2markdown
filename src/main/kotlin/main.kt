package moe.uki.app.epub2markdown

const val customText1: String = ""
const val customText2: String = ""
val pathProcessor = PathProcessor()
val textProcessor = TextProcessor()

/**
 * Parameter: outputFileNameType[titleNum, titleWithoutNum, title], inputFilePath
 */
fun main(args: Array<String>) {
  when (args.size) {
    0 -> println("Error, EPUB file path is empty")
    1 -> {
      val converter = Converter(args[args.size - 1])
      converter.outputEachChapterTextOnly()
    }
    2 -> {
      val converter = Converter(args[args.size - 1])
      converter.outputEachChapterTextOnly(args[0])
    }
  }
}
