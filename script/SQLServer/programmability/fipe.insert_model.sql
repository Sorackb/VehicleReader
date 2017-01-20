if object_id('fipe.insert_model', 'P') is null
begin
  exec('create procedure fipe.insert_model as');
end
go

alter procedure fipe.insert_model
  @pid                        varchar(8),
  @pid_brand                  int,
  @pid_vehicle_classification int,
  @pdescription               varchar(90)
as
begin
  -- Avoids duplicate
  if not exists(select m.id
                  from fipe.model m
                 where m.id = @pid)
  begin
    insert fipe.model(id,
                      id_brand,
                      id_vehicle_classification,
                      description)
               values(@pid,
                      @pid_brand,
                      @pid_vehicle_classification,
                      @pdescription);
  end;
end;