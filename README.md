# Lifffter

An offline-first native Android workout tracking application backed by a custom Node.js, Express, and PostgreSQL REST API.

Built to handle gym dead zones seamlessly—users can create custom routines, search exercises, log live sets (weight, reps, RIR), and review historical workout metrics with zero internet connectivity. Completed sessions automatically sync to the backend once network connectivity is restored.

---

## Tech Stack
1. Android: Kotlin, Jetpack Compose, Type-Safe Navigation
2. Architecture: Clean Architecture, MVVM/MVI (Android) & MVC (Backend)
3. Dependency Injection: Dagger/Hilt
4. Local Storage: Room DB, Jetpack DataStore Preferences, Android Keystore (AES)
5. Background/Async: Kotlin Coroutines, StateFlow, CollectAsStateWithLifecycle, WorkManager
6. Networking: Retrofit2, OkHttp3 (AuthInterceptor)
7. Backend: Node.js, Express.js, PostgreSQL, JWT

---

## Key Features

### Android Client
* **Offline-First Sync:** Log sets, build routines, and browse history with zero internet; `WorkManager` (`@HiltWorker`) syncs completed workouts automatically once back online.
* **Secure Auth:** AES-encrypted JWT storage (Android KeyStore + DataStore) with an OkHttp `401` interceptor that auto-clears tokens and local data on logout.
* **Live Workout Tracking:** Track weight, reps, RIR, and session duration in real time with dynamic set management.
* **Custom Routines:** Build reusable workout templates using Room's many-to-many `@Relation` mapping and start sessions in one tap.
* **Fast Workout History:** Instant volume, duration, and set-by-set breakdowns powered by a single SQL `LEFT JOIN` multimap query.
* **Offline Exercise Search:** Locally cached exercise catalog with real-time filtering by name or muscle group.

### Backend API
* **RESTful API:** Node.js and Express endpoints protected by custom JWT authentication middleware.
* **Relational Database:** PostgreSQL schema using UUIDs, foreign keys, junction tables, and connection pooling.

---

## Architecture Overview

Lifffter follows **Clean Architecture** with **MVVM/MVI** on Android and an **MVC** pattern on the backend, using Room DB as the single source of truth on the client.

```text
[ Jetpack Compose UI (Material 3) ]
               │
               ▼
   [ ViewModel (StateFlow / MVI) ]
               │
               ▼
[ Room DB (SQLite) ] ◄──► [ DataStore + Android KeyStore (AES-Encrypted JWT) ]
               │
               ▼
[ WorkManager (@HiltWorker) ]  (Queued when offline; syncs on network availability)
               │
               ▼
[ Retrofit + OkHttp AuthInterceptor ]  (Attaches JWT / Handles 401 auto-logout)
               │
               ▼
[ Node.js + Express REST API ]  (JWT Auth Middleware + MVC Controllers)
               │
               ▼
[ PostgreSQL ]  (Relational Schema with UUIDs, Foreign Keys & Connection Pooling)

```
---

## Getting Started

### Prerequisites
* **Node.js** (v18+)
* **PostgreSQL** (v14+)
* **Android Studio** (Ladybug or newer) with JDK 17+

### 1. Backend Setup

```bash
# Clone the repository and navigate to the backend folder
git clone [https://github.com/](https://github.com/)<relaxsleepydev>/<lifffter>.git

# Install dependencies
npm install
```
Create a `.env` file in the `lifffter_backend/` root directory:

```env
PORT=5000
DB_USER=your_postgres_user
DB_HOST=localhost
DB_NAME=lifffter
DB_PASSWORD=your_postgres_password
DB_PORT=5432
JWT_SECRET=your_jwt_secret_key
```

Run your PostgreSQL schema setup, then start the server:

```bash
npm start
```

### 2. Android Client Setup

1. Open the `lifffter_android/` directory in **Android Studio**.
2. Update the API base URL in your network module:
    * **Android Emulator:** `http://10.0.2.2:5000/api/v1/`
    * **Physical Device:** `http://<your-local-ip>:5000/api/v1/`
3. Sync Gradle and run the app on an emulator or physical device.

---

## License

This project is open source and available under the [MIT License](LICENSE).

---

## Interface
![Login Screen](assets/loginScreen.png)
![Workout History Screen](assets/workoutHistoryScreen.png)
![Workout Session Screen](assets/workoutSessionScreen.png)
![Routine Screen](assets/routineScreen.png)
![Create Routine Screen](assets/createRoutineScreen.png)
![Workout Details Screen](assets/WorkoutDetailsScreen.png)
![Exercise Catalog Screen](assets/exerciseCatalog.png)