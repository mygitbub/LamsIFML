package com.bwzk.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
}
