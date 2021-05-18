package com.bayside.app.util;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportExcelUtil {

	private static final Logger log = Logger.getLogger(ImportExcelUtil.class);
	private final static String excel2003L =".xls";    //2003- 版本的excel  
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel  
      
    /** 
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象 
     * @param in,fileName 
     * @return 
     * @throws IOException  
     */  
    public static  List<List<Object>> getBankListByExcel(InputStream in,String fileName){  
        List<List<Object>> list = null;  
          
        //创建Excel工作薄  
        Workbook work = null;
		try {
			work = getWorkbook(in,fileName);
		} catch (Exception e) {
 			log.error(e.getMessage(),e);
			e.printStackTrace();
		}  
        if(null == work){  
        	System.out.println("创建Excel工作薄为空！");
            
        }  
        Sheet sheet = null;  
        Row row = null;  
        Cell cell = null;  
          
        list = new ArrayList<List<Object>>();  
        //遍历Excel中所有的sheet  
        if(null!=work){
        	   for (int i = 0; i < work.getNumberOfSheets(); i++) {  
                   sheet = work.getSheetAt(i);  
                   if(sheet==null){continue;}  
                     
                   //遍历当前sheet中的所有行  
                   int rownum = sheet.getLastRowNum();
                   int cellLength = sheet.getRow(0).getLastCellNum();
                   System.out.println(rownum);
                   for (int j = 1; j <= sheet.getLastRowNum(); j++) {  
                       row = sheet.getRow(j);  
                       if(row==null||row.getFirstCellNum()==j){continue;}  
                         
                       //遍历所有的列  
                       List<Object> li = new ArrayList<Object>();  
                       for (int y = row.getFirstCellNum(); y < cellLength; y++) {  
                       	System.out.println(row.getFirstCellNum());
                           cell = row.getCell(y);
                          if(cell!=null){
                           	li.add(getCellValue(cell));  
                           }else{
                           	li.add(cell);
                           }
                           System.out.println(cell);
                       }  
                       list.add(li);  
                   }  
               }
        }
       
        // work.close();  
        return list;  
    }  
      
    /** 
     * 描述：根据文件后缀，自适应上传文件的版本  
     * @param inStr,fileName 
     * @return 
     * @throws Exception 
     */  
    public  static Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{  
        Workbook wb = null;  
        String fileType = fileName.substring(fileName.lastIndexOf("."));  
        if(excel2003L.equals(fileType)){  
            wb = new HSSFWorkbook(inStr);  //2003-  
        }else if(excel2007U.equals(fileType)){  
            wb = new XSSFWorkbook(inStr);  //2007+  
        }else{  
            throw new Exception("解析的文件格式有误！");  
        }  
        return wb;  
    }  
  
    /** 
     * 描述：对表格中数值进行格式化 
     * @param cell 
     * @return 
     */  
    public  static Object getCellValue(Cell cell){  
        Object value = null;  
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");  //日期格式化  
        switch (cell.getCellType()) {  
        case Cell.CELL_TYPE_STRING:  
            value = cell.getRichStringCellValue().getString();  
            break;  
        case Cell.CELL_TYPE_NUMERIC:  
            if("General".equals(cell.getCellStyle().getDataFormatString())){  
                value = df.format(cell.getNumericCellValue());  
            }else if(HSSFDateUtil.isCellDateFormatted(cell)){
            	System.out.println(cell+"time");
                value = sdf.format(cell.getDateCellValue());  
            }else{  
                value = df.format(cell.getNumericCellValue());  
            }  
            break;  
        case Cell.CELL_TYPE_BOOLEAN:  
            value = cell.getBooleanCellValue();  
            break;  
        case Cell.CELL_TYPE_BLANK:  
            value = "";  
            break;  
        default:  
            break;  
        }  
        return value;  
    }  
      
}
