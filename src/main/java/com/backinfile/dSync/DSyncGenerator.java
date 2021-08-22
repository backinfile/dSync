package com.backinfile.dSync;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringJoiner;

import com.backinfile.dSync.gen.GenDSync;
import com.backinfile.dSync.model.DSyncException;
import com.backinfile.dSync.parser.TokenWorker;

public class DSyncGenerator {
	private Class<?> resourceLoaderClass = DSyncGenerator.class;
	private String templateFileDir = "/template";
	private String projectPath = "src\\main\\java";
	private String templateFileName = "proxy.ftl";
	private String targetPackagePath = "com.backinfile.dSync.tmp";
	private String fileName = "Handler.java";
	private String className = "Handler";
	private String dsSource = "";

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
		gen.setDsSource(dsSource);
		gen.genFile();
	}

	public void setDsSource(String dsSource) {
		this.dsSource = dsSource;
	}
	
	public void setClassName(String className) {
		this.className = className;
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
		generator.setDsSource(getDSSource());
		generator.genFile();
	}

	private static String getDSSource() {
		String resourcePath = TokenWorker.class.getClassLoader().getResource("demo.ds").getPath();
		Path path = Paths.get(resourcePath.substring(1));
		try {
			List<String> lines = Files.readAllLines(path);
			var sj = new StringJoiner("\n");
			for (var line : lines) {
				sj.add(line);
			}
			return sj.toString();
		} catch (IOException e) {
			throw new DSyncException(e);
		}
	}
}
