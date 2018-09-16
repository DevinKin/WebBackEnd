package cn.devinkin.lucene;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class IndexManagerTest {
    @Test
    public void testIndexCreate() throws Exception {
        // 采集文件系统中的文档数据,放入Lucene中

        // 创建文档列表,保存多个Document
        List<Document> docList = new ArrayList<Document>();

        // 指定文件所在目录
        File dir = new File("F:\\WebBackEnd\\Lucene\\lucene01\\searchsource");
        for (File file : dir.listFiles()) {
            // 文件名称
            String fileName = file.getName();
            // 文件内容
            String fileContext = FileUtils.readFileToString(file);
            // 文件大小
            Long fileSize = FileUtils.sizeOf(file);

            // 文档对象,文件系统中的一个文件就是一个Document对象
            Document doc = new Document();

            // 第一个参数:域名(key)
            // 第二个参数:域值(value)
            // 第三个参数:是否存储,YES或NO
//            TextField nameField = new TextField("fileName", fileName, Field.Store.YES);
//            TextField contextField = new TextField("fileContext", fileContext, Field.Store.YES);
//            TextField sizeField = new TextField("fileSize", fileSize.toString(), Field.Store.YES);
            // 是否分词:要,因为它要索引,并且它不是一个整体,分词有意义,所以要分词
            // 是否索引:要索引,因为要通过文件名进行搜索
            // 是否存储:要,因为要直接在页面上显示
            TextField nameField = new TextField("fileName", fileName, Field.Store.YES);
            // 是否分词:要,因为要根据文件内容进行索引,并且它分词有意义
            // 是否索引:要,因为要根据文件内容进行搜索
            // 是否存储:可以要,也可以不要,不存储,搜索完内容就提取不出来
            TextField contextField = new TextField("fileContext", fileContext, Field.Store.NO);
            // 是否分词:不要,大小索引没有意义,因为大小的字符串不需要分割
            // 是否索引:要,因为要根据文件大小进行搜索
            // 是否存储:要,因为要显示文档大小
            // NumericDocValuesField提供索引,评分,排序功能,但不提供存储功能,需要额外添加StoredField进行存储
            NumericDocValuesField numericDocValuesField = new NumericDocValuesField("fileSize", fileSize);
            // 要存储值,必须要添加一个同名的文件大小字段
            StoredField storedField = new StoredField("fileSize", fileSize.toString());


            // 将所有的域存入document中
            doc.add(nameField);
            doc.add(contextField);
//            doc.add(sizeField);
            doc.add(numericDocValuesField);
            doc.add(storedField);

            // 将文档存入文档集合中
            docList.add(doc);
        }

        // 创建分词器
        // StandardAnalyzer标准分词器,标准分词器对英文分词效果很好,对中文单字分词
//        Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new IKAnalyzer();


        // FS:FileSystem,指定索引和文档存储的目录
//        Directory directory = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict").toPath());
        Directory directory = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict"));
        // 创建写对象的初始化对象
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
        // 创建索引和文档写对象
        // 第一个参数:Directory,指定索引和文档存储的目录
        // 第二个参数:IndexWriterConfig,写对象的初始化对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 将文档加入到索引和文档的写对象中
        for (Document doc : docList) {
            indexWriter.addDocument(doc);
        }

        // 提交索引和文档写对象
        indexWriter.commit();
        // 关闭流
        indexWriter.close();
    }

    @Test
    public void testIndexDel() throws Exception{
        // 创建分词器
        // StandardAnalyzer标准分词器,标准分词器对英文分词效果很好,对中文单字分词
        Analyzer analyzer = new StandardAnalyzer();


        // FS:FileSystem,指定索引和文档存储的目录
//        Directory directory = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict").toPath());
        Directory directory = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict"));
        // 创建写对象的初始化对象
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
        // 创建索引和文档写对象
        // 第一个参数:Directory,指定索引和文档存储的目录
        // 第二个参数:IndexWriterConfig,写对象的初始化对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 删除所有索引
        //indexWriter.deleteAll();

        // 根据名称删除索引
        // Term为词元,就是一个词
        // 第一个参数:域名
        // 第二个参数:要删除含有此关键词的数据
        indexWriter.deleteDocuments(new Term("fileName", "apache"));

        // 提交
        indexWriter.commit();

        // 关闭流
        indexWriter.close();
    }


    /**
     * 更新就是按照传入的Term进行搜索
     * 如果找到结果,删除,将更新的内容重新生成一个Document对象
     * 如果没找到结果,将更新的内容直接生成一个新的Document对象
     * @throws Exception
     */
    @Test
    public void testIndexUpdate() throws Exception {
        // 创建分词器
        // StandardAnalyzer标准分词器,标准分词器对英文分词效果很好,对中文单字分词
        Analyzer analyzer = new StandardAnalyzer();


        // FS:FileSystem,指定索引和文档存储的目录
//        Directory directory = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict").toPath());
        Directory directory = FSDirectory.open(new File("F:\\WebBackEnd\\Lucene\\lucene01\\dict"));
        // 创建写对象的初始化对象
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
        // 创建索引和文档写对象
        // 第一个参数:Directory,指定索引和文档存储的目录
        // 第二个参数:IndexWriterConfig,写对象的初始化对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 根据文件名称进行更新
        // 无法被搜索到
//        Term term = new Term("fileName", "1.create web page.txt");

        Term term = new Term("fileName", "web");
        // 更新的对象
        Document doc = new Document();
        doc.add(new TextField("fileName", "xxxxxx", Field.Store.YES));
        doc.add(new TextField("fileContext", "Think in java xxxxxx", Field.Store.YES));
        // 更新
        indexWriter.updateDocument(term, doc);

        // 提交
        indexWriter.commit();

        // 关闭流
        indexWriter.close();

    }


}
