package cn.devinkin.jk.action.cargo;

import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.domain.ContractProduct;
import cn.devinkin.jk.service.cargo.ContractProductService;
import cn.devinkin.jk.utils.DownloadUtil;
import cn.devinkin.jk.utils.UtilFuns;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class OutProductAction extends BaseAction {
    // 接受页面上的船期
    private String inputDate;

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    // 注入货物的业务逻辑
    private ContractProductService contractProductService;

    public void setContractProductService(ContractProductService contractProductService) {
        this.contractProductService = contractProductService;
    }

    /**
     * 进入出货表的打印页面
     *
     * @return
     * @throws Exception
     */
    public String toedit() throws Exception {
        return "toedit";
    }


    /**
     * 打印出货表
     *
     * @return
     * @throws Exception
     */
//    public String print() throws Exception {
//        // 通用变量
//        int rowNo = 0, cellNo = 1;
//        Row nRow  = null;
//        Cell nCell = null;
//
//        // 1. 读取工作簿
//        String path = ServletActionContext.getServletContext().getRealPath("/") + "WEB-INF/make/xlsprint/tOUTPRODUCT.xls";
//        System.out.println(path);
//        InputStream is = new FileInputStream(path);
//        Workbook wb = new HSSFWorkbook(is);
//
//        // 2. 读取工作表,Sheet1
//        Sheet sheet = wb.getSheetAt(0);
//
//        // 重置cellNo
//        cellNo = 1;
//        // 3. 创建行对象
//        // =========================大标题===========================
//        // 设置大标题
//        // 读取行对象
//        nRow = sheet.getRow(rowNo++);
//        // 读取单元格对象
//        nCell = nRow.getCell(cellNo);
//
//        // 设置单元格内容,2015-01,2015-12
//        nCell.setCellValue(inputDate.replace("-0","-").replace("-","年") + "月份出货表");
//
//
//        // =========================小标题===========================
//        // 已存在,直接跳过第二行
//        rowNo++;
//
//        // =========================数据输出===========================
//        // 读取行对象,读取第三行,读取第三行的样式
//        nRow = sheet.getRow(rowNo);
//        CellStyle customerCellStyle = nRow.getCell(cellNo++).getCellStyle();
//        CellStyle orderNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
//        CellStyle productNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
//        CellStyle cnumberCellStyle = nRow.getCell(cellNo++).getCellStyle();
//        CellStyle factoryNameCellStyle = nRow.getCell(cellNo++).getCellStyle();
//        CellStyle deliveryPeriodCellStyle = nRow.getCell(cellNo++).getCellStyle();
//        CellStyle shipTimeCellStyle = nRow.getCell(cellNo++).getCellStyle();
//        CellStyle tradeTermsCellStyle = nRow.getCell(cellNo++).getCellStyle();
//
//        String hql = "from ContractProduct cp where to_char(contract.shipTime,'yyyy-MM') = ?";
//        // 查询出符合指定船期的货物列表
//        List<ContractProduct> list = contractProductService.find(hql, ContractProduct.class, new Object[] {inputDate});
//        for (ContractProduct cp : list) {
//            // 产生数据行
//            nRow = sheet.createRow(rowNo++);
//            // 设置行高
//            nRow.setHeightInPoints(24);
//
//            cellNo = 1;
//            // 设置单元格对象
//            nCell = nRow.createCell(cellNo++);
//            // 显示客户名称
//            nCell.setCellValue(cp.getContract().getCustomName());
//            // 设置文本样式
//            nCell.setCellStyle(customerCellStyle);
//
//            // 设置单元格对象
//            nCell = nRow.createCell(cellNo++);
//            // 显示订单(购销合同)号
//            nCell.setCellValue(cp.getContract().getContractNo());
//            // 设置文本样式
//            nCell.setCellStyle(orderNoCellStyle);
//
//            // 设置单元格对象
//            nCell = nRow.createCell(cellNo++);
//            // 显示货号
//            nCell.setCellValue(cp.getProductNo());
//            // 设置文本样式
//            nCell.setCellStyle(productNoCellStyle);
//
//            // 设置单元格对象
//            nCell = nRow.createCell(cellNo++);
//            // 显示数量
//            nCell.setCellValue(cp.getCnumber());
//            // 设置文本样式
//            nCell.setCellStyle(cnumberCellStyle);
//
//            // 设置单元格对象
//            nCell = nRow.createCell(cellNo++);
//            // 显示工厂名
//            nCell.setCellValue(cp.getFactoryName());
//            // 设置文本样式
//            nCell.setCellStyle(factoryNameCellStyle);
//
//            // 设置单元格对象
//            nCell = nRow.createCell(cellNo++);
//            // 显示交期
//            nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));
//            // 设置文本样式
//            nCell.setCellStyle(deliveryPeriodCellStyle);
//
//            // 设置单元格对象
//            nCell = nRow.createCell(cellNo++);
//            // 显示船期
//            nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));
//            // 设置文本样式
//            nCell.setCellStyle(shipTimeCellStyle);
//
//            // 设置单元格对象
//            nCell = nRow.createCell(cellNo++);
//            // 显示贸易条款
//            nCell.setCellValue(cp.getContract().getTradeTerms());
//            // 设置文本样式
//            nCell.setCellStyle(tradeTermsCellStyle);
//        }
//
//
//        // 输出
//        DownloadUtil downloadUtil = new DownloadUtil();
//        // 流,内存中的缓存区
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        // 把Excel表格输出到缓存流中
//        wb.write(baos);
//        // 得到Response对象
//        HttpServletResponse response = ServletActionContext.getResponse();
//        downloadUtil.download(baos, response, "出货表.xls");
//        return NONE;
//    }
    public String print() throws Exception {
        // 通用变量
        int rowNo = 0, cellNo = 1;
        Row nRow = null;
        Cell nCell = null;

        // 1. 创建工作簿
        // 只支持excel2003版本,扩展.xls
//        Workbook wb = new HSSFWorkbook();
        // 支持excel2007+版本,扩展名.xlsx
//        Workbook wb = new XSSFWorkbook();
        // 支持excel2007+版本,扩展名.xlsx,不支持模板操作,可以将产生的一部分对象从内存中转移到磁盘
        // 默认转移对象数量为100,如果new SXSSFWorkbook(500),代表内存中对象个数达到500,就转移到磁盘上
        Workbook wb = new SXSSFWorkbook(500);

        // 2. 创建工作表
        Sheet sheet = wb.createSheet();

        // 设置列宽,本身是个bug
        sheet.setColumnWidth(cellNo++, 26 * 256);
        sheet.setColumnWidth(cellNo++, 11 * 256);
        sheet.setColumnWidth(cellNo++, 29 * 256);
        sheet.setColumnWidth(cellNo++, 12 * 256);
        sheet.setColumnWidth(cellNo++, 15 * 256);
        sheet.setColumnWidth(cellNo++, 10 * 256);
        sheet.setColumnWidth(cellNo++, 10 * 256);
        sheet.setColumnWidth(cellNo++, 8 * 256);

        // 重置cellNo
        cellNo = 1;
        // 3. 创建行对象
        // =========================大标题===========================
        // 设置大标题
        nRow = sheet.createRow(rowNo++);
        // 设置行高
        nRow.setHeightInPoints(36);
        // 创建单元格对象
        nCell = nRow.createCell(cellNo);
        // 合并单元格,起始行,结束行,起始列,结束列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 8));
        // 设置单元格内容,2015-01,2015-12
        nCell.setCellValue(inputDate.replace("-0", "-").replace("-", "年") + "月份出货表");
        // 设置样式
        nCell.setCellStyle(bigTitle(wb));


        // =========================小标题===========================
        String titles[] = {"客户", "订单号", "货号", "数量", "工厂", "工厂交期", "船期", "贸易条款"};
        // 创建小标题行对象
        nRow = sheet.createRow(rowNo++);
        // 设置行高
        nRow.setHeightInPoints(26.25f);
        // 创建单元格对象,设置内容,并设置样式
        for (String title : titles) {
            // 创建单元格对象
            nCell = nRow.createCell(cellNo++);
            // 设置内容
            nCell.setCellValue(title);
            // 设置小标题样式
            nCell.setCellStyle(this.title(wb));
        }

        // =========================数据输出===========================
        String hql = "from ContractProduct cp where to_char(contract.shipTime,'yyyy-MM') = ?";
        // 查询出符合指定船期的货物列表
        List<ContractProduct> list = contractProductService.find(hql, ContractProduct.class, new Object[]{inputDate});


//        for (int i = 1; i <= 1000; i++) {
        for (ContractProduct cp : list) {
            // 产生数据行
            nRow = sheet.createRow(rowNo++);
            // 设置行高
            nRow.setHeightInPoints(24);

            cellNo = 1;
            // 设置单元格对象
            nCell = nRow.createCell(cellNo++);
            // 显示客户名称
            nCell.setCellValue(cp.getContract().getCustomName());
            // 设置文本样式
            nCell.setCellStyle(this.text(wb));

            // 设置单元格对象
            nCell = nRow.createCell(cellNo++);
            // 显示订单(购销合同)号
            nCell.setCellValue(cp.getContract().getContractNo());
            // 设置文本样式
            nCell.setCellStyle(this.text(wb));

            // 设置单元格对象
            nCell = nRow.createCell(cellNo++);
            // 显示货号
            nCell.setCellValue(cp.getProductNo());
            // 设置文本样式
            nCell.setCellStyle(this.text(wb));

            // 设置单元格对象
            nCell = nRow.createCell(cellNo++);
            // 显示数量
            nCell.setCellValue(cp.getCnumber());
            // 设置文本样式
            nCell.setCellStyle(this.text(wb));

            // 设置单元格对象
            nCell = nRow.createCell(cellNo++);
            // 显示工厂名
            nCell.setCellValue(cp.getFactoryName());
            // 设置文本样式
            nCell.setCellStyle(this.text(wb));

            // 设置单元格对象
            nCell = nRow.createCell(cellNo++);
            // 显示交期
            nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));
            // 设置文本样式
            nCell.setCellStyle(this.text(wb));

            // 设置单元格对象
            nCell = nRow.createCell(cellNo++);
            // 显示船期
            nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));
            // 设置文本样式
            nCell.setCellStyle(this.text(wb));

            // 设置单元格对象
            nCell = nRow.createCell(cellNo++);
            // 显示贸易条款
            nCell.setCellValue(cp.getContract().getTradeTerms());
            // 设置文本样式
            nCell.setCellStyle(this.text(wb));
        }
