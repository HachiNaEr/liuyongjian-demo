package liuyongjian.demo.lucene.index;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class UpdateIndex {
	public static void main(String[] args) {
		Analyzer analyzer = new SmartChineseAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		
		Path indexPath = Paths.get("D:\\Lucene\\index");
		try {
			Directory directory = FSDirectory.open(indexPath);
			IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
			
			Document document = new Document();
			document.add(new TextField("id", "2", Store.YES));
			document.add(new TextField("title", "北京大学迎来4380名新生", Store.YES));
			document.add(new TextField("content", "昨天，北京大学迎来4380名来自"
				+ "全国各地及数十个国家的本科新生，其中，农村共700余人，为近年来最多...", Store.YES));
			
			indexWriter.updateDocument(new Term("title", "北大"), document);
			indexWriter.commit();
			indexWriter.close();
			
			System.err.println("更新完成");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
