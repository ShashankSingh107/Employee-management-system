
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);

        while(true){
            System.out.println("\n====== Employee Management and Payroll System ======");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employee");
            System.out.println("3. Remove Employees");
            System.out.println("4. Generate Payroll");
            System.out.println("5. Exit!");
            System.out.println("Choose the number:- ");
            int choice=sc.nextInt();
            sc.nextLine();

            switch(choice){
                case 1-> Employee.addEmployee(sc);
                case 2-> Employee.viewEmployees();
                case 3-> Employee.removeEmployee(sc);
                case 4->Employee.generatePayroll(sc);
//                    System.out.print("Enter Employee ID to generate payroll: ");
//                    int payrollId = sc.nextInt();
                
                case 5-> {
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                }
                default-> System.out.println("Invalid choice!");
            }

        }
    }
}
