create table Settings (
  user_id integer not null,
  anonymity integer not null
);

create table Guide (
    guide_id serial,
    creator_id integer not null,
    name varchar(50) not null,
    description varchar(250),
    city varchar(50),
    budget varchar(50),
    likes integer,
    primary key(guide_id)
);

create table Location (
    location_id serial,
    guide_id integer not null,
    name varchar(50) not null,
    description varchar(250),
    photo varchar(50),
    geo_point varchar(50),
    primary key(location_id),
    foreign key (guide_id) references Guide
);

create table HashTag (
    hashtag_id serial,
    name varchar(50),
    primary key(hashtag_id)
);

create table Guide_HashTag (
    hashtag_id integer not null,
    guide_id integer not null,
    foreign key (hashtag_id) references HashTag,
    foreign key (guide_id) references Guide
);

create table Trip(
    trip_id serial,
    org_id integer not null,
    guide_id integer not null,
    visibility integer not null,
    start_date date,
    finish_date date,
    chat varchar(50),
    primary key(trip_id),
    foreign key (guide_id) references Guide
);


create table TripParticipant(
  participant_id serial,
  user_id integer not null,
  trip_id integer not null,
  accept_status integer not null,
  role varchar(50),
  primary key (participant_id),
  foreign key (trip_id) references Trip
);

create table Ticket(
    ticket_id serial,
    participant_id integer not null,
    trip_id integer not null,
    ticket varchar(100),
    primary key(ticket_id),
    foreign key (participant_id) references TripParticipant,
    foreign key (trip_id) references Trip
);