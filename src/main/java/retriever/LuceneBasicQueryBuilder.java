package retriever;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import utils.ParserUtil;

import java.util.Arrays;

public class LuceneBasicQueryBuilder {
    private static String field = "keywordSequence";

    public static Query buildQuery(String tokenText) {
        Query query = null;
        QueryParser parser = new QueryParser(LuceneBasicQueryBuilder.field, new StandardAnalyzer());
        tokenText = String.join(" ", ParserUtil.extractNLwords(Arrays.asList(tokenText.split(" ", 0))));
        try {
            query = parser.parse(tokenText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return query;
    }
}
