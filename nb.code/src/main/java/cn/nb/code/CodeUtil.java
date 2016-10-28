package cn.nb.code;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class CodeUtil {

	public void genModelClass(String modelClassName, List<String> list){
		String pkg = PropertiesUtil.get("package");
		String pathFile = PropertiesUtil.get("java_outpath");
		String path = (pathFile + pkg + ".model").replaceAll("\\.", "\\\\");
		
		System.out.println(path);
		
		PackageUtil.ifPackageNotExistCreate(path);
		
		System.out.println(path+ File.separator+ modelClassName + ".java");
		File file = new File(path+ File.separator+ modelClassName + ".java");
		try {
			file.createNewFile();
			String content = FileUtils.readFileToString(
					new File(CodeUtil.class.getResource("/vm").getFile() + "/model"));
			content = content.replaceAll("\\$thisPackage", PackageUtil.getPackage()+".model");
			content = content.replaceAll("\\$modelName", modelClassName);
			content = content.replaceAll("\\$param", genParam(list));
			content = content.replaceAll("\\$method", genMethod(list));
			
			FileUtils.writeStringToFile(file, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
			content = content.replaceAll("\\$mybatis", PropertiesUtil.get("mybatis"));
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
	
	
	public void genRestControllerClass(String modelClassName){
		String pkg = PropertiesUtil.get("package");
		String pathFile = PropertiesUtil.get("java_outpath");
		String path = (pathFile + pkg + ".restcontroller").replaceAll("\\.", "\\\\");
		
		System.out.println(path);
		
		PackageUtil.ifPackageNotExistCreate(path);
		
		System.out.println(path+ File.separator+ modelClassName + "Controller.java");
		File file = new File(path+ File.separator+ modelClassName + "Controller.java");
		try {
			file.createNewFile();
			String content = FileUtils.readFileToString(
					new File(CodeUtil.class.getResource("/vm").getFile() + "/restcontroller"));
			content = content.replaceAll("\\$thisPackage", PackageUtil.getPackage()+".controller");
			content = content.replaceAll("\\$modelClass", PackageUtil.getModelClass(modelClassName));
			content = content.replaceAll("\\$thisClassName", modelClassName+"Service");
			content = content.replaceAll("\\$modelName", modelClassName);
			content = content.replaceAll("\\$modelObj", modelClassName.toLowerCase());
			
			content = content.replaceAll("\\$serviceClass", PackageUtil.getServiceClass(modelClassName));
			String serviceName = modelClassName+"Service";
			content = content.replaceAll("\\$serviceName", serviceName);
			content = content.replaceAll("\\$serviceObj", serviceName.toLowerCase());
			content = content.replaceAll("\\$package", pkg);
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
			content = content.replaceAll("\\$package", pkg);
			FileUtils.writeStringToFile(file, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void genMybatisXml(String modelClassName, List<String> list, String tmplate){
		String path = PropertiesUtil.get("mybatis_outpath")
				+ "mybatis" + File.separator + "mapper" + File.separator;
		
		System.out.println(path);
		PackageUtil.ifPackageNotExistCreate(path);
		File xmlFile = new File(path + modelClassName + "_sqlmap.xml");
		try{
		if(!xmlFile.exists())
			xmlFile.createNewFile();
		
		String content = FileUtils.readFileToString(
				new File(CodeUtil.class.getResource("/vm").getFile() + "/"+tmplate));
		content = content.replaceAll("\\$daoClass", PackageUtil.getDaoClass(modelClassName));
		content = content.replaceAll("\\$modelName", modelClassName);
		content = content.replaceAll("\\$modelObj", modelClassName.toLowerCase());
		content = content.replaceAll("\\$resultMap", genResultMap(list));
		content = content.replaceAll("\\$searchcondition", genSearchCondition(list));
		
		StringBuilder addColumn = new StringBuilder();
		StringBuilder addValue = new StringBuilder();
		StringBuilder updateColumn = new StringBuilder();
		StringBuilder searchSelect = new StringBuilder();
		for(String s : list){
			String[] arr = s.split(",");
			if(!"ID".equals(arr[2])){//如果不是id
				addColumn.append(arr[0].toLowerCase()).append(",");
				addValue.append("#{").append(arr[0].toLowerCase()).append("},");
				updateColumn.append(arr[0].toLowerCase()).append("=#{").
						append(arr[0].toLowerCase()).append("},");
			}
			searchSelect.append("t.").append(arr[0]).append(",");
		}
		addColumn.deleteCharAt(addColumn.length()-1);
		addValue.deleteCharAt(addValue.length()-1);
		updateColumn.deleteCharAt(updateColumn.length()-1);
		searchSelect.deleteCharAt(searchSelect.length()-1);
		content = content.replaceAll("\\$searchSelect", searchSelect.toString());
		content = content.replaceAll("\\$addColumn", addColumn.toString());
		content = content.replaceAll("\\$addValue", addValue.toString());
		content = content.replaceAll("\\$updateColumn", updateColumn.toString());
		
		FileUtils.writeStringToFile(xmlFile, content);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private String genSearchCondition(List<String> list){
		StringBuilder sb = new StringBuilder();
		for(String s : list){
			String[] arr = s.split(",");
			sb.append("<if test=\"").append(arr[0]).append(" != null and ")
			.append(arr[0]).append(" != ''\">").append("\n\t\t").append("and t.").append(arr[0])
			.append("=#{").append(arr[0]).append("}").append("\n\t").append("</if>").append("\n\t");
		}
		return sb.toString();
	}
	
	
	private String genResultMap(List<String> list){
		StringBuilder sb = new StringBuilder();
		for(String s : list){
			String[] arr = s.split(",");
			if(arr.length<3)
				System.err.println("XmlTypeAliasUtil========");
			if("ID".equals(arr[2])){
				sb.append("<id property=\"");
				sb.append(arr[0]);
				sb.append("\" column=\"");
				sb.append(arr[0].toLowerCase());
				sb.append("\" />");
				sb.append("\n\t\t");
			}else{
				sb.append("<result property=\"");
				sb.append(arr[0]);
				sb.append("\" column=\"");
				sb.append(arr[0].toLowerCase());
				sb.append("\" />");
				sb.append("\n\t\t");
			}
		}
		sb.deleteCharAt(sb.length()-1);//delete last \t
		return sb.toString();
	}
	
	
	private List<String> getTypeAlias(File file){
		List<String> list = new ArrayList<String>();
		try{
			SAXReader reader = new SAXReader();  
			Document document = reader.read(file);  
			Element root = document.getRootElement();
			Element eta = root.element("typeAliases");
			Iterator it= eta.elements("typeAlias").iterator();  
	        while(it.hasNext()){
	        	Element elm=(Element)it.next();
	        	list.add(elm.attributeValue("type"));
	        }
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
	private String genParam(List<String> list){
		StringBuilder sb = new StringBuilder();
		for(String s : list){
			String[] arr = s.split(",");
			sb.append("\n\t").append("private ").append(arr[1]).append(" ").append(arr[0])
			.append(";");
		}
		return sb.toString();
	}
	
	
	private String genMethod(List<String> list){
		StringBuilder sb = new StringBuilder();
		for(String s : list){
			String[] arr = s.split(",");
			sb.append("\n\t").append("public void set").append(CharUpDownUtil.toUpperCaseFirstOne(arr[0]))
			.append("(").append(arr[1]).append(" ").append(arr[0]).append(")").append(" ")
			.append("{").append("\n\t\t").append("this.").append(arr[0]).append(" = ")
			.append(arr[0]).append(";").append("\n\t").append("}");
			sb.append("\n\t").append("public ").append(arr[1]).append(" get")
			.append(CharUpDownUtil.toUpperCaseFirstOne(arr[0])).append("() {").append("\n\t\t")
			.append("return ").append(arr[0]).append(";").append("\n\t").append("}");
		}
		return sb.toString();
	}
	
}
