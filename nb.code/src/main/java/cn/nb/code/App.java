package cn.nb.code;

import java.util.List;

public class App {

	public static void main(String[] args){
		try {
			List<TableObj> list = new DBUtil().getDBColumnList();
			CodeUtil code = new CodeUtil();
			String dbtype = PropertiesUtil.get("database.type");
			
			for(TableObj obj : list){
				
				String modelClassName = CharUpDownUtil.
					toUpperCaseFirstOne(PackageUtil.getModelClassName(obj.getTableName()));
				code.genModelClass(modelClassName, obj.getList());//生成dao
				code.genDaoClass(modelClassName);//生成dao
				code.genServiceClass(modelClassName);//生成service
				code.genRestControllerClass(modelClassName);//生成controller
				code.genControllerClass(modelClassName);//生成controller
				
				if("mysql".equalsIgnoreCase(dbtype)){
					code.genMybatisXml(modelClassName, obj.getList(), "sqlmap_mysql");//mysql生成mybatis配置文件
				}else if("oracle".equalsIgnoreCase(dbtype)){
					code.genMybatisXml(modelClassName, obj.getList(), "sqlmap_oracle");//oracle生成mybatis配置文件
				}else{
					//
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
