if not exists(select p.object_id
                from sys.procedures p
               inner join sys.schemas s
                  on s.schema_id = p.schema_id
               where s.name = 'fipe'
                 and p.name = 'sp_brand_ins')
begin
  declare @sql nvarchar(1000);
  set @sql = 'create procedure fipe.sp_brand_ins as';
  execute sp_executesql @sql;
end;
go

alter procedure fipe.sp_brand_ins
  @pid          int output,
  @pdescription varchar(30)
as
begin
  -- Search for the next code if the parameter is null
  if @pid is null
  begin
    select @pid = isnull(max(b.id), 0) + 1
      from fipe.tb_brand b;
  end;

  -- Avoids duplicate
  if not exists(select b.id
                  from fipe.tb_brand b
                 where b.id = @pid)
  begin
    insert into fipe.tb_brand(id,
                              description)
                       values(@pid,
                              @pdescription);
  end;
end;