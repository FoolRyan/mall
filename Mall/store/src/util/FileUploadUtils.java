package util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class FileUploadUtils {

    public static Map<String, String> parseRequest(HttpServletRequest request) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = request.getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        ServletFileUpload upload = new ServletFileUpload(factory);
        Map<String,String> resultMap = new HashMap<>();
        try {
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> iterator = items.iterator();
            while (iterator.hasNext()) {
                FileItem item = iterator.next();
                if (item.isFormField()) {
                    // 处理普通form表单数据
                    processFormField(item,resultMap);
                }else {
                    // 处理上传的文件
                    processUploadedFile(item,resultMap,request);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    private static void processUploadedFile(FileItem item, Map<String, String> resultMap,HttpServletRequest request) {
        String fieldName = item.getFieldName();
        String fileName = item.getName();
        System.out.println("upload file :" + fieldName);
        System.out.println("upload file :" + fileName);
        String uuid = UUID.randomUUID().toString();
        fileName = uuid + fileName;
        int hashCode = fileName.hashCode();
        String hexString = Integer.toHexString(hashCode);
        System.out.println(hexString);
        char[] chars = hexString.toCharArray();
        String uploadPath = "upload";
        for (char c : chars) {
            uploadPath = uploadPath + "/" + c;
        }
        String realPath = request.getServletContext().getRealPath(uploadPath + "/" + fileName);
        File file = new File(realPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            item.write(file);
            resultMap.put(fieldName,uploadPath + "/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processFormField(FileItem item, Map<String, String> resultMap) {
        String fieldName = item.getFieldName();
        String string = null;
        try {
            string = item.getString("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        resultMap.put(fieldName, string);
    }
}
