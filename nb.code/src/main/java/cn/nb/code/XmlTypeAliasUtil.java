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

	public static String genResultMap(Field[] field){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<field.length; i++){
			if(field[i].getAnnotation(Id.class)!=null){
				//<id property="adminId" column="admin_id" />
				sb.append("<id property=\"");
				sb.append(field[i].getName());
				sb.append("\" column=\"");
				sb.append(field[i].getName().toLowerCase());
				sb.append("\" />");
				sb.append("\n\t\t");
			}else{
				//<result property="name" column="name" />
				sb.append("<result property=\"");
				sb.append(field[i].getName());
				sb.append("\" column=\"");
				sb.append(field[i].getName().toLowerCase());
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
