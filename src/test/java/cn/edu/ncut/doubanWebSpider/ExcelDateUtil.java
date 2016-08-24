package cn.edu.ncut.doubanWebSpider;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelDateUtil {

    static List<DateFormat> dateFormatList = new ArrayList<DateFormat>();

    public static Date toDateByDateFormatList(String dateStr) {
        Date date = null;
        for (DateFormat dateFormat : dateFormatList) {
            try {
                date = dateFormat.parse(dateStr);
            } catch (Exception e) {
                continue;
            }
            break;
        }
        return date;
    }

    static {
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
        DateFormat dateFormat3 = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        DateFormat dateFormat4 = new SimpleDateFormat("yyyy-M-d H:mm:ss");
        DateFormat dateFormat5 = new SimpleDateFormat("yyyy-M-d H:m:ss");
        DateFormat dateFormat6 = new SimpleDateFormat("yyyy-M-d H:m:s");
        DateFormat dateFormat7 = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
        DateFormat dateFormat8 = new SimpleDateFormat("yyyy-MM-d H:mm:ss");
        DateFormat dateFormat9 = new SimpleDateFormat("yyyy-MM-d H:m:ss");
        DateFormat dateFormat10 = new SimpleDateFormat("yyyy-MM-d H:m:s");
        DateFormat dateFormat11 = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        DateFormat dateFormat12 = new SimpleDateFormat("yyyy-MM-dd H:m:ss");
        DateFormat dateFormat13 = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        DateFormat dateFormat14 = new SimpleDateFormat("yyyy-MM-dd HH:m:ss");
        DateFormat dateFormat15 = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        DateFormat dateFormat16 = new SimpleDateFormat("yyyy-MM-dd HH:mm:s");
        DateFormat dateFormat17 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat18 = new SimpleDateFormat("yyyy/M/dd HH:mm:ss");
        DateFormat dateFormat19 = new SimpleDateFormat("yyyy/M/d HH:mm:ss");
        DateFormat dateFormat20 = new SimpleDateFormat("yyyy/M/d H:mm:ss");
        DateFormat dateFormat21 = new SimpleDateFormat("yyyy/M/d H:m:ss");
        DateFormat dateFormat22 = new SimpleDateFormat("yyyy/M/d H:m:s");
        DateFormat dateFormat23 = new SimpleDateFormat("yyyy/MM/d HH:mm:ss");
        DateFormat dateFormat24 = new SimpleDateFormat("yyyy/MM/d H:mm:ss");
        DateFormat dateFormat25 = new SimpleDateFormat("yyyy/MM/d H:m:ss");
        DateFormat dateFormat26 = new SimpleDateFormat("yyyy/MM/d H:m:s");
        DateFormat dateFormat27 = new SimpleDateFormat("yyyy/MM/dd H:mm:ss");
        DateFormat dateFormat28 = new SimpleDateFormat("yyyy/MM/dd H:m:ss");
        DateFormat dateFormat29 = new SimpleDateFormat("yyyy/MM/dd H:m:s");
        DateFormat dateFormat30 = new SimpleDateFormat("yyyy/MM/dd HH:m:ss");
        DateFormat dateFormat31 = new SimpleDateFormat("yyyy/MM/dd HH:m:s");
        DateFormat dateFormat32 = new SimpleDateFormat("yyyy/MM/dd HH:mm:s");
        DateFormat dateFormat33 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateFormat dateFormat34 = new SimpleDateFormat("yyyy-M-dd HH:mm");
        DateFormat dateFormat35 = new SimpleDateFormat("yyyy-MM-d HH:mm");
        DateFormat dateFormat36 = new SimpleDateFormat("yyyy-MM-d H:mm");
        DateFormat dateFormat37 = new SimpleDateFormat("yyyy-MM-d H:m");
        DateFormat dateFormat38 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        DateFormat dateFormat39 = new SimpleDateFormat("yyyy/M/dd HH:mm");
        DateFormat dateFormat40 = new SimpleDateFormat("yyyy/MM/d HH:mm");
        DateFormat dateFormat41 = new SimpleDateFormat("yyyy/MM/d H:mm");
        DateFormat dateFormat42 = new SimpleDateFormat("yyyy/MM/d H:m");
        DateFormat dateFormat43 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat44 = new SimpleDateFormat("yyyy-M-dd");
        DateFormat dateFormat45 = new SimpleDateFormat("yyyy-M-d");
        DateFormat dateFormat46 = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat dateFormat47 = new SimpleDateFormat("yyyy/M/dd");
        DateFormat dateFormat48 = new SimpleDateFormat("yyyy/M/d");
        DateFormat dateFormat49 = new SimpleDateFormat("yyyy年MM月dd日");
        dateFormatList.add(dateFormat1);
        dateFormatList.add(dateFormat2);
        dateFormatList.add(dateFormat3);
        dateFormatList.add(dateFormat4);
        dateFormatList.add(dateFormat5);
        dateFormatList.add(dateFormat6);
        dateFormatList.add(dateFormat7);
        dateFormatList.add(dateFormat8);
        dateFormatList.add(dateFormat9);
        dateFormatList.add(dateFormat10);
        dateFormatList.add(dateFormat11);
        dateFormatList.add(dateFormat12);
        dateFormatList.add(dateFormat13);
        dateFormatList.add(dateFormat14);
        dateFormatList.add(dateFormat15);
        dateFormatList.add(dateFormat16);
        dateFormatList.add(dateFormat17);
        dateFormatList.add(dateFormat18);
        dateFormatList.add(dateFormat19);
        dateFormatList.add(dateFormat20);
        dateFormatList.add(dateFormat21);
        dateFormatList.add(dateFormat22);
        dateFormatList.add(dateFormat23);
        dateFormatList.add(dateFormat24);
        dateFormatList.add(dateFormat25);
        dateFormatList.add(dateFormat26);
        dateFormatList.add(dateFormat27);
        dateFormatList.add(dateFormat28);
        dateFormatList.add(dateFormat29);
        dateFormatList.add(dateFormat30);
        dateFormatList.add(dateFormat31);
        dateFormatList.add(dateFormat32);
        dateFormatList.add(dateFormat33);
        dateFormatList.add(dateFormat34);
        dateFormatList.add(dateFormat35);
        dateFormatList.add(dateFormat36);
        dateFormatList.add(dateFormat37);
        dateFormatList.add(dateFormat38);
        dateFormatList.add(dateFormat39);
        dateFormatList.add(dateFormat40);
        dateFormatList.add(dateFormat41);
        dateFormatList.add(dateFormat42);
        dateFormatList.add(dateFormat43);
        dateFormatList.add(dateFormat44);
        dateFormatList.add(dateFormat45);
        dateFormatList.add(dateFormat46);
        dateFormatList.add(dateFormat47);
        dateFormatList.add(dateFormat48);
        dateFormatList.add(dateFormat49);
    }
}
