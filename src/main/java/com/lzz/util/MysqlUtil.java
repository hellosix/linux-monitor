package com.lzz.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * Created by gl49 on 2017/11/7.
 */
public class MysqlUtil extends SQLBase{
    private static byte[] lock = new byte[0];
    private static volatile  MysqlUtil mysqlUtil;
    private static SQLBase getInstance(){
        if( null == mysqlUtil ){
            synchronized ( lock ){
                if( null == mysqlUtil ){
                    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
                    mysqlUtil = (MysqlUtil) ctx.getBean("mysqlUitl");
                }
            }
        }
        return mysqlUtil;
    }

    /**
     *  插入一条数据
     * @param sql
     * @return 插入的 ID
     */
    public static int insertId(String sql){
        return getInstance().baseInsert( sql );
    }

    public static int insert(String sql){
        return getInstance().baseInsert( sql );
    }

    public static int update(String sql){
        return getInstance().baseUpdate( sql );
    }

    /**
     * 获取 roles 列表
     * @return
     */
    public static List select(String sql){
        return getInstance().baseSelect( sql );
    }

    public static Map selectMap(String sql){
        return getInstance().baseSelectRow( sql );
    }

    public static List<Map> getTables(){
        String sql = "show tables";
        return select(sql);
    }

    public static boolean delete( String sql ){
        return  getInstance().baseDelete( sql );
    }

    public static Map selectRow(String sql ){
        return getInstance().baseSelectRow( sql );
    }

    public static void initMonitorTable(){
        getInstance().initMonitorTables();
    }
    public static void createNodeInfoTable (String table){
        getInstance().createNodeInfoTableTemplete( table );
    }

    public static void dropNodeInfoTable(String table){
        getInstance().dropNodeInfoTableTemplete(table);
    }
}