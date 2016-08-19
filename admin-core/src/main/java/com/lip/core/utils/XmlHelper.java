/* 
 *
 * ============================================================
 *
 * FileName: XmlHelper.java 
 *
 * Created: [2015年9月22日 下午4:22:11] by lip
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 *
 * Description: 
 * 
 * ==========================================================*/

package com.lip.core.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.persistence.criteria.Root;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlHelper {
    /**
     * 将自定义数据对象转化为XML字符串
     * @param clazz 自定义数据类型
     * @param object 自定义数据对象
     * @return XML字符串
     * @throws JAXBException 异常
     */
    @SuppressWarnings({ "rawtypes" })
    public static String objectToXML(Class clazz, Object object)
            throws Exception
        {
            String xml = null;
            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
            Writer w = new StringWriter();
            //ByteArrayOutputStream w = new ByteArrayOutputStream();  
            m.marshal(object, w);
            //xml = w.toString();
            xml = w.toString().replace(" standalone=\"yes\"", "");
            return xml;
        }
    
    
    /**
     * 将自定义数据对象转化为XML字符串
     * @param clazz 自定义数据类型
     * @param object 自定义数据对象
     * @return XML字符串
     * @throws JAXBException 异常
     */
    @SuppressWarnings({ "rawtypes" })
    public static String objectToXML_ICBC(Class clazz, Object object)
            throws Exception
        {
            String xml = null;
            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);
            Writer w = new StringWriter();
            //ByteArrayOutputStream w = new ByteArrayOutputStream();  
            m.marshal(object, w);
            //xml = w.toString();
            xml = w.toString().replace(" standalone=\"yes\"", "");
            return xml;
        }
    
    
    /**
     * 将XML字符串转化为自定义数据对象
     * 
     * @param clazz 自定义数据类型
     * @param xml XML字符串
     * @return 自定义数据对象
     * @throws JAXBException 异常
     */
    @SuppressWarnings({ "rawtypes" })
    public static Object xmlToObject(Class clazz, String xml)
        throws JAXBException
    {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller um = context.createUnmarshaller();
        return um.unmarshal(new StringReader(xml));
    }
    
}
