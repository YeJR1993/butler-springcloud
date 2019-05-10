package com.tuxiaoer.shanghai.common.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;

/**
* @author: YeJR 
* @version: 2018年7月13日 下午1:58:43
* 导出Excel文件（导出“XLSX”格式，支持大数据量导出）， 参考jeeplus
*/
public class ExportExcel {
	
	private final static Logger logger = LoggerFactory.getLogger(ExportExcel.class);

	/**
	 * Excel的文档对象，扩展名是.xlsx
	 * 基于XSSF的低内存占用的API----SXSSF
	 * 当数据量超出65536条后，在使用HSSFWorkbook或XSSFWorkbook，程序会报OutOfMemoryError
	 */
	private SXSSFWorkbook workbook;

	/**
	 * 在workbook中添加一个sheet,对应Excel文件中的sheet
	 */
	private SXSSFSheet sheet;

	/**
	 * 样式列表
	 */
	private Map<String, XSSFCellStyle> styles;

	/**
	 * 当前行号
	 */
	private int rownum;

	/**
	 * 注解列表（Object[]{ ExcelField, Field/Method }）
	 */
	List<Object[]> annotationList = new ArrayList<>();
	
	/**
	 * 构造函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 */
	public ExportExcel(String title, List<String> headerList) {
		initialize(title, headerList);
	}
	
	/**
	 * 构造函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param cls 实体对象，通过annotation.ExportField获取标题
	 * @param groups 导入分组
	 */
	public ExportExcel(String title, Class<?> cls, int... groups) {
		// 为了给导出Excel好判断是否导出成功，所以在session中存入特定的值
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute("exportExcel", false);
		logger.info("Excel导出开始: {}", session.getAttribute("exportExcel"));
		// 反射获取cls类中的所有属性，不包含继承父类的属性
		Field[] fields = cls.getDeclaredFields();
		// 编辑获取到的所有属性
		for (Field field : fields) {
			// 获取该属性上的ExcelField注解
			ExcelField excelField = field.getAnnotation(ExcelField.class);
			boolean flag = excelField != null && (excelField.type() == 0 || excelField.type() == 1);
			if (flag) {
				// 若需要分组
				if (groups != null && groups.length > 0) {
					boolean inGroup = false;
					// 遍历需要的分组
					for (int group : groups) {
						if (inGroup) {
							break;
						}
						// 对注解中的分组遍历
						for (int excelFieldGroup : excelField.groups()) {
							// 如果相同就加入到annotationList中
							if (group == excelFieldGroup) {
								inGroup = true;
								annotationList.add(new Object[] {excelField, field});
								break;
							}
						}
					}
				} else {
					annotationList.add(new Object[] {excelField, field});
				}
			}
		}

		// 反射获取cls类中的所有方法，不包含继承父类的方法
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			// 通过反射获取该方法上的注解
			ExcelField excelField = method.getAnnotation(ExcelField.class);
			boolean flag = excelField != null && (excelField.type() == 0 ||  excelField.type() == 1);
			if (flag) {
				// 需不需要分组
				if (groups != null && groups.length > 0) {
					boolean inGroup = false;
					for (int g : groups) {
						if (inGroup) {
							break;
						}
						for (int efg : excelField.groups()) {
							if (g == efg) {
								inGroup = true;
								annotationList.add(new Object[] {excelField, method});
								break;
							}
						}
					}
				} else {
					annotationList.add(new Object[] {excelField, method});
				}
			}
		}
		
		// 对annotationList排序，重新定义Collections.sort中的Comparator接口，定义新的排序规则。
		Collections.sort(annotationList, new Comparator<Object[]>() {
			@Override
			public int compare(Object[] objects1, Object[] objects2) {
				return new Integer(((ExcelField)objects1[0]).sort()).compareTo(new Integer(((ExcelField)objects2[0]).sort()));
			};
		});
		
