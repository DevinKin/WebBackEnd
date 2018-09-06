# Apache POI的使用
## 导入POI的相关jar包
1. poi-x.x.jar
2. poi-ooxml-x.x.jar
3. poi-ooxml-schemas-x.x.jar
4. Maven的坐标
```xml  
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>3.11</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>3.11</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml-schemas</artifactId>
            <version>3.11</version>
        </dependency>
```

## 需求:需要八步
1. 创建一个工作簿 workbook
2. 创建一个工作表 sheet
3. 创建一个行对象 row(下标起始值为0)
4. 创建一个单元格对象cell (下标起始值为0)
5. 给单元格设置内容
6. 设置单元格的样式,设置字体和字体的大小
7. 保存,关闭流对象
8. 下载

## 实例代码
```java
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
```

## 模板打印
1. 制作模板文件(模板文件路径(/make/xlsprint/tOUTPRODUCT.xml))
2. 导入(加载)模板文件,从而得到一个工作簿
3. 读取工作表
4. 读取行
5. 读取单元格
6. 读取单元格样式