# JETPACK COMPOSE: DE CERO A CREADOR DE INTERFACES

Una aplicación de ejemplo para desarrolladores Android con conocimientos básicos de Kotlin que quieren aprender Jetpack Compose desde cero.

---

¡Bienvenido! Este repositorio contiene una app educativa que muestra patrones sencillos para construir interfaces modernas con Jetpack Compose, integrando código Kotlin y una estructura de proyecto amigable para principiantes.

Características principales

- Interfaz construida con Jetpack Compose (componentes y estados básicos).
- Estructura clara pensada para aprendizaje: módulo `app`, recursos y ejemplos prácticos.
- Configuración lista con Gradle Wrapper para compilar en cualquier máquina.
- Buen punto de partida para experimentar con layout, navegación y recursos.

Índice

- ¿Por qué este proyecto?
- Requisitos
- Primeros pasos (ejecutar la app)
- Compilar desde terminal
- Estructura del proyecto
- Buenas prácticas y qué aprenderás
- Contribuir
- Licencia y contacto

¿Por qué este proyecto?

Si vienes de trabajar con Views tradicionales o estás empezando con Kotlin, este proyecto te guía con ejemplos prácticos y pequeños ejercicios que demuestran cómo pensar la interfaz con Compose.

Requisitos

- Android Studio (recomendado) o una herramienta compatible con proyectos Android Gradle.
- JDK 11 o superior.
- Gradle wrapper incluido (no requiere instalar Gradle globalmente).
- Emulador Android o dispositivo físico con modo desarrollador activado.

Primeros pasos (Android Studio)

1. Clona el repositorio:

```bash
git clone https://github.com/jvmartinez/book_Interface_creator.git
```

2. Abre el proyecto en Android Studio (File > Open...) y selecciona la carpeta `book_Interface_creator`.
3. Deja que Android Studio sincronice Gradle y descargue dependencias.
4. Ejecuta la app en un emulador o dispositivo (Run > App).

Compilar desde terminal

Si prefieres usar la terminal, puedes compilar el APK con el Gradle wrapper incluido:

```bash
cd book_Interface_creator
./gradlew assembleDebug
```

El artefacto resultante queda en `app/build/outputs/apk/debug/`.

Estructura del proyecto (resumen)

- `app/` — Módulo principal de la aplicación.
  - `src/main/java` — Código Kotlin/Java.
  - `src/main/res` — Recursos (drawables, layouts, strings).
  - `build.gradle.kts` — Configuración del módulo.
- `build.gradle.kts` — Configuración raíz del proyecto.
- `gradle/` — Wrapper y versionado.

Qué aprenderás

- Fundamentos de Jetpack Compose: composables, layouts y manejo de estado.
- Cómo mezclar código Kotlin y Java en un proyecto Android.
- Estructura de un proyecto Android moderno con Gradle.
- Flujos básicos de navegación y organización de recursos.

Contribuir

¡Las contribuciones son bienvenidas! Si quieres ayudar, sigue estos pasos:

1. Haz fork del repositorio.
2. Crea una rama descriptiva: `feature/tu-mejora` o `fix/descripcion`.
3. Haz commits claros y atómicos.
4. Abre un Pull Request describiendo los cambios y por qué son valiosos.

Buenas prácticas al enviar PRs

- Mantén el scope pequeño y documenta el propósito.
- Incluye capturas si tocas la UI.
- Añade pruebas mínimas si modificas lógica crítica.

Licencia

Este proyecto incluye un archivo `LICENSE` en la raíz. Revisa ese archivo para los términos completos

Contacto

Para dudas, ideas o colaboración, abre un issue del repo.

---
