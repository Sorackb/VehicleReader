if object_id('fipe.sp_reference_ins', 'P') is null
begin
  exec('create procedure fipe.sp_reference_ins as');
end
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
   where rs.description_en = 'Pending';

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
                                  description_pt,
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