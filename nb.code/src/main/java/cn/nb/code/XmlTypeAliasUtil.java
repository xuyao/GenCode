package cn.nb.code;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class XmlTypeAliasUtil {

	public static String genResultMap(List<String> list){
		StringBuilder sb = new StringBuilder();
		for(String s : list){
			String[] arr = s.split(",");
			if(arr.length<3)
				System.err.println("XmlTypeAliasUtil========");
			if("ID".equals(arr[2])){
				//<id property="adminId" column="admin_id" />
				sb.append("<id property=\"");
				sb.append(arr[0]);
				sb.append("\" column=\"");
				sb.append(arr[0].toLowerCase());
				sb.append("\" />");
				sb.append("\n\t\t");
			}else{
				//<result property="name" column="name" />
				sb.append("<result property=\"");
				sb.append(arr[0]);
				sb.append("\" column=\"");
				sb.append(arr[0].toLowerCase());
				sb.append("\" />");
				sb.append("\n\t\t");
			}
		}
		return sb.toString();
	}
	
	public static List<String> getTypeAlias(File file){
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
	
}
