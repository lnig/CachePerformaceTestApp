# 📌 Opis projektu
Projekt został stworzony na potrzeby **pracy magisterskiej** w celu zbadania i porównania wydajności aplikacji webowej opartej na frameworku **Spring Boot** przy użyciu różnych strategii dostępu do danych.

Aplikacja symuluje obciążenie bazy danych poprzez operacje na zbiorze 50 000 rekordów produktów i analizuje wpływ zastosowania pamięci podręcznej o dostępie lokalnym (Caffeine) oraz rozproszonym (Redis) na kluczowe parametry jakościowe: czas odpowiedzi, przepustowość oraz obciążenie infrastruktury. 

# 🛠 Stos technologiczny
- Java 21 & Spring Boot 4.0.2
- PostgreSQL
- Redis
- Docker
- Hibernate / Spring Data JPA

# 🚀 Architektura i Konteneryzacja
Projekt jest w pełni skonteneryzowany, co zapewnia powtarzalność testów niezależnie od środowiska. W skład infrastruktury wchodzą:
1. **app**: Aplikacja Spring Boot serwująca API.
2. **postgres**: Baza danych przechowująca struktury produktów i kategorii.
3. **redis**: Szybka baza klucz-wartość wykorzystywana do cache'owania wyników zapytań.

# 🚦 Jak uruchomić?
**Wymagania**
- Zainstalowany **Docker Desktop**

**Kroki**
1. Sklonuj repozytorium:
```
git clone https://github.com/lnig/CachePerformaceTestApp.git
```
2. Przejdź do folderu projektu i uruchom infrastrukturę:
```
docker compose up --build
```
3. Aplikacja będzie dostępna pod adresem: http://localhost:8080
