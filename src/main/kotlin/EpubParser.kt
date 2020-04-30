package moe.uki.app.epub2markdown


import java.io.FileInputStream

import nl.siegmann.epublib.domain.Book
import nl.siegmann.epublib.domain.Resource
import nl.siegmann.epublib.epub.EpubReader

class EpubParser(inputFilePath: String) {
  val book: Book = EpubReader().readEpub(FileInputStream(inputFilePath))
  val contents: List<Resource> = book.tableOfContents.allUniqueResources
  var bookTitle: String = book.metadata.firstTitle

  init {
    // If the title in EPUB's metadata is empty, use filename as title
    if (bookTitle == "") bookTitle = textProcessor.removeExtension(inputFilePath)
  }
}
