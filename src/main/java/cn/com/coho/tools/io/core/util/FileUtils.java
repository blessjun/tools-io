package cn.com.coho.tools.io.core.util;

import cn.hutool.core.collection.LineIter;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

public class FileUtils {

    public static String getFilePath(String str) {
        if (StringUtils.isBlank(str)) {
            return StringUtils.EMPTY;
        }
        str = str.replace("\\", "/");
        if (str.startsWith("/")) {
            str = str.substring(1);
        }
        return str;
    }

    public static String getFileName(String filePath) {
        return new File(filePath).getName();
    }

    public static String getFullPath(String rootPath, String tenantCode, String filePath) {

        try {
            Path path = Paths.get(rootPath, tenantCode, filePath);
            String fullPathString = new File(path.toString()).getCanonicalPath();
            if (!isBelongToParent(rootPath, fullPathString)) {
                return null;
            }
            return fullPathString;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //逐行写入文件
    public static boolean writer(String rootPath, String tenantCode, String filePath, String value, Boolean isAppend) {
        try {
            boolean ret = createDir(rootPath, tenantCode, filePath);
            if (ret) {
                String path = Paths.get(getFolder(rootPath, tenantCode, filePath), getFileName(filePath)).toString();
                FileWriter writer = new FileWriter(path);
                writer.write(value, isAppend);
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("写入文件失败：".concat(filePath), e);
        }
        return false;
    }

    //逐行追加写入文件
    public static boolean appendLines(String rootPath, String tenantCode, String filePath, Iterable<?> list, Boolean isAppend) {
        try {
            boolean ret = createDir(rootPath, tenantCode, filePath);
            if (ret) {
                String path = Paths.get(getFolder(rootPath, tenantCode, filePath), getFileName(filePath)).toString();
                FileWriter writer = new FileWriter(path);
                writer.writeLines(JSON.parseArray(JSON.toJSONString(list)), isAppend);
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("写入文件失败：".concat(filePath), e);
        }
        return false;
    }

    public static boolean saveFile(String rootPath, String tenantCode, String filePath, InputStream stream) {
        try {
            boolean ret = createDir(rootPath, tenantCode, filePath);
            if (ret) {
                String path = Paths.get(getFolder(rootPath, tenantCode, filePath), getFileName(filePath)).toString();
                FileWriter writer = new FileWriter(path);
                writer.writeFromStream(stream);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //读取文件 全部字符
    public static String readString(String rootPath, String tenantCode, String filePath) {
        try {
            boolean ret = createDir(rootPath, tenantCode, filePath);
            if (ret) {
                String path = Paths.get(getFolder(rootPath, tenantCode, filePath), getFileName(filePath)).toString();
                FileReader reader = new FileReader(path);
                String string = reader.readString();
                return string;
            }
        } catch (Exception e) {
            throw new RuntimeException("读取文件失败：".concat(filePath), e);
        }
        return null;
    }

    //清理文件
    public static void clean(String rootPath, String tenantCode, String filePath) {
        try {
            boolean ret = createDir(rootPath, tenantCode, filePath);
            if (ret) {
                String path = Paths.get(getFolder(rootPath, tenantCode, filePath)).toString();
                boolean clean = FileUtil.clean(path);
            }
        } catch (Exception e) {
            throw new RuntimeException("清理文件失败：".concat(filePath), e);
        }
    }


    //读取文件 按行读取 最多读1024*1024 字节
    public static JSONArray readLines(String rootPath, String tenantCode, String filePath, long lineNum) {
        try {
            boolean ret = createDir(rootPath, tenantCode, filePath);
            JSONArray jsonArray = new JSONArray();
            if (ret) {
                String path = Paths.get(getFolder(rootPath, tenantCode, filePath), getFileName(filePath)).toString();
                BufferedReader reader = null;
                try {
                    reader = FileUtil.getReader(path, CharsetUtil.CHARSET_UTF_8);

                    Iterator var2 = new LineIter(reader).iterator();
                    long index = 0;
                    while (var2.hasNext()) {
                        index++;
                        String line = (String) var2.next();
                        if (lineNum <= index) {
                            jsonArray.add(JSON.parseObject(line));
                        }
                        int length = jsonArray.toJSONString().getBytes().length;
                        System.out.println("读取文件字节长度是：" + length);
                        if (length > 1024 * 1024) {
                            return jsonArray;
                        }
                    }
                } finally {
                    IoUtil.close(reader);
                }
            }
            return jsonArray;
        } catch (Exception e) {
            throw new RuntimeException("按行读取文件失败：".concat(filePath), e);
        }
    }

    public static boolean createDir(String rootPath, String tenantCode, String filePath) {
        try {
            String folder = getFolder(rootPath, tenantCode, filePath);
            if (!isBelongToParent(rootPath, folder)) {
                return false;
            }
            File fileNew = new File(folder);
            if (!fileNew.exists()) {// 如果文件夹不存在
                fileNew.mkdirs();// 创建文件夹
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getFolder(String rootPath, String tenantCode, String filePath) throws Exception {
        Path path = Paths.get(rootPath, tenantCode, filePath);
        File file = new File(path.toString());
        return file.getCanonicalFile().getParent();
    }

    /**
     * 主要防止跨路径访问 ...../。。/。。/
     *
     * @param rootPath
     * @param filePath
     * @return
     */
    public static boolean isBelongToParent(String rootPath, String filePath) {
        try {
            String file1 = new File(rootPath).getCanonicalPath();
            String file2 = new File(filePath).getCanonicalPath();
            return file2.startsWith(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 校验文件格式是否正确
     *
     * @param name
     * @param format(例如：png、jpg、jpeg、bmp)
     * @return
     */
    public static boolean checkFormat(String name, String... format) {
        String ext = FileNameUtil.extName(name);
        if (!StringUtils.isBlank(ext)) {
            return Arrays.asList(format).stream().filter(x -> x.equalsIgnoreCase(ext)).findAny().isPresent();
        }
        return false;
    }

}
