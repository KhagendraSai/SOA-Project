**Auth-Service Postman Endpoints:

1.POST:** http://localhost:8083/api/auth/register


	{

&#x20; 		"username": "student101",

&#x20; 		"email": "student101@example.com",

&#x20; 		"password": "Student@123",

&#x20; 		"role": "STUDENT"

&#x09;}


**2.POST:** http://localhost:8083/api/auth/login



&#x09;{

&#x20; 		"username": "student101",

&#x20; 		"password": "Student@123"

&#x09;}



**3.GET:** http://localhost:8083/api/auth/users





###### **Student-Service Endpoints:**

###### 

**1.POST:** http://localhost:8081/api/students



{

&#x20; "rollNumber": "STU101",

&#x20; "name": "Rahul Kumar",

&#x20; "dateOfBirth": "2005-05-15",

&#x20; "gender": "Male",

&#x20; "email": "rahul@example.com",

&#x20; "phone": "9876543210",

&#x20; "className": "10",

&#x20; "section": "A",

&#x20; "address": "Vijayawada",

&#x20; "guardianName": "Ramesh Kumar",

&#x20; "guardianPhone": "9876501234",

&#x20; "admissionDate": "2026-06-01",

&#x20; "status": "ACTIVE"

}



**2.GET:** http://localhost:8081/api/students 



**3.GET:** http://localhost:8081/api/students/{id}



**4.PUT**: http://localhost:8081/api/students/{id}



{

&#x20; "rollNumber": "STU101",

&#x20; "name": "Rahul Kumar Updated",

&#x20; "dateOfBirth": "2005-05-15",

&#x20; "gender": "Male",

&#x20; "email": "rahul.updated@example.com",

&#x20; "phone": "9876543211",

&#x20; "className": "11",

&#x20; "section": "B",

&#x20; "address": "Guntur",

&#x20; "guardianName": "Ramesh Kumar",

&#x20; "guardianPhone": "9876501234",

&#x20; "admissionDate": "2026-06-01",

&#x20; "status": "ACTIVE"

}



**5.DELETE:** http://localhost:8081/api/students/{id}





###### **Attendance-Service Endpoints:**



**1.POST:** http://localhost:8082/api/attendance



{

&#x20; "studentId": 1,

&#x20; "date": "2026-10-30",

&#x20; "status": "PRESENT",

&#x20; "remarks": "Present for all classes"

}



**2.GET:** http://localhost:8082/api/attendance/{attendance\_id}



**3.GET:** http://localhost:8082/api/attendance/student/{student\_id}



**4.GET:** http://localhost:8082/api/attendance/student/{student\_id}/summary



**5.GET:** http://localhost:8082/api/attendance/date/2026-10-30


**6.PUT:** http://localhost:8082/api/attendance/{attendance\_id}


{

&#x20; "studentId": 1,

&#x20; "date": "2026-10-30",

&#x20; "status": "LATE",

&#x20; "remarks": "Arrived 15 minutes late"

}



**7.DELETE:** http://localhost:8082/api/attendance/{attendance\_id}



###### 

###### **Results-Service Endpoints:**



**1.POST:** http://localhost:8084/api/results

{

&#x20; "studentId": 1,

&#x20; "subject": "Mathematics",

&#x20; "examType": "MID",

&#x20; "marks": 85,

&#x20; "maxMarks": 100,

&#x20; "academicYear": "2026-2027",

&#x20; "semester": "1",

&#x20; "remarks": "Good performance"

}



**2.GET:** http://localhost:8084/api/results/{result\_id}



**3.GET:** http://localhost:8084/api/results/student/{student\_id}



**4.GET:** http://localhost:8084/api/results/student/{student\_id}/summary



**5.PUT:** http://localhost:8084/api/results/{result\_id}



{

&#x20; "studentId": 1,

&#x20; "subject": "Mathematics",

&#x20; "examType": "MID",

&#x20; "marks": 90,

&#x20; "maxMarks": 100,

&#x20; "academicYear": "2026-2027",

&#x20; "semester": "1",

&#x20; "remarks": "Excellent performance"

}



**6.DELETE:** http://localhost:8084/api/results/{result\_id}


















