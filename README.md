# 📚 Sistema de Gestión Escolar - App Escolar

Una aplicación web y móvil para la **gestión académica de colegios**, desarrollada con **Spring Boot**, **Flutter**, y **PostgreSQL**. Permite a administradores, profesores y estudiantes gestionar asignaturas, horarios, calificaciones y más.

---

## 🧠 Funcionalidades Principales

### 🧑‍💼 Administrador
- Crear y asignar materias a profesores.
- Gestionar grados y secciones (ej: 11-A, 11-B).
- Asignar horarios a cada sección.
- CRUD completo de estudiantes y usuarios.

### 👨‍🏫 Profesor
- Confirmar disponibilidad horaria.
- Registrar y modificar calificaciones.
- Visualizar su carga académica semanal.
- Consultar historial de calificaciones por estudiante.

### 👨‍🎓 Estudiante
- Ver horario semanal.
- Consultar notas y su historial académico.
- Visualizar materias según su sección.
- Acceder a su perfil y datos personales.

---

## 🏗️ Arquitectura del Proyecto

- **Backend**: Spring Boot + Spring Security
- **Frontend Web/Mobile**: Flutter (Dart)
- **Base de datos**: PostgreSQL (Neon)
- **ORM**: JPA/Hibernate
- **Seguridad**: JWT con roles (`ADMIN`, `PROFESSOR`, `STUDENT`)
- **Autenticación**: `UserEntity` con roles relacionados (`@ManyToMany` con `RoleEntity`)

---

## 📁 Estructura de Entidades

- `UserEntity` (autenticación)
- `RoleEntity` (`ADMIN`, `PROFESSOR`, `STUDENT`)
- `Estudiante`, `Profesor`
- `Grado`, `Seccion` (como 11-A, 11-B, etc.)
- `Materia`, `Nota`, `Horario`

---

## 🚀 Cómo ejecutar el proyecto

### 1. Backend (Spring Boot)
```bash
cd backend
./mvnw spring-boot:run
2. Frontend (Flutter)
bash
Copy
Edit
cd frontend
flutter pub get
flutter run
3. Base de datos
PostgreSQL local o NeonDB (ver archivo .env para variables)

🔒 Seguridad
Implementación completa de Spring Security.

JWT para autenticación.

Roles con permisos diferenciados en endpoints protegidos.

📄 Licencia
Este proyecto está licenciado bajo la MIT License.

👥 Autores

Los Ings 
