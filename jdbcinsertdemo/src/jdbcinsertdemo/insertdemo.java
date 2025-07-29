package jdbcinsertdemo;
import java.sql.*;
import java.util.Scanner;

public class insertdemo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/placement_db", "root", "adarsh12345"
            );

            while (true) {
            	 System.out.print("\033[H\033[2J");
            	    System.out.flush();
                System.out.println("\n--- Placement Menu ---");
                System.out.println("1. Add Student");
                System.out.println("2. Add Company");
                System.out.println("3. Check Eligible Students");
                System.out.println("4. Record Placement");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                if (choice == 1) {
                    System.out.print("Enter Roll No: ");
                    int roll = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter CGPA: ");
                    float cgpa = sc.nextFloat();

                    PreparedStatement ps = conn.prepareStatement("INSERT INTO student VALUES (?, ?, ?)");
                    ps.setInt(1, roll);
                    ps.setString(2, name);
                    ps.setFloat(3, cgpa);
                    ps.executeUpdate();

                    System.out.println("Student added successfully.");
                }

                else if (choice == 2) {
                    System.out.print("Enter Company ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Company Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Minimum CGPA: ");
                    float minCgpa = sc.nextFloat();

                    PreparedStatement ps = conn.prepareStatement("INSERT INTO company VALUES (?, ?, ?)");
                    ps.setInt(1, id);
                    ps.setString(2, name);
                    ps.setFloat(3, minCgpa);
                    ps.executeUpdate();

                    System.out.println("Company added successfully.");
                }

                else if (choice == 3) {
                    System.out.print("Enter Company ID to check eligibility: ");
                    int compId = sc.nextInt();

                    PreparedStatement ps1 = conn.prepareStatement("SELECT min_cgpa FROM company WHERE company_id = ?");
                    ps1.setInt(1, compId);
                    ResultSet rs1 = ps1.executeQuery();

                    if (rs1.next()) {
                        float minCgpa = rs1.getFloat("min_cgpa");

                        PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM student WHERE cgpa >= ?");
                        ps2.setFloat(1, minCgpa);
                        ResultSet rs2 = ps2.executeQuery();

                        System.out.println("\nEligible Students:");
                        while (rs2.next()) {
                            System.out.println("Roll No: " + rs2.getInt("roll_no") +
                                               ", Name: " + rs2.getString("name") +
                                               ", CGPA: " + rs2.getFloat("cgpa"));
                        }
                    } else {
                        System.out.println("Company not found.");
                    }
                }

                else if (choice == 4) {
                    System.out.print("Enter Student Roll No: ");
                    int roll = sc.nextInt();
                    System.out.print("Enter Company ID: ");
                    int compId = sc.nextInt();
                    System.out.print("Enter Salary: ");
                    float salary = sc.nextFloat();

                    PreparedStatement ps1 = conn.prepareStatement("SELECT cgpa FROM student WHERE roll_no = ?");
                    ps1.setInt(1, roll);
                    ResultSet rs1 = ps1.executeQuery();

                    PreparedStatement ps2 = conn.prepareStatement("SELECT min_cgpa FROM company WHERE company_id = ?");
                    ps2.setInt(1, compId);
                    ResultSet rs2 = ps2.executeQuery();

                    if (rs1.next() && rs2.next()) {
                        float studentCgpa = rs1.getFloat("cgpa");
                        float requiredCgpa = rs2.getFloat("min_cgpa");

                        if (studentCgpa >= requiredCgpa) {
                            PreparedStatement ps3 = conn.prepareStatement(
                                "INSERT INTO placement (roll_no, company_id, salary) VALUES (?, ?, ?)"
                            );
                            ps3.setInt(1, roll);
                            ps3.setInt(2, compId);
                            ps3.setFloat(3, salary);
                            ps3.executeUpdate();

                            System.out.println("Placement recorded.");
                        } else {
                            System.out.println("Student is not eligible for this company.");
                        }
                    } else {
                        System.out.println("Invalid roll number or company ID.");
                    }
                }

                else if (choice == 5) {
                    System.out.println("Exiting...");
                    break;
                }

                else {
                    System.out.println("Invalid choice. Try again.");
                }
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sc.close();
    }
}
