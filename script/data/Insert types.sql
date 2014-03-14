if not exists(select vc.id
                from fipe.tb_vehicle_classification vc
               where vc.id = 1)
begin
  insert into fipe.tb_vehicle_classification(id,
                                             description_en,
                                             description_pt)
                                      values(1,
                                             'Car',
                                             'Passeio');
end;
go

if not exists(select vc.id
                from fipe.tb_vehicle_classification vc
               where vc.id = 2)
begin
  insert into fipe.tb_vehicle_classification(id,
                                             description_en,
                                             description_pt)
                                      values(2,
                                             'Motocycle',
                                             'Moto');
end;
go

if not exists(select vc.id
                from fipe.tb_vehicle_classification vc
               where vc.id = 3)
begin
  insert into fipe.tb_vehicle_classification(id,
                                             description_en,
                                             description_pt)
                                      values(3,
                                             'Truck',
                                             'Caminhão');
end;
go

if not exists(select ft.id
                from fipe.tb_fuel_type ft
               where ft.id = 1)
begin
  insert into fipe.tb_fuel_type(id,
                                description_en,
                                description_pt)
                         values(1,
                                'Gasoline',
                                'Gasolina');
end;
go

if not exists(select ft.id
                from fipe.tb_fuel_type ft
               where ft.id = 2)
begin
  insert into fipe.tb_fuel_type(id,
                                description_en,
                                description_pt)
                         values(2,
                                'Diesel',
                                'Diesel');
end;
go

if not exists(select ft.id
                from fipe.tb_fuel_type ft
               where ft.id = 3)
begin
  insert into fipe.tb_fuel_type(id,
                                description_en,
                                description_pt)
                         values(3,
                               'Alcohol',
                               'Álcool');
end;
go

if not exists(select ft.id
                from fipe.tb_fuel_type ft
               where ft.id = 4)
begin
  insert into fipe.tb_fuel_type(id,
                                description_en,
                                description_pt)
                         values(4,
                                'Unidentified',
                                'Não Identificado');
end;
go

if not exists(select tf.id
                from fipe.tb_fuel_type tf
               where tf.id = 5)
begin
  insert into fipe.tb_fuel_type(id,
                                description_en,
                                description_pt)
                         values(5,
                                'Other',
                                'Outros');
end;
go

if not exists(select rs.id
                from fipe.tb_reference_situation rs
               where rs.id = 1)
begin
  insert into fipe.tb_reference_situation(id,
                                          description_en,
                                          description_pt)
                                   values(1,
                                          'Pending',
                                          'Pendente');
end;
go

if not exists(select rs.id
                from fipe.tb_reference_situation rs
               where rs.id = 2)
begin
  insert into fipe.tb_reference_situation(id,
                                          description_en,
                                          description_pt)
                                   values(2,
                                          'Incomplete',
                                          'Incompleto');
end;
go

if not exists(select rs.id
                from fipe.tb_reference_situation rs
               where rs.id = 3)
begin
  insert into fipe.tb_reference_situation(id,
                                          description_en,
                                          description_pt)
                                   values(3,
                                          'Complete',
                                          'Completo');
end;
go 
