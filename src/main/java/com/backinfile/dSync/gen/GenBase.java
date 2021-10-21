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
abstract class GenBase {

	private Configuration configuration;
	protected final Map<String, Object> rootMap = new HashMap<>();

	protected String templateFileName = "";
	protected String targetPackagePath = "";
	protected String targetPackagePathHead = "";
	protected String fileName = "";
	protected String className = "";
	protected String templateFileDir = "/template";
	protected String projectPath = "src\\main\\java";
	protected Class<?> resourceLoaderClass = GenBase.class;

	public boolean canGen = true;

	public GenBase() {

	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public void setTargetPackagePath(String targetPackagePath) {
		this.targetPackagePath = targetPackagePath.replace(".", "\\");
		this.targetPackagePathHead = targetPackagePath;
	}

	public void setTemplateFileDir(String templateFileDir) {
		this.templateFileDir = templateFileDir;
	}

	public void setResourceLoaderClass(Class<?> resourceLoaderClass) {
		this.resourceLoaderClass = resourceLoaderClass;
	}

	public void setTemplateFileName(String fileName) {
		templateFileName = fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int genFile() {
		if (!canGen)
			return ErrorCode.GEN_CANNOT_GEN;

		if (configuration == null) {
			configuration = new Configuration(Configuration.VERSION_2_3_22);
			configuration.setClassForTemplateLoading(resourceLoaderClass, templateFileDir);
			configuration.setDefaultEncoding("UTF-8");
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		}

		Template temp = null;
		try {
			temp = configuration.getTemplate(templateFileName);
		} catch (IOException e) {
			Log.Gen.error("load template error", e);
			return ErrorCode.GEN_TEMPLATE_FILE_NOT_FOUND;
		}

		File file = new File(projectPath + "\\" + targetPackagePath + "\\" + fileName);
		if (!file.exists()) {
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
