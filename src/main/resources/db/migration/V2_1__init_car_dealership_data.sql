insert into SALESMAN (name, surname, pesel)
values
('Jack', 'Salesman', '67020499436'),
('Monic', 'Currency', '73021314515'),
('Tom', 'Price', '55091699846'),
('Will', 'Discount', '62081825675');

insert into MECHANIC (name, surname, pesel)
values
('Robert', 'Mechanic', '52070997836'),
('John', 'Service', '83011863727'),
('Ann', 'Car', '67111396321');

insert into CAR_TO_BUY (vin, brand, model, year, color, price)
values
('1FT7X2B60FEA74019', 'BMW', 'Series 1', '2020', 'black', '20000'),
('1N6BD06T45C416702', 'BMW', 'Series 3', '2020', 'black', '30000'),
('1G1PE5S97B7239380', 'BMW', 'Series 3', '2020', 'black', '30000'),
('1GCEC19X27Z109567', 'BMW', 'Series 5', '2020', 'black', '40000'),
('2C3CDYAG2DH731952', 'BMW', 'Series 5', '2020', 'black', '40000'),
('1GB6G5CG1C1105936', 'BMW', 'Series 7', '2020', 'black', '60000');

insert into SERVICE (service_code, description, price)
values
('58394-014', 'Replacing a wheel', '240.20'),
('55319-866', 'Wheel tuning', '50.20'),
('0008-0407', 'Oil change', '140.15'),
('43063-180', 'Replacing the oil cap', '17.19'),
('14222-2022', 'Replacing the fuel filter', '14.98');

insert into PART (serial_number, description, price)
values
('11523-7310', 'Wheel', '320.11'),
('54340-777', 'Oil', '270.18'),
('68180-556', 'Oil plug', '140.15'),
('0268-1264', 'Air filter', '90.19');