import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class POI01Test {

    @Test
    public void testPoi() throws IOException {
        // 1. 创建工作簿 workbook
        Workbook wb = new HSSFWorkbook();

        // 2. 创建工作表 Sheet
        Sheet sheet = wb.createSheet();

        // 3. 创建行对象,下标从0开始
        Row row = sheet.createRow(3);

        // 4. 创建单元格对象,下标从0开始
        Cell cell = row.createCell(3);

        // 5. 设置单元格内容
        cell.setCellValue("黑客,拉拉");

        // 6. 设置单元格的样式
        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        // 设置字体名称
        font.setFontName("华文琥珀");
        // 设置字体大小
        font.setFontHeightInPoints((short)48);
        // 样式中添加一个字体样式
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        // 7. 保存,关闭流
        OutputStream os = new FileOutputStream("F:/WebBackEnd/SSHMavenProject/note/test01.xls");

        // os.write(wb);
        wb.write(os);

        // 关闭流
        os.close();

        // 8. 下载
    }
}
