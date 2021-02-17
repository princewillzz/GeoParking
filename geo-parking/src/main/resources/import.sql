



--  Sample Parking
insert into parking(uid, latitude, longitude, no_of_times_booked, address, total, occupied, vacant) values(uuid(), "123", "312", 0, "Manberia, Barakar, kulti", 10, 0, 10);
insert into parking(uid, latitude, longitude, no_of_times_booked, address, total, occupied, vacant) values(uuid(), "42", "39", 0, "Manberia, Barakar, kulti", 10, 0, 10);
insert into parking(uid, latitude, longitude, no_of_times_booked, address, total, occupied, vacant) values(uuid(), "13", "324", 0, "Manberia, Barakar, kulti", 10, 0, 10);
insert into parking(uid, latitude, longitude, no_of_times_booked, address, total, occupied, vacant) values(uuid(), "293", "32", 0, "Manberia, Barakar, kulti", 10, 0, 10);
insert into parking(uid, latitude, longitude, no_of_times_booked, address, total, occupied, vacant) values(uuid(), "1123", "3122", 0, "Manberia, Barakar, kulti", 10, 0, 10);

UPDATE parking SET no_of_times_booked = floor(rand() * 100);






