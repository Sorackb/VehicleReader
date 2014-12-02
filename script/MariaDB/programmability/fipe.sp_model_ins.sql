drop procedure if exists fipe.sp_model_ins;
create procedure fipe.sp_model_ins(
  pid                        varchar(8),
  pid_brand                  int,
  pid_vehicle_classification int,
  pdescription               varchar(90))
begin
  -- Avoids duplicate
  if not exists(select m.id
                  from fipe.tb_model m
                 where m.id = pid) then
    insert fipe.tb_model(id,
                         id_brand,
                         id_vehicle_classification,
                         description)
                   values(pid,
                          pid_brand,
                          pid_vehicle_classification,
                          pdescription);
  end if;
end;
