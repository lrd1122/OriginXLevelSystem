package gx.lrd1122.OriginXLevelSystem.OriginXAPI;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OriginXJavaAPI {
    public static HashMap<String, String> getArgs(String key){
        Pattern pattern = Pattern.compile("[\\(|（].*[\\)|）]$");
        Matcher matcher = pattern.matcher(key);
        if(matcher.find()){
            HashMap<String, String> map = new HashMap<>();
            String[] find = matcher.group().substring(1, matcher.group().length() - 1).split(";");
            for(String str : find){
                String[] arg = str.split("=");
                map.put(arg[0], arg[1]);
            }
            return map;
        }
        else{
            return null;
        }
    }
    public static String getMd5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.digest(text.getBytes(StandardCharsets.UTF_8));

        StringBuilder builder = new StringBuilder();

        for (byte aByte : bytes) {
            builder.append(Integer.toHexString((0x000000FF & aByte) | 0xFFFFFF00).substring(6));
        }

        return builder.toString();
    }

    public static String MapToString(Map<String, String> map){
        List<String> keys = new ArrayList<>(map.keySet());
        String value = keys.get(0) + "=" + map.get(keys.get(0));
        for(int i = 1; i < keys.size(); i++){
            value = "&" + keys.get(i) + "=" + map.get(keys.get(i));
        }
        return value;
    }
    public static boolean SingleStringFormula(String s){
        s = s.replace(" ", "");
        if(s.contains("<")){
            String[] strings = s.split("<");
            if(Integer.parseInt(strings[0]) < Integer.parseInt(strings[1])){
                return true;
            }
        }
        if(s.contains(">")){
            String[] strings = s.split(">");
            if(Integer.parseInt(strings[0]) > Integer.parseInt(strings[1])){
                return true;
            }
        }
        if(s.contains("<=")){
            String[] strings = s.split("<=");
            if(Integer.parseInt(strings[0]) <= Integer.parseInt(strings[1])){
                return true;
            }
        }
        if(s.contains(">=")){
            String[] strings = s.split(">=");
            if(Integer.parseInt(strings[0]) >= Integer.parseInt(strings[1])){
                return true;
            }
        }
        if(s.contains("==")){
            String[] strings = s.split("==");
            if(Integer.parseInt(strings[0]) == Integer.parseInt(strings[1])){
                return true;
            }
        }
        return false;
    }
    public static boolean LineStringFormula(String s){
        boolean out = true;
        String[] strings = s.split("\\|\\||&&");
        for(String key : strings){
            if(!SingleStringFormula(key)){
                out = false;
                break;
            }
        }
        return out;

    }
}
