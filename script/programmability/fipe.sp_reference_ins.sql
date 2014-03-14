if not exists(select p.object_id
                from sys.procedures p
               inner join sys.schemas s
                  on s.schema_id = p.schema_id
               where s.name = 'fipe'
                 and p.name = 'sp_reference_ins')
begin
  declare @sql nvarchar(1000);
  set @sql = 'create procedure fipe.sp_reference_ins as';
  execute sp_executesql @sql;
end;
go

alter procedure fipe.sp_reference_ins
  @pid          int output,
  @pdescription varchar(15),
  @pmonth       int,
  @pyear        int
as
begin
  declare @vid_reference_situation int;


  select @vid_reference_situation = rs.id
    from fipe.tb_reference_situation rs
   where rs.description = 'Pending';

  -- Search for the next code if the parameter is null
  if @pid is null
  begin
    select @pid = isnull(max(r.id), 0) + 1
      from fipe.tb_reference r;
  end;

  -- Avoids duplicate
  if not exists(select r.id
                  from fipe.tb_reference r
                 where r.id = @pid)
  begin
    insert into fipe.tb_reference(id,
                                  id_reference_situation,
                                  description,
                                  month,
                                  year)
                            values(@pid,
                                   @vid_reference_situation,
                                   @pdescription,
                                   @pmonth,
                                   @pyear);
  end;
end
go