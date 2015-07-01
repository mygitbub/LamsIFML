
package org.tempuri;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "DataSvcSoap", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface DataSvcSoap {


    /**
     * 根据数据类型返回全部数据
     * 
     * @param dataType
     * @param token
     * @return
     *     returns org.tempuri.ReturnData
     */
    @WebMethod(operationName = "GetAllData", action = "http://tempuri.org/GetAllData")
    @WebResult(name = "GetAllDataResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetAllData", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetAllData")
    @ResponseWrapper(localName = "GetAllDataResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetAllDataResponse")
    public ReturnData getAllData(
        @WebParam(name = "Token", targetNamespace = "http://tempuri.org/")
        String token,
        @WebParam(name = "DataType", targetNamespace = "http://tempuri.org/")
        String dataType);

    /**
     * 返回当前库中的USER表标示。
     * 
     * @param token
     * @param getUserFlagResult
     * @param sMSG
     */
    @WebMethod(operationName = "GetUserFlag", action = "http://tempuri.org/GetUserFlag")
    @RequestWrapper(localName = "GetUserFlag", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetUserFlag")
    @ResponseWrapper(localName = "GetUserFlagResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetUserFlagResponse")
    public void getUserFlag(
        @WebParam(name = "Token", targetNamespace = "http://tempuri.org/")
        String token,
        @WebParam(name = "sMSG", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.INOUT)
        Holder<String> sMSG,
        @WebParam(name = "GetUserFlagResult", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.OUT)
        Holder<Integer> getUserFlagResult);

    /**
     * 返回部门用户数据信息。
     * 
     * @param dataType
     * @param token
     * @param getDataResult
     * @param err
     */
    @WebMethod(operationName = "GetData", action = "http://tempuri.org/GetData")
    @RequestWrapper(localName = "GetData", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetData")
    @ResponseWrapper(localName = "GetDataResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetDataResponse")
    public void getData(
        @WebParam(name = "Token", targetNamespace = "http://tempuri.org/")
        String token,
        @WebParam(name = "DataType", targetNamespace = "http://tempuri.org/")
        String dataType,
        @WebParam(name = "err", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.INOUT)
        Holder<String> err,
        @WebParam(name = "GetDataResult", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.OUT)
        Holder<String> getDataResult);

}