//        }


        // 输出
        DownloadUtil downloadUtil = new DownloadUtil();
        // 流,内存中的缓存区
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把Excel表格输出到缓存流中
        wb.write(baos);
        // 得到Response对象
        HttpServletResponse response = ServletActionContext.getResponse();
        downloadUtil.download(baos, response, "出货表.xlsx");
        return NONE;
    }

    // 设置大标题样式
    public CellStyle bigTitle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        // 字体加粗
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        style.setFont(font);

        // 横向居中
        style.setAlignment(CellStyle.ALIGN_CENTER);
        // 纵向居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return style;
    }


    // 设置小标题样式
    public CellStyle title(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);

        style.setFont(font);

        // 横向居中
        style.setAlignment(CellStyle.ALIGN_CENTER);
        // 纵向居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        // 上细线
        style.setBorderTop(CellStyle.BORDER_THIN);
        // 下细线
        style.setBorderBottom(CellStyle.BORDER_THIN);
        // 左细线
        style.setBorderLeft(CellStyle.BORDER_THIN);
        // 右细线
        style.setBorderRight(CellStyle.BORDER_THIN);

        return style;

    }


    // 设置文字样式
    public CellStyle text(Workbook wb) {
        CellStyle style = wb.createCellStyle();

        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 10);

        style.setFont(font);

        // 横向居右
        style.setAlignment(CellStyle.ALIGN_LEFT);
        // 纵向居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        // 上细线
        style.setBorderTop(CellStyle.BORDER_THIN);
        // 下细线
        style.setBorderBottom(CellStyle.BORDER_THIN);
        // 左细线
        style.setBorderLeft(CellStyle.BORDER_THIN);
        // 右细线
        style.setBorderRight(CellStyle.BORDER_THIN);

        return style;
    }
}
