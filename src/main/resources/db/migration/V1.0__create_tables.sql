create table movie (
    id uuid not null,
    title varchar(255),
    winner boolean,
    "year" integer,
    primary key (id)
);

create table movie_producer (
    movie_id uuid not null,
    producer_id uuid not null,
    primary key (movie_id, producer_id)
);

create table movie_studio (
    movie_id uuid not null,
    studio_id uuid not null,
    primary key (movie_id, studio_id)
);

create table producer (
    id uuid not null,
    name varchar(255),
    primary key (id)
);

create table studio (
    id uuid not null,
    name varchar(255),
    primary key (id)
);

alter table if exists movie_producer add foreign key (producer_id) references producer;
alter table if exists movie_producer add foreign key (movie_id) references movie;
alter table if exists movie_studio add foreign key (studio_id) references studio;
alter table if exists movie_studio add foreign key (movie_id) references movie;