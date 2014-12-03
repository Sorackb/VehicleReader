VehicleReader
=============

A simple web scraper that read the references, brand, model and year/price of vehicles from http://www.fipe.org.br/

### Deploy structure

```
VehicleReader/
├── VehicleReader.jar
├── config.ini
```

### config.ini structure

- connection.dbms (optional)
  - `(default) SQLServer`
  - `MariaDB`
- connection.host (optional)
  - `(default) 127.0.0.1`
- connection.database
- connection.user
- connection.password
