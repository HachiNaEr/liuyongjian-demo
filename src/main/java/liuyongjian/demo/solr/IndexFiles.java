package liuyongjian.demo.solr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

public class IndexFiles {
	public static void main(String[] args) throws IOException {
		FSDirectory directory = FSDirectory.open(Paths.get("D:\\lucene\\luceneIndex"));
		
		File dataFile = new File("D:\\lucene\\luceneData");
		
		Analyzer analyzer = new StandardAnalyzer();
		Document document = new Document();
		IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(analyzer));
	}
}
