package controller;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import dal.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Instructor;
import model.Subject;

@WebServlet(urlPatterns = {"/list"})
public class ListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            DBContext db = new DBContext();

            // Get all subjects
            ArrayList<Subject> subjects = db.getAllSubjects();
            request.setAttribute("subjects", subjects);
            
            // Get the selected subject from session or parameter
            String subjectName = request.getParameter("subject");
            if (subjectName == null) {
                // Try to get from session
                HttpSession session = request.getSession();
                subjectName = (String) session.getAttribute("selectedSubject");
            } else {
                // Store in session for future use
                HttpSession session = request.getSession();
                session.setAttribute("selectedSubject", subjectName);
            }
            
            // Get instructors based on selected subject
            ArrayList<Instructor> instructors;
            if (subjectName != null && !subjectName.isEmpty()) {
                instructors = (ArrayList<Instructor>) db.getInstructorsBySubject(subjectName);
                request.setAttribute("selectedSubject", subjectName);
            } else {
                instructors = db.getAllInstructors();
                request.setAttribute("selectedSubject", "");
            }
            request.setAttribute("instructors", instructors);

            // Handle instructor selection by ID
            String instructorId = request.getParameter("id");
            if (instructorId != null) {
                Instructor selectedInstructor = db.getInstructorById(instructorId);
                request.setAttribute("selected", selectedInstructor);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ListServlet.class.getName()).log(Level.SEVERE, "Database error", ex);
        }
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            DBContext db = new DBContext();

            // Get all subjects
            ArrayList<Subject> subjects = db.getAllSubjects();
            request.setAttribute("subjects", subjects);
            
            // Get instructors by selected subject
            String subjectName = request.getParameter("subject");
            
            // Store in session for future use
            HttpSession session = request.getSession();
            session.setAttribute("selectedSubject", subjectName);
            
            ArrayList<Instructor> instructors;
            if (subjectName != null && !subjectName.isEmpty()) {
                instructors = (ArrayList<Instructor>) db.getInstructorsBySubject(subjectName);
                request.setAttribute("selectedSubject", subjectName);
            } else {
                instructors = db.getAllInstructors();
                request.setAttribute("selectedSubject", "");
            }
            request.setAttribute("instructors", instructors);

        } catch (SQLException ex) {
            Logger.getLogger(ListServlet.class.getName()).log(Level.SEVERE, "Database error", ex);
        }
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for managing instructor and subject listings";
    }
}
