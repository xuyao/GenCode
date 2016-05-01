package cn.nb.code;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class CodeUtil {

	public void genModelClass(){
		
		
	}
	
	
	public void genDaoClass(String modelClassName){
		String pkg = PropertiesUtil.get("package");
		String pathFile = PropertiesUtil.get("java_outpath");
		String path = (pathFile + pkg + ".dao").replaceAll("\\.", "\\\\");
		
		System.out.println(path);
		
		PackageUtil.ifPackageNotExistCreate(path);
		
		System.out.println(path+ File.separator+ modelClassName + "Dao.java");
		File file = new File(path+ File.separator+ modelClassName + "Dao.java");
		try {
			file.createNewFile();
			String content = FileUtils.readFileToString(
					new File(CodeUtil.class.getResource("/vm").getFile() + "/dao"));
			content = content.replaceAll("\\$thisPackage", PackageUtil.getPackage()+".dao");
			content = content.replaceAll("\\$modelClass", PackageUtil.getModelClass(modelClassName));
			content = content.replaceAll("\\$thisClassName", modelClassName+"Dao");
			content = content.replaceAll("\\$modelName", modelClassName);
			content = content.replaceAll("\\$modelObj", modelClassName.toLowerCase());
			FileUtils.writeStringToFile(file, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void genServiceClass(String modelClassName){
		String pkg = PropertiesUtil.get("package");
		String pathFile = PropertiesUtil.get("java_outpath");
		String path = (pathFile + pkg + ".service").replaceAll("\\.", "\\\\");
		
		System.out.println(path);
		
		PackageUtil.ifPackageNotExistCreate(path);
		
		System.out.println(path+ File.separator+ modelClassName + "Service.java");
		File file = new File(path+ File.separator+ modelClassName + "Service.java");
		try {
			file.createNewFile();
			String content = FileUtils.readFileToString(
					new File(CodeUtil.class.getResource("/vm").getFile() + "/service"));
			content = content.replaceAll("\\$thisPackage", PackageUtil.getPackage()+".service");
			content = content.replaceAll("\\$modelClass", PackageUtil.getModelClass(modelClassName));
			content = content.replaceAll("\\$daoClass", PackageUtil.getDaoClass(modelClassName));
			content = content.replaceAll("\\$thisClassName", modelClassName+"Service");
			content = content.replaceAll("\\$modelName", modelClassName);
			content = content.replaceAll("\\$modelObj", modelClassName.toLowerCase());
			String daoName = modelClassName+"Dao";
			content = content.replaceAll("\\$daoName", daoName);
			content = content.replaceAll("\\$daoObj", daoName.toLowerCase());
			FileUtils.writeStringToFile(file, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void genControllerClass(String modelClassName){
		String pkg = PropertiesUtil.get("package");
		String pathFile = PropertiesUtil.get("java_outpath");
		String path = (pathFile + pkg + ".controller").replaceAll("\\.", "\\\\");
		
		System.out.println(path);
		
		PackageUtil.ifPackageNotExistCreate(path);
		
		System.out.println(path+ File.separator+ modelClassName + "Controller.java");
		File file = new File(path+ File.separator+ modelClassName + "Controller.java");
		try {
			file.createNewFile();
			String content = FileUtils.readFileToString(
					new File(CodeUtil.class.getResource("/vm").getFile() + "/controller"));
			content = content.replaceAll("\\$thisPackage", PackageUtil.getPackage()+".controller");
			content = content.replaceAll("\\$modelClass", PackageUtil.getModelClass(modelClassName));
			content = content.replaceAll("\\$thisClassName", modelClassName+"Service");
			content = content.replaceAll("\\$modelName", modelClassName);
			content = content.replaceAll("\\$modelObj", modelClassName.toLowerCase());
			
			content = content.replaceAll("\\$serviceClass", PackageUtil.getServiceClass(modelClassName));
			String serviceName = modelClassName+"Service";
			content = content.replaceAll("\\$serviceName", serviceName);
			content = content.replaceAll("\\$serviceObj", serviceName.toLowerCase());
			FileUtils.writeStringToFile(file, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void genMybatisXml(String modelClassName, List<String> list){
		String path = PropertiesUtil.get("mybatis_outpath")
				+ "mybatis" + File.separator + "mapper" + File.separator;
		
		System.out.println(path);
		PackageUtil.ifPackageNotExistCreate(path);
		File xmlFile = new File(path + modelClassName + "_sqlmap.xml");
		try{
		if(!xmlFile.exists())
			xmlFile.createNewFile();
		
		String content = FileUtils.readFileToString(
				new File(CodeUtil.class.getResource("/vm").getFile() + "/sqlmap"));
		content = content.replaceAll("\\$daoClass", PackageUtil.getDaoClass(modelClassName));
		content = content.replaceAll("\\$modelName", modelClassName);
		content = content.replaceAll("\\$modelObj", modelClassName.toLowerCase());
		content = content.replaceAll("\\$resultMap", XmlTypeAliasUtil.genResultMap(list));
		
		FileUtils.writeStringToFile(xmlFile, content);
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
