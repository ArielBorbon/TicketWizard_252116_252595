#Ariel Eduardo Borbon Izaguirre 	ID:252116
#Alberto Jimenez Garcia 			ID:252595


drop database if exists ticketwizard;
create database if not exists Ticketwizard;
use Ticketwizard;

create table Personas (
    persona_id int auto_increment primary key,
    correo varchar(80) unique not null,
    nombre_completo varchar(80) not null,
    domicilio varchar(80),
    fecha_nacimiento date not null,
    edad int not null,
    total_boletos int not null default 0,
    saldo decimal(10,2) default 0.00 check (saldo >= 0),
    usuario varchar(50) unique not null,
    contrasena varchar(255) not null
);

create table Eventos(
    evento_id int auto_increment primary key,
    nombre varchar(60) not null,
    fecha datetime not null,
    recinto varchar(80),
    ciudad varchar(80),
    estado varchar(50),
    descripcion varchar(80),
    total_boletos int not null default 0
); 

create table Boletos(

    boleto_id int auto_increment primary key,
    num_serie char(8) unique not null,
    fila varchar(50) not null,
    asiento varchar(50) not null,
    num_control varchar(255) unique not null,
    precio_original decimal(10,2) not null,
    evento_id int not null,
    persona_id int not null,
    foreign key (evento_id) references Eventos(evento_id),
    foreign key (persona_id) references Personas(persona_id)
    
);

create table Transacciones(
    transaccion_id int auto_increment primary key,
    num_transaccion varchar(255) unique not null,
    fecha_hora datetime not null default current_timestamp,
    tipo enum('compra_directa','compra_reventa') not null,
    monto_total decimal(10,2) not null,
    comision decimal(10,2) default 0.00,
    estado enum('completado','pendiente','cancelado') default 'pendiente',
    fecha_expiracion datetime,
    persona_id int not null,
    foreign key (persona_id) references Personas(persona_id)
);

create table Reventas(
    reventa_id int auto_increment primary key,
    precio_reventa decimal(10,2) not null,
    fecha_limite datetime not null,
    estado enum('activo','vendido','expirado') default 'activo',
    boleto_id int not null,
    persona_id_vendedor int not null,
    foreign key (boleto_id) references Boletos(boleto_id),
    foreign key (persona_id_vendedor) references Personas(persona_id)
);

create table Transacciones_boletos(
    transaccion_id int not null,
    boleto_id int not null,
    primary key(transaccion_id, boleto_id),
    foreign key(transaccion_id) references Transacciones(transaccion_id),
    foreign key(boleto_id) references Boletos(boleto_id)
);

create table Personas_transacciones(
    transaccion_id int not null primary key,
    comprador_id int not null,
    vendedor_id int not null,
    foreign key(transaccion_id) references Transacciones(transaccion_id),
    foreign key (comprador_id) references Personas(persona_id),
    foreign key (vendedor_id) references Personas(persona_id)
);

ALTER TABLE Personas_transacciones MODIFY vendedor_id INT NULL;

delimiter //
create trigger edad 
before insert on Personas
for each row
begin
    set new.edad = timestampdiff(year,new.fecha_nacimiento, curdate());
end //
delimiter;

delimiter //
create trigger edad_actualizada 
before update on Personas
for each row
begin
    set new.edad = timestampdiff(year,new.fecha_nacimiento, curdate());
end //
delimiter;

delimiter //
create trigger actualizar_saldo_compra
after update on Transacciones
for each row
begin
    if new.estado = 'completado' then
 
        update Personas set saldo = saldo - new.monto_total where persona_id = new.persona_id;


        if new.tipo = 'compra_directa' then
            update Personas set saldo = saldo + (new.monto_total - new.comision) where persona_id = (
                select vendedor_id from Personas_transacciones where transaccion_id = new.transaccion_id
            );
        end if;
    end if;
end //
delimiter;

delimiter //
create event liberar_boletos_pendientes
on schedule every 1 minute
do
    update Transacciones
    set estado = 'cancelado'
    where estado = 'pendiente' and fecha_expiracion < now();
//
delimiter //
create trigger total_boletos
after insert on Boletos
for each row
begin
    update Personas
    set total_boletos = total_boletos + 1
    where persona_id = new.persona_id;
end;

