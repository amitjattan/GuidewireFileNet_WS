
/**
 * AddRetrievDocumentGeneralSecurityExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.4  Built on : Oct 21, 2016 (10:47:34 BST)
 */

package com.filenet.webservice;

public class AddRetrievDocumentGeneralSecurityExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1492581798191L;
    
    private com.filenet.webservice.AddRetrievDocumentStub.AddRetrievDocumentGeneralSecurityException faultMessage;

    
        public AddRetrievDocumentGeneralSecurityExceptionException() {
            super("AddRetrievDocumentGeneralSecurityExceptionException");
        }

        public AddRetrievDocumentGeneralSecurityExceptionException(java.lang.String s) {
           super(s);
        }

        public AddRetrievDocumentGeneralSecurityExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public AddRetrievDocumentGeneralSecurityExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.filenet.webservice.AddRetrievDocumentStub.AddRetrievDocumentGeneralSecurityException msg){
       faultMessage = msg;
    }
    
    public com.filenet.webservice.AddRetrievDocumentStub.AddRetrievDocumentGeneralSecurityException getFaultMessage(){
       return faultMessage;
    }
}
    