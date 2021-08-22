package com.backinfile.dSync.gen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.backinfile.dSync.Log;

/**
 * 每个实现类对应一个模板文件，可以生成多个代码文件
 */
public abstract class GenBase {

	private final Configuration configuration;
	protected final Map<String, Object> rootMap = new HashMap<>();

	private String templateFileName = "";
	private String targetPackagePath = "";
	private String fileName = "";
	private static final String TemplateFileDir = "/template";
	private static final String projectPath = "src\\main\\java";

	public boolean canGen = true;

	public GenBase() {

		configuration = new Configuration(Configuration.VERSION_2_3_22);

		configuration.setClassForTemplateLoading(GenBase.class, TemplateFileDir);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}

	protected void setTemplateFileName(String fileName) {
		templateFileName = fileName;
	}

	protected void setTargetPackage(String packagePath) {
		targetPackagePath = packagePath.replace(".", "\\");
	}

	protected void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int genFile() {
		if (!canGen)
			return ErrorCode.GEN_CANNOT_GEN;
		Template temp = null;
		try {
			temp = configuration.getTemplate(templateFileName);
		} catch (IOException e) {
			Log.Gen.error("load template error", e);
			return ErrorCode.GEN_TEMPLATE_FILE_NOT_FOUND;
		}

		File file = new File(projectPath + "\\" + targetPackagePath + "\\" + fileName);
//        File file = Path.of(projectPath, targetPackagePath, fileName).toFile();
//        Log.Gen.info("target path = {0}", file.getPath());
		if (!file.exists()) {
//            file.mkdir();
			try {
				file.createNewFile();
			} catch (IOException e) {
				Log.Gen.error("create New File error", e);
				return ErrorCode.GEN_FILE_CREATE_FAILED;
			}
		}
		try (FileWriter writer = new FileWriter(file)) {
			temp.process(rootMap, writer);
		} catch (IOException | TemplateException e) {
			Log.Gen.error("process ftl file error", e);
			return ErrorCode.GEN_FILE_WRITE_FAILED;
		}

		Log.Gen.info("生成{}成功", fileName);
		return ErrorCode.OK;
	}

	public String getTargetFileName() {
		return fileName;
	}
}
