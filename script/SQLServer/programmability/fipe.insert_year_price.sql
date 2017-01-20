if object_id('fipe.insert_year_price', 'P') is null
begin
  exec('create procedure fipe.insert_year_price as');
end
go

alter procedure fipe.insert_year_price
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
      from fipe.year_price yp;
  end;

  -- Avoids duplicate
  if not exists(select yp.id
                  from fipe.year_price yp
                 where yp.id = @pid
                   and yp.id_model = @pid_model)
  begin
    insert into fipe.year_price(id,
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
