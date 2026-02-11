# 🚀 Loop – Personalized Learning & Productivity Assistant

**Loop** is a modern learning and productivity platform that helps users track topics, manage progress, and set intelligent reminders.

It combines a sleek **Kotlin Android app** with a **Spring Boot backend** powered by **PostgreSQL** and deployed on **Google Cloud Run / AWS**.

---

## 🧩 Overview

Loop enables users to:
- ✅ Register and log in securely using **Firebase Authentication**
- 📚 Browse and manage topics fetched dynamically from the backend
- 🧠 Mark topics as complete and receive personalized reminders
- 🔄 Sync progress seamlessly across devices
- 💡 Enjoy a futuristic **Neo Green UI** built with Jetpack Compose and Orbitron fonts

---

## 🏗️ Architecture

### 🔹 **Frontend (Android App)**
- **Language:** Kotlin
- **Framework:** Jetpack Compose (Material 3)
- **Design:** Dark "Hacker" theme using Orbitron font
- **Networking:** OkHttp / Retrofit
- **Authentication:** Firebase Auth

### 🔹 **Backend (Spring Boot)**
- **Language:** Java (Spring Boot 3)
- **Database:** PostgreSQL (Cloud SQL / AWS RDS)
- **Endpoints:**
  - `/api/users/verify` → Verifies Firebase tokens
  - `/api/topics` → Returns paginated topics
- **Security:** JWT / Firebase verification
- **Deployment:** Google Cloud Run or AWS Elastic Beanstalk

---

## 📁 Project Structure

### Backend (`Loop_Backend/`)
```
Loop_Backend/
├── src/main/java/
│   └── com/loop/
│       ├── LoopApplication.java
│       ├── controller/
│       ├── service/
│       ├── model/
│       └── config/
├── src/main/resources/
│   └── application.yml
├── pom.xml
└── Dockerfile
```

### Android App (`Loop_Frontend/`)
```
Loop_Frontend/
├── app/src/main/java/
│   └── com/loop/android/
│       ├── MainActivity.kt
│       ├── ui/
│       │   ├── auth/
│       │   ├── topics/
│       │   └── theme/
│       ├── data/
│       └── network/
├── app/build.gradle.kts
└── google-services.json
```

---

## ☁️ Deployment Steps

### 🧱 Backend

1. **Build JAR**
```bash
mvn clean package
```

2. **Deploy to Google Cloud Run**
```bash
gcloud builds submit --tag gcr.io/[PROJECT_ID]/loop-backend
gcloud run deploy loop-backend \
  --image gcr.io/[PROJECT_ID]/loop-backend \
  --platform managed \
  --region asia-south1 \
  --allow-unauthenticated
```

3. **Configure Database Connection**
Update your `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://[DB_HOST]:5432/loop_db
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

### 📱 Android Setup

1. **Add Firebase Configuration**
   - Place `google-services.json` in `app/` directory
   
2. **Build APK**
```bash
./gradlew assembleDebug
```

---

## 🌟 Ready to Loop?

**Loop** isn't just another productivity app – it's your personal learning companion that grows with you. Whether you're mastering new skills, tracking study progress, or building better habits, Loop keeps you in the flow.

*Start your learning journey today and let Loop transform the way you grow.* 🚀

---

**Built with ❤️ using modern tech stack | Designed for the future of learning**