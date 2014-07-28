package org.jboss.test.ws.jaxws.cxf.jbws3809;

import org.jboss.ws.api.annotation.WebContext;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * User: rsearls
 * Date: 7/25/14
 */
@WebService(
   portName = "EjbWebContextServicePort",
   targetNamespace = "http://org.jboss.ws.test")
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL)
@WebContext(urlPattern="MyEjbWebContext")
public class EjbWebContext  implements BasicEjb {
   @WebMethod(operationName = "getStr")
   public String getStr (@WebParam(name = "str") String str) {
      return this.getClass().getSimpleName() + ": " + str;
   }
}
