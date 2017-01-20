declare @classification table(id             int,
                              description_en varchar(15),
                              description_pt varchar(15));
declare @fuel_type table(id             int,
                         description_en varchar(30),
                         description_pt varchar(30));
declare @situation table(id             int,
                         description_en varchar(15),
                         description_pt varchar(15));

set nocount on;

insert into @classification values(1, 'Car', 'Passeio');
insert into @classification values(2, 'Motocycle', 'Moto');
insert into @classification values(3, 'Truck', 'Caminhão');

insert into @fuel_type values(1, 'Gasoline', 'Gasolina');
insert into @fuel_type values(2, 'Diesel', 'Diesel');
insert into @fuel_type values(3, 'Alcohol', 'Álcool');
insert into @fuel_type values(4, 'Unidentified', 'Não Identificado');
insert into @fuel_type values(5, 'Other', 'Outros');

insert into @situation values(1, 'Pending', 'Pendente');
insert into @situation values(2, 'Incomplete', 'Incompleto');
insert into @situation values(3, 'Complete', 'Completo');

set nocount off;

insert into fipe.vehicle_classification(id,
                                        description_en,
                                        description_pt)
select c.id,
       c.description_en,
       c.description_pt
  from @classification c
 where not exists(select 1
                    from fipe.vehicle_classification vc with(nolock)
                   where vc.id = c.id);

insert into fipe.fuel_type(id,
                           description_en,
                           description_pt)
select ft.id,
       ft.description_en,
       ft.description_pt
  from @fuel_type ft
 where not exists(select 1
                    from fipe.fuel_type ft with(nolock)
                   where ft.id = ft.id);

insert into fipe.reference_situation(id,
                                     description_en,
                                     description_pt)
select s.id,
       s.description_en,
       s.description_pt
  from @situation s
 where not exists(select 1
                    from fipe.reference_situation rs with(nolock)
                   where rs.id = s.id);