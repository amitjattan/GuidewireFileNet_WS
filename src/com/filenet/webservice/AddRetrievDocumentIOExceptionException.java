
/**
 * AddRetrievDocumentIOExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.4  Built on : Oct 21, 2016 (10:47:34 BST)
 */

package com.filenet.webservice;

public class AddRetrievDocumentIOExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1492581798214L;
    
    private com.filenet.webservice.AddRetrievDocumentStub.AddRetrievDocumentIOException faultMessage;

    
        public AddRetrievDocumentIOExceptionException() {
            super("AddRetrievDocumentIOExceptionException");
        }

        public AddRetrievDocumentIOExceptionException(java.lang.String s) {
           super(s);
        }

        public AddRetrievDocumentIOExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public AddRetrievDocumentIOExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.filenet.webservice.AddRetrievDocumentStub.AddRetrievDocumentIOException msg){
       faultMessage = msg;
    }
    
    public com.filenet.webservice.AddRetrievDocumentStub.AddRetrievDocumentIOException getFaultMessage(){
       return faultMessage;
    }
}
    