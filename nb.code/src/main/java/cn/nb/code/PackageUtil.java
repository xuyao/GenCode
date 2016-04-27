package cn.nb.code;

import java.io.File;

public class PackageUtil {

	
	//得到model类名
	public static String getClassName(String classPath){
		String[] str = classPath.split("\\.");
		String modelClassName = str[str.length-1];
		return modelClassName;
	}
	
	//得到当前类名
	public static String getThisClassName(String modelClass, String type){
		String modelClassName = getClassName(modelClass);
		String packageClass = modelClassName + CharUpDownUtil.toUpperCaseFirstOne(type);
		return packageClass;
	}
	
	
	//得到包路径
	public static String getPackage(String modelClass, String type){
		modelClass = modelClass.substring(0, modelClass.lastIndexOf("."));
		String daoPackage = modelClass.substring(0, modelClass.lastIndexOf("."))
				+"."+type;
		return daoPackage;
	}
	
	
	//得到本类的全路径和名称
	public static String getThisClass(String modelClass, String type){
		String modelClassName = getClassName(modelClass);
		modelClass = modelClass.substring(0, modelClass.lastIndexOf("."));
		String daoPackage = modelClass.substring(0, modelClass.lastIndexOf("."))
				+"."+type+".";
		String packageClass = daoPackage + modelClassName + CharUpDownUtil.toUpperCaseFirstOne(type);
		return packageClass;
	}
	
	
	public static void ifPackageNotExistCreate(String pathFile){
		File f = new File(pathFile);
		if(!f.exists()){
			try {
				f.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
