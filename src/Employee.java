import java.sql.*;
import java.util.Scanner;

abstract class Person{
    String name;
    String position;

    public Person(String name, String position){
        this.name=name;
        this.position=position;
    }
    public abstract double calculateSalary();
}

class Employee extends Person{
    double salary;

    public Employee(String name, String position, double salary){
        super(name,position);
        this.salary=salary;
    }

    @Override
    public double calculateSalary(){

        return salary;
    }

    public String getName() { return name; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }

    public static void addEmployee(Scanner sc){
        System.out.print("Enter Name:- ");
        String name=sc.nextLine();
        System.out.print("Enter Position:- ");
        String position=sc.nextLine();
        System.out.print("Enter Salary:- ");
        double salary=sc.nextDouble();
        sc.nextLine();

        try(Connection con=DBConnection.getConnection()){
            String query="INSERT INTO employees(name,position,salary) VALUES(?,?,?)";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,name);
            ps.setString(2,position);
            ps.setDouble(3,salary);
            ps.executeUpdate();
            System.out.println("Employee added Sucessfully!");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void viewEmployees() {
        try (Connection con = DBConnection.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employees");

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("position") + " | " +
                        rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeEmployee(Scanner sc) {
        System.out.print("Enter Employee ID to Remove: ");
        int id = sc.nextInt();
        sc.nextLine();

        try (Connection con = DBConnection.getConnection()) {
            String query = "DELETE FROM employees WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Employee Removed!");
            else System.out.println("Employee Not Found!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generatePayroll(Scanner sc) {
        System.out.print("Enter Employee DB id to generate payroll: ");
        int id = sc.nextInt();

        try (Connection con = DBConnection.getConnection()) {
            if (con == null) return;
            String sql = "SELECT name, position, salary FROM employees WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Employee not found.");
                        return;
                    }
                    String name = rs.getString("name");
                    String position = rs.getString("position");
                    double salary = rs.getDouble("salary");

                    // Use your OOP class to compute payroll
                    Employee e = new Employee(name, position, salary);
                    double payroll = e.calculateSalary();

                    // Print a neat slip
                    System.out.println("\n====== PAYROLL SLIP ======");
                    System.out.println("DB ID     : " + id);
                    System.out.println("Name      : " + e.getName());
                    System.out.println("Position  : " + e.getPosition());
                    System.out.printf ("Base Pay  : %.2f%n", e.getSalary());
                    System.out.printf ("Net Pay   : %.2f%n", payroll);
                    System.out.println("==========================\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
