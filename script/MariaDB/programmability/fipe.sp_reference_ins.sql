drop procedure if exists fipe.sp_reference_ins;
create procedure fipe.sp_reference_ins(
  out pid          int,
      pdescription varchar(15),
      pmonth       int,
      pyear        int)
begin
  declare vid_reference_situation int;

  select rs.id
    into vid_reference_situation
    from fipe.tb_reference_situation rs
   where rs.description_en = 'Pending';

  -- Search for the next code if the parameter is null
  if isnull(pid) then
    select max(r.id)
      into pid
      from fipe.tb_reference r;

    set pid = ifnull(pid, 0) + 1;
  end if;

  -- Avoids duplicate
  if not exists(select r.id
                  from fipe.tb_reference r
                 where r.id = pid) then
    insert into fipe.tb_reference(id,
                                  id_reference_situation,
                                  description_pt,
                                  month,
                                  year)
                            values(pid,
                                   vid_reference_situation,
                                   pdescription,
                                   pmonth,
                                   pyear);
  end if;
end;