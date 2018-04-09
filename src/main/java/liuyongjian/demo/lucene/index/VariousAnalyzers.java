package liuyongjian.demo.lucene.index;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class VariousAnalyzers {
//	private static String str = "中华人民共和国简称中国，是一个有13亿人口的国家";
	private static String str = "公路局正在治理解放大道路面积水问题";
	
	public static void main(String[] args) {
		Analyzer analyzer = null;
		analyzer = new StandardAnalyzer(); // 标准分词
		System.err.println("标准分词：" + analyzer.getClass());
		printAnalyzer(analyzer);
		
		analyzer = new WhitespaceAnalyzer(); // 空格分词
		System.err.println("空格分词：" + analyzer.getClass());
		printAnalyzer(analyzer);
		
		analyzer = new SimpleAnalyzer(); // 简单分词
		System.err.println("简单分词：" + analyzer.getClass());
		printAnalyzer(analyzer);
		
		analyzer = new CJKAnalyzer(); // 二分法分词
		System.err.println("二分法分词：" + analyzer.getClass());
		printAnalyzer(analyzer);
		
		analyzer = new KeywordAnalyzer(); // 关键字分词
		System.err.println("关键字分词：" + analyzer.getClass());
		printAnalyzer(analyzer);
		
		analyzer = new StopAnalyzer(); // 停用词分词
		System.err.println("停用词分词：" + analyzer.getClass());
		printAnalyzer(analyzer);
		
		analyzer = new SmartChineseAnalyzer(); // 中文智能分词
		System.err.println("中文智能分词：" + analyzer.getClass());
		printAnalyzer(analyzer);
	}
	
	public static void printAnalyzer(Analyzer analyzer) {
		StringReader reader = new StringReader(str);
		TokenStream tokenStream = analyzer.tokenStream(str, reader);
		try {
			tokenStream.reset();
			CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
			while (tokenStream.incrementToken()) {
				System.err.print(attribute.toString() + "|");
			}
			System.err.println("\n");
			analyzer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
