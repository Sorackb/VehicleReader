drop procedure if exists fipe.sp_year_price_ins;
create procedure procedure fipe.sp_year_price_ins
  out pid                  int,
      pid_model            varchar(8),
      pid_reference        int,
      pid_fuel_type        int,
      pyear                int,
      pprice               decimal(15, 2)
as
begin
  -- Search for the next code if the parameter is null
  if isnull(pid) then
    select max(yp.id)
      into pid
      from fipe.tb_year_price yp;

    set pid = ifnull(pid, 0) + 1;
  end if;

  -- Avoids duplicate
  if not exists(select yp.id
                  from fipe.tb_year_price yp
                 where yp.id = @pid
                   and yp.id_model = @pid_model) then
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
  end if;
end;