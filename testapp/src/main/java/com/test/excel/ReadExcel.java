package com.test.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel 
{
	
	
	private static Object getCellValue(Cell cell) {
	    
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
		{
			if(HSSFDateUtil.isCellDateFormatted(cell))
		        return cell.getDateCellValue();
		}
	
		switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    }
	 
	    return null;
	}
	
	/**
	 * New Player Registration
	 * @return
	 * @throws IOException
	 */
	public static List<Record> readRowList(Integer row_limit) throws IOException{
		
//		System.out.println(ReadExcel.class.getClassLoader().getResource("defaults/swadeshi_list.xlsx"));
		InputStream inputStream = null;
    	inputStream = ReadExcel.class.getClassLoader().getResourceAsStream("defaults/swadeshi_list.xlsx");
		
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
         List<Record> listofRecords = new ArrayList<Record>();
        Integer i = 0;
         while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
           Record record = new Record();
     
            while (cellIterator.hasNext()) {
                Cell nextCell = cellIterator.next();
                int columnIndex = nextCell.getColumnIndex();
     
                switch (columnIndex) {
                case 0:
                    record.setItemName((String) getCellValue(nextCell));
                    break;
                case 1:
                    record.setProductName((String) getCellValue(nextCell));
                case 2:
                	record.setCategoryName((String) getCellValue(nextCell));
                    break;
                case 3:
                	record.setBossName((String) getCellValue(nextCell));
                    break; 
                case 4:
                   	record.setParentName((String) getCellValue(nextCell));
                    break;
                case 5:
                  	record.setCountry((String) getCellValue(nextCell));
                    break;  
             }
     
            }
            listofRecords.add(record);
            if(i == row_limit){
            	return listofRecords;
            }
       	 i++;

        }
        
//        System.out.println(listofPlayers);
         
//        ((FileInputStream) workbook).close();
        inputStream.close();
        inputStream = null;
        
        return listofRecords;
	}
	
	
	
	
	public static void main(String args[]){
		try {
			System.out.println(readRowList(15));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}