delimiter //
create trigger actualizar_total_boletos
after insert on Boletos
for each row
begin
    update Eventos
    set total_boletos = total_boletos + 1
    where evento_id = new.evento_id;
end //
delimiter ;



delimiter //
create trigger actualizar_total_boletos_comprados
after insert on Transacciones_boletos
for each row
begin

    update Personas
    set total_boletos = total_boletos + 1
    where persona_id = (select persona_id from Boletos where boleto_id = new.boleto_id);
end //
delimiter ;
ALTER TABLE Personas ADD COLUMN contrasena_encriptada ENUM("Y", "N") DEFAULT 'N';


INSERT INTO Eventos (nombre, fecha, recinto, ciudad, estado, descripcion, total_boletos) VALUES
('Coldplay - Music of Spheres', '2025-09-15 20:00:00', 'Foro Sol', 'CDMX', 'CDMX', 'Concierto de Coldplay con invitados especiales', 0),
('Liga MX - Final', '2025-05-25 19:00:00', 'Estadio Azteca', 'CDMX', 'CDMX', 'Final de torneo Clausura 2024', 0),
('Bad Bunny - World''s Hottest Tour', '2025-11-20 21:00:00', 'Estadio Akron', 'Guadalajara', 'Jalisco', 'Concierto de Bad Bunny', 0);


INSERT INTO Personas (correo, nombre_completo, domicilio, fecha_nacimiento, edad, saldo, usuario, contrasena) VALUES
('boletera@mail.com', 'Boletera MX', 'Av. Principal 123', '1990-01-01', 34, 100000.00, 'boletera', 'password123'),
('cliente1@mail.com', 'Juan Pérez', 'Calle Falsa 123', '1985-05-15', 39, 500000.00, 'juanperez', 'password456'),
('cliente2@mail.com', 'María García', 'Av. Revolución 45', '1992-08-22', 31, 7500.00, 'mariagarcia', 'password789'),
('cliente3@mail.com', 'Pedro López', 'Callejón del Beso 7', '1998-03-30', 26, 3000.00, 'pedrolopez', 'password101'),
('cliente4@mail.com', 'Ana Martínez', 'Blvd. Libertad 89', '1980-12-10', 43, 12000.00, 'anamartinez', 'password202'),
('cliente5@mail.com', 'Carlos Rodríguez', 'Privada Olvido 12', '1995-07-04', 28, 6000.00, 'carlosrodriguez', 'password303');

ALTER TABLE Boletos 
ADD estado ENUM('disponible', 'reservado', 'vendido') NOT NULL DEFAULT 'disponible';


INSERT INTO Boletos (num_serie, fila, asiento, num_control, precio_original, evento_id, persona_id, estado) VALUES
('A1B2C3D4', 'A', '1', 'EVT1-A1-2500', 2500.00, 1, 1, 'disponible'),
('E5F6G7H8', 'A', '2', 'EVT1-A2-2500', 2500.00, 1, 1, 'disponible'),
('I9J0K1L2', 'A', '3', 'EVT1-A3-2500', 2500.00, 1, 1, 'disponible'),
('M3N4O5P6', 'A', '4', 'EVT1-A4-2500', 2500.00, 1, 1, 'disponible'),
('Q7R8S9T0', 'A', '5', 'EVT1-A5-2500', 2500.00, 1, 1, 'disponible'),
('U1V2W3X4', 'B', '1', 'EVT1-B1-2500', 2500.00, 1, 1, 'disponible'),
('Y5Z6A7B8', 'B', '2', 'EVT1-B2-2500', 2500.00, 1, 1, 'disponible'),
('C9D0E1F2', 'B', '3', 'EVT1-B3-2500', 2500.00, 1, 1, 'disponible'),
('G3H4I5J6', 'B', '4', 'EVT1-B4-2500', 2500.00, 1, 1, 'disponible'),
('K7L8M9N0', 'B', '5', 'EVT1-B5-2500', 2500.00, 1, 1, 'disponible');


