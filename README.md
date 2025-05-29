# Persons Finder

## Run with H2 (default)

```bash
./gradlew bootRun
```

## Run with PostgreSQL

Start PostgreSQL using Docker Compose:
```bash
docker-compose -f docker-complose.yml up -d
```

```bash
./gradlew bootRun --args='--spring.profiles.active=postgres'
```

## Run Tests

```bash
./gradlew test
```

