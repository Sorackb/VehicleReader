for %%G in (%~dp0\structure\*.sql) do sqlcmd /S 127.0.0.1\SQLEXPRESS /d fipe -U sa -P *password* -i"%%G"
for %%G in (%~dp0\programmability\*.sql) do sqlcmd /S 127.0.0.1\SQLEXPRESS /d fipe -U sa -P *password* -i"%%G"
for %%G in (%~dp0\data\*.sql) do sqlcmd /S 127.0.0.1\SQLEXPRESS /d fipe -U sa -P *password* -i"%%G"
pause
