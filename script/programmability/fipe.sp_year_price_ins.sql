if not exists(select p.object_id
                from sys.procedures p
               inner join sys.schemas s
                  on s.schema_id = p.schema_id
               where s.name = 'fipe'
                 and p.name = 'sp_year_price_ins')
begin
  declare @sql nvarchar(1000);
  set @sql = 'create procedure fipe.sp_year_price_ins as';
  execute sp_executesql @sql;
end;
go

alter procedure fipe.sp_year_price_ins
  @pid                  int output,
  @pid_model            varchar(8),
  @pid_reference        int,
  @pid_fuel_type        int,
  @pyear                int,
  @pprice               money
as
begin
  -- Search for the next code if the parameter is null
  if @pid is null
  begin
    select @pid = isnull(max(yp.id), 0) + 1
      from fipe.tb_year_price yp;
  end;

  -- Avoids duplicate
  if not exists(select yp.id
                  from fipe.tb_year_price yp
                 where yp.yd = @pid)
  begin
    insert into fipe.tb_year_price(id,
                                  id_model,
                                  id_reference,
                                  id_fuel_type,
                                  year,
                                  price)
                           values(@pid,
                                  @pid_model,
                                  @pid_reference,
                                  @pid_fuel_type,
                                  @pyear,
                                  @pprice);
  end;
end;