package cn.devinkin.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

public class IndexSearchTest {

    @Test
    public void testIndexSearch() throws Exception {
        // 创建分词器(创建索引和搜索时所用的分词器必须一致)
        Analyzer analyzer = new StandardAnalyzer();
//        Analyzer analyzer = new IKAnalyzer();

        // 创建查询对象
        // 第一个参数:默认搜索域,如果query中没有指定域名,从默认搜索域的域中搜索,如果query指定了域,就在query指定域中搜索
        // 第二个参数,分词器
        QueryParser queryParser = new QueryParser("fileContext", analyzer);
        // 查询语法=域名:搜索的关键字
//        Query query = queryParser.parse("fileName:apache");
        Query query = queryParser.parse("fileName:apache");

        // 指定索引和文档的目录
//        Directory directory = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict").toPath());
        Directory directory = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict"));

        // 索引和文档的读取对象
        IndexReader indexReader = DirectoryReader.open(directory);

        // 创建索引的搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 搜索
        // 第一个参数:Query,为查询语句对象
        // 第二个参数:int,指定显示多少条
//        TopDocs topDocs = indexSearcher.search(query, 10);
        TopDocs topDocs = indexSearcher.search(query, 5);

        System.out.println("==========count==========");
        // 一共搜索到多少条记录
        System.out.println(topDocs.totalHits);
        System.out.println("=========================");
        // 从搜索结果对象中获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 获取docId
            int docId = scoreDoc.doc;
            // 通过文档id从硬盘中读取出对应的文档
            Document document = indexReader.document(docId);
            // 可以通过域名取出值
            System.out.println("fileName: " + document.get("fileName"));
            System.out.println("fileSize: " + document.get("fileSize"));
            System.out.println("=====================================");
        }
    }

    @Test
    public void testIndexTermQuery() throws Exception {
        // 创建分词器(创建索引和搜索时所用的分词器必须一致)
        Analyzer analyzer = new StandardAnalyzer();

        // 创建词元
        Term term = new Term("fileName", "apache");
        // 使用TermQuery查询,根据term对象进行查询
        TermQuery termQuery = new TermQuery(term);

        // 指定索引和文档的目录
//        Directory directory = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict").toPath());
        Directory directory = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict"));

        // 索引和文档的读取对象
        IndexReader indexReader = DirectoryReader.open(directory);

        // 创建索引的搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 搜索
        // 第一个参数:Query,为查询语句对象
        // 第二个参数:int,指定显示多少条
//        TopDocs topDocs = indexSearcher.search(query, 10);
        TopDocs topDocs = indexSearcher.search(termQuery, 5);

        System.out.println("==========count==========");
        // 一共搜索到多少条记录
        System.out.println(topDocs.totalHits);
        System.out.println("=========================");
        // 从搜索结果对象中获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 获取docId
            int docId = scoreDoc.doc;
            // 通过文档id从硬盘中读取出对应的文档
            Document document = indexReader.document(docId);
            // 可以通过域名取出值
            System.out.println("fileName: " + document.get("fileName"));
            System.out.println("fileSize: " + document.get("fileSize"));
            System.out.println("=====================================");
        }
    }


    @Test
    public void testNumericRangeQuery() throws Exception {
        //创建分词器(创建索引和所有时所用的分词器必须一致)
        Analyzer analyzer = new IKAnalyzer();

        //根据数字范围查询
        //查询文件大小,大于100 小于1000的文章
        //第一个参数:域名      第二个参数:最小值,  第三个参数:最大值, 第四个参数:是否包含最小值,   第五个参数:是否包含最大值
        Query query = NumericRangeQuery.newLongRange("fileSize", 100L, 1000L, true, true);

        //指定索引和文档的目录
        Directory dir = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict"));
        //索引和文档的读取对象
        IndexReader indexReader = IndexReader.open(dir);
        //创建索引的搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //搜索:第一个参数为查询语句对象, 第二个参数:指定显示多少条
        TopDocs topdocs = indexSearcher.search(query, 5);
        //一共搜索到多少条记录
        System.out.println("=====count=====" + topdocs.totalHits);
        //从搜索结果对象中获取结果集
        ScoreDoc[] scoreDocs = topdocs.scoreDocs;

        for (ScoreDoc scoreDoc : scoreDocs) {
            //获取docID
            int docID = scoreDoc.doc;
            //通过文档ID从硬盘中读取出对应的文档
            Document document = indexReader.document(docID);
            //get域名可以取出值 打印
            System.out.println("fileName:" + document.get("fileName"));
            System.out.println("fileSize:" + document.get("fileSize"));
            System.out.println("============================================================");
        }
    }

    @Test
    public void testBooleanQuery() throws Exception {
        //创建分词器(创建索引和所有时所用的分词器必须一致)
        Analyzer analyzer = new IKAnalyzer();

        // 布尔查询,可以根据多个条件组合进行查询
        // 文件名称包含apache的,并且文件大小大于等于100小于等于100字节的文件
        BooleanQuery query = new BooleanQuery();

        //根据数字范围查询
        //查询文件大小,大于100 小于1000的文章
        //第一个参数:域名      第二个参数:最小值,  第三个参数:最大值, 第四个参数:是否包含最小值,   第五个参数:是否包含最大值
        Query numericRangeQuery = NumericRangeQuery.newLongRange("fileSize", 100L, 1000L, true, true);

        // 创建词元
        Term term = new Term("fileName", "apache");
        // 使用TermQuery查询,根据term对象进行查询
        TermQuery termQuery = new TermQuery(term);

        //must相当于and
        //must_not相当于not
        //should相当于or
        query.add(numericRangeQuery, BooleanClause.Occur.SHOULD);
        query.add(termQuery, BooleanClause.Occur.MUST);

        //指定索引和文档的目录
        Directory dir = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict"));
        //索引和文档的读取对象
        IndexReader indexReader = IndexReader.open(dir);
        //创建索引的搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //搜索:第一个参数为查询语句对象, 第二个参数:指定显示多少条
        TopDocs topdocs = indexSearcher.search(query, 5);
        //一共搜索到多少条记录
        System.out.println("=====count=====" + topdocs.totalHits);
        //从搜索结果对象中获取结果集
        ScoreDoc[] scoreDocs = topdocs.scoreDocs;

        for (ScoreDoc scoreDoc : scoreDocs) {
            //获取docID
            int docID = scoreDoc.doc;
            //通过文档ID从硬盘中读取出对应的文档
            Document document = indexReader.document(docID);
            //get域名可以取出值 打印
            System.out.println("fileName:" + document.get("fileName"));
            System.out.println("fileSize:" + document.get("fileSize"));
            System.out.println("============================================================");
        }
    }


    @Test
    public void testMatchAllQuery() throws Exception {
        //创建分词器(创建索引和所有时所用的分词器必须一致)
        Analyzer analyzer = new IKAnalyzer();

        // 查询所有文档
        MatchAllDocsQuery query = new MatchAllDocsQuery();


        //指定索引和文档的目录
        Directory dir = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict"));
        //索引和文档的读取对象
        IndexReader indexReader = IndexReader.open(dir);
        //创建索引的搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //搜索:第一个参数为查询语句对象, 第二个参数:指定显示多少条
        TopDocs topdocs = indexSearcher.search(query, 5);
        //一共搜索到多少条记录
        System.out.println("=====count=====" + topdocs.totalHits);
        //从搜索结果对象中获取结果集
        ScoreDoc[] scoreDocs = topdocs.scoreDocs;

        for (ScoreDoc scoreDoc : scoreDocs) {
            //获取docID
            int docID = scoreDoc.doc;
            //通过文档ID从硬盘中读取出对应的文档
            Document document = indexReader.document(docID);
            //get域名可以取出值 打印
            System.out.println("fileName:" + document.get("fileName"));
            System.out.println("fileSize:" + document.get("fileSize"));
            System.out.println("============================================================");
        }
    }


    @Test
    public void testMultifieldQuery() throws Exception {
        // 创建分词器(创建索引和搜索时所用的分词器必须一致)
        Analyzer analyzer = new StandardAnalyzer();
//        Analyzer analyzer = new IKAnalyzer();


        String[] fields = {"fileName", "fileContext"};

        // 从文件名称和文件内容中查询,只有含有apache关键字的就查出来
        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(fields, analyzer);
        Query query = multiFieldQueryParser.parse("apache");


        // 指定索引和文档的目录
//        Directory directory = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict").toPath());
        Directory directory = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict"));

        // 索引和文档的读取对象
        IndexReader indexReader = DirectoryReader.open(directory);

        // 创建索引的搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 搜索
        // 第一个参数:Query,为查询语句对象
        // 第二个参数:int,指定显示多少条
//        TopDocs topDocs = indexSearcher.search(query, 10);
        TopDocs topDocs = indexSearcher.search(query, 5);

        System.out.println("==========count==========");
        // 一共搜索到多少条记录
        System.out.println(topDocs.totalHits);
        System.out.println("=========================");
        // 从搜索结果对象中获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 获取docId
            int docId = scoreDoc.doc;
            // 通过文档id从硬盘中读取出对应的文档
            Document document = indexReader.document(docId);
            // 可以通过域名取出值
            System.out.println("fileName: " + document.get("fileName"));
            System.out.println("fileSize: " + document.get("fileSize"));
            System.out.println("=====================================");
        }
    }
}
