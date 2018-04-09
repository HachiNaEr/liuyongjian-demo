package liuyongjian.demo.lucene.index;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class HighlighterTest {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		String title = "title";
		Path path = Paths.get("D:\\Lucene\\index");
		try {
			Directory directory = FSDirectory.open(path);
			IndexReader reader = DirectoryReader.open(directory);
			
			IndexSearcher searcher = new IndexSearcher(reader);
			SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
			 
			QueryParser parser = new QueryParser(title, analyzer);
			Query query = parser.parse("美国");
			
			System.err.println("Query : " + query);
			QueryScorer queryScorer = new QueryScorer(query);
			
			SimpleHTMLFormatter fors = new SimpleHTMLFormatter("<span style=\"color:red;\">", "</span>");
			Highlighter highlighter = new Highlighter(fors, queryScorer);
			
			// 高亮分词器
			TopDocs topDocs = searcher.search(query, 10);
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				Document document = searcher.doc(scoreDoc.doc);
				System.err.println("id : " + document.get("id"));
				System.err.println("title : " + document.get("title"));
				System.err.println("content: " + document.get("content"));
				
				TokenStream tokenStream = TokenSources.getAnyTokenStream(reader, scoreDoc.doc, title, analyzer);
				// 获取tokenStream
				Fragmenter fragmenter = new SimpleSpanFragmenter(queryScorer);
				highlighter.setTextFragmenter(fragmenter);
				String highStrTitle = highlighter.getBestFragment(tokenStream, document.get(title));
				System.err.println("高亮片段：" + highStrTitle);
			}
		} catch (IOException | ParseException | InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}
	}
}
