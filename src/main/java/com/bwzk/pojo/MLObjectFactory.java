//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.11 at 05:15:35 下午 CST 
//


package com.bwzk.pojo;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.ly package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class MLObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.ly
     * 
     */
    public MLObjectFactory() {
    }

    /**
     * Create an instance of {@link MLXmlResult }
     * 
     */
    public MLXmlResult createResult() {
        return new MLXmlResult();
    }

    /**
     * Create an instance of {@link MLXmlResult.DocInfo }
     * 
     */
    public MLXmlResult.DocInfo createResultDocInfo() {
        return new MLXmlResult.DocInfo();
    }

    /**
     * Create an instance of {@link MLXmlResult.Attachments.Attachment }
     * 
     */
    public MLXmlResult.Attachments.Attachment createResultAttachmentsAttachment() {
        return new MLXmlResult.Attachments.Attachment();
    }

    /**
     * Create an instance of {@link MLXmlResult.Attachments }
     * 
     */
    public MLXmlResult.Attachments createResultAttachments() {
        return new MLXmlResult.Attachments();
    }

    /**
     * Create an instance of {@link MLXmlResult.DocInfo.Field }
     * 
     */
    public MLXmlResult.DocInfo.Field createResultDocInfoField() {
        return new MLXmlResult.DocInfo.Field();
    }

}
