if object_id('fipe.sp_brand_ins', 'P') is null
begin
  exec('create procedure fipe.sp_brand_ins as');
end 
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