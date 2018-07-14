package demo;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import org.junit.Test;
import utils.JDBCUtils;

import java.sql.*;

public class TestOracle {
    /**
     * create or replace procedure queryEmpInformation(eno in number,
     *                                              pename out varchar2,
     *                                              psal out number,
     *                                              pjob out varchar2)
     */
    @Test
    public void testProcedure() {
        // {call <procedure-name>[(<arg1>,<arg2>,...)]}
        String sql = "{call queryEmpInformation(?,?,?,?)}";

        Connection conn = null;
        CallableStatement call = null;
        try {
            conn = JDBCUtils.getConnection();
            call = conn.prepareCall(sql);

            //对于in参数，赋值
            call.setInt(1, 7839);

            //对于out参数，声明
            call.registerOutParameter(2, OracleTypes.VARCHAR);
            call.registerOutParameter(3, OracleTypes.NUMBER);
            call.registerOutParameter(4, OracleTypes.VARCHAR);

            //执行
            call.execute();

            //取出结果
            String name = call.getString(2);
            double sal = call.getDouble(3);
            String job = call.getString(4);

            System.out.println(name + "\t" + sal + "\t" + job);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn, call, null);
        }
    }


    /**
     * create or replace function queryEmpIncome(eno in number)
     return number
     */
    @Test
    public void testFunciton() {
        // {?= call <procedure-name>[(<arg1>,<arg2>,...)]}
        String sql = "{?= call queryEmpIncome(?)}";

        Connection conn = null;
        CallableStatement call = null;
        try {
            conn = JDBCUtils.getConnection();
            call = conn.prepareCall(sql);

            //对于返回值，声明
            call.registerOutParameter(1,OracleTypes.NUMBER);;

            //对于输入参数，赋值
            call.setInt(2,7839);
            call.execute();

            double income = call.getDouble(1);
            System.out.println(income);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn,call,null);
        }

    }

    @Test
    public void testCursorProcedure() {
        String sql = "{call MYPACKAGE.QUERYEMPLIST(?,?)}";
        Connection conn = null;
        CallableStatement call = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            call = conn.prepareCall(sql);

            //对于输入参数，赋值
            call.setInt(1,30);

            //对于输出参数，声明
            call.registerOutParameter(2, OracleTypes.CURSOR);

            //执行
            call.execute();

            //取出结果
            rs = ((OracleCallableStatement)call).getCursor(2);

            while (rs.next()) {
                int eno = rs.getInt("empno");
                String name = rs.getString("ename");
                String job = rs.getString("job");
                String mgr = rs.getString("mgr");
                Date date = rs.getDate("hiredate");
                double sal = rs.getDouble("sal");
                double comm = rs.getDouble("comm");
                int deptno = rs.getInt("deptno");
                System.out.println(eno + "|" + name + "|" + job + "|" + mgr
                            + "|" + date + "|" + sal + "|" + comm + "|" + deptno);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn, call, rs);
        }

    }
}
