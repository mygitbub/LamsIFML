package com.bwzk.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * xml和对象的互相转换.要求待转换对象是需要符合xsd描述的
 * @author LuYu
 */
public class XmlObjUtil<T> {
	public static <T> T xml2Obj(String xmlString, Class<T> clazz) throws RuntimeException {
		T c = null;
		Reader reader = null;
		try {
			reader = new StringReader(xmlString);
			Unmarshaller unMarshaller = JAXBContext.newInstance(clazz)
					.createUnmarshaller();
			c = (T) unMarshaller.unmarshal(reader);
		} catch (JAXBException ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return c;
	}

	public  static <T> String obj2Xml(T c) throws RuntimeException {
		String returnXml = "";
		Writer writer = null;
		try {
			writer = new StringWriter();
			JAXBContext.newInstance(c.getClass()).createMarshaller()
					.marshal(c, writer);
			returnXml = writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return returnXml;
	}
	
	/**
	 * <p>Title: xmlString parse to dom4j.document</p>
	 * <p>Description: 利用dom4j 将xmlstr解析成dom4j.Document</p>
	 * @param xmlStr
	 * @return: dom4j.Document
	 * 
	 * @date 2014年1月22日
	*/
	public static synchronized Document xmlStr2Document(String xmlStr){
		Document docu = null;
		SAXBuilder sBuilder = null;
		StringReader reader = null;
		try {
			sBuilder = new SAXBuilder();
			reader = new StringReader(xmlStr);
			docu = sBuilder.build(reader);
		} catch (JDOMException e) {
			throw new RuntimeException("xml字符串为空"+e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("xml字符串为空"+e.getMessage());
		}finally{
			if(reader != null){
				reader.close();
			}
		}
		return docu;
	}
}