INSERT INTO Boletos (num_serie, fila, asiento, num_control, precio_original, evento_id, persona_id, estado) VALUES
('O1P2Q3R4', 'VIP', '1', 'EVT2-VIP1-5000', 5000.00, 2, 1, 'disponible'),
('S5T6U7V8', 'VIP', '2', 'EVT2-VIP2-5000', 5000.00, 2, 1, 'disponible'),
('W9X0Y1Z2', 'VIP', '3', 'EVT2-VIP3-5000', 5000.00, 2, 1, 'disponible'),
('A3B4C5D6', 'VIP', '4', 'EVT2-VIP4-5000', 5000.00, 2, 1, 'disponible'),
('E7F8G9H0', 'VIP', '5', 'EVT2-VIP5-5000', 5000.00, 2, 1, 'disponible'),
('I1J2K3L4', 'General', '1', 'EVT2-G1-1000', 1000.00, 2, 1, 'disponible'),
('M5N6O7P8', 'General', '2', 'EVT2-G2-1000', 1000.00, 2, 1, 'disponible'),
('Q9R0S1T2', 'General', '3', 'EVT2-G3-1000', 1000.00, 2, 1, 'disponible'),
('U3V4W5X6', 'General', '4', 'EVT2-G4-1000', 1000.00, 2, 1, 'disponible'),
('Y7Z8A9B0', 'General', '5', 'EVT2-G5-1000', 1000.00, 2, 1, 'disponible');


INSERT INTO Boletos (num_serie, fila, asiento, num_control, precio_original, evento_id, persona_id, estado) VALUES
('C1D2E3F4', 'A', '1', 'EVT3-A1-3500', 3500.00, 3, 1, 'disponible'),
('G5H6I7J8', 'A', '2', 'EVT3-A2-3500', 3500.00, 3, 1, 'disponible'),
('K9L0M1N2', 'A', '3', 'EVT3-A3-3500', 3500.00, 3, 1, 'disponible'),
('O3P4Q5R6', 'A', '4', 'EVT3-A4-3500', 3500.00, 3, 1, 'disponible'),
('S7T8U9V0', 'A', '5', 'EVT3-A5-3500', 3500.00, 3, 1, 'disponible'),
('W1X2Y3Z4', 'B', '1', 'EVT3-B1-3500', 3500.00, 3, 1, 'disponible'),
('A5B6C7D8', 'B', '2', 'EVT3-B2-3500', 3500.00, 3, 1, 'disponible'),
('E9F0G1H2', 'B', '3', 'EVT3-B3-3500', 3500.00, 3, 1, 'disponible'),
('I3J4K5L6', 'B', '4', 'EVT3-B4-3500', 3500.00, 3, 1, 'disponible'),
('M7N8O9P0', 'B', '5', 'EVT3-B5-3500', 3500.00, 3, 1, 'disponible');


UPDATE Boletos SET persona_id = 2 WHERE boleto_id IN (1, 2);


UPDATE Boletos SET persona_id = 3 WHERE boleto_id IN (11, 12);


UPDATE Boletos SET persona_id = 4 WHERE boleto_id IN (21, 22);


UPDATE Boletos SET persona_id = 5 WHERE boleto_id IN (3, 4);

UPDATE Boletos SET persona_id = 6 WHERE boleto_id IN (13, 14);




UPDATE Personas SET total_boletos = (
    SELECT COUNT(*) FROM Boletos WHERE persona_id = Personas.persona_id
);





UPDATE Boletos 
SET persona_id = 2, estado = 'vendido' 
WHERE boleto_id IN (1, 2);


UPDATE Boletos 
SET persona_id = 3, estado = 'vendido' 
WHERE boleto_id IN (11, 12);


UPDATE Boletos 
SET persona_id = 4, estado = 'vendido' 
WHERE boleto_id IN (21, 22);


UPDATE Boletos 
SET persona_id = 5, estado = 'vendido' 
WHERE boleto_id IN (3, 4);


UPDATE Boletos 
SET persona_id = 6, estado = 'vendido' 
WHERE boleto_id IN (13, 14);







UPDATE Eventos SET total_boletos = (
    SELECT COUNT(*) FROM Boletos WHERE evento_id = Eventos.evento_id
);
/*
SELECT b.num_Serie FROM boletos AS b
INNER JOIN
eventos AS e ON b.evento_id = e.evento_id
WHERE e.recinto = "foro sol";


SELECT * FROM Personas;
SELECT * FROM Transacciones;
SELECT * FROM transacciones_boletos;
SELECT * FROM boletos;
SELECT * FROM eventos;
SELECT * FROM reventas;
*/



