package liuyongjian.demo.lucene.analyzer;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class StdAnalyzer {
	private static String strCh = "中华人民共和国简称中国，是一个有13亿人口的国家";
	private static String strEn = "Dogs can not achieve a place, eyes can reach: ";
	
	public static void main(String[] args) {
		System.err.println("StandardAnalyzer 对中文的分词：");
		stdAnalyzer(strCh);
		System.err.println("StandardAnalyzer 对英文的分词：");
		stdAnalyzer(strEn);
	}
	
	public static void stdAnalyzer(String str) {
		Analyzer analyzer = new StandardAnalyzer();
		StringReader reader = new StringReader(str);
		TokenStream toStream = analyzer.tokenStream(str, reader);
		try {
			toStream.reset();
			CharTermAttribute termAttribute = toStream.getAttribute(CharTermAttribute.class);
			System.err.println("分词结果：");
			while (toStream.incrementToken()) {
				System.err.print(termAttribute.toString() + "|");
			}
			System.err.println("\n");
			analyzer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
