package io.renren.utils;

import org.apache.log4j.Logger;
import org.apkinfo.api.util.AXmlResourceParser;
import org.apkinfo.api.util.TypedValue;
import org.apkinfo.api.util.XmlPullParser;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 抽取apk中的信息
 *
 * @authorZJS
 * @create 2017-05-05 10:15
 */
public class ExtractAPK {

    private static Logger logger = Logger.getLogger(ExtractAPK.class);

    private static String getPackage(int id) {
        if (id >>> 24 == 1) {
            return "android:";
        }
        return "";
    }

    private static float complexToFloat(int complex) {
        return (float) (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4) & 3];
    }

    private static final float RADIX_MULTS[] = { 0.00390625F, 3.051758E-005F,
            1.192093E-007F, 4.656613E-010F };
    private static final String DIMENSION_UNITS[] = { "px", "dip", "sp", "pt", "in",
            "mm", "", "" };
    private static final String FRACTION_UNITS[] = { "%", "%p", "", "", "", "", "", "" };

    /**
     *
     * @param filePath apk路径，例如：/home/ /Qk_test.apk
     * @return versionCodee
     * @throws Exception
     */
    public static Map<String,Object> getApkVersion(String filePath) throws Exception {

        String versionCode = null;
        Map<String,Object> mapApkInfo = new HashMap<String, Object>();
        ZipFile zip = null;
        try {

            File file = new File(filePath);
            zip = new ZipFile(file);
            Enumeration enume = zip.entries();

            String filename = null;
            ZipEntry zipEntry = null;
            while (enume.hasMoreElements()) {
                zipEntry = (ZipEntry) enume.nextElement();
                // 判断是目录还是文件
                if(!zipEntry.isDirectory()){
                    filename = zipEntry.getName();
                    if ("AndroidManifest.xml".equalsIgnoreCase(filename)) {
                        mapApkInfo = getCode(zip.getInputStream(zipEntry));
                        break;
                    }
                }else{

                }
            }

        } catch (Exception e) {
            logger.error("读取apk文件失败", e);
            throw e;
        } finally {
            try {
                if (null != zip)
                    zip.close();
            } catch (Exception e) {
                logger.error("关闭zip失败", e);
            }
        }

        return mapApkInfo;
    }

    private static Map<String,Object> getCode(InputStream is) throws Exception {

        Map<String,Object> map = new HashMap<String, Object>();
        try {
            AXmlResourceParser parser = new AXmlResourceParser();
            parser.open(is);
            boolean brek = false;
            while (true) {
                int type = parser.next();
                if (type == XmlPullParser.END_DOCUMENT) {
                    break;
                }
                String name = parser.getName();
                if(null != name && name.toLowerCase().equals("manifest")){
                    for (int i = 0; i != parser.getAttributeCount(); i++) {
                        if ("versionName".equals(parser.getAttributeName(i))) {
                            String versionName = getAttributeValue(parser, i);
                            if(null == versionName){
                                versionName = "";
                            }
                            map.put("versionName", versionName);
                        } else if ("package".equals(parser.getAttributeName(i))) {
                            String packageName = getAttributeValue(parser, i);
                            if(null == packageName){
                                packageName = "";
                            }
                            map.put("package", packageName);
                        } else if("versionCode".equals(parser.getAttributeName(i))){
                            String versionCode = getAttributeValue(parser, i);
                            if(null == versionCode){
                                versionCode = "";
                            }
                            map.put("versionCode", versionCode);
                        }
                    }
                    break;
                }
            }

        } catch (Exception e) {
            logger.error("读取versionCode失败", e);
            throw e;
        }

        return map;
    }

    private static String getAttributeValue(AXmlResourceParser parser, int index) {
        int type = parser.getAttributeValueType(index);
        int data = parser.getAttributeValueData(index);
        if (type == TypedValue.TYPE_STRING) {
            return parser.getAttributeValue(index);
        }
        if (type == TypedValue.TYPE_ATTRIBUTE) {
            return String.format("?%s%08X", getPackage(data), data);
        }
        if (type == TypedValue.TYPE_REFERENCE) {
            return String.format("@%s%08X", getPackage(data), data);
        }
        if (type == TypedValue.TYPE_FLOAT) {
            return String.valueOf(Float.intBitsToFloat(data));
        }
        if (type == TypedValue.TYPE_INT_HEX) {
            return String.format("0x%08X", data);
        }
        if (type == TypedValue.TYPE_INT_BOOLEAN) {
            return data != 0 ? "true" : "false";
        }
        if (type == TypedValue.TYPE_DIMENSION) {
            return Float.toString(complexToFloat(data))
                    + DIMENSION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
        }
        if (type == TypedValue.TYPE_FRACTION) {
            return Float.toString(complexToFloat(data))
                    + FRACTION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
        }
        if (type >= TypedValue.TYPE_FIRST_COLOR_INT
                && type <= TypedValue.TYPE_LAST_COLOR_INT) {
            return String.format("#%08X", data);
        }
        if (type >= TypedValue.TYPE_FIRST_INT
                && type <= TypedValue.TYPE_LAST_INT) {
            return String.valueOf(data);
        }
        return String.format("<0x%X, type 0x%02X>", data, type);
    }


}
