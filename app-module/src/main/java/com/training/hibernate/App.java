package com.training.hibernate;

import java.util.Scanner;
import com.training.hibernate.AppManager;

public class App {
    public static void main( String[] args ) {
        Scanner in = new Scanner(System.in);
        AppManager appManager = new AppManager();
        boolean cont = true;
        appManager.menu();
        while(cont){
        	int select = in.nextInt();
        	switch(select){
	        	case 1: appManager.showAllPersons();
	        		appManager.next();
	        		break;
	        	case 2: appManager.getPersonByLastName();
	        		appManager.next();
		        	break;
		        case 3: appManager.getAddressById();
		        	appManager.next();
		        	break;
		        case 4: appManager.getContactsByPersonId();
		        	appManager.next();
		        	break;	
		        case 5: appManager.addPerson();
		        	appManager.next();
		        	break;
		        case 6: appManager.addContactByPersonId();
		        	appManager.next();
		        	break;
		        case 7: appManager.updatePerson();
		        	appManager.next();
		        	break;
		        case 8: appManager.updateAddress();
		        	appManager.next();
		        	break;
		        case 9: appManager.deletePersonById();
		        	appManager.next();
		        	break;
		        case 10: appManager.deleteContactById();
		        	appManager.next();
		        	break;							
	        	case 11: cont = false;
	        		System.exit(0);
	        		break;
	        	default: System.out.println("Invalid");
	        	break;
        	}
        }
    }
}
