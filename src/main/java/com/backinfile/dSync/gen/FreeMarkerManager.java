package com.backinfile.dSync.gen;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import com.backinfile.dSync.Log;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkerManager {
	public static void formatFile(String filePath, String fileName, Map<String, Object> rootMap, String outPath,
			String outFileName) {
		try {
			var config = new Configuration(Configuration.VERSION_2_3_22);
			config.setDefaultEncoding("UTF-8");
			config.setDirectoryForTemplateLoading(new File(filePath));
			config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

			var file = new File(outPath, outFileName);
			file.getParentFile().mkdirs();
			try (FileWriter writer = new FileWriter(file)) {
				var template = config.getTemplate(fileName);
				template.process(rootMap, writer);
			}
			Log.Gen.info("gen {} success", file.getPath());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void formatFile(String fileName, Map<String, Object> rootMap, String outPath, String outFileName) {
		try {
			var config = new Configuration(Configuration.VERSION_2_3_22);
			config.setDefaultEncoding("UTF-8");
			config.setClassForTemplateLoading(FreeMarkerManager.class, "");
			config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

			var file = new File(outPath, outFileName);
			file.getParentFile().mkdirs();
			try (FileWriter writer = new FileWriter(file)) {
				var template = config.getTemplate(fileName);
				template.process(rootMap, writer);
			}
			Log.Gen.info("gen {} success", file.getPath());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
