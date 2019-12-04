
insert into driver (driver_id, date_created, deleted, online_status, password, username) values ('1', now(), false, 'OFFLINE','driver01pw','driver01');
insert into driver (driver_id, date_created, deleted, online_status, password, username) values ('2', now(), false, 'OFFLINE','driver02pw','driver02');
insert into driver (driver_id, date_created, deleted, online_status, password, username) values ('3', now(), false, 'OFFLINE','driver03pw','driver03');

insert into car (car_id, license_plate, seat_count, convertible, rating, manufacturer, engine_type) values ('1', 'licenseplate1', 10, false,1, 'carmanifacturer1', 'ELECTRIC');
insert into car (car_id, license_plate, seat_count, convertible, rating, manufacturer, engine_type) values ('2', 'licenseplate2', 20, false,2, 'carmanifacturer2', 'GAS');
insert into car (car_id, license_plate, seat_count, convertible, rating, manufacturer, engine_type) values ('3', 'licenseplate3', 30, false,3, 'carmanifacturer3', 'ELECTRIC');
insert into car (car_id, license_plate, seat_count, convertible, rating, manufacturer) values ('4', 'licenseplate4', 40, false,4, 'carmanifacturer4');
insert into car (car_id, license_plate, seat_count, convertible, rating, manufacturer) values ('5', 'licenseplate5', 50, false,5, 'carmanifacturer5');