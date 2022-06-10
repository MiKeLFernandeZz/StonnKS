DROP DATABASE IF EXISTS StonnKS;
CREATE DATABASE StonnKS;

USE StonnKS;

-- [1] ---------------------------------------------------------------------------------------------||			EMPRESA
CREATE TABLE Empresa(
	NIF 			 CHAR(9)									,
	nombre			 VARCHAR(20)		NOT NULL 				,
	descripcion		 VARCHAR(175)								,
	direccion		 VARCHAR(120)								,
	correo			 VARCHAR(40)								,
	telefono		 CHAR(15)									,
	estado			 VARCHAR(15)								
);
-- 	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	
ALTER TABLE Empresa
	ADD CONSTRAINT PK_Empresa_NIF PRIMARY KEY (NIF);

-- [2] ---------------------------------------------------------------------------------------------||			CALENDARIO
CREATE TABLE Calendario(
	CalendarioID	 TINYINT			UNSIGNED 				,
	descripcion		 VARCHAR(175)								,
	temporada		 CHAR(9)									
);
-- 	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	
ALTER TABLE Calendario
	ADD CONSTRAINT PK_Calendario_CalendarioID PRIMARY KEY (CalendarioID);

-- [3] ---------------------------------------------------------------------------------------------||			DEPARTAMENTO
CREATE TABLE Departamento(
	DepartamentoID	 SMALLINT			UNSIGNED				,
	nombre			 VARCHAR(20)		NOT NULL 				,
	descripcion		 VARCHAR(175)								,
	EmpresaNIF		 CHAR(9)									,
	CalendarioID	 TINYINT			UNSIGNED 						
);
-- 	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	
ALTER TABLE Departamento
	ADD CONSTRAINT PK_Departamento_DepartamentoID PRIMARY KEY (DepartamentoID),
    ADD CONSTRAINT FK_Departamento_CalendarioID FOREIGN KEY (CalendarioID)
		REFERENCES Calendario (CalendarioID),
    ADD CONSTRAINT FK_Departamento_EmpresaNIF FOREIGN KEY (EmpresaNIF)
		REFERENCES Empresa (NIF);

-- [4] ---------------------------------------------------------------------------------------------||			PROYECTO
CREATE TABLE Proyecto(
	ProyectoID		 SMALLINT			UNSIGNED 				,
	descripcion		 VARCHAR(175)		NOT NULL 				,
	estado			 VARCHAR(15)								,
	EmpresaNIF		 CHAR(9)											
);
-- 	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	
ALTER TABLE Proyecto
	ADD CONSTRAINT PK_Proyecto_ProyectoID PRIMARY KEY (ProyectoID),
    ADD CONSTRAINT FK_Proyecto_EmpresaNIF FOREIGN KEY (EmpresaNIF)
		REFERENCES Empresa (NIF);

-- [5] ---------------------------------------------------------------------------------------------||			PARTICIPA			||DEPARTAMENTO-PROYECTO			
CREATE TABLE Participa(
	DepartamentoID		 SMALLINT		UNSIGNED 				,
	ProyectoID			 SMALLINT		UNSIGNED 						
);
-- 	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	
ALTER TABLE Participa
	ADD CONSTRAINT FK_Participa_DepartamentoID FOREIGN KEY (DepartamentoID)
		REFERENCES Departamento (DepartamentoID),
    ADD CONSTRAINT FK_Participa_ProyectoID FOREIGN KEY (ProyectoID)
		REFERENCES Proyecto (ProyectoID);

-- [6] ---------------------------------------------------------------------------------------------||			ACTIVIDAD
CREATE TABLE Actividad(
	ActividadID		 SMALLINT			UNSIGNED 				,
	Act_NFC			 CHAR(16)			NOT NULL 				,
	descripcion		 VARCHAR(175)		NOT NULL 				,
	estado			 VARCHAR(15)								,
	ProyectoID		 SMALLINT			UNSIGNED 						
);
-- 	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	
ALTER TABLE Actividad
	ADD CONSTRAINT PK_Actividad_ActividadID PRIMARY KEY (ActividadID),
    ADD CONSTRAINT FK_Actividad_ProyectoID FOREIGN KEY (ProyectoID)
		REFERENCES Proyecto (ProyectoID);

-- [7] ---------------------------------------------------------------------------------------------||			TRABAJADOR
CREATE TABLE Trabajador(
	TrabajadorID	 SMALLINT			UNSIGNED 				,
	NFC_ID			 CHAR(16)			NOT NULL 				,
	puesto			 VARCHAR(130)		NOT NULL 				,
	nombre			 VARCHAR(30)		NOT NULL 				,
	apellidos		 VARCHAR(100)		NOT NULL 				,
	salario			 FLOAT				NOT NULL 				,
	comision		 FLOAT										,
	DNI				 CHAR(9)									,
	fecha_nacimiento DATE										,
	telefono		 CHAR(15)									,
	direccion		 VARCHAR(120)								,
	correo			 VARCHAR(40)								,
	fecha_entrada	 DATE				NOT NULL 				,
	fecha_salida	 DATE										,
	jefe			 SMALLINT			UNSIGNED 				,
	DepartamentoID	 SMALLINT			UNSIGNED 						
);
-- 	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	
ALTER TABLE Trabajador
	ADD CONSTRAINT PK_Trabajador_TrabajadorID PRIMARY KEY (TrabajadorID),
    ADD CONSTRAINT FK_Trabajador_DepartamentoID FOREIGN KEY (DepartamentoID)
		REFERENCES Departamento (DepartamentoID);
