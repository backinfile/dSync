package com.backinfile.dSync;

import com.backinfile.dSync.gen.GenDSync;

public class DSyncGenerator {
	private Class<?> resourceLoaderClass = DSyncGenerator.class;
	private String templateFileDir = "/template";
	private String projectPath = "src\\main\\java";
	private String templateFileName = "proxy.ftl";
	private String targetPackagePath = "com.backinfile.dSync.tmp";
	private String fileName = "Handler.java";
	private String className = "Handler";

	public DSyncGenerator() {
	}

	public void genFile() {
		var gen = new GenDSync();
		gen.setResourceLoaderClass(resourceLoaderClass);
		gen.setTemplateFileDir(templateFileDir);
		gen.setProjectPath(projectPath);
		gen.setTemplateFileName(templateFileName);
		gen.setTargetPackagePath(targetPackagePath);
		gen.setFileName(fileName);
		gen.setClassName(className);
		gen.genFile();
	}

	public void setResourceLoaderClass(Class<?> resourceLoaderClass) {
		this.resourceLoaderClass = resourceLoaderClass;
	}

	public void setTemplateFileDir(String templateFileDir) {
		this.templateFileDir = templateFileDir;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setTargetPackagePath(String targetPackagePath) {
		this.targetPackagePath = targetPackagePath;
	}

	public static void main(String[] args) {
		var generator = new DSyncGenerator();
		generator.genFile();
	}
}
