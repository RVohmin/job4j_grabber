create table rabbit (
   id serial primary key not null,
   created_date timestamp without timezone default now()
);