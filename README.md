VehicleReader
=============

| PagSeguro     | PayPal      |
| ------------- |-------------|
[![ague com PagSeguro - é rápido, grátis e seguro!](https://stc.pagseguro.uol.com.br/public/img/botoes/doacoes/209x48-doar-laranja-assina.gif)](https://pag.ae/bhmK2Xf) | [![Make a donation](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=LKDGCQBKYBW5E)

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
