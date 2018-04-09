package liuyongjian.demo.lucene.index;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

@SuppressWarnings("unused")
public class QueryParseTest {
	public static void main(String[] args) {
		String[] fields = {"title", "content"};
		String field = "title";
		Path indexPath = Paths.get("D:\\Lucene\\index");
		
		try {
			Directory directory = FSDirectory.open(indexPath);
			IndexReader reader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			
			SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
			QueryParser parser = new QueryParser(field, analyzer);
			// MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
			parser.setDefaultOperator(Operator.AND);
			
			// TermQuery query = new TermQuery(new Term("title", "美国"));
			Query query = parser.parse("美国");
			System.err.println("Query: " + query.toString());
			
			// 返回前十条
			TopDocs topDocs = searcher.search(query, 10);
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				Document document = searcher.doc(scoreDoc.doc);
				
				System.err.println("DocId: " + scoreDoc.doc);
				System.err.println("id: " + document.get("id"));
				System.err.println("title: " + document.get("title"));
				System.err.println("content: " + document.get("content"));
				System.err.println("文档评分: " + scoreDoc.score);
				System.err.println();
			}
			
			directory.close();
			reader.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
	}
}
