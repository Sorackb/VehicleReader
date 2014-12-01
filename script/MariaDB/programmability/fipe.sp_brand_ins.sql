drop procedure if exists fipe.sp_brand_ins;
create procedure fipe.sp_brand_ins(
  out pid          int,
      pdescription varchar(30))
begin
  -- Search for the next code if the parameter is null
  if isnull(pid) then
    select max(b.id)
      into pid
      from fipe.tb_brand b;

    set pid = ifnull(pid, 0) + 1;
  end if;

  -- Avoids duplicate
  if not exists(select b.id
                  from fipe.tb_brand b
                 where b.id = pid) then
    insert into fipe.tb_brand(id,
                              description)
                       values(pid,
                              pdescription);
  end if;
end;