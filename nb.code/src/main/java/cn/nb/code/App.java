package cn.nb.code;

import java.util.List;

public class App {

	public static void main(String[] args){
		try {
			List<TableObj> list = new DBUtil().getDBColumnList();
			CodeUtil code = new CodeUtil();
			for(TableObj obj : list){
				
				String modelClassName = CharUpDownUtil.
					toUpperCaseFirstOne(PackageUtil.getModelClassName(obj.getTableName()));
//				CodeUtil.genModelClass(modelClassName);//生成dao
				code.genDaoClass(modelClassName);//生成dao
				code.genServiceClass(modelClassName);//生成service
				code.genControllerClass(modelClassName);//生成controller
				code.genMybatisXml(modelClassName, obj.getList());//生成mybatis配置文件
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
