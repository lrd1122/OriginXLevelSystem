package gx.lrd1122.OriginXLevelSystem.OriginXAPI;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class OriginXWindowsAPI {
    public static OriginXLanguage getWindowsLanguage(){
        Locale locale = Locale.getDefault();
        if(locale.equals(Locale.SIMPLIFIED_CHINESE)){
            return OriginXLanguage.zhCN;
        }
        if(locale.equals(Locale.ENGLISH)){
            return OriginXLanguage.enUS;
        }
        return OriginXLanguage.enUS;
    }
    public static String getSerial() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            String serial = sc.next();
            return serial;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
