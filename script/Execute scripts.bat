for %%G in (%~dp0\structure\*.sql) do sqlcmd /S 192.168.112.28\SQLEXPRESS /d fipe -U sa -P seguros1129! -i"%%G"
for %%G in (%~dp0\programmability\*.sql) do sqlcmd /S 192.168.112.28\SQLEXPRESS /d fipe -U sa -P seguros1129! -i"%%G"
for %%G in (%~dp0\data\*.sql) do sqlcmd /S 192.168.112.28\SQLEXPRESS /d fipe -U sa -P seguros1129! -i"%%G"
pause