if object_id('fipe.insert_brand', 'P') is null
begin
  exec('create procedure fipe.insert_brand as');
end
go

alter procedure fipe.insert_brand
  @pid          int output,
  @pdescription varchar(30)
as
begin
  -- Search for the next code if the parameter is null
  if @pid is null
  begin
    select @pid = isnull(max(b.id), 0) + 1
      from fipe.brand b;
  end;

  -- Avoids duplicate
  if not exists(select b.id
                  from fipe.brand b
                 where b.id = @pid)
  begin
    insert into fipe.brand(id,
                           description)
                    values(@pid,
                           @pdescription);
  end;
end;