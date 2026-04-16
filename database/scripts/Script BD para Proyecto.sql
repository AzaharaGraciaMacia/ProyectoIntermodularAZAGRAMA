SET FOREIGN_KEY_CHECKS = 0; -- Esto lo tuve que buscar, es para que se pueda usar el script varias veces y no de error con las claves ajenas al reemplazar las tablas de origen. "Apaga" el check de las fk. 

CREATE DATABASE IF NOT EXISTS ProyIntUD5AzaharaGracia;

USE ProyIntUD5AzaharaGracia;

CREATE OR REPLACE TABLE usuario (
idUsuario INT UNSIGNED AUTO_INCREMENT,
nombre VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL,
fechaNacimiento DATE NOT NULL,
tipoUsuario ENUM('administrador', 'normal') NOT NULL,
contrasena VARCHAR(100) NOT NULL,
dni VARCHAR(9) NOT NULL,
fechaRegistro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT PK_USU PRIMARY KEY (idUsuario), -- En teoría a las restricciones de PK no hace falta darles un nombre, el sistema los ignora, pero como en los apuntes aparecía, se lo he dado.
CONSTRAINT UK_USU_CE UNIQUE KEY (email),
CONSTRAINT UK_USU_DNI UNIQUE KEY (dni)
);

CREATE OR REPLACE TABLE administrador (
idUsuario INT UNSIGNED,
telefonoGuardia VARCHAR(20) NOT NULL,
CONSTRAINT PK_ADM PRIMARY KEY (idUsuario),
CONSTRAINT FK_ID_ADM FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario)
		ON DELETE CASCADE
        ON UPDATE CASCADE  -- Aquí nos interesa que cualquier modificación o borrado se propaguen.
);

CREATE OR REPLACE TABLE usuarioNormal (
idUsuario INT UNSIGNED,
direccion VARCHAR(200),
telefonoMovil VARCHAR(20),
fotografia VARCHAR(255),
CONSTRAINT PK_USUNOR PRIMARY KEY (idUsuario),
CONSTRAINT FK_ID_USU_NOR FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario)
		ON DELETE CASCADE
        ON UPDATE CASCADE -- Exactamente igual, interesa que se propague. Dudaba con el borrado, pero las actualizaciones sí o sí.
);

CREATE OR REPLACE TABLE recurso (
idRecurso VARCHAR(10),
nombre VARCHAR(100) NOT NULL,
descripcion TEXT NOT NULL,
ubicacion VARCHAR(200) NOT NULL,
capacidad INT NOT NULL ,
CONSTRAINT PK_REC PRIMARY KEY (idRecurso)
);

CREATE OR REPLACE TABLE horario (
idHorario INT  UNSIGNED AUTO_INCREMENT, 
diaSemana ENUM('lunes','martes','miercoles','jueves','viernes','sabado','domingo') NOT NULL,
horaInicio TIME NOT NULL,
horaFin TIME NOT NULL,
CONSTRAINT PK_HOR PRIMARY KEY (idHorario),
CONSTRAINT HOR_TIME CHECK (horaFin>horaInicio)
);

CREATE OR REPLACE TABLE disponibleEn (
idRecurso VARCHAR(10), 
idHorario INT UNSIGNED, 
CONSTRAINT PK_DISP PRIMARY KEY (idRecurso,idHorario),
CONSTRAINT FK_ID_REC_DISP FOREIGN KEY (idRecurso) REFERENCES recurso(idRecurso)
		ON DELETE CASCADE
        ON UPDATE CASCADE ,
CONSTRAINT FK_ID_HOR_DISP FOREIGN KEY (idHorario) REFERENCES horario(idHorario)
		ON DELETE CASCADE
        ON UPDATE CASCADE -- Cualquier modificación o borrado en recursos y horarios se propagarán a esta tabla, me parece lo más lógico, el recurso no estará disponible si cualquiera de las dos se borra o tendrá diferente disponibilidad si se modifican.
);

CREATE OR REPLACE TABLE reserva (
idRecurso VARCHAR(10),
idReservaLocal INT UNSIGNED AUTO_INCREMENT, 
idUsuario INT UNSIGNED NOT NULL,
fecha DATE NOT NULL,
horaInicio TIME NOT NULL,
horaFin TIME NOT NULL,
coste DECIMAL (10,2) DEFAULT 0.00 NOT NULL,
numeroPlazas INT NOT NULL,
motivo TEXT DEFAULT 'Cumpleaños' NOT NULL , -- Probando el default.
observaciones TEXT,
CONSTRAINT PK_RESERVA PRIMARY KEY (idReservaLocal),
CONSTRAINT FK_ID_REC_RES FOREIGN KEY (idRecurso) REFERENCES recurso(idRecurso)
		ON DELETE NO ACTION
        ON UPDATE CASCADE,
CONSTRAINT FK_ID_USUNOR FOREIGN KEY (idUsuario) REFERENCES usuarioNormal(idUsuario)
		ON DELETE NO ACTION 
        ON UPDATE CASCADE, -- En este caso, he creído conveniente que las modificaciones se propaguen pero los borrados no, ya que entiendo que se querrá guardar un registro de las reservas pasadas aunque el usuario se borre.
CONSTRAINT RES_TIME CHECK (horaFin>horaInicio)
);

