package cn.nb.code;

import java.io.File;

public class PackageUtil {

	
	public static String getPackage(){
		return PropertiesUtil.get("package");
	}
	
	
	public static String getCutTableName(String tableName){
		String cutTableName = tableName.substring(tableName.indexOf("_")+1);
		return cutTableName;
	}
	
	
	public static String getModelClassName(String tableName){
		String modelClassName = tableName.substring(tableName.indexOf("_")+1);
		modelClassName = modelClassName.replaceAll("_", "");
		return modelClassName;
	}
	
	public static String getModelClass(String modelClassName){
		return getPackage()+".model."+modelClassName;
	}
	
	public static String getDaoClass(String modelClassName){
		return getPackage()+".dao."+modelClassName+"Dao";
	}
	
	public static String getServiceClass(String modelClassName){
		return getPackage()+".service."+modelClassName+"Service";
	}
	
	public static String getControllerClass(String modelClassName){
		return getPackage()+".controller."+modelClassName+"Controller";
	}
	
	
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
				f.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
