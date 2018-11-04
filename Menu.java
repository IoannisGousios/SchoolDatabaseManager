
import java.util.Scanner;

public class Menu {
	DataLogic datalogic = new DataLogic();
	
	
	public void startApp(){
		datalogic.open();
		Scanner sc = new Scanner(System.in);
		boolean exit=false;


		printMenu();

		while(!exit) {

			String input = sc.next();

			switch(input) {
			case "1" :
				studApp();
				break;
			case "2" :
				workApp();
				break;
			case "q" :			
				System.out.println("Thank you for using our app. \n App terminated.");
				exit=true;
				break;
			default :
				System.out.println("Invalid command");
			}
		}

	}
	
	
	
	
	
	public void studApp() {		
		Scanner sc = new Scanner(System.in);
		boolean exit=false;


		studentMenu();

		while(!exit) {

			String input = sc.next();

			switch(input) {
			case "1" :
				datalogic.readStudents();
				break;
			case "2" :
				datalogic.insertStudent();
				break;
			case "3" :
				datalogic.updateStudent();
				break;
			case "4" :
				datalogic.deleteStudent();
				break;
			case "menu" :
				printMenu();
				break;
			case "q" :			
				System.out.println("Thank you for using our app. \n App terminated.");
				exit=true;
				break;
			default :
				System.out.println("Invalid command");
			}
		}

	}
	
	
	
	
	public void workApp() {
		Scanner sc = new Scanner(System.in);
		boolean exit=false;


		workerMenu();

		while(!exit) {

			String input = sc.next();

			switch(input) {
			case "1" :
				datalogic.readWorkers();
				break;
			case "2" :
				datalogic.insertWorker();
				break;
			case "3" :
				datalogic.updateWorker();
				break;
			case "4" :
				datalogic.deleteWorker();
				break;
			case "menu" :
				printMenu();
				break;
			case "q" :			
				System.out.println("Thank you for using our app. \n App terminated.");
				exit=true;
				break;
			default :
				System.out.println("Invalid command");
			}
		}

	}
	


	public void printMenu() {
		System.out.println("Welcome to School Database manager app®. How you want to procced? "
				+"\n========================================================"
				+ "\n1. Type '1' to see students management menu. "
				+ "\n2. Type '2' to see workers management menu. "
				+ "\n3. Type 'q' to quit. ");

	}
	
	public void studentMenu() {
		System.out.println(
		  "\n1. Type '1' to see all the students. "
		+ "\n2. Type '2' to insert a new student. "
		+ "\n3. Type '3' to update an existing. "
		+ "\n4. Type '4' to delete a student. "
		+ "\n5. Type 'menu' to see the main menu. "
		+ "\n6. Type 'q' to quit. ");
	}
	
	public void workerMenu() {
		System.out.println(
		  "\n1. Type '1' to see all the workers and their total number. "
		+ "\n2. Type '2' to insert a new worker. "
		+ "\n3. Type '3' to update an existing worker. "
		+ "\n4. Type '4' to delete a worker. "
		+ "\n5. Type 'menu' to see the main menu. "
		+ "\n6. Type 'q' to quit. ");
	}
	
	
}









