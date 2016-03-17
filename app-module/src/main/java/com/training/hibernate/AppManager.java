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
		System.out.println(String.format("%-34s%1s","|7. Update person last name","|"));
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
		System.out.print("Id of person... ");
		while(!in.hasNextInt()){
			System.out.print("Invalid... ");
			in.next();
		}
		int id = in.nextInt();
		Address address = managePersonService.getAddressById(id);
		addressGui(address);
	}

	public void getContactsByPersonId(){
		Scanner in = new Scanner(System.in);
		System.out.print("Person id... ");
		while(!in.hasNextInt()){
			System.out.print("Invalid... ");
			in.next();
		}
		int id = in.nextInt();
		Set<Contact> contacts = managePersonService.getContactsByPersonId(id);
		contactsGui(contacts);
	} 

	public void addPerson(){
		boolean isEmployed;
		Scanner in = new Scanner(System.in);
		System.out.print("Last name... ");
		String lastName = in.nextLine();
		System.out.print("First name... ");
		String firstName = in.nextLine();
		System.out.print("Middle name... ");
		String middleName = in.nextLine();
		System.out.print("Gender M/F... ");
		while(!in.hasNext("[mfMF]$")){
			System.out.print("Invalid... ");
			in.nextLine();
		}
		String gender = in.nextLine();
		System.out.print("Birthdate dd/mm/yyyy... ");
		while(!in.hasNext("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)$")){
			System.out.print("Invalid... ");
			in.nextLine();
		}
		String birthDate = in.nextLine();
		System.out.print("Employed? y/n... ");
		while(!in.hasNext("[ynYN]$")){
			System.out.print("Invalid... ");
			in.nextLine();
		}
		String employed = in.nextLine();
		System.out.print("GWA... ");
		while(!in.hasNext("(\\d+\\.)?\\d+$")){
			System.out.print("Invalid... ");
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
		
		// construct person
		Address address = addAddress();
		Set<Contact> contacts = addContacts();
		Person person = new Person(lastName, firstName, middleName, convertToGenderEnum(gender), date, address, isEmployed,
			Float.parseFloat(gwa), contacts, Role.USER);
		for (Contact c : contacts){
			c.setPerson(person);
		}
		person.setContacts(contacts);
		savePerson(person);
	}

	public Address addAddress(){
		Scanner in = new Scanner(System.in);
		System.out.println("Address ============================= ");
		System.out.print("Street... ");
		String street = in.nextLine();
		System.out.print("House number... ");
		while(!in.hasNext("\\d+$")){
			System.out.print("Invalid... ");
			in.nextLine();
		}
		String houseNo = in.nextLine();
		System.out.print("Barangay... ");
		String brgy = in.nextLine().trim();
		System.out.print("Subdivision... ");
		String subd = in.nextLine().trim();
		System.out.print("City... ");
		String city = in.nextLine().trim();
		System.out.print("Zip code... ");
		String zipCode = in.nextLine().trim();
		return new Address(street, Integer.parseInt(houseNo), brgy, subd, city, zipCode);
	}

	public Contact addContact(){
		Scanner in  = new Scanner(System.in);
		System.out.println("Contact number =============================");
		Contact contact = new Contact();
		System.out.print("Mobile or phone? m/p... ");
		while(!in.hasNext("[mpMP]$")){
			System.out.print("Invalid... ");
			in.nextLine();
		}
		contact.setType(convertToTypeEnum(in.nextLine()));
		System.out.print("Number... ");
		contact.setValue(in.nextLine().trim());
		return contact;
	}

	public Set<Contact> addContacts(){
		Scanner in = new Scanner(System.in);
		boolean isAddContact = true;
		Set<Contact> contacts = new HashSet<>();
		while(isAddContact){
			contacts.add(addContact());
			System.out.print("Do you want to add another contact? y/n... ");
			while(!in.hasNext("[yYnN]$")){
				System.out.print("Invalid... ");
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

	public void addContactByPersonId(){
		Scanner in = new Scanner(System.in);
		System.out.print("Person id... ");
		while(!in.hasNextInt()){
			System.out.print("Invalid... ");
			in.next();
		}
		int id = in.nextInt();
		Contact contact = addContact();
		managePersonService.updatePersonContact(id, contact);
	}

	public void updatePersonLastName(){
		Scanner in = new Scanner(System.in);
		System.out.print("Person id... ");
		String name;
		while(!in.hasNextInt()){
			System.out.print("Invalid... ");
			in.next();
		}
		int id = in.nextInt();
		System.out.print("New last name... ");
		name = in.next();
		managePersonService.updatePersonLastName(id,name);
	}

	public void updateAddress(){
		Scanner in = new Scanner(System.in);
		System.out.print("Address id... ");
		while(!in.hasNextInt()){
			System.out.print("Invalid... ");
			in.next();
		}
		int id = in.nextInt();
		Address address = addAddress();
		address.setId(id);
		managePersonService.updateAddress(address);
	}

	public void deletePersonById(){
		Scanner in = new Scanner(System.in);
		System.out.print("Person id... ");
		while(!in.hasNextInt()){
			System.out.print("Invalid... ");
			in.next();
		}
		int id = in.nextInt();
		managePersonService.deletePersonById(id);
	}

	public void deleteContactById() {
		Scanner in = new Scanner(System.in);
		System.out.print("Contact id... ");
		while(!in.hasNextInt()){
			in.nextInt();
		}
		int id = in.nextInt();
		managePersonService.deleteContactById(id);
	}

	public void personsGui(List<Person> persons) {
		System.out.println(String.format("%-5s%-30s%-10s%-12s%-13s%-10s%-6s%-7s%1s","+----","+-----------------------------","+---------","+-----------","+------------","+---------","+-----","+------","+"));
		System.out.println(String.format("%-5s%-30s%-10s%-12s%-13s%-10s%-6s%-7s%1s","|id","|full_name","|gender","|birthdate","|address_id","|employed","|gwa","|role","|"));
		System.out.println(String.format("%-5s%-30s%-10s%-12s%-13s%-10s%-6s%-7s%1s","+----","+-----------------------------","+---------","+-----------","+------------","+---------","+-----","+------","+"));
		for (Person p : persons){
			System.out.println(String.format("%-5s%-30s%-10s%-12s%-13s%-10s%-6s%-7s%1s","|"+p.getId(),"|"+p.getFullName(),"|"+p.getGender(),"|"+p.getBirthdate(),"|"+p.getAddress().getId(),"|"+p.isEmployed(),"|"+p.getGwa(),"|"+p.getRole(),"|"));
		}
		System.out.println(String.format("%-5s%-30s%-10s%-12s%-13s%-10s%-6s%-7s%1s","+----","+-----------------------------","+---------","+-----------","+------------","+---------","+-----","+------","+"));
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

	public void savePerson(Person person){
		managePersonService.addPerson(person);
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