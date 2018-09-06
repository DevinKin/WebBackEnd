package cn.devinkin.jk.action.stat;

import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.service.stat.StatChartService;
import cn.devinkin.jk.utils.UtilFuns;
import cn.devinkin.jk.utils.file.FileUtil;
import org.apache.http.HttpResponse;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class StatChartAction extends BaseAction {
    private StatChartService statChartService;

    public void setStatChartService(StatChartService statChartService) {
        this.statChartService = statChartService;
    }

    /**
     * 厂家的销售拍平
     *
     * @return
     * @throws Exception
     */
    public String factorysale() throws Exception {
        // 将拼接好的字符串写入data.xml中
//        writeXML("stat\\chart\\factorysale\\data.xml", sb.toString());
        return "factorysale";
    }

    /**
     * 产品销量前15名
     *
     * @return
     * @throws Exception
     */
    public String productsale() throws Exception {
        // 将拼接好的字符串写入data.xml中
//        writeXML("stat\\chart\\factorysale\\data.xml", sb.toString());
        return "productsale";
    }


    /**
     * 在线人数统计
     *
     * @return
     * @throws Exception
     */
    public String onlineinfo() throws Exception {
        // 将拼接好的字符串写入data.xml中
//        writeXML("stat\\chart\\factorysale\\data.xml", sb.toString());
        return "onlineinfo";
    }

    /**
     * 厂家销售排行
     *
     * @return
     * @throws Exception
     */
    public String factorysaleData() throws Exception {
        String sql = "select factory_name, sum(amount) samount from contract_product_c " +
                "group by factory_name order by samount";
        List<String> list = execSQL(sql);

        // 组织符合要求的json数据
        String json = genPieDataSet(list);

        // 将json字符串回写
        writeBack(json);
        return NONE;
    }

    /**
     * 产品销量的前15名
     *
     * @return
     * @throws Exception
     */
    public String productsaleData() throws Exception {
        String sql = "select b.* from (select product_no,sum(cnumber) samount from contract_product_c group by product_no order by samount desc ) b where rownum<16";
        List<String> list = execSQL(sql);

        // 组织符合要求的json数据
        String json = genBarDataSet(list);
        writeBack(json);

        return NONE;
    }

    /**
     * 在线人数统计
     * 表:LOGIN_LOG_P
     * 它的数据来源于,在登录时,对于登陆者的ip信息
     * 统计出14点在线的人数
     * String sql = "select count(*) from login_log where to_char(login_time, 'HH24') = '14'";
     * @return
     * @throws Exception
     */
    public String onlineinfoData() throws Exception {
        // 统计出各个时间片段的人数
//        String sql = "select to_char(login_time, 'HH24') loginTime, count(*) from login_log group by to_char(login_time, 'HH24') order by loginTime";
        String sql = "select a.a1,nvl(b.c,0) from online_info_t a " +
                "left join " +
                "(select to_char(login_time, 'HH24') a1, count(*) c from login_log_p group by to_char(login_time, 'HH24') order by a1) b " +
                "on (a.a1 = b.a1) " +
                "order by a.a1";
        List<String> list = execSQL(sql);

        // 组织符合要求的json数据
        String json = genLineDataSet(list);
        System.out.println(json);
        writeBack(json);

        return NONE;
    }

    private void writeBack(String json) throws IOException {
        // 将json字符串回写
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        // 6. 使用response对象输出json串
        response.getWriter().write(json);
    }


    private List<String> execSQL(String sql) {
        // 执行sql语句,得到统计结果
        return statChartService.executeSQL(sql);
    }

    /**
     * 生成饼图的方法
     *
     * @param list 数据列表
     * @return
     */
    private String genPieDataSet(List<String> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append("{\"factory\":\"" + list.get(i) + "\",\"value\":" + list.get(++i) + "}");
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 生成柱状图的方法
     *
     * @param list 数据列表
     * @return
     */
    private String genBarDataSet(List<String> list) {
        StringBuilder sb = new StringBuilder();
        String[] colors = new String[]{
                "#FF0F00","#FF6600","#FF9E01","#FCD202","#F8FF01",
                "#B0DE09","#04D215","#0D8ECF","#0D52D1","#2A0CD0",
                "#8A0CCF","#CD0D74","#FF0F00","#FF6600","#FF9E01"
        };
        sb.append("[");
        for (int i = 0,j=0; i < list.size(); i++,j++) {
            sb.append("{\"product\":\"" + list.get(i) + "\",\"amount\":" + list.get(++i) + ",\"color\":\"" + colors[j] + "\"}");
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 生成折线图方法
     * @param list
     * @return
     */
    private String genLineDataSet(List<String> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append("{\"hour\":\"" + list.get(i) + "\",\"count\":" + list.get(++i) + "}");
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private void writeXML(String fileName, String content) throws FileNotFoundException {
        FileUtil fileUtil = new FileUtil();
        String sPath = ServletActionContext.getServletContext().getRealPath("/");
        fileUtil.createTxt(sPath, fileName, content, "UTF-8");
    }
}
