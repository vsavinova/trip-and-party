create table Settings (
  user_id integer not null unique,
  anonymity integer not null
);

create table Trip(
    trip_id serial,
    org_id integer not null,
    name varchar(50) not null,
    description varchar(250),
    visibility integer not null,
    start_date date,
    finish_date date,
    location varchar(50),
    chat varchar(50),
    primary key(trip_id)
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