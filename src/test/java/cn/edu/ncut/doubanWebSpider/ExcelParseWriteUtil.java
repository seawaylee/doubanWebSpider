package cn.edu.ncut.doubanWebSpider;

/**
 * POSi报表测试
 *
 * @author lixiwei-mac
 * @create 16:08
 */

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

/**
 * 类CtripOfflineExcelParser.java的实现描述：TODO 类实现描述
 *
 * @author hooverhe 2015-8-17 下午3:39:18
 */
public class ExcelParseWriteUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelParseWriteUtil.class);

    /**
     * EXCEL文件读取并组装对象<br>
     *
     * @param localFilePath
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> parse(String localFilePath) {
        List<Map<String, Object>> sourceDataList = new ArrayList<Map<String, Object>>();
        FileInputStream in = null;
        Workbook workBook = null;
        try {
            in = new FileInputStream(localFilePath);
            boolean isExcel2003 = true;
            if (isExcel2007(localFilePath)) {
                isExcel2003 = false;
            }
            if (isExcel2003) {
                workBook = new HSSFWorkbook(in);
            } else {
                workBook = new XSSFWorkbook(in);
            }

            Map<Integer, String> columnOrderNameMap = new HashMap<Integer, String>();
            Sheet sheet = workBook.getSheetAt(0);
            Row head = sheet.getRow(0);
            // 获取到Excel文件中的所有的列
            int headCellCount = head.getPhysicalNumberOfCells();
            for (int j = 0; j < headCellCount; j++) {
                // 获取到列的值
                Cell cell = head.getCell(j);
                // + 设置单元格类型
                cell.setCellType(Cell.CELL_TYPE_STRING);
                columnOrderNameMap.put(j, cell.getStringCellValue());
            }

            // 获取到Excel文件中的所有行数
            int rowCount = sheet.getPhysicalNumberOfRows();
            // 遍历行
            for (int i = 1; i < rowCount; i++) {
                // 读取左上端单元格
                Row row = sheet.getRow(i);
                // 行不为空
                if (row != null) {
                    Map<String, Object> columnDataMap = new HashMap<String, Object>();
                    // 获取到Excel文件中的所有的列
                    //int cellCount = row.getPhysicalNumberOfCells();
                    // 遍历列
                    for (int j = 0; j < headCellCount; j++) {
                        Object value = null;
                        // 获取到列的值
                        Cell cell = row.getCell(j);
                        if (j == 1) {
                            String dataFormatString = cell.getCellStyle().getDataFormatString();
                            Date javaDate = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                            System.out.println(javaDate);
                        }

                        if (cell != null) {
                            switch (cell.getCellType()) {
                                // 公式
                                case Cell.CELL_TYPE_FORMULA:
                                    break;
                                // 数据
                                case Cell.CELL_TYPE_NUMERIC:
                                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                        // 如果是date类型则 ，获取该cell的date值
                                        value = cell.getDateCellValue();
                                    } else {
                                        // 纯数字
                                        value = new DecimalFormat("#").format(cell.getNumericCellValue());
                                    }
                                    break;
                                // 字符串
                                case Cell.CELL_TYPE_STRING:
                                    value = cell.getStringCellValue().trim();
                                    break;
                                // 空值
                                case Cell.CELL_TYPE_BLANK:
                                    value = "";
                                    break;
                                // 故障
                                case HSSFCell.CELL_TYPE_ERROR:
                                    value = "非法字符";
                                    break;
                                // Boolean
                                case HSSFCell.CELL_TYPE_BOOLEAN:
                                    value = cell.getBooleanCellValue();
                                    break;
                                default:
                                    value = "未知类型";
                                    break;
                            }
                        }
                        String key = columnOrderNameMap.get(j);
                        if (StringUtils.isBlank(key)) {
                            //excel格式不正确的情况下会出现null
                            continue;
                        }
                        if (key.endsWith("日期")
                                || key.endsWith("时间")
                                || key.endsWith("Date")
                                || key.endsWith("date")
                                || key.endsWith("DATE")) {
                            if (value instanceof String) {
                                value = ExcelDateUtil.toDateByDateFormatList((String) value);
                            }
                        }

                        columnDataMap.put(key, value);
                    }
                    sourceDataList.add(columnDataMap);
                }
            }
        } catch (Exception e) {
            logger.error("读取Excel失败", e);
            e.printStackTrace();
            throw new RuntimeException("读取解析Excel失败", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                logger.error("读取解析异常" + e);
            }
        }
        return sourceDataList;
    }

    /**
     * EXCEL文件写入并拼装返回对象属性<br>
     *
     * @param localFilePath
     * @param responseDataList
     * @return void
     */
    public static void write(String localFilePath, List<Map<String, String>> responseDataList,
                             String fileType) {

        FileInputStream in = null;
        Workbook workBook = null;
        try {
            in = new FileInputStream(localFilePath);
            boolean isExcel2003 = true;
            if (isExcel2007(localFilePath)) {
                isExcel2003 = false;
            }
            if (isExcel2003) {
                workBook = new HSSFWorkbook(in);
            } else {
                workBook = new XSSFWorkbook(in);
            }

            Sheet sheet = workBook.getSheetAt(0);
            Row head = sheet.getRow(0);
            // 1、获取到Excel文件中的所有的列
            int headCellCount = head.getPhysicalNumberOfCells();
            Cell resultPolicyNoColumnNameCell = head.createCell(headCellCount);
            resultPolicyNoColumnNameCell.setCellValue("保单号");
            Cell resultStatusColumnNameCell = head.createCell(headCellCount + 1);
            resultStatusColumnNameCell.setCellValue("resultStatus");
            Cell resultMsgColumnNameCell = head.createCell(headCellCount + 2);
            resultMsgColumnNameCell.setCellValue("resultMsg");

            // 2、遍历行拼装
            int i = 1;
            for (Map<String, String> responseDataMap : responseDataList) {
                Row row = sheet.getRow(i);
                Cell policyNoCell = row.createCell(headCellCount);
                policyNoCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                policyNoCell.setCellValue(responseDataMap.get("保单号"));
                Cell resultStatusColumnValueCell = row.createCell(headCellCount + 1);
                resultStatusColumnValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                resultStatusColumnValueCell.setCellValue(responseDataMap.get("resultStatus"));
                Cell resultMsgColumnValueCell = row.createCell(headCellCount + 2);
                resultMsgColumnValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
                resultMsgColumnValueCell.setCellValue(responseDataMap.get("resultMsg"));
                i++;
            }
        } catch (Exception e) {
            logger.error("读取组装写入Excel失败", e);
            e.printStackTrace();
            throw new RuntimeException("读取组装写入Excel失败", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                logger.error("读取解析异常" + e);
            }
        }

        // 3、拼装结果写入
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(localFilePath);
            workBook.write(fos);
        } catch (IOException e) {
            logger.error("写入Excel失败", e);
            e.printStackTrace();
            throw new RuntimeException("写入Excel失败", e);
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                logger.error("写入解析异常" + e);
            }
        }

    }

    /**
     * 是否是2003的excel，返回true是2003
     *
     * @param filePath
     * @return boolean
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 是否是2007的excel，返回true是2007
     *
     * @param filePath
     * @return boolean
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        // 1、
        List<Map<String, Object>> objects = parse("/Users/lixiwei-mac/Documents/xingreng医责险22.xlsx");
//        for (Map<String, Object> map : objects) {
//            Set<Entry<String, Object>> entrySet = map.entrySet();
//            for (Entry<String, Object> entry : entrySet) {
//                Object value = entry.getValue();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                String formatValue = sdf.format(value);
//                System.out.println(entry.getKey() + " : " + formatValue);
//            }
//        }
        String str = JSONObject.toJSONString(objects);
//        String str = JSONObject.toJSONStringWithDateFormat(objects, "yyyy-MM-dd");
        System.out.println("-------str--------" + str);
        for (Map<String, Object> map : objects) {
            Map<String, Object> requestDataMap = new HashMap<String, Object>(map);
            String str2 = JSONObject.toJSONString(requestDataMap);
            JSONObject requestJsonObject = JSONObject.parseObject(str2);
            String dateStr = requestJsonObject.getString("日期");
            System.out.println("-------str--------" + dateStr);
//            Date birthday = new Date(Long.valueOf(dateStr));
//            System.out.println("-------strDATE--------" + birthday);
        }


        // 2、
//        List<Map<String, String>> stringMap = Lists.newArrayList();
//        int aas[] = {1,1};
//        for (int aa : aas){
//            Map<String, String> map = Maps.newHashMap();
//            map.put("保单号", "111" + aa);
//            map.put("resultStatus", "成功");
//            map.put("resultMsg", "5555");
//            stringMap.add(map);
//        }
//        write("C:\\Users\\yuanweihong\\Desktop\\产品开发\\在 众安家安心租财产综合保险需求文档 v1.2 - 副本.xlsx", stringMap, "");
    }

}

