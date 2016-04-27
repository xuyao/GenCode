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

	public static List<String> getDBColumnList() throws Exception {  
		
        String driver = "com.mysql.jdbc.Driver";  
        String url = "jdbc:mysql://218.241.108.40:3306/nbdb?noAccessToProcedureBodies=true&useUnicode=true&characterEncoding=UTF-8";  
        String user = "museum";  
        String password = "museum123456";  

        Class.forName(driver);  
        Connection conn = DriverManager.getConnection(url, user, password);  
        if (!conn.isClosed())  
            System.out.println("Succeeded connecting to the Database!");  
        else  
            System.err.println("connect filed");  
        // 获取所有表名  
        Statement statement = conn.createStatement();  
  
        List<String> list = getTables(conn);  
  
        statement.close();  
        conn.close();  
        
        return list;
    }  
  
    private static List<String> getTables(Connection conn) throws SQLException {
    	List<String> list = new ArrayList<String>();
        DatabaseMetaData dbMetData = conn.getMetaData();  
        ResultSet rs = dbMetData.getTables(null, "mysql", null, new String[] { "TABLE"});  
  
        while (rs.next()) {
            if (rs.getString(4) != null && rs.getString(4).equalsIgnoreCase("TABLE")) {  
                String tableName = rs.getString(3).toLowerCase();  
                System.out.println(tableName);  
                // 根据表名提前表里面信息：  
                ResultSet colRet = dbMetData.getColumns(null, "%", tableName, "%");  
                while (colRet.next()) {
                    String columnName = colRet.getString("COLUMN_NAME");  
                    String columnType = colRet.getString("TYPE_NAME");
//                    int datasize = colRet.getInt("COLUMN_SIZE");
//                    int digits = colRet.getInt("DECIMAL_DIGITS"); 
//                    int nullable = colRet.getInt("NULLABLE");
                    System.out.println(columnName + " " + columnType);
                    list.add(columnName+","+changeDbType(columnType));
                }
            }
        }
        rs.close();
        return list;
    }
    
    
    private static String changeDbType(String column){
    	if(column.equalsIgnoreCase("varchar"))
    		return "String";
    	if(column.equalsIgnoreCase("varchar2"))
    		return "String";
    	if(column.equalsIgnoreCase("int"))
    		return "Integer";
    	if(column.equalsIgnoreCase("long"))
    		return "Integer";
    	if(column.equalsIgnoreCase("tinyint"))
    		return "Integer";
    	if(column.equalsIgnoreCase("datetime"))
    		return "java.util.Date";
    	if(column.equalsIgnoreCase("char"))
    		return "char";
    	if(column.equalsIgnoreCase("boolean"))
    		return "boolean";
    	return "String";
    }
    
}