		// Initialize
		List<String> headerList = new ArrayList<>();
		for (Object[] os : annotationList) {
			String t = ((ExcelField) os[0]).title();
			headerList.add(t);
		}
		initialize(title, headerList);
	}

	/**
	 * 初始化函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 */
	private void initialize(String title, List<String> headerList) {
		// 在内存中创建一个500 行的工作簿对象
		this.workbook = new SXSSFWorkbook(500);
		// 创建一个名字叫“Export”的工作表(Sheet)对象
		this.sheet = workbook.createSheet("Export");
		// 创建该工作簿对象的样式
		this.styles = createStyles(workbook);
		// 创建表格标题 title
		if (StringUtils.isNotBlank(title)){
			SXSSFRow titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), titleRow.getRowNum(), headerList.size()-1));
		}
		// 创建表头列表
		if (headerList == null){
			throw new RuntimeException("headerList not null!");
		}
		SXSSFRow headerRow = sheet.createRow(rownum++);
		headerRow.setHeightInPoints(16);
		for (int i = 0; i < headerList.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(styles.get("header"));
			String[] ss = StringUtils.split(headerList.get(i), "**", 2);
			if (ss.length==2){
				cell.setCellValue(ss[0]);
				Comment comment = this.sheet.createDrawingPatriarch().createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
				comment.setString(new XSSFRichTextString(ss[1]));
				cell.setCellComment(comment);
			}else{
				cell.setCellValue(headerList.get(i));
			}
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < headerList.size(); i++) {  
			int colWidth = sheet.getColumnWidth(i)*2;
	        sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
		}
	}

	
	/**
	 * 创建表格样式
	 * @param workbook 工作薄对象
	 * @return 样式列表
	 */
	private Map<String, XSSFCellStyle> createStyles(Workbook workbook) {
		
		Map<String, XSSFCellStyle> styles = new HashMap<>(16);


		XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
		
		// 标题字体格式，并居中显示
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		Font titleFont = workbook.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBold(true);
		style.setFont(titleFont);
		styles.put("title", style);

		// 数据的表格边框样式，以及字体格式，居中显示
		style = (XSSFCellStyle) workbook.createCellStyle();
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = workbook.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);
		
		// 左对齐样式
		style = (XSSFCellStyle) workbook.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.LEFT);
		styles.put("data1", style);

		// 居中对齐样式
		style = (XSSFCellStyle) workbook.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.CENTER);
		styles.put("data2", style);

		// 右对齐样式
		style = (XSSFCellStyle) workbook.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.RIGHT);
		styles.put("data3", style);
		
		// 表头列表的样式
		style = (XSSFCellStyle) workbook.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font headerFont = workbook.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		titleFont.setBold(true);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);
		
		return styles;
	}
	
	
	/**
	 * 添加一行
	 * @return 行对象
	 */
	public SXSSFRow addRow(){
		return sheet.createRow(rownum++);
	}
	

	/**
	 * 添加一个单元格
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 添加值
	 * @return 单元格对象
	 */
	public Cell addCell(SXSSFRow row, int column, Object val){
		return this.addCell(row, column, val, 0, Class.class);
	}

	/**
	 * 添加一个单元格
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 添加值
	 * @param align 对齐方式（1：靠左；2：居中；3：靠右）
	 * @return 单元格对象
	 */
	public Cell addCell(SXSSFRow row, int column, Object val, int align, Class<?> fieldType){
		Cell cell = row.createCell(column);
		CellStyle style = styles.get("data"+(align>=1&&align<=3?align:""));
		try {
			if (val == null){
				cell.setCellValue("");
			} else if (val instanceof String) {
				cell.setCellValue((String) val);
			} else if (val instanceof Integer) {
				cell.setCellValue((Integer) val);
			} else if (val instanceof Long) {
				cell.setCellValue((Long) val);
			} else if (val instanceof Double) {
				cell.setCellValue((Double) val);
			} else if (val instanceof Float) {
				cell.setCellValue((Float) val);
			} else if (val instanceof Date) {
				DataFormat format = workbook.createDataFormat();
	            style.setDataFormat(format.getFormat("yyyy-MM-dd"));
				cell.setCellValue((Date) val);
			} else {
				if (fieldType != Class.class){
					cell.setCellValue((String)fieldType.getMethod("setValue", Object.class).invoke(null, val));
				}else{
					cell.setCellValue((String)Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), 
						"fieldtype."+val.getClass().getSimpleName()+"Type")).getMethod("setValue", Object.class).invoke(null, val));
				}
			}
		} catch (Exception ex) {
			cell.setCellValue(val.toString());
		}
		cell.setCellStyle(style);
		return cell;
	}

	/**
	 * 添加数据（通过annotation.ExportField添加数据）
	 * @return list 数据列表
	 */
	public <E> ExportExcel setDataList(List<E> list){
		for (E e : list){
			int colunm = 0;
			SXSSFRow row = this.addRow();
			StringBuilder sb = new StringBuilder();
			for (Object[] os : annotationList){
				ExcelField ef = (ExcelField)os[0];
				Object val = null;
				// Get entity value
				try{
					if (StringUtils.isNotBlank(ef.value())){
						val = Reflections.invokeGetter(e, ef.value());
					}else{
						if (os[1] instanceof Field){
							val = Reflections.invokeGetter(e, ((Field)os[1]).getName());
						}else if (os[1] instanceof Method){
							val = Reflections.invokeMethod(e, ((Method)os[1]).getName(), new Class[] {}, new Object[] {});
						}
					}
				}catch(Exception ex) {
					val = "";
				}
				this.addCell(row, colunm++, val, ef.align(), ef.fieldType());
				sb.append(val + ", ");
			}
		}
		return this;
	}
	
	/**
	 * 输出数据流
	 * @param os 输出数据流
	 */
	public ExportExcel write(OutputStream os) throws IOException{
		workbook.write(os);
		return this;
	}
	
	/**
	 * 输出到客户端
	 * @param fileName 输出文件名
	 */
	public ExportExcel write(HttpServletResponse response, String fileName) throws IOException{
		response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(fileName, "UTF-8"));
		write(response.getOutputStream());
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute("exportExcel", true);
		logger.info("Excel导出结束: {}", session.getAttribute("exportExcel"));
		return this;
	}
	
	/**
	 * 输出到文件
	 * @param name 输出文件名
	 */
	public ExportExcel writeFile(String name) throws FileNotFoundException, IOException{
		FileOutputStream os = new FileOutputStream(name);
		this.write(os);
		return this;
	}
	
	/**
	 * 清理临时文件
	 */
	public ExportExcel dispose(){
		workbook.dispose();
		return this;
	}
	
	
}
