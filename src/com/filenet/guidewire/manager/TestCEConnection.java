package com.filenet.guidewire.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import com.filenet.api.collection.ChoiceListSet;
import com.filenet.api.collection.DocumentSet;
import com.filenet.api.constants.PropertyNames;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Factory.ChoiceList;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;


public class TestCEConnection {

	public TestCEConnection() {
		// TODO Auto-generated constructor stub
	}


	public static void main(String[] args) throws Exception {


		CEConnectionManager CE=new CEConnectionManager();


		ObjectStore ObjectStore = CE.getObjectStore_Dev();


		System.out.println("ObjectaStore::"+ObjectStore);


	}


}
