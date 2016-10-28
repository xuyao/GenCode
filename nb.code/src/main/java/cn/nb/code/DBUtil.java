package cn.nb.code;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
  

public class DBUtil {

	public List<TableObj> getDBColumnList() throws Exception {  
		
		String dbtype = PropertiesUtil.get("database.type");
		
        String driver = PropertiesUtil.get("jdbc.driver");
        String url = PropertiesUtil.get("jdbc.url");
        String user = PropertiesUtil.get("jdbc.user");
        String password = PropertiesUtil.get("jdbc.password");
        
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        //获取所有表名
        Statement statement = conn.createStatement();

        List<TableObj> list = getTables(conn, dbtype, statement);

        statement.close();
        conn.close();

        return list;
    }

	

	private List<TableObj> getTables(Connection conn, String dbtype, Statement statement) throws SQLException {
		if("mysql".equalsIgnoreCase(dbtype)){
			return getMysqlTables(conn);
		}else if("oracle".equalsIgnoreCase(dbtype)){
			return getOracleTables(conn, statement);
		}else{
			return getMysqlTables(conn);
		}
		
	}

	
	
    private List<TableObj> getOracleTables(Connection conn , Statement statement) throws SQLException {
    	List<TableObj> tableList = new ArrayList<TableObj>();
    	
        String sqlTable = "select table_name from user_tables";
        statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs= statement.executeQuery(sqlTable);
        
        while (rs.next()) {
        	if (StringUtils.isNotEmpty(rs.getString("TABLE_NAME"))) {
        		String tableName = rs.getString("TABLE_NAME");
//        		System.out.println(tableName);
          	 ResultSet rst = null;
          	 Statement stmt=null;
          	 //Connection conn = null;
          	 TableObj to = new TableObj();
          	 String sqlm="select COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION, DATA_SCALE, NULLABLE, COLUMN_ID　"
          	 		+ " from user_tab_columns where table_name =UPPER('"+tableName+"')";
          	 List<String> columnslist = new ArrayList<String>();
   
//          	     conn = dbcon.dbconn();
          	     stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
          	   rst= stmt.executeQuery(sqlm);
          	     while(rst.next()){
          	    	 String columnName = rst.getString("COLUMN_NAME");  
          	    	 String columnType = rst.getString("DATA_TYPE"); 
//   					fmap.put("COLUMN_NAME", rs.getString("COLUMN_NAME"));//获取字段名
//          	        fmap.put("DATA_TYPE",rs.getString("DATA_TYPE"));    //获取数据类型
//          	        fmap.put("DATA_LENGTH",rs.getString("DATA_LENGTH"));//获取数据长度
//          	        fmap.put("DATA_PRECISION","");
//          	        fmap.put("DATA_PRECISION",rs.getString("DATA_PRECISION"));//获取数据长度
//          	        fmap.put("DATA_SCALE","");
//          	        fmap.put("DATA_SCALE",rs.getString("DATA_SCALE"));//获取数据精度
//          	        fmap.put("NULLABLE",rs.getString("NULLABLE"));    //获取是否为空
//          	        fmap.put("COLUMN_ID",rs.getString("COLUMN_ID"));    //字段序号
          	        columnslist.add(columnName+","+changeDbType(columnType)+",C");
          	     }
                to.setList(columnslist);
                tableList.add(to); 	
        		
        	}

        }
             
    	      conn.close();

        return tableList;
    }
    

    
    private List<TableObj> getMysqlTables(Connection conn) throws SQLException {
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