SET FOREIGN_KEY_CHECKS = 1; -- Vuelvo a "encender" el check de las fk para que haga las comprobaciones después de crear o reemplazar todas las tablas.

INSERT INTO usuario (email,contrasena,nombre,fechaNacimiento,tipoUsuario,dni) VALUES 
('azagramac@alu.edu.gva.es', 'DaWNoviembre2025!', 'Azahara Gracia Maciá', '1989-01-14', 'Administrador', '74411130F'),
('jorge.garcia@gmail.com', 'secreto123', 'Jorge García Pérez', '1992-08-23', 'Normal', '74411131F'),
('lucia.perez@hotmail.com', 'lucia_99', 'Lucía Pérez García', '1999-11-05', 'Normal', '74411132F'),
('roberto.sanz@yahoo.es', 'futbol2024', 'Roberto Sanz Domínguez', '2001-02-14', 'Normal', '74411133F'),
('elena.dominguez@outlook.com', 'claveSegura.5', 'Elena Domínguez Sanz', '1978-06-30', 'Administrador', '74411134F'),
('admin@centro.com', 'AdminPass2024!', 'Laura Martínez Jiménez', '1985-04-12', 'Administrador', '74411135F'),
('shadowfiltro@gmail.com', 'StrangerPass2025!', 'Víctor Manuel González Mira', '1987-06-06', 'Administrador', '74411136F'),
('persona@centro.com', 'sinIdeas.1', 'Elena Martínez Díaz', '1988-12-14', 'Administrador', '74411137F'),
('fulanito@gmail.com', 'sinIdeas.2', 'Fulanito García García', '2000-05-15', 'Normal', '74411138F'),
('menganito@gmail.com', 'sinIdeas.3!', 'Menganito González González', '1999-11-23', 'Normal', '74411139F');

INSERT INTO administrador (idUsuario,telefonoGuardia) VALUES 
(1, '695439149'),
(5, '605385316'),
(6, '765846596'),
(7, '644373777'),
(8, '649528752');

INSERT INTO usuarionormal (idUsuario,direccion,telefonoMovil,fotografia) VALUES 
(2,'Calle inventada 1, Elche','679528463','www.facebook.com/foto'),
(3,'Calle inventada 2, Elche',NULL,NULL),
(4,NULL,'600000000','www.facebook.com/foto2'),
(9,'Calle inventada 3, Elche','651358963',NULL),
(10,NULL,'679528463',NULL);

INSERT INTO recurso (idRecurso,nombre,descripcion,ubicacion,capacidad) VALUES 
('1F','Pista de Hielo','Pista para patinaje sobre hielo','Elche',50),
('2G','Pista de Pádel','Pista para jugar al pádel','Alicante', 4),
('3F','Karts','Circuito de karting','Elche', 10),
('4G','Escape Room','Escape room de temática x', 'Alicante', 8),
('5F','Salón Nupcial','Salón para celebración de bodas', 'Elche', 250);

INSERT INTO horario (diaSemana,horaInicio,horaFin) VALUES 
('Martes','18:00','20:00'),
('Viernes','17:00','21:00'),
('Sábado','20:00','23:59'),
('Domingo','10:00','13:30'),
('Sábado','10:00','12:00');

INSERT INTO disponibleen (idRecurso,idHorario) VALUES 
('1F',2),
('2G',1),
('3F',4),
('4G',5),
('5F',3);

INSERT INTO reserva (idRecurso,idUsuario,fecha,horaInicio,horaFin,coste,numeroPlazas,motivo,observaciones) VALUES
('5F',2,'2025-12-06','20:00','23:59',25380.57,100,'Boda','Alergia a cacahuetes'),
('2G',3,'2025-12-02','18:00','19:00',13.50,2,'Partido',NULL),
('1F',4,'2025-12-05','18:00','19:00',20.24,3,'Fiesta de empresa',NULL),
('3F',9,'2025-12-07','10:00','13:30',351.26,10,'Despedida de soltero','Regalo para el novio'),
('4G',10,'2025-12-13','10:00','11:00',240.00,8,'Cumpleaños infantil','Tarta casera');

INSERT INTO reserva (idRecurso,idUsuario,fecha,horaInicio,horaFin,coste,numeroPlazas,observaciones) VALUES 
('2G',10,'2025-12-9','18:00','20:00',35.30,4,'Dobles'); -- Probando el default y de paso comprobando que se pueda repetir usuario.

	
