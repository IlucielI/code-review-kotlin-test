package com.benchmark

import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class XmlProcessor {
    // XXE vulnerability: XML parser without entity resolution disabled
    fun processXml(xmlData: String) {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        builder.parse(InputSource(StringReader(xmlData)))
    }
}
