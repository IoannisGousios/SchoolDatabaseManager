import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class DataLogic {

	public static final String DB_NAME = "school.db";
	public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\John\\Desktop\\CB6 files\\weekly 4\\" +DB_NAME;

	public static final String TABLE_STUDENTS = "students";
	public static final String COLUMN_STUDENT_ID = "id";
	public static final String COLUMN_STUDENT_NAME = "name";
	public static final String COLUMN_STUDENT_SURNAME = "surname";
	public static final String COLUMN_STUDENT_FACNUMBER = "facNumber";


	public static final String TABLE_WORKERS = "workers";
	public static final String COLUMN_WORKER_ID = "id";
	public static final String COLUMN_WORKER_NAME = "name";
	public static final String COLUMN_WORKER_SURNAME = "surname";
	public static final String COLUMN_WORKER_WEEKSAL = "weekSal";
	public static final String COLUMN_WORKER_HOURSPERDAY = "hoursPerDay";
	public static final String COLUMN_WORKER_HOURSAL = "hourSal";


	private Connection conn;


	public boolean open() {
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			return true;
		}catch(SQLException e){
			System.out.println("Couldn't connect to database " + e.getMessage());
			return false;
		}
	}


	public void close() {
		try {
			if(conn != null) {
				conn.close();
			}
		}catch(SQLException e) {
			System.out.println("Couldn't close connection " + e.getMessage());
		}
	}



	public List<Student> readStudents(){

		try(Statement statement = conn.createStatement();
				ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_STUDENTS)) {

			List<Student> students = new ArrayList<>();
			while(results.next()) {
				Student student = new Student();
				student.setId(results.getInt(COLUMN_STUDENT_ID));
				student.setName(results.getString(COLUMN_STUDENT_NAME));
				student.setSurname(results.getString(COLUMN_STUDENT_SURNAME));
				student.setFacNumber(results.getString(COLUMN_STUDENT_FACNUMBER));
				students.add(student);
			}

			for(Student student: students) {
				System.out.println("ID: " + student.getId() + "\n Name: " +student.getName() + "\n Surname: " + student.getSurname() + 
						"\n Faculty number: " + student.getFacNumber()
						+ "\n===============================");
			}

			return students;

		}catch(SQLException e) {
			System.out.println("Query failed " + e.getMessage());
			return null;
		}			
	}


	public List<Worker> readWorkers(){
		int count=0;
		try(Statement statement = conn.createStatement();
				ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_WORKERS)) {

			List<Worker> workers = new ArrayList<>();
			while(results.next()) {
				Worker worker = new Worker();
				worker.setId(results.getInt(COLUMN_WORKER_ID));
				worker.setName(results.getString(COLUMN_WORKER_NAME));
				worker.setSurname(results.getString(COLUMN_WORKER_SURNAME));
				worker.setWeekSal(results.getInt(COLUMN_WORKER_WEEKSAL));
				worker.setHoursPerDay(results.getInt(COLUMN_WORKER_HOURSPERDAY));
				worker.setHourSal(results.getInt(COLUMN_WORKER_HOURSAL));
				workers.add(worker);
			}

			for (int i = 0; i < workers.size(); i++) {
				count++;

				System.out.println("ID: " + workers.get(i).getId() + "\n Name: " +workers.get(i).getName() + "\n Surname: " + workers.get(i).getSurname() + 
						"\n Weekly salary: " + workers.get(i).getWeekSal() + "\n Hours per day: " + workers.get(i).getHoursPerDay()
						+ "\n Hourly salary: " + workers.get(i).getHourSal()
						+ "\n===============================");
			}
			System.out.println("The number of total workers is: "+count);

			return workers;

		}catch(SQLException e) {
			System.out.println("Query failed " + e.getMessage());
			return null;
		}	
	}

	public void insertStudent() {
		Scanner sc = new Scanner(System.in);
		String name;
		String surname;
		String facNumber;

		try(Statement statement = conn.createStatement()){

			System.out.println("Insert a student's name containing more than 2 characters");
			name=sc.next();
			while(!name.matches("^.{2,}$")) {
				System.out.println("Name must be longer than 2 characters");
				System.out.println("Enter valid name");
				name=sc.next();
			}

			System.out.println("Insert student's surname");
			surname=sc.next();
			while(!surname.matches("^.{3,}$")) {
				System.out.println("Surname must be longer than 2 characters");
				System.out.println("Enter valid name");
				surname=sc.next();
			}
			System.out.println("Insert student's faculty id. Id consists of 5 to 10 alphanumeric characters");
			facNumber=sc.next();
			while(!facNumber.matches("^([a-zA-Z0-9]{5,10})$")) {
				System.out.println("Faculty id must consist of 5 to 10 letters or numbers");
				System.out.println("Enter valid id");
				name=sc.next();
			}


			String sql = "INSERT INTO students (name, surname, facNumber) VALUES ( ?, ?, ?)";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, name.substring(0, 1).toUpperCase()+name.substring(1));
			stmt.setString(2, surname.substring(0, 1).toUpperCase()+surname.substring(1));
			stmt.setString(3, facNumber);

			stmt.executeUpdate();

			System.out.println("Student " + surname + " , " + name + " saved succesfully");

		}catch(SQLException e) {
			System.out.println("Insert failed " +e.getMessage());
		}
	}


	public void insertWorker() {
		Scanner sc = new Scanner(System.in);
		String name;
		String surname;
		double weekSal;
		double hoursPerDay;
		double hourSal;

		try(Statement statement = conn.createStatement()){

			System.out.println("Insert worker's name");
			name=sc.next();
			while(!name.matches("^.{2,}$")) {
				System.out.println("Name must be longer than 2 characters");
				System.out.println("Enter valid name");
				name=sc.next();
			}
			System.out.println("Insert worker's surname");
			surname=sc.next();
			while(!surname.matches("^.{3,}$")) {
				System.out.println("Surname must be longer than 2 characters");
				System.out.println("Enter valid name");
				surname=sc.next();
			}
			System.out.println("Insert worker's weekly salary. Must be a number larger than 10");
			weekSal=sc.nextDouble();
			while(weekSal < 10) {
				System.out.println("Enter a salary higher than 10");
				System.out.println("Enter valid salary");
				weekSal=sc.nextDouble();
			}
			System.out.println("Insert worker's working hours per day. Provide a number in range 1-12");
			hoursPerDay=sc.nextDouble();
			while(hoursPerDay < 1 || hoursPerDay >12) {
				System.out.println("Working hours are between 1 and 12");
				System.out.println("Enter valid working hours");
				hoursPerDay=sc.nextDouble();
			}
			hourSal = weekSal/(hoursPerDay*5);

			String sql = "INSERT INTO workers (name, surname, weekSal, hoursPerDay, hourSal) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, name.substring(0, 1).toUpperCase()+name.substring(1));
			stmt.setString(2, surname.substring(0, 1).toUpperCase()+surname.substring(1));
			stmt.setDouble(3, weekSal);
			stmt.setDouble(4, hoursPerDay);
			stmt.setDouble(5, hourSal);

			stmt.executeUpdate();

			System.out.println("Worker " + surname + " , " + name + " saved succesfully");

		}catch(SQLException e) {
			System.out.println("Insert failed " +e.getMessage());
		}
	}



	public void updateStudent() {
		Scanner sc = new Scanner(System.in);
		String useInp;;
		String name;
		String surname;
		int facNumber;

		try(Statement statement = conn.createStatement()){

			System.out.println("Enter SURNAME of the student you want to update");
			useInp=sc.next();
			while(!searchStudent(useInp)) {
				System.out.println("Student doesnt exist");
				System.out.println("Enter correct student:");
				useInp=sc.next();
			}
			System.out.println("Insert NEW name for student");
			name=sc.next();
			System.out.println("Insert NEW surname for student");
			surname=sc.next();
			System.out.println("Insert NEW faculty number for student");
			facNumber=sc.nextInt();


			String sql = "UPDATE students SET name=?, surname=?, facNumber=? WHERE surname=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, surname);
			stmt.setInt(3, facNumber);
			stmt.setString(4, useInp);


			stmt.executeUpdate();

			System.out.println("Student " + surname + " , " + name + " updated succesfully");

		}catch(SQLException e) {
			System.out.println("Update failed " +e.getMessage());
		}
	}


	public void updateWorker() {
		Scanner sc = new Scanner(System.in);
		String useInp;
		String name;
		String surname;
		double weekSal;
		double hoursPerDay;
		double hourSal;

		try(Statement statement = conn.createStatement()){

			System.out.println("Enter SURNAME of the worker you want to update");
			useInp=sc.next();
			while(!searchWorker(useInp)) {
				System.out.println("Worker doesnt exist");
				System.out.println("Enter correct worker:");
				useInp=sc.next();
			}
			System.out.println("Insert NEW name for worker");
			name=sc.next();
			System.out.println("Insert NEW surname for worker");
			surname=sc.next();
			System.out.println("Insert NEW weekly salary for worker");
			weekSal=sc.nextDouble();
			System.out.println("Insert NEW working hours per day for worker");
			hoursPerDay=sc.nextDouble();
			hourSal = weekSal/(hoursPerDay*5);

			String sql = "UPDATE workers SET name=?, surname=?, weekSal=?, hoursPerDay=?, hourSal=? WHERE surname=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, surname);
			stmt.setDouble(3, weekSal);
			stmt.setDouble(4, hoursPerDay);
			stmt.setDouble(5, hourSal);
			stmt.setString(6, useInp);


			stmt.executeUpdate();

			System.out.println("Worker " + surname + " , " + name + " updated succesfully");

		}catch(SQLException e) {
			System.out.println("Update failed " +e.getMessage());
		}
	}

	public void deleteStudent() {
		try {
			String surname;
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter SURNAME of the student you want to delete ");
			surname=sc.next();
			while(!searchStudent(surname)) {
				System.out.println("Student doesnt exist");
				System.out.println("Enter correct student:");
				surname=sc.next();
			}
			String sql = "DELETE FROM students WHERE surname=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, surname.substring(0, 1).toUpperCase()+surname.substring(1));
			stmt.executeUpdate();

			System.out.println("Student " + surname +  " deleted succesfully");

		}catch(SQLException e) {
			System.out.println("Deletion failed " +e.getMessage());
		}
	}



	public void deleteWorker() {
		try {
			String surname;
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter SURNAME of the worker you want to delete");
			surname=sc.next();
			while(!searchWorker(surname)) {
				System.out.println("Worker doesnt exist");
				System.out.println("Enter correct worker:");
				surname=sc.next();
			}
			String sql = "DELETE FROM workers WHERE surname=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, surname.substring(0, 1).toUpperCase()+surname.substring(1));
			stmt.executeUpdate();

			System.out.println("Worker " + surname +  " deleted succesfully");

		}catch(SQLException e) {
			System.out.println("Deletion failed " +e.getMessage());
		}
	}


	public boolean searchStudent(String surname) {
				
		try {
		
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students WHERE surname=?");
		stmt.setString(1, surname.substring(0, 1).toUpperCase()+surname.substring(1)); 
		ResultSet resultSet = stmt.executeQuery();

		if (resultSet.next()) {
		   return true;
		} else {
		    return false;
		}
		}catch(SQLException e) {
			System.out.println("search failed " +e.getMessage());
		}
		return false;
	}
	
	public boolean searchWorker(String surname) {
		
		try {
		
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM workers WHERE surname=?");
		stmt.setString(1, surname.substring(0, 1).toUpperCase()+surname.substring(1)); 
		ResultSet resultSet = stmt.executeQuery();

		if (resultSet.next()) {
		   return true;
		} else {
		    return false;
		}
		}catch(SQLException e) {
			System.out.println("search failed " +e.getMessage());
		}
		return false;
	}

}
