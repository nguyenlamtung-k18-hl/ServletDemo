package dal;

import model.Instructor;
import model.Subject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database context class for handling database operations
 */
public class DBContext {

    protected Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public DBContext() {
        //@Students: You are allowed to edit user, pass, url variables to fit 
        //your system configuration
        //You can also add more methods for Database Interaction tasks. 
        //But we recommend you to do it in another class
        // For example : StudentDBContext extends DBContext , 
        //where StudentDBContext is located in dal package, 
        try {
            String user = "root";
            String password = "1234";
            String url = "jdbc:mysql://localhost:3306/WS_PRJ301?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                System.out.println("Database connection successful!");
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "Database connection error", ex);
        }
    }

    /**
     * Retrieves all subjects from the database
     *
     * @return List of subjects
     * @throws SQLException if a database access error occurs
     */
    public ArrayList<Subject> getAllSubjects() throws SQLException {
        ArrayList<Subject> subjects = new ArrayList<>();
        try {
            String query = "SELECT * FROM Subjects";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String subjectId = resultSet.getString(1);
                String subjectName = resultSet.getString(2);
                Subject subject = new Subject(subjectId, subjectName);
                subjects.add(subject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "Error retrieving subjects", ex);
            throw ex;
        }

        return subjects;
    }

    /**
     * Retrieves all instructors from the database
     *
     * @return List of instructors
     * @throws SQLException if a database access error occurs
     */
    public ArrayList<Instructor> getAllInstructors() throws SQLException {
        ArrayList<Instructor> instructors = new ArrayList<>();
        try {
            String query = "SELECT i.InstructorID, i.InstructorName, i.BirthDate, i.Gender, s.SubjectName " +
                    "FROM Instructors i " +
                    "JOIN Subjects s ON s.SubjectID = i.SubjectID";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String instructorId = resultSet.getString(1);
                String instructorName = resultSet.getString(2);
                Date dob = resultSet.getDate(3);
                boolean gender = resultSet.getBoolean(4);
                String subjectName = resultSet.getString(5);
                Instructor instructor = new Instructor(instructorId, instructorName, dob, gender, subjectName);
                instructors.add(instructor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "Error retrieving instructors", ex);
            throw ex;
        }

        return instructors;
    }

    /**
     * Retrieves instructors by subject name
     *
     * @param subjectName The name of the subject
     * @return List of instructors for the given subject
     * @throws SQLException if a database access error occurs
     */
    public List<Instructor> getInstructorsBySubject(String subjectName) throws SQLException {
        ArrayList<Instructor> instructors = new ArrayList<>();
        try {
            String query = "SELECT i.InstructorID, i.InstructorName, i.BirthDate, i.Gender, s.SubjectName " +
                    "FROM Instructors i " +
                    "JOIN Subjects s ON s.SubjectID = i.SubjectID " +
                    "WHERE s.SubjectName = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, subjectName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String instructorId = resultSet.getString(1);
                String instructorName = resultSet.getString(2);
                Date dob = resultSet.getDate(3);
                boolean gender = resultSet.getBoolean(4);
                String subject = resultSet.getString(5);
                Instructor instructor = new Instructor(instructorId, instructorName, dob, gender, subject);
                instructors.add(instructor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "Error retrieving instructors by subject", ex);
            throw ex;
        }

        return instructors;
    }

    /**
     * Retrieves an instructor by ID
     *
     * @param instructorId The ID of the instructor
     * @return The instructor with the given ID, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public Instructor getInstructorById(String instructorId) throws SQLException {
        try {
            String query = "SELECT i.InstructorID, i.InstructorName, i.BirthDate, i.Gender, s.SubjectName " +
                    "FROM Instructors i " +
                    "JOIN Subjects s ON s.SubjectID = i.SubjectID " +
                    "WHERE i.InstructorID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, instructorId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                Date dob = resultSet.getDate(3);
                boolean gender = resultSet.getBoolean(4);
                String subjectName = resultSet.getString(5);
                return new Instructor(id, name, dob, gender, subjectName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "Error retrieving instructor by ID", ex);
            throw ex;
        }
        return null;
    }

    public boolean testConnection() {
        if (connection != null) {
            try {
                return !connection.isClosed();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        //test connection
        DBContext db = new DBContext();
        if (db.testConnection()) {
            System.out.println("Kết nối thành công!");
        } else {
            System.out.println("Kết nối thất bại!");
        }
    }
}