<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 4/2/2025
  Time: 5:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Instructor Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        form {
            margin-bottom: 20px;
        }
        .subject-list {
            margin-bottom: 20px;
        }
        .detail-form {
            border: 1px solid #ddd;
            padding: 15px;
            border-radius: 5px;
        }
        .detail-form input[type="text"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            box-sizing: border-box;
        }
        .selected-subject {
            font-weight: bold;
            color: #0066cc;
        }
    </style>
</head>
<body>
    <h1>Instructor Management System</h1>
    
    <div class="subject-list">
        <h2>Select a Subject:</h2>
        <form action="list" method="POST">
            <input type="radio" name="subject" value="" 
                   ${empty selectedSubject ? 'checked' : ''} 
                   onclick="submit()" />All Instructors
            <c:forEach items="${subjects}" var="subject">
                <input type="radio" name="subject" value="${subject.subjectName}" 
                       ${selectedSubject == subject.subjectName ? 'checked' : ''} 
                       onclick="submit()" />${subject.subjectName}
            </c:forEach>
        </form>
        <c:if test="${not empty selectedSubject}">
            <p class="selected-subject">Currently selected: ${selectedSubject}</p>
        </c:if>
        <c:if test="${empty selectedSubject}">
            <p class="selected-subject">Currently selected: All Instructors</p>
        </c:if>
    </div>

    <h2>List of Instructors:</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Date of Birth</th>
                <th>Gender</th>
                <th>Subject</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${instructors}" var="instructor">
                <tr>
                    <td>${instructor.instructorId}</td>
                    <td>${instructor.instructorName}</td>
                    <td>${instructor.dob}</td>
                    <td>${instructor.gender ? 'Male' : 'Female'}</td>
                    <td>${instructor.subjectName}</td>
                    <td><a href="list?id=${instructor.instructorId}&subject=${selectedSubject}">Select</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="detail-form">
        <h2>Instructor Details</h2>
        <form>
            <div>
                <label for="id">ID:</label>
                <input type="text" id="id" name="id" value="${selected.instructorId}" readonly>
            </div>
            <div>
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${selected.instructorName}" readonly>
            </div>
            <div>
                <label for="dob">Date of Birth:</label>
                <input type="text" id="dob" name="dob" value="${selected.dob}" readonly>
            </div>
            <div>
                <label>Gender:</label>
                <input type="radio" name="gender" value="male" ${selected.gender ? 'checked' : ''} disabled>Male
                <input type="radio" name="gender" value="female" ${!selected.gender ? 'checked' : ''} disabled>Female
            </div>
            <div>
                <label for="subject">Subject:</label>
                <input type="text" id="subject" name="subject" value="${selected.subjectName}" readonly>
            </div>
        </form>
    </div>
</body>
</html>
