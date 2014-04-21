
package org.tempuri;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "MrBaseServiceSoap", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface MrBaseServiceSoap {


    /**
     * 
     * @param errMessage
     * @param aUserCode
     * @param wmUserLoginByPasswordResult
     * @param aPassword
     */
    @WebMethod(action = "http://tempuri.org/wmUserLoginByPassword")
    @RequestWrapper(localName = "wmUserLoginByPassword", targetNamespace = "http://tempuri.org/", className = "org.tempuri.WmUserLoginByPassword")
    @ResponseWrapper(localName = "wmUserLoginByPasswordResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.WmUserLoginByPasswordResponse")
    public void wmUserLoginByPassword(
        @WebParam(name = "AUserCode", targetNamespace = "http://tempuri.org/")
        String aUserCode,
        @WebParam(name = "APassword", targetNamespace = "http://tempuri.org/")
        String aPassword,
        @WebParam(name = "errMessage", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.INOUT)
        Holder<String> errMessage,
        @WebParam(name = "wmUserLoginByPasswordResult", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.OUT)
        Holder<Integer> wmUserLoginByPasswordResult);

    /**
     * 
     * @param errMessage
     * @param token
     * @param userCode
     * @param wmCheckUserByTokenResult
     */
    @WebMethod(action = "http://tempuri.org/wmCheckUserByToken")
    @RequestWrapper(localName = "wmCheckUserByToken", targetNamespace = "http://tempuri.org/", className = "org.tempuri.WmCheckUserByToken")
    @ResponseWrapper(localName = "wmCheckUserByTokenResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.WmCheckUserByTokenResponse")
    public void wmCheckUserByToken(
        @WebParam(name = "userCode", targetNamespace = "http://tempuri.org/")
        String userCode,
        @WebParam(name = "token", targetNamespace = "http://tempuri.org/")
        String token,
        @WebParam(name = "errMessage", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.INOUT)
        Holder<String> errMessage,
        @WebParam(name = "wmCheckUserByTokenResult", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.OUT)
        Holder<Integer> wmCheckUserByTokenResult);

    /**
     * 
     * @param aUserName
     * @param aUserCode
     * @param aUserDepID
     * @param aUserID
     * @param aUserDepCode
     * @param aUserLongDepName
     * @param aSessionID
     * @param aUserDepName
     * @param wmUserInfoExtResult
     */
    @WebMethod(action = "http://tempuri.org/wmUserInfoExt")
    @RequestWrapper(localName = "wmUserInfoExt", targetNamespace = "http://tempuri.org/", className = "org.tempuri.WmUserInfoExt")
    @ResponseWrapper(localName = "wmUserInfoExtResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.WmUserInfoExtResponse")
    public void wmUserInfoExt(
        @WebParam(name = "ASessionID", targetNamespace = "http://tempuri.org/")
        String aSessionID,
        @WebParam(name = "wmUserInfoExtResult", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.OUT)
        Holder<Integer> wmUserInfoExtResult,
        @WebParam(name = "AUserID", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.OUT)
        Holder<Long> aUserID,
        @WebParam(name = "AUserCode", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.OUT)
        Holder<String> aUserCode,
        @WebParam(name = "AUserName", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.OUT)
        Holder<String> aUserName,
        @WebParam(name = "AUserDepID", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.OUT)
        Holder<Long> aUserDepID,
        @WebParam(name = "AUserDepCode", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.OUT)
        Holder<String> aUserDepCode,
        @WebParam(name = "AUserDepName", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.OUT)
        Holder<String> aUserDepName,
        @WebParam(name = "AUserLongDepName", targetNamespace = "http://tempuri.org/", mode = WebParam.Mode.OUT)
        Holder<String> aUserLongDepName);

}
