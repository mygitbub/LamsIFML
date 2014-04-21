
package org.tempuri;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "DataSvc", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://tlk.glodon.com:8877/WebService/LK6SyncService/MrMMSSvc/DataSvc.asmx?WSDL")
public class DataSvc
    extends Service
{

    private final static URL DATASVC_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(org.tempuri.DataSvc.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = org.tempuri.DataSvc.class.getResource(".");
            url = new URL(baseUrl, "http://tlk.glodon.com:8877/WebService/LK6SyncService/MrMMSSvc/DataSvc.asmx?WSDL");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://tlk.glodon.com:8877/WebService/LK6SyncService/MrMMSSvc/DataSvc.asmx?WSDL', retrying as a local file");
            logger.warning(e.getMessage());
        }
        DATASVC_WSDL_LOCATION = url;
    }

    public DataSvc(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DataSvc() {
        super(DATASVC_WSDL_LOCATION, new QName("http://tempuri.org/", "DataSvc"));
    }

    /**
     * 
     * @return
     *     returns DataSvcSoap
     */
    @WebEndpoint(name = "DataSvcSoap")
    public DataSvcSoap getDataSvcSoap() {
        return super.getPort(new QName("http://tempuri.org/", "DataSvcSoap"), DataSvcSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns DataSvcSoap
     */
    @WebEndpoint(name = "DataSvcSoap")
    public DataSvcSoap getDataSvcSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "DataSvcSoap"), DataSvcSoap.class, features);
    }

}
