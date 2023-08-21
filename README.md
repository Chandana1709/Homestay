# Homestay

>The homestay booking app is a mobile application that allows tourists to search and book accommodations in private homes, apartments, or other non-traditional lodging options. The app aims to provide a user-friendly platform that allows tourists to find unique and affordable accommodations that suit their preferences and budget.The software system will be a standalone application. The app should be easy to navigate and use for both guests and owners. It should have a simple and intuitive design that allows users to quickly find what they need. The app should allow hosts to manage their listings, including availability, pricing and booking requests. Guests should be able to search for and book
homestays, and receive confirmation and payment details


## Functionalities

- User registration: The app should allow users to register as either tourists or owners. This registration process should include the collection of relevant information such as
  name, email, and password

- Search functionality: The app should allow guests to search for homestays based on their location, availability, pricing, and other relevant criteria. Hosts should be able to
  search for potential guests who are interested in staying at their homestay.
  
- Listing management: Hosts should be able to manage their homestay listings, including availability, pricing, and description of the property. They should also be
  able to upload photos and other relevant information about their homestay
  
- Booking management: The app should allow guests to book homestays and owners to manage these bookings. Owner should be able to accept or decline booking requests
  and update their availability calendar accordingly. Guests should be able to view their booking status, make payments, and receive confirmation details
  
- Secure payment options: The app should offer secure payment options to ensure that guests' payments are protected and hosts receive their payments on time
  
## Tools and Technologies Used

Back4app Cloud Storage,Java and XML 

## Screenshots

<img src="https://github.com/Chandana1709/Student_Result_Publishing_System/assets/95367438/808eeebe-5dfb-4523-a136-78581b94f8dd" alt="Image 1" width="700" height="300">
<br><br>
<img src="https://github.com/Chandana1709/Student_Result_Publishing_System/assets/95367438/fcdf7a46-59d8-45e1-9867-73ca3f9f3862" alt="Image 2" width="700" height="300">
<br><br>
<img src="https://github.com/Chandana1709/Student_Result_Publishing_System/assets/95367438/41f94099-9aad-46f3-baef-856942ee7d86" alt="Image" width="700" height="300">





## Installation

>STEP 1
- sudo apt install mysql-server
- sudo apt install nodejs
- CREATE DATABASE ResultSystem
  
>STEP 2
- import backup.sql file to local MYSQL.Navigate to the directory where your SQL backup file (backup.sql) is located. You can use the cd command to change directories if needed.
  your_username: Replace this with your MySQL username.your_database_name: Replace this with the name of the database where you want to import the data.
- mysql -u your_username -p your_database_name < backup.sql

>STEP 3
- Replace password of your MYSQL in server.js

> STEP 4
- Go to frontend type : npm install
- Go to backend type : npm install
   
## Run Locally

- Open Student_Result_Publishing_System
- go to Student_Result_Publishing_System>frontend type npm start
- open other terminal and go to Student_Result_Publishing_System > backend     type node server.js
