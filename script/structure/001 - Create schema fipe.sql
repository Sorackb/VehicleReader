if object_id('fipe.tb_vehicle_classification', 'U') is null
begin
  create table fipe.tb_vehicle_classification(
    id             int         not null,
    description_en varchar(15) not null,
    description_pt varchar(15) not null,
    constraint pk_vehicle_classification primary key(id));
end;
go

if object_id('fipe.tb_fuel_type', 'U') is null
begin
  create table fipe.tb_fuel_type(
    id             int         not null,
    description_en varchar(30) not null,
    description_pt varchar(30) not null,
    constraint  pk_tb_fuel_type primary key(id));
end;
go

if object_id('fipe.tb_reference_situation', 'U') is null
begin
  create table fipe.tb_reference_situation(
    id             int         not null,
    description_en varchar(15) not null,
    description_pt varchar(15) not null,
    constraint  pk_reference_situation primary key(id));
end;
go

if object_id('fipe.tb_reference', 'U') is null
begin
  create table fipe.tb_reference(
    id                     int not null,
    id_reference_situation int not null,
    description_en         varchar(15),
    description_pt         varchar(15),
    month                  int not null,
    year                   int not null,
    constraint pk_reference primary key(id),
    constraint fk_reference_reference_situation foreign key(id_reference_situation)
               references fipe.tb_reference_situation(id));
end;
go

if object_id('fipe.tb_brand', 'U') is null
begin
  create table fipe.tb_brand(
    id          int         not null,
    description varchar(30) not null,
    constraint pk_brand primary key(id));
end;
go

if object_id('fipe.tb_model', 'U') is null
begin
  create table fipe.tb_model(
    id                        varchar(8)  not null,
    id_brand                  int         not null,
    id_vehicle_classification int         not null,
    description               varchar(90) not null,
    constraint pk_model primary key(id),
    constraint fk_model_brand foreign key(id_brand)
               references fipe.tb_brand(id) on delete cascade,
    constraint fk_model_vehicle_classification foreign key(id_vehicle_classification)
               references fipe.tb_vehicle_classification(id));
end;
go

if object_id('fipe.tb_year_price', 'U') is null
begin
  create table fipe.tb_year_price(
    id           int        not null,
    id_model     varchar(8) not null,
    id_reference int        not null,
    id_fuel_type int,
    year         int        not null,
    price        money,
    reading_date datetime default getDate(),
    constraint pk_year_price primary key(id, id_model),
    constraint fk_year_price_model foreign key(id_model)
               references fipe.tb_model(id) on delete cascade,
    constraint fk_year_price_reference foreign key(id_reference)
               references fipe.tb_reference(id) on delete cascade,
    constraint fk_year_price_fuel_type foreign key(id_fuel_type)
               references fipe.tb_fuel_type(id));
end;
go
