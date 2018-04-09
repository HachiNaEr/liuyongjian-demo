package liuyongjian.demo.lucene.index;

import java.util.Arrays;
import java.util.List;

public class TfIdfCal {
	public double tf(List<String> doc, String term) {
		double termFrequency = 0 ;
		for (String string : doc) {
			if (string.equalsIgnoreCase(term)) {
				termFrequency++;
			}
		}
		return termFrequency/doc.size();
	}
	
	public int df(List<List<String>> docs, String term) {
		int n = 0;
		if (term != null && term != "") {
			for (List<String> doc : docs) {
				for (String word : doc) {
					if (term.equalsIgnoreCase(word)) {
						n++;
						break;
					}
				}
			}
		} else {
			System.err.println("term不能为null或者空字符串");
		}
		return n;
	}
	
	public double idf(List<List<String>> docs, String term) {
		int df = df(docs, term);
		return Math.log(docs.size()/(df > 0 ? df : 1));
	}
	
	public double tfIdf(List<String> doc, List<List<String>> docs, String term) {
		return tf(doc, term) * idf(docs, term);
	}
	
	public static void main(String[] args) {
		List<String> doc1 = Arrays.asList("人工", "智能", "成为", "互联网", "大会", "焦点");
		List<String> doc2 = Arrays.asList("谷歌", "推出", "开源", "人工", "智能", "系统", "工具");
		List<String> doc3 = Arrays.asList("互联网", "的", "未来", "在", "人工", "智能");
		List<String> doc4 = Arrays.asList("谷歌", "开源", "机器", "学习", "工具");
		
		List<List<String>> documents = Arrays.asList(doc1, doc2, doc3, doc4);
		TfIdfCal calculator = new TfIdfCal();
		
		System.err.println(calculator.tf(doc2, "谷歌"));
		System.err.println(calculator.df(documents, "谷歌"));
		double tfidf = calculator.tfIdf(doc2, documents, "谷歌");
		System.err.println("TF-IDF (谷歌) = " + tfidf);
	}
}
