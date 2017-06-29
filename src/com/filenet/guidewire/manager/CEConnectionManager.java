package com.filenet.guidewire.manager;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Iterator;


import javax.security.auth.Subject;

import org.apache.log4j.Logger;

import com.filenet.api.collection.ObjectStoreSet;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;


public class CEConnectionManager {


	private static Logger logger = Logger.getLogger(CEConnectionManager.class);

	/******** Development Content Engine Connection ********/

	public static ObjectStore getObjectStore_Dev() throws GeneralSecurityException, IOException

	{
		ObjectStore store = null;
		try{


			String url = Messages.getString("CE_OPS.CEURL"); //Content Engine Url

			String username = Messages.getString("CE_OPS.USERNAME"); //UserName 
			String objectStoreName=Messages.getString("CE_OPS.OSNAME");
			String password="";
			password=Messages.getString("CE_OPS.PASSWORD");
			//password = PasswordUtils.decrypt( Messages.getString("CE_OPS.15")); 





			Connection conn = (Connection) Factory.Connection.getConnection(url);

			//UserContext.createSubject(conn, user, password, optionalJAASStanzaName)
			Subject subject = UserContext.createSubject(conn, username, password, "FileNetP8WSI");

			UserContext.get().pushSubject(subject);


			// Get default domain.
			Domain domain = Factory.Domain.fetchInstance((com.filenet.api.core.Connection) conn, null, null);
			logger.info("Domain: " + domain.get_Name());

			// Get object stores for domain.
			 store=Factory.ObjectStore.fetchInstance(domain, objectStoreName, null);
			logger.info("Connection to Content Engine successful");
		}
		catch (Exception e) {
			// TODO: handle exception
			logger.info("Exception::"+e.getMessage());
			System.out.println("Exception::"+e.getMessage());
		}
		return store;
	}















}
