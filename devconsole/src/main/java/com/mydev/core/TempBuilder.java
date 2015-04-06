package com.mydev.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 
 * 项目名字:轻应用 <br/>
 * 类名称:TempBuilder <br/>
 * 类描述:根据模板构建简单重复代码 <br/>
 * 创建人:wengmd <br/>
 * 创建时间:2014-09-12 16:12:37 <br/>
 * 修改人: 涂清平 修改时间:2014-09-24 16:12:37 <br/>
 * 修改备注:代码自动生成到配置指定的包
 * 
 * @version
 */

public class TempBuilder {
	final static String ENCODE = "UTF-8";
	private static final Logger logger = LoggerFactory
			.getLogger(TempBuilder.class);
	// 当前类路径
	private static String curPath = "";

	// 当前项目路径
	private String projectPath = "";

	private String classTarget = "";

	public TempBuilder() {
		// TODO Auto-generated constructor stub
		try {
			curPath = System.getProperty("user.dir") + "/";
			System.out.println(curPath);
			System.out.println(System.getProperty("user.dir"));
			logger.debug("curPath:" + curPath);
			File directory = new File("");// 参数为空
			projectPath = directory.getCanonicalPath();
			classTarget = projectPath + File.separatorChar + "target"
					+ File.separatorChar + "classes" + File.separatorChar;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getCurPath() {
		return curPath;
	}

	String getProjectPath() {
		return projectPath;
	}

	public String getClassTarget() {
		return classTarget;
	}

	/**
	 * 读取ftl，生成目标文件
	 * 
	 * @param parameters
	 * @param ftlfile
	 * @param outfile
	 * @throws Exception
	 */
	public void buildByFtl(Map<String, Object> parameters, File fttlPath,
			String ftlfile, String outfile) throws Exception {
		Configuration configuration = new Configuration();
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		if (fttlPath != null)
			configuration.setDirectoryForTemplateLoading(fttlPath);
		else
			configuration.setClassForTemplateLoading(TempBuilder.class, "/");
		Template template = configuration.getTemplate(ftlfile);
		OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(outfile),
					ENCODE);
			template.process(parameters, writer);

			writer.flush();
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	/**
	 * 读取存取取代值XML
	 * 
	 * @param path
	 *            文件路径
	 * @param keyvalueFileName
	 *            取代XML文件名称
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> loadIndex(String path,
			String keyvalueFileName, Map<String, Object> outMapKeys) throws Exception {
		Document doc = null;
		Map<String, Object> rt = null;
		rt = new HashMap<String, Object>();
		rt.putAll(outMapKeys);
		/**
		 * 设置系统默认
		 */
		rt.put("_now",
				FastDateFormat.getInstance("yyyy-MM-dd HH:mm:s").format(
						new Date()));
		// 读取并解析XML文档
		// SAXReader就是一个管道，用一个流的方式，把xml文件读出来
		// 下面的是通过解析xml字符串的
		String vPath = StringUtils.isEmpty(path) ? curPath : path;
		String vSfindex = StringUtils.isEmpty(keyvalueFileName) ? "index.xml"
				: keyvalueFileName;
		String xmlIndex = vPath + vSfindex;
		logger.debug("xmlIndex:" + xmlIndex);
		SAXReader reader = new SAXReader(); // SAXReader主要用于解析XML文件

		doc = reader.read(xmlIndex); // 将字符串转为XML

		dom4jDoc2map(doc, rt);

		// 第二次读取index.xml内容到map中
		StringBuilder newIndexContext = load2loadIndex(rt, new File(vPath),
				vSfindex);
		doc = DocumentHelper.parseText(newIndexContext.toString());
		dom4jDoc2map(doc, rt);

		return rt;
	}

	public static Map<String, Object> loadIndex(Map<String, Object> map)
			throws Exception {
		Document doc = null;
		Map<String, Object> rt = null;
		rt = new HashMap<String, Object>();
		rt.putAll(map);
		/**
		 * 设置系统默认
		 */
		rt.put("_now",
				FastDateFormat.getInstance("yyyy-MM-dd HH:mm:s").format(
						new Date()));
		// 读取并解析XML文档
		// SAXReader就是一个管道，用一个流的方式，把xml文件读出来
		// 下面的是通过解析xml字符串的
		String vPath = curPath;
		String vSfindex = "index.xml";
		String xmlIndex = vPath + vSfindex;
		logger.debug("xmlIndex:" + xmlIndex);
		SAXReader reader = new SAXReader(); // SAXReader主要用于解析XML文件

		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("index.xml");
		doc = reader.read(is); // 将字符串转为XML

		dom4jDoc2map(doc, rt);

		// 第二次读取index.xml内容到map中
		StringBuilder newIndexContext = load2loadIndex(rt, new File(vPath),
				vSfindex);
		System.out.println(newIndexContext.toString());
		doc = DocumentHelper.parseText(newIndexContext.toString());
		dom4jDoc2map(doc, rt);

		return rt;
	}

	protected static void dom4jDoc2map(Document doc,
			Map<String, Object> parameters) {
		Element rootElt = doc.getRootElement(); // 获取根节点
		// 对应要读取的key列表
		Element eleKeys = rootElt.element("keys");
		Iterator<Element> itEleKeys = eleKeys.elementIterator();
		while (itEleKeys.hasNext()) {

			Element eleKey = itEleKeys.next();
			VarKeyBean varKeyBean = new VarKeyBean();
			varKeyBean.setId(eleKey.elementText("id"));
			varKeyBean.setName(eleKey.elementText("name"));
			varKeyBean.setValue(eleKey.elementText("value"));
			parameters.put(varKeyBean.getId(), varKeyBean);
			logger.debug("id:" + varKeyBean.getId());
			logger.debug("name:" + varKeyBean.getName());
			logger.debug("value:" + varKeyBean.getValue());
		}
	}

	public static StringBuilder load2loadIndex(Map<String, Object> parameters,
			File fttlPath, String ftlfile) throws Exception {
		Configuration configuration = new Configuration();

		configuration.setObjectWrapper(new DefaultObjectWrapper());
		// 设置外部路径
		if (fttlPath != null)
			configuration.setDirectoryForTemplateLoading(fttlPath);
		else
			// 设置class路径
			configuration.setClassForTemplateLoading(TempBuilder.class, "/");
		Template template = configuration.getTemplate(ftlfile, ENCODE);
		OutputStreamWriter writer = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			writer = new OutputStreamWriter(byteArrayOutputStream, ENCODE);
			template.process(parameters, writer);

			writer.flush();
		} finally {
			if (writer != null)
				writer.close();
		}
		// 新的index文件内容
		StringBuilder newindex = new StringBuilder(
				byteArrayOutputStream.toString(ENCODE));
		return newindex;
	}

	/**
	 * 获取模板列表
	 * 
	 * @param path
	 *            获取模板路径
	 * @return
	 */
	public Collection<File> getPathFtls(String path) {
		String vPath = StringUtils.isEmpty(path) ? curPath : path;
		File flist = new File(vPath);
		if (flist.isFile()) {
			logger.warn(vPath + " is file");
			return null;
		}
		// 查找指定文件下的模板文件
		return FileUtils.listFiles(flist, new String[] { "ftl" }, true);
	}

	/**
	 * 构建文件
	 * 
	 * @param path
	 *            获取模板路径
	 * @param keyvalueFileName
	 *            存储取代XML文件名称
	 * @param outPath
	 *            输出文件
	 * @throws Exception
	 */
	public void build(String path, String outPath) throws Exception {
		build(path, null, outPath, null);
	}

	/**
	 * 构建文件
	 * 
	 * @param path
	 *            获取模板路径
	 * @param keyvalueFileName
	 *            存储取代XML文件名称
	 * @param outPath
	 *            输出文件
	 * @throws Exception
	 */
	public void build(String path, String outPath,
			Map<String, Object> outMapKeys) throws Exception {
		build(path, null, outPath, outMapKeys);
	}

	/**
	 * 构建文件
	 * 
	 * @param path
	 *            获取模板路径
	 * @param keyvalueFileName
	 *            存储取代XML文件名称
	 * @param outPath
	 *            输出文件
	 * @param outMapKeys
	 *            外部扩展key
	 * @throws Exception
	 */
	public void build(String path, String keyvalueFileName, String outPath,
			Map<String, Object> outMapKeys) throws Exception {
		// 获取模板要取代值
		Map<String, Object> mapKeys = loadIndex(path, keyvalueFileName, outMapKeys);

		build(mapKeys, path, keyvalueFileName, outPath);
	}

	public void build(Map<String, Object> mapKeys, String path,
			String keyvalueFileName, String outPath) throws Exception {
		// 模板地址
		File fftlpath = new File(StringUtils.isEmpty(path) ? curPath : path);
		Collection<File> fftls = getPathFtls(path);
		for (File file : fftls) {
			logger.debug("find ftl file:" + file);
			// 获取输出文件
			String fsname = file.getName();
			VarKeyBean varKeyBeanFtlFile = (VarKeyBean) mapKeys.get(fsname);
			String outFsname = varKeyBeanFtlFile.getValue();
			// 是否有指定路径
			int pathIdxSpe = outFsname.lastIndexOf('/');
			if (pathIdxSpe >= 0) {
				String extDir = outFsname.substring(0, pathIdxSpe);
				// 判断路径是否存在
				File newDir = new File(outPath + extDir);
				if (!newDir.exists()) {
					newDir.mkdirs();
				}
			}
			String outFileName = outPath + outFsname;
			logger.debug("out file:" + outFileName);
			buildByFtl(mapKeys, fftlpath, fsname, outFileName);
		}
	}

	public static void main(String[] args) throws Exception {
		TempBuilder tempBuilder = new TempBuilder();	
		tempBuilder.build("E:/work/edu/project/code_factory/",
				"E:/work/edu/project/code_factory/target/");

	}
}
