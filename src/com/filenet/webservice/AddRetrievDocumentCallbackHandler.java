
/**
 * AddRetrievDocumentCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.4  Built on : Oct 21, 2016 (10:47:34 BST)
 */

    package com.filenet.webservice;

    /**
     *  AddRetrievDocumentCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class AddRetrievDocumentCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public AddRetrievDocumentCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public AddRetrievDocumentCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for addDocumentToCE method
            * override this method for handling normal response from addDocumentToCE operation
            */
           public void receiveResultaddDocumentToCE(
                    com.filenet.webservice.AddRetrievDocumentStub.AddDocumentToCEResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addDocumentToCE operation
           */
            public void receiveErroraddDocumentToCE(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for testWS method
            * override this method for handling normal response from testWS operation
            */
           public void receiveResulttestWS(
                    com.filenet.webservice.AddRetrievDocumentStub.TestWSResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from testWS operation
           */
            public void receiveErrortestWS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for retrieveDocumentFromCE method
            * override this method for handling normal response from retrieveDocumentFromCE operation
            */
           public void receiveResultretrieveDocumentFromCE(
                    com.filenet.webservice.AddRetrievDocumentStub.RetrieveDocumentFromCEResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from retrieveDocumentFromCE operation
           */
            public void receiveErrorretrieveDocumentFromCE(java.lang.Exception e) {
            }
                


    }
    