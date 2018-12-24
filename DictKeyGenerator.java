package com.duobei.tools.dic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

//create dc.java
public class DictKeyGenerator {

	public static void main(String[] args) throws SQLException {

		DBUtil dbUtil = new DBUtil();

		List<DictVo> dctList = dbUtil.selectTopCode();
		Configuration cfg = new Configuration();

		try {

			System.out.println("Merging template...");

			cfg.setObjectWrapper(new DefaultObjectWrapper());

			Template temp = cfg.getTemplate("/duobei-console-tools/src/main/resources/ZD.ftl");

			/* Create a data-model */
			Map<String, List<DictVo>> root = new HashMap<String, List<DictVo>>();
			root.put("dctList", dctList);

			File f = new File("C:/Users/wm/Desktop/aa/ZD.java");

			if (f.exists()) {
				System.out.println("Deleting the old file.");

				if (!f.delete()) {
					Thread.sleep(5000);

					if (!f.delete()) {
						System.out.println("File can not be deleted, please delete it manually.");
						return;
					}
				}

			}

			f.createNewFile();

			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");

			temp.process(root, osw);

			osw.flush();

			System.out.println("File created at " + f.getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
