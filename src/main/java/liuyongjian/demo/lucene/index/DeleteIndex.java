package liuyongjian.demo.lucene.index;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class DeleteIndex {
	public static void main(String[] args) {
		// 删除title中含有关键词“美国”的文档
		deleteDoc("title", "美国");
	}
	
	public static void deleteDoc(String field, String key) {
		Analyzer analyzer = new SmartChineseAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		Path indexPath = Paths.get("D:\\Lucene\\index");
		try {
			Directory directory = FSDirectory.open(indexPath);
			IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
			
			indexWriter.deleteDocuments(new Term(field, key));
			indexWriter.commit();
			indexWriter.close();
			System.err.println("删除完成");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
