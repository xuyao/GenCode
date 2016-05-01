package cn.nb.code;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class CodeUtil {

	public void genModelClass(){
		
		
	}
	
	
	public void genDaoClass(String modelClassName){
		String pkg = PropertiesUtil.get("package");
		String pathFile = PropertiesUtil.get("java_outpath");
		String daoPath = (pathFile + pkg + ".dao").replaceAll("\\.", "\\\\");
		
		System.out.println(daoPath);
		
		PackageUtil.ifPackageNotExistCreate(daoPath);
		
		System.out.println(daoPath+ File.separator+ modelClassName + "Dao.java");
		File daoFile = new File(daoPath+ File.separator+ modelClassName + "Dao.java");
		try {
			daoFile.createNewFile();
			String content = FileUtils.readFileToString(
					new File(CodeUtil.class.getResource("/vm").getFile() + "/dao"));
			content = content.replaceAll("\\$thisPackage", PackageUtil.getPackage()+".dao");
			content = content.replaceAll("\\$modelClass", PackageUtil.getModelClass(modelClassName));
			content = content.replaceAll("\\$thisClassName", modelClassName+"Dao");
			content = content.replaceAll("\\$modelName", modelClassName);
			content = content.replaceAll("\\$modelObj", modelClassName.toLowerCase());
			FileUtils.writeStringToFile(daoFile, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void genServiceClass(){
		
		
	}
	
	
	public void genControllerClass(){
		
		
	}
	
}
