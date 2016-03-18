package com.training.hibernate;

import com.training.hibernate.model.*;
import com.training.hibernate.services.ManagePersonService;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class AppManager {
	private ManagePersonService managePersonService = new ManagePersonService();

	public void menu(){
		System.out.println(String.format("%35s","-----------------------------------"));
		System.out.println(String.format("%-34s%1s","|1. Show all persons","|"));
		System.out.println(String.format("%-34s%1s","|2. Get person by last name","|"));
		System.out.println(String.format("%-34s%1s","|3. Show address of a person","|"));
		System.out.println(String.format("%-34s%1s","|4. Show contacts of a person","|"));
		System.out.println(String.format("%-34s%1s","|5. Add person","|"));
		System.out.println(String.format("%-34s%1s","|6. Add contact of a person","|"));
		System.out.println(String.format("%-34s%1s","|7. Update person","|"));
		System.out.println(String.format("%-34s%1s","|8. Update address of a person","|"));
		System.out.println(String.format("%-34s%1s","|9. Delete person","|"));
		System.out.println(String.format("%-34s%1s","|10. Delete contact of a person","|"));
		System.out.println(String.format("%-34s%1s","|11. Exit","|"));
		System.out.println(String.format("%35s","-----------------------------------"));
		System.out.print("Select... ");
	}

	public void showAllPersons(){
		List<Person> persons = managePersonService.getAllPersons();
		personsGui(persons);
	}

	public void getPersonByLastName(){
		Scanner in = new Scanner(System.in);
		System.out.print("Search name... ");
		String name = in.next();
		List<Person> persons = managePersonService.getPersonByLastName(name);
		personsGui(persons);
	}

	public void getAddressById(){
		Scanner in = new Scanner(System.in);
		System.out.print("Person id --> ");
		while(!in.hasNextInt()){
			System.out.print("Please enter valid id --> ");
			in.next();
		}
		int id = in.nextInt();
		Address address = managePersonService.getAddressById(id);
		addressGui(address);
	}

	public void getContactsByPersonId(){
		Scanner in = new Scanner(System.in);
		System.out.print("Person id --> ");
		while(!in.hasNextInt()){
			System.out.print("Please enter valid id --> ");
			in.next();
		}
		int id = in.nextInt();
		Set<Contact> contacts = managePersonService.getContactsByPersonId(id);
		contactsGui(contacts);
	} 

	public Person createPerson(){
		boolean isEmployed;
		Scanner in = new Scanner(System.in);
		System.out.print("First name --> ");
		String firstName = in.nextLine();
		System.out.print("Middle name --> ");
		String middleName = in.nextLine();
		System.out.print("Last name --> ");
		String lastName = in.nextLine();
		System.out.print("Gender M/F --> ");
		while(!in.hasNext("[mfMF]$")){
			System.out.print("m/f only --> ");
			in.nextLine();
		}
		String gender = in.nextLine();
		System.out.print("Birthdate dd/mm/yyyy --> ");
		while(!in.hasNext("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)$")){
			System.out.print("Please enter valid date format --> ");
			in.nextLine();
		}
		String birthDate = in.nextLine();
		System.out.print("Employed? y/n --> ");
		while(!in.hasNext("[ynYN]$")){
			System.out.print("y/n only --> ");
			in.nextLine();
		}
		String employed = in.nextLine();
		System.out.print("GWA --> ");
		while(!in.hasNext("(\\d+\\.)?\\d+$")){
			System.out.print("Please enter valid gwa --> ");
			in.nextLine();
		}
		String gwa = in.nextLine();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		try{
			date = df.parse(birthDate);
		}catch(ParseException e){
			e.printStackTrace();
		}
		if(employed.equalsIgnoreCase("y")) isEmployed = true;
		else isEmployed = false;
		
		return new Person(lastName, firstName, middleName, convertToGenderEnum(gender), date, null, isEmployed,
			Float.parseFloat(gwa), null, Role.USER);
	}

	public Address createAddress(){
		Scanner in = new Scanner(System.in);
		System.out.println("Address ============================= ");
		System.out.print("Street --> ");
		String street = in.nextLine();
		System.out.print("House number --> ");
		while(!in.hasNext("\\d+$")){
			System.out.print("Numbers only --> ");
			in.nextLine();
		}
		String houseNo = in.nextLine();
		System.out.print("Barangay --> ");
		String brgy = in.nextLine().trim();
		System.out.print("Subdivision --> ");
		String subd = in.nextLine().trim();
		System.out.print("City --> ");
		String city = in.nextLine().trim();
		System.out.print("Zip code --> ");
		String zipCode = in.nextLine().trim();
		return new Address(street, Integer.parseInt(houseNo), brgy, subd, city, zipCode);
	}

	public Contact createContact(){
		Scanner in  = new Scanner(System.in);
		System.out.println("Contact number =============================");
		Contact contact = new Contact();
		System.out.print("Mobile or phone? m/p --> ");
		while(!in.hasNext("[mpMP]$")){
			System.out.print("m/p only --> ");
			in.nextLine();
		}
		contact.setType(convertToTypeEnum(in.nextLine()));
		System.out.print("Number --> ");
		contact.setValue(in.nextLine().trim());
		return contact;
	}

	public Set<Contact> createContacts(){
		Scanner in = new Scanner(System.in);
		boolean isAddContact = true;
		Set<Contact> contacts = new HashSet<>();
		while(isAddContact){
			contacts.add(createContact());
			System.out.print("Do you want to add another contact? y/n --> ");
			while(!in.hasNext("[yYnN]$")){
				System.out.print("y/n only --> ");
				in.nextLine();
			}
			if(in.hasNext("[yY]$")){
				isAddContact = true;
			}
			else if (in.hasNext("[nN]$")){
				isAddContact = false;
			}
			in.nextLine();
		}
		return contacts;
	}

	public void addPerson(){
		Person person = createPerson();
		Address address = createAddress();
		Set<Contact> contacts = createContacts();
		for (Contact c : contacts){
			c.setPerson(person);
		}
		person.setAddress(address);
		person.setContacts(contacts);
		managePersonService.addPerson(person);
		System.out.println("Person added");
	}

	public void addContactByPersonId(){
		Scanner in = new Scanner(System.in);
		System.out.print("Person id --> ");
		while(!in.hasNextInt()){
			System.out.print("Please enter valid id --> ");
			in.next();
		}
		int id = in.nextInt();
		Contact contact = createContact();
		managePersonService.updatePersonContact(id, contact);
		System.out.println("Contact added succesfully");
	}

	public Person createUpdatePerson(Person person){
		boolean isEmployed;
		Scanner in = new Scanner(System.in);
		System.out.print("First name --> ");
		String firstName = in.nextLine();
		System.out.print("Middle name --> ");
		String middleName = in.nextLine();
		System.out.print("Last name --> ");
		String lastName = in.nextLine();
		System.out.print("Gender M/F --> ");
		String gender;
		do{
        	gender = in.nextLine();
       		if(!(gender.matches("[mMfF]$")||gender.isEmpty())){
            	System.out.print("m/f only --> ");
        	}
    	}while(!(gender.matches("[mMfF]$")||gender.isEmpty()));

		System.out.print("Birthdate dd/mm/yyyy --> ");
		String birthdate;
		do{
        	birthdate = in.nextLine();
       		if(!(birthdate.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)$")||birthdate.isEmpty())){
            	System.out.println("Please enter valid date --> ");
        	}
    	}while(!(birthdate.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)$")||birthdate.isEmpty()));
		String employed;
		System.out.print("Employed? y/n --> ");
		do{
        	employed = in.nextLine();
       		if(!(employed.matches("[ynYN]$")||employed.isEmpty())){
            	System.out.println("Please choose y/n only --> ");
        	}
    	}while(!(employed.matches("[ynYN]$")||employed.isEmpty()));
		System.out.print("GWA --> ");
		String gwa;
		do{
        	gwa = in.nextLine();
       		if(!(gwa.matches("(\\d+\\.)?\\d+$")||gwa.isEmpty())){
            	System.out.println("Please choose m/f only --> ");
        	}
    	}while(!(gwa.matches("(\\d+\\.)?\\d+$")||gwa.isEmpty()));
		
		if(!lastName.isEmpty()) person.setLastName(lastName);
		if(!firstName.isEmpty()) person.setFirstName(firstName);
		if(!middleName.isEmpty()) person.setMiddleName(middleName);
		if(!gender.isEmpty()) person.setGender(convertToGenderEnum(gender));
		if(!birthdate.isEmpty()){
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date date = null;
			try{
				date = df.parse(birthdate);
			}catch(ParseException e){
				e.printStackTrace();
			}
			person.setBirthdate(date);	
		}
		if(!employed.isEmpty()){
			if(employed.equalsIgnoreCase("y")) isEmployed = true;
			else isEmployed = false;
			person.setEmployed(isEmployed);
		}
		if(!gwa.isEmpty()) person.setGwa(Float.parseFloat(gwa));
		return person;
	}

	public Address createUpdateAddress(Person person){
		Address address = person.getAddress();
		String houseNo;
		Scanner in = new Scanner(System.in);
		System.out.println("Address ============================= ");
		System.out.print("Street --> ");
		String street = in.nextLine();
		System.out.print("House number --> ");
		do{
        	houseNo = in.nextLine();
       		if(!(houseNo.matches("\\d+$")||houseNo.isEmpty())){
            	System.out.print("Numbers only --> ");
        	}
    	}while(!(houseNo.matches("\\d+$")||houseNo.isEmpty()));
		System.out.print("Barangay --> ");
		String brgy = in.nextLine().trim();
		System.out.print("Subdivision --> ");
		String subd = in.nextLine().trim();
		System.out.print("City --> ");
		String city = in.nextLine().trim();
		System.out.print("Zip code --> ");
		String zipCode = in.nextLine().trim();
		if (!street.isEmpty()) address.setStreet(street);
		if (!houseNo.isEmpty()) address.setHouseNo(Integer.parseInt(houseNo));
		if (!brgy.isEmpty()) address.setBarangay(brgy);
		if (!subd.isEmpty()) address.setSubdivision(subd);
		if (!city.isEmpty()) address.setCity(city);
		if (!zipCode.isEmpty()) address.setZipCode(zipCode);
		return address;	
	}

	public void updatePerson(){
		Scanner in = new Scanner(System.in);
		System.out.print("Person id --> ");
		String name;
		while(!in.hasNextInt()){
			System.out.print("Please enter valid id --> ");
			in.next();
		}
		int id = in.nextInt();
		System.out.println("Press enter to retain old value.");
		Person person = managePersonService.getPersonById(id);
		Person updatedPerson = createUpdatePerson(person);
		System.out.print("Do you want to update Address? y/n");
		while(!in.hasNext("[ynYN]$")){
			System.out.print("y/n only --> ");
			in.nextLine();
		}
		String isUpdateAddress = in.nextLine();
    	if(isUpdateAddress.equalsIgnoreCase("y")){
    		Address updatedAddress = createUpdateAddress(person);
    		updatedPerson.setAddress(updatedAddress);
    	}
		managePersonService.updatePerson(updatedPerson);
		System.out.println("Person updated succesfully");
	}

	public void updateAddress(){
		Scanner in = new Scanner(System.in);
		System.out.print("Address id --> ");
		while(!in.hasNextInt()){
			System.out.print("Please enter a valid id --> ");
			in.next();
		}
		int id = in.nextInt();
		Address address = createAddress();
		address.setId(id);
		managePersonService.updateAddress(address);
		System.out.println("Address updated succesfully");
	}

	public void deletePersonById(){
		Scanner in = new Scanner(System.in);
		System.out.print("Person id --> ");
		while(!in.hasNextInt()){
			System.out.print("Please enter a valid id --> ");
			in.next();
		}
		int id = in.nextInt();
		managePersonService.deletePersonById(id);
		System.out.println("Person deleted succesfully");
	}

	public void deleteContactById() {
		Scanner in = new Scanner(System.in);
		System.out.print("Contact id --> ");
		while(!in.hasNextInt()){
			in.nextInt();
		}
		int id = in.nextInt();
		managePersonService.deleteContactById(id);
		System.out.println("Contact deleted succesfully");
	}

	public void personsGui(List<Person> persons) {
		System.out.println(String.format("%-5s%-30s%-10s%-20s%-13s%-10s%-6s%-7s%1s","+----","+-----------------------------","+---------","+-------------------","+------------","+---------","+-----","+------","+"));
		System.out.println(String.format("%-5s%-30s%-10s%-20s%-13s%-10s%-6s%-7s%1s","|id","|full_name","|gender","|birthdate","|address_id","|employed","|gwa","|role","|"));
		System.out.println(String.format("%-5s%-30s%-10s%-20s%-13s%-10s%-6s%-7s%1s","+----","+-----------------------------","+---------","+-------------------","+------------","+---------","+-----","+------","+"));
		String date = null;
		for (Person p : persons){
			date = new SimpleDateFormat("MMMM-dd-yyyy").format(p.getBirthdate());
			System.out.println(String.format("%-5s%-30s%-10s%-20s%-13s%-10s%-6s%-7s%1s","|"+p.getId(),"|"+p.getFullName(),"|"+p.getGender(),"|"+date,"|"+p.getAddress().getId(),"|"+p.isEmployed(),"|"+p.getGwa(),"|"+p.getRole(),"|"));
		}
		System.out.println(String.format("%-5s%-30s%-10s%-20s%-13s%-10s%-6s%-7s%1s","+----","+-----------------------------","+---------","+-------------------","+------------","+---------","+-----","+------","+"));
	}

	public void addressGui(Address a) {
		System.out.println(String.format("%-5s%-20s%-10s%-20s%-20s%-20s%-10s%1s","+----","+-------------------","+---------","+-------------------","+-------------------","+-------------------","+---------","+"));
		System.out.println(String.format("%-5s%-20s%-10s%-20s%-20s%-20s%-10s%1s","|id","|street","|house_no","|barangay","|subdivision","|city","|zip_code","|"));
		System.out.println(String.format("%-5s%-20s%-10s%-20s%-20s%-20s%-10s%1s","+----","+-------------------","+---------","+-------------------","+-------------------","+-------------------","+---------","+"));
		System.out.println(String.format("%-5s%-20s%-10s%-20s%-20s%-20s%-10s%1s","|"+a.getId(),"|"+a.getStreet(),"|"+a.getHouseNo(),"|"+a.getBarangay(),"|"+a.getSubdivision(),"|"+a.getCity(),"|"+a.getZipCode(),"|"));
		System.out.println(String.format("%-5s%-20s%-10s%-20s%-20s%-20s%-10s%1s","+----","+-------------------","+---------","+-------------------","+-------------------","+-------------------","+---------","+"));
	}

	public void contactsGui(Set<Contact> contacts) {
		System.out.println(String.format("%-5s%-10s%-20s%-5s%1s","+----","+---------","+-------------------","+----","+"));
		System.out.println(String.format("%-5s%-10s%-20s%-5s%1s","|id","|type","|value","|p_id","|"));
		System.out.println(String.format("%-5s%-10s%-20s%-5s%1s","+----","+---------","+-------------------","+----","+"));
		for (Contact c : contacts){
			System.out.println(String.format("%-5s%-10s%-20s%-5s%1s","|"+c.getId(),"|"+c.getType(),"|"+c.getValue(),"|"+c.getPerson().getId(),"|"));	
		}
		System.out.println(String.format("%-5s%-10s%-20s%-5s%1s","+----","+---------","+-------------------","+----","+"));
	}

	public Gender convertToGenderEnum(String val){
		if(val.equalsIgnoreCase("m")){
			return Gender.MALE;
		}
		else{
			return Gender.FEMALE;
		}
	}

	public Type convertToTypeEnum(String val){
		if(val.equalsIgnoreCase("m")){
			return Type.MOBILE;
		}
		else{
			return Type.PHONE;
		}
	}

	public void next(){
		Scanner in = new Scanner(System.in);
        Scanner keyboard = new Scanner(System.in);
		System.out.print("Press enter to continue...");
		keyboard.nextLine();
		menu();
	}

}