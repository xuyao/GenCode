package cn.nb.code;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
  

public class DBUtil {

	public List<TableObj> getDBColumnList() throws Exception {  
		
        String driver = PropertiesUtil.get("jdbc.driver");  
        String url = PropertiesUtil.get("jdbc.url");  
        String user = PropertiesUtil.get("jdbc.user");  
        String password = PropertiesUtil.get("jdbc.password");  

        Class.forName(driver);  
        Connection conn = DriverManager.getConnection(url, user, password);  
        // 获取所有表名  
        Statement statement = conn.createStatement();  
  
        List<TableObj> list = getTables(conn);  
  
        statement.close();  
        conn.close();  
        
        return list;
    }  
  
    private List<TableObj> getTables(Connection conn) throws SQLException {
    	List<TableObj> tableList = new ArrayList<TableObj>();
    	
        DatabaseMetaData dbMetData = conn.getMetaData();  
        ResultSet rs = dbMetData.getTables(null, "mysql", null, new String[] { "TABLE"});  
  
        while (rs.next()) {
            if (rs.getString(4) != null && rs.getString(4).equalsIgnoreCase("TABLE")) {
            	TableObj to = new TableObj();
            	List<String> columnslist = new ArrayList<String>();
                String tableName = rs.getString(3).toLowerCase();
//                System.out.println(tableName);
                to.setTableName(tableName);//设置表名
                // 根据表名提前表里面信息:
                ResultSet colID = dbMetData.getColumns(null, "%", tableName, "%ID");
                colID.next();
                String IDName = colID.getString("COLUMN_NAME");  
                String IDType = colID.getString("TYPE_NAME");
                columnslist.add(IDName+","+changeDbType(IDType)+",ID");
                
                ResultSet colRet = dbMetData.getColumns(null, "%", tableName, "%");  
                while (colRet.next()) {
                    String columnName = colRet.getString("COLUMN_NAME");  
                    String columnType = colRet.getString("TYPE_NAME");
                	if(IDName.equals(columnName))
                		continue;
//                    int datasize = colRet.getInt("COLUMN_SIZE");
//                    int digits = colRet.getInt("DECIMAL_DIGITS"); 
//                    int nullable = colRet.getInt("NULLABLE");
//                    System.out.println(columnName + " " + changeDbType(columnType));
                    columnslist.add(columnName+","+changeDbType(columnType)+",C");
                }
                to.setList(columnslist);
                tableList.add(to);
            }
        }
        rs.close();
        return tableList;
    }
    
    
    private String changeDbType(String column){
    	if(column.equalsIgnoreCase("varchar"))
    		return "String";
    	if(column.equalsIgnoreCase("varchar2"))
    		return "String";
    	if(column.equalsIgnoreCase("text"))
    		return "String";
    	if(column.equalsIgnoreCase("int"))
    		return "Integer";
    	if(column.equalsIgnoreCase("long"))
    		return "Long";
    	if(column.equalsIgnoreCase("tinyint"))
    		return "Integer";
    	if(column.equalsIgnoreCase("datetime"))
    		return "java.util.Date";
    	if(column.equalsIgnoreCase("char"))
    		return "String";
    	if(column.equalsIgnoreCase("boolean"))
    		return "boolean";
    	return "String";
    }
    
}
