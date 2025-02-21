# Simple Blog

## Overview
Simple Blog adalah aplikasi berbasis **Spring Boot** yang memungkinkan pengguna untuk membuat, mengedit, dan mengelola postingan blog. Aplikasi ini menerapkan **Role-Based Access Control (RBAC)** di mana hanya admin yang dapat mempublikasikan postingan.

## 🚀 Features
- User dapat membuat draft postingan
- Admin dapat mempublikasikan postingan
- CRUD untuk Post dan Tag
- Autentikasi dan Otorisasi dengan Spring Security
- Database menggunakan JPA & Hibernate
- REST API dengan JSON Response

## Tech Stack
- **Backend:  Java, Spring Boot, Spring Security, JPA, Hibernate
- **Database: PostgreSQL
- **Build Tool:  Maven
- **Authentication: JWT (JSON Web Token)

## 📂 Project Structure
```
Simple-Blog/
│── src/
│   ├── main/
│   │   ├── java/com/example/simpleblog/
│   │   │   ├── config/      # Security & App Configurations
│   │   │   ├── controller/  # API Controllers
│   │   │   ├── model/       # Entity Models
│   │   │   ├── repository/  # Spring Data JPA Repositories
│   │   │   ├── service/     # Business Logic
│   │── resources/
│   │   ├── application.properties  # Database & App Configs
│── pom.xml   # Maven Dependencies
│── README.md # Project Documentation


## Installation & Setup
### 1️⃣ Clone Repository
git clone https://github.com/OliverNathann/Simple_Blog_App.git
cd Simple_Blog_App


### 2️⃣ Konfigurasi Database (PostgreSQL)
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/simple_blog
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### 3️⃣ Build & Run
```sh
mvn clean install
mvn spring-boot:run
```
Aplikasi berjalan di **http://localhost:8080

## 🔐 API Authentication
Gunakan **JWT Token** untuk autentikasi:
1. **Register User:** `POST /api/auth/register`
2. **Login:** `POST /api/auth/login`
3. **Gunakan Token:** Tambahkan token ke setiap request:
   ```http
   Authorization: Bearer <JWT_TOKEN>
   ```

## 📌 API Endpoints
### **Auth**
- `POST /api/auth/register` → Daftar user baru
- `POST /api/auth/login` → Login & mendapatkan token JWT

### **Posts**
- `GET /api/posts` → Lihat semua postingan
- `POST /api/posts` → Buat postingan baru (User/Admin)
- `PUT /api/posts/{id}` → Edit postingan (User/Admin)
- `DELETE /api/posts/{id}` → Hapus postingan (Admin)
- `PUT /api/posts/{id}/publish` → Publikasi postingan (Admin)

### **Tags**
- `GET /api/tags` → Lihat semua tag
- `POST /api/tags` → Tambah tag baru
- `DELETE /api/tags/{id}` → Hapus tag

## **Menjalankan Aplikasi**
- mvn clean install
- mvn spring-boot:run

## **POSTMAN COLLECTION
https://drive.google.com/file/d/1XWjcl6BtRBC7cDUlrZEshfmz3A5NZwR2/view?usp=sharing

## 📝 License
2025 Oliver Nathan