ALTER TABLE Trabajador
	ADD CONSTRAINT FK_Trabajador_jefe FOREIGN KEY (jefe)
		REFERENCES Trabajador (TrabajadorID);

-- [8] ---------------------------------------------------------------------------------------------||			JORNADA
CREATE TABLE Jornada(
	JornadaID			 INT			UNSIGNED 				,
	Fecha_Hora_entrada	 DATETIME								,
	Fecha_Hora_salida	 DATETIME								,
    TrabajadorID	 SMALLINT			UNSIGNED 				
);
-- 	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	
ALTER TABLE Jornada
	ADD CONSTRAINT PK_Jornada_JornadaID PRIMARY KEY (JornadaID),
	ADD CONSTRAINT FK_Jornada_TrabajadorID FOREIGN KEY (TrabajadorID)
		REFERENCES Trabajador (TrabajadorID);

-- [9] ---------------------------------------------------------------------------------------------||			PARTE ACTIVIDAD			||ACTIVIDAD-JORNADA
CREATE TABLE ParteActividad(
	ActividadID			 SMALLINT		UNSIGNED 				,
	JornadaID			 INT			UNSIGNED 				,
	Hora_Inicio			 TIME									,
	Hora_Fin			 TIME									
);
-- 	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	
ALTER TABLE ParteActividad
	ADD CONSTRAINT PK_ParteActividad_Hora_Inicio PRIMARY KEY (Hora_Inicio),
	ADD CONSTRAINT FK_ParteActividad_JornadaID FOREIGN KEY (JornadaID)
		REFERENCES Jornada (JornadaID),
    ADD CONSTRAINT FK_ParteActividad_ActividadID FOREIGN KEY (ActividadID)
		REFERENCES Actividad (ActividadID);

-- [10] ---------------------------------------------------------------------------------------------||			JUSTIFICANTE
CREATE TABLE Justificante(
	JustificanteID	 SMALLINT			UNSIGNED 				,
	motivo			 VARCHAR(150)		NOT NULL 				,
	fecha			 DATE				NOT NULL 				,
	validez			 DATE				NOT NULL 				,
	TrabajadorID	 SMALLINT			UNSIGNED 						
);
-- 	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	
ALTER TABLE Justificante
	ADD CONSTRAINT PK_Justificante_JustificanteID PRIMARY KEY (JustificanteID),
	ADD CONSTRAINT FK_Justificante_TrabajadorID FOREIGN KEY (TrabajadorID)
		REFERENCES Trabajador (TrabajadorID);

-- [11] ---------------------------------------------------------------------------------------------||			TIPO
CREATE TABLE Tipo(
	TipoID			 TINYINT			UNSIGNED 				,
	descripcion		 VARCHAR(175)		NOT NULL 				,
	tiempo			 TINYINT			NOT NULL 						
);
-- 	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	
ALTER TABLE Tipo
	ADD CONSTRAINT PK_Tipo_TipoID PRIMARY KEY (TipoID);

-- [12] ---------------------------------------------------------------------------------------------||			DESCANSO
CREATE TABLE Descanso(
	DescansoID		 SMALLINT			UNSIGNED 				,
	HoraComienzo	 TIME				NOT NULL 				,
	HoraFinal		 TIME										,
	JornadaID		 INT				UNSIGNED 				,
	TipoID			 TINYINT			UNSIGNED 						
);
-- 	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	
ALTER TABLE Descanso
	ADD CONSTRAINT PK_Descanso_DescansoID PRIMARY KEY (DescansoID),
	ADD CONSTRAINT FK_Descanso_JornadaID FOREIGN KEY (JornadaID)
		REFERENCES Jornada (JornadaID),
    ADD CONSTRAINT FK_Descanso_TipoID FOREIGN KEY (TipoID)
		REFERENCES Tipo (TipoID);

-- [13] ---------------------------------------------------------------------------------------------||			DIA
CREATE TABLE Dia(
	DiaID			 SMALLINT			UNSIGNED				,
    fecha			 DATE				NOT NULL				,
	Hora_Entrada	 TIME										,
	Hora_Salida		 TIME										,
	motivo			 VARCHAR(120)									
);
-- 	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	
ALTER TABLE Dia
	ADD CONSTRAINT PK_Dia_DiaID PRIMARY KEY (DiaID);

-- [14] ---------------------------------------------------------------------------------------------||			CORRESPONDE			||	CALENDARIO-DIA
CREATE TABLE Corresponde(
	CalendarioID	 TINYINT			UNSIGNED 				,
	DiaID			 SMALLINT			UNSIGNED				
);
-- 	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	|	
ALTER TABLE Corresponde
	ADD CONSTRAINT FK_Corresponde_CalendarioID FOREIGN KEY (CalendarioID)
		REFERENCES Calendario (CalendarioID),
    ADD CONSTRAINT FK_Corresponde_DiaID FOREIGN KEY (DiaID)
		REFERENCES Dia (DiaID);

