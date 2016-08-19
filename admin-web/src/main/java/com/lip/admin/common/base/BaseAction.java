package com.lip.admin.common.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lip.admin.common.security.util.MyRequestContextHolder;
import com.lip.admin.util.ExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.google.common.collect.Maps;

@SuppressWarnings("all")
public abstract class BaseAction implements Serializable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 成功
    protected static final String SUCCESS = "success";
    // 失败
    protected static final String ERROR = "error";

    // 消息
    protected static final String MSG = "msg";
    
    //错误码
    protected static final String ERRORCODE = "errorCode";
    //错误信息
    protected static final String ERRORMSG = "errorMsg";
    
    
    /**
     * Description: 获得分页和排序的参数Map
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author lip
     * Create Date: 2015-1-21 上午10:14:30
     */
    public Map<String, Object> getPageParam(){
        HttpServletRequest request = MyRequestContextHolder.getCurrentRequest();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String pageStr = request.getParameter("page");
        pageStr=StringUtils.isEmpty(pageStr)?"1":pageStr;
        int page = Integer.parseInt(pageStr);
        page=page==0?1:page;
        String rowsStr = request.getParameter("rows");
        rowsStr = StringUtils.isEmpty(rowsStr)?"0":rowsStr;
        int rows=Integer.parseInt(rowsStr);
        paramMap.put("startPage", (page - 1) * rows);
        paramMap.put("endPage", rows);
        paramMap.put("sort", request.getParameter("sort"));
        paramMap.put("order", request.getParameter("order"));
        return paramMap;
    }
    
    /**
     * Description: 获取所有的传递参数
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author lip
     * Create Date: 2015-2-9 下午3:12:24
     */
    public Map<String, Object> getRequestParam() {
        HttpServletRequest request = MyRequestContextHolder.getCurrentRequest();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            logger.error("", e);
        }
        Map<String, Object> result = Maps.newHashMap();
        Iterator<?> iter = request.getParameterMap().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String value = request.getParameter(key);
            try {
                value = new String(value.getBytes("iso8859-1"),request.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
            }
            result.put(key, value);
        }
        return result;
    }
    
    @ExceptionHandler(Exception.class)
    public @ResponseBody Map<String, Object> handleException(Exception ex,HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ex instanceof org.springframework.web.multipart.MaxUploadSizeExceededException){
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "文件大小不应大于"+((MaxUploadSizeExceededException)ex).getMaxUploadSize()/1000+"kb");
         } else{
             logger.error("参数异常", ex);
             resultMap.put(SUCCESS, false);
             resultMap.put(MSG, "参数异常");
        }
        return resultMap;
    }
    
    /**
     * 将查询结果封装为easyui自带的分页模式
     * @date 2015年12月31日
     * @author lip
     * @param list
     * @return
     */
    public Object toSearchResult(List list){
        Map map = getRequestParam();
        String page = (String)map.get("page");
        String rows = (String)map.get("rows");
        int size = list.size();
        int pageNo = Integer.valueOf(page!=null ? page  :   "1");
        int pageSize = Integer.valueOf(rows!=null ? rows  :   ""+size);
        int fromIndex = (pageNo-1) * pageSize;
        int toIndex = fromIndex + pageSize;
        
        toIndex = toIndex > size ? size : toIndex;
        return new SearchResult<>(list.subList(fromIndex, toIndex), size );
    }
    
    /**
     * 下载需要excel
     * 
     * @param response
     * @param list
     * @param keys
     * @param columnNames
     */
    public void download(HttpServletResponse response, List<Map<String, Object>> list, String[] keys,
            String[] columnNames) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            ExcelUtil.createWorkBook(null, list, keys, columnNames).write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String((System.currentTimeMillis() + ".xls").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();

            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("fail to download :{}", e);
        } finally {
            if (bis != null)
                try {
                    bis.close();
                    if (bos != null)
                        bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    }
}