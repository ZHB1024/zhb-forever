package com.forever.zhb.util.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.SortField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.forever.zhb.util.ValueNotNullUtil;

public class LuceneUtil {
	
	private static Log log = LogFactory.getLog(LuceneUtil.class);
	
	public static String INDEX_DIR = "C:\\lucene\\luceneIndex";
	
	public static Directory createDirectory(String path){
		if (StringUtils.isBlank(path)) {
			return new RAMDirectory();
		}
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		try {
			return FSDirectory.open(file);
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return null;
	}
	
	public static Analyzer createStandardAnalyzer(Version matchVersion){
		if (null == matchVersion) {
			matchVersion = Version.LUCENE_CURRENT;
		}
		return new StandardAnalyzer(matchVersion);
	}
	
	public static Analyzer createIKAnalyzer(){
		return new IKAnalyzer();
	}
	
	public static IndexWriterConfig createIndexWriterConfig(Version matchVersion,Analyzer analyzer){
		if (null == matchVersion) {
			matchVersion = Version.LUCENE_CURRENT;
		}
		if (null == analyzer) {
			analyzer = new StandardAnalyzer(matchVersion);
		}
		return new IndexWriterConfig(matchVersion,analyzer);
	}
	
	public static IndexWriter createIndexWriter(Directory directory,IndexWriterConfig indexWriterConfig){
		if (null == directory || null == indexWriterConfig) {
			return null;
		}
		try {
			indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
            if (IndexWriter.isLocked(directory)) {
                IndexWriter.unlock(directory);
            }
			return new IndexWriter(directory,indexWriterConfig);
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return null;
	}
	
	public static Document createDocument(){
		return new Document();
	}
	
	public static void addStringFieldToDocument(Document doc,String field,String value,Field.Store store){
		doc.add(new TextField(field, ValueNotNullUtil.valueNotNull(value),store));
	}
	
	public static DirectoryReader createDirectoryReader(Directory directory) {
		try {
			return DirectoryReader.open(directory);
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return null;
	}
	
	public static IndexReader createIndexReader(Directory directory){
		try {
			return IndexReader.open(directory);
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return null;
	}
	
	public static IndexSearcher createIndexSearcher(IndexReader indexReader){
		return new IndexSearcher(indexReader);
	}
	
	public static SortField createSortField(String field,SortField.Type type,boolean reverse){
		return new SortField(field,type,reverse);
	}
	
	/**
     * 更新索引
     * 
     * 例如：Term term = new Term("id","1234567");
     * 先去索引文件里查找id为1234567的Document，如果有就更新它(如果有多条，最后更新后只有一条)，如果没有就新增。
     * 数据库更新的时候，我们可以只针对某个列来更新，而lucene只能针对一行数据更新。
     * 
     * @param field Document的Field(类似数据库的字段)
     * @param value Field中的一个关键词
     * @param doc
     * @return
     */
    public static boolean updateIndex(IndexWriter indexWriter, Document doc,Term term) {
    	try {
        	indexWriter.updateDocument(term, doc);
            log.info("lucene update success.");
            return true;
        } catch (Exception e) {
            log.error("lucene update failure.", e);
            return false;
        }
    }
	
	/**
     * 删除整个索引库
     * 
     * @return
     */
	public static void deleteAllIndex(IndexWriter indexWriter){
		try {
			indexWriter.deleteAll();
			log.info("lucene delete all success.");
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}
	
	/**
     * 判断索引库是否已创建
     * 
     * @return true:存在，false：不存在
     * @throws Exception
     */
    public static boolean existsIndex() throws Exception {
        File file = new File(LuceneUtil.INDEX_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        String indexSufix = "/segments.gen";
        // 根据索引文件segments.gen是否存在判断是否是第一次创建索引
        File indexFile = new File(LuceneUtil.INDEX_DIR + indexSufix);
        return indexFile.exists();
    }

}
