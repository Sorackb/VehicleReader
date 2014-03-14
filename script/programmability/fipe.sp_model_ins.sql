if not exists(select p.object_id
                from sys.procedures p
               inner join sys.schemas s
                  on s.schema_id = p.schema_id
               where s.name = 'fipe'
                 and p.name = 'sp_model_ins')
begin
  declare @sql nvarchar(1000);
  set @sql = 'create procedure fipe.sp_model_ins as';
  execute sp_executesql @sql;
end;
go

alter procedure fipe.sp_model_ins
  @pid                        varchar(8),
  @pid_brand                  int,
  @pid_vehicle_classification int,
  @pdescription               varchar(90)
as
begin
  -- Avoids duplicate
  if not exists(select m.id
                  from fipe.tb_model m
                 where m.id = @pid)
  begin
    insert fipe.tb_model(id,
                         id_brand,
                         id_vehicle_classification,
                         description)
                   values(@pid,
                          @pid_brand,
                          @pid_vehicle_classification,
                          @pdescription);
  end;
end;