

 

--  Sample Parking
insert into parking(uid, name, latitude, longitude, no_of_times_booked, address, total, occupied, vacant) values(uuid(), "ParkingName", "42", "39", 0, "Manberia, Barakar, kulti", 10, 0, 10);
insert into parking(uid, name, latitude, longitude, no_of_times_booked, address, total, occupied, vacant) values(uuid(), "ParkingName", "123", "312", 0, "Manberia, Barakar, kulti", 10, 0, 10);
insert into parking(uid, name, latitude, longitude, no_of_times_booked, address, total, occupied, vacant) values(uuid(), "ParkingName", "13", "324", 0, "Manberia, Barakar, kulti", 10, 0, 10);
insert into parking(uid, name, latitude, longitude, no_of_times_booked, address, total, occupied, vacant) values(uuid(), "ParkingName", "293", "32", 0, "Manberia, Barakar, kulti", 10, 0, 10);
insert into parking(uid, name, latitude, longitude, no_of_times_booked, address, total, occupied, vacant) values(uuid(), "ParkingName", "1123", "123", 0, "Dhanbad, Barakar, kulti", 10, 0, 10);
insert into parking(uid, name, latitude, longitude, no_of_times_booked, address, total, occupied, vacant) values(uuid(), "ParkingName", "3", "39", 0, "Jharia, Asansol, Raniganj", 10, 0, 10);
insert into parking(uid, name, latitude, longitude, no_of_times_booked, address, total, occupied, vacant) values(uuid(), "ParkingName", "123", "423", 0, "Jharia, Asansol, Kokkata", 10, 0, 10);
insert into parking(uid, name, latitude, longitude, no_of_times_booked, address, total, occupied, vacant) values(uuid(), "ParkingName", "213", "32", 0, "Dhanbad, RamNagar, Howrah", 10, 0, 10);
insert into parking(uid, name, latitude, longitude, no_of_times_booked, address, total, occupied, vacant) values(uuid(), "ParkingName", "31", "23", 0, "Manberia, Barakar, kulti", 10, 0, 10);


UPDATE parking SET no_of_times_booked = floor(rand() * 100);






