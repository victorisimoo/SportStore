-----	|@uthor: Victor Noé Hernández Meléndez|	-----
-----	|Creación de base de datos: DBApplicationSportStore2013241	|-----
CREATE DATABASE DBApplicationSportStore2013241
GO

-----	|Uso de la base de datos|	-----
USE DBApplicationSportStore2013241
GO

-----	|Creacion de tabla Categorias|	------
	CREATE TABLE Categorias
	(
		CodigoCategoria int identity (1, 1) primary key,
		DescripcionCategoria varchar (256) NOT NULL,
	);
	GO

-----	|Creacion de tabla Marcas|	-----
	CREATE TABLE Marcas
	(
		CodigoMarca int identity (1, 1) primary key,
		DescripcionMarca varchar (256) NOT NULL,
	);
	GO

-----	|Creacion de tabla Tallas|	-----
	CREATE TABLE Tallas
	(
		CodigoTalla int identity (1, 1) primary key,
		DescripcionTalla varchar (256) NOT NULL,
	);
	GO

----	|Creación de tablas para la base de datos|	-----
	CREATE TABLE Productos 
	(
		CodigoProducto int identity (1,1) primary key,
		PrecioUnitario decimal (10,2),
		DescripcionProducto varchar (256),
		PrecioDocena decimal (10, 2), 
		PrecioPorMayor decimal (10, 2), 
		ExistenciaProducto int default 0.00,
		CodigoCategoria int,
		CodigoMarca int,
		CodigoTalla int, 
	);
	GO
	
-----	|Alteración de la tabla Productos|	-----
	alter table Productos add constraint FK_Productos_Categorias foreign key (CodigoCategoria) references Categorias (CodigoCategoria)
	GO

	alter table Productos add constraint FK_Productos_Marcas foreign key (CodigoMarca) references Marcas (CodigoMarca)
	GO

	alter table Productos add constraint FK_Productos_Tallas foreign key (CodigoTalla) references Tallas (CodigoTalla)
	GO

	alter table Productos add Imagen varchar (256) NOT NULL
	GO
	

-----	|Creacion de tabla Proveedores|	-----
	CREATE TABLE Proveedores
	(
		CodigoProveedor int identity (1, 1) primary key,
		RazonSocial varchar (256) NOT NULL,
		NitProveedor varchar (256) NOT NULL,
		DireccionProveedor varchar (256) NOT NULL,
		PaginaWeb varchar (256) NOT NULL,
		ContactoPrincipal varchar (256) NOT NULL,
	);
	GO

-----	|Creacion de tabla Clientes|	-----
	CREATE TABLE Clientes
	(
		CodigoCliente int identity (1, 1) primary key,
		NombreCliente varchar (256) NOT NULL,
		NitCliente varchar (256),
		DireccionCliente varchar (256),
	);
	GO

-----	|Creacion de tabla TelefonoProveedores|	-----
	CREATE TABLE TelefonoProveedores
	(
		CodigoTelefonoProveedor int identity (1, 1) primary key,
		NumeroTelefono varchar (256) NOT NULL,
		DescripcionTelefono varchar (256) NOT NULL,
		CodigoProveedor int,
	);
	GO

-----	|Alteración de la tabla TelefonoProveedores|	-----
	alter table TelefonoProveedores add constraint FK_TelefonoProveedores_Proveedores foreign key (CodigoProveedor) references Proveedores (CodigoProveedor)
	GO

-----	|Creacion de tabla EmailProveedores|	-----
	CREATE TABLE EmailProveedores
	(
		CodigoEmailProveedor int identity (1, 1) primary key,
		DescripcionEmail varchar (256) NOT NULL,
		EmailProveedor varchar (256) NOT NULL,
		CodigoProveedor int,
	);
	GO

-----	|Alteración de la tabla EmailProveedores|	-----
	alter table EmailProveedores add constraint FK_EmailProveedores_Proveedores foreign key (CodigoProveedor) references Proveedores (CodigoProveedor)
	GO
	
-----	|Creacion de tabla Compras|	-----
	CREATE TABLE Compras
	(
		NumeroDocumento int identity (1500, 1) primary key,
		DescripcionCompra varchar (256) NOT NULL,
		FechaCompra date NOT NULL,
		TotalCompra decimal (10, 2) NOT NULL,
		CodigoProveedor int,
	);
	GO

-----	|Alteración de la tabla TelefonoProveedores|	-----
	alter table Compras add constraint FK_Compras_Proveedores foreign key (CodigoProveedor) references Proveedores (CodigoProveedor)
	GO

-----	|Creacion de tabla DetalleCompra|	-----
	CREATE TABLE DetalleCompras
	(
		CodigoDetalleCompra int identity (1, 1) primary key,
		NumeroDocumento int,
		CostoProducto decimal (10, 2), 
		CostoUnitario decimal (10, 2), 
		Cantidad int NOT NULL,
		CodigoProducto int,
	);
	GO
-----	|Alteración de la tabla DetalleCompras|	-----
	alter table DetalleCompras add constraint FK_DetalleCompras_Productos foreign key (CodigoProducto) references Productos (CodigoProducto)
	GO
	
	alter table DetalleCompras add constraint FK_DetalleCompras_Compras foreign key (NumeroDocumento) references Compras (NumeroDocumento)
	GO

-----	|Creacion de tabla TelefonoClientes|	-----
	CREATE TABLE TelefonoClientes
	(
		CodigoTelefonoCliente int identity (1, 1) primary key,
		DescripcionTelefonoCliente varchar (256) NOT NULL,
		NumeroCliente varchar (256) NOT NULL,
		CodigoCliente int,
	);
	GO

-----	|Alteración de la tabla TelefonoClientes|	-----
	alter table TelefonoClientes add constraint FK_TelefonoClientes_Clientes foreign key (CodigoCliente) references Clientes (CodigoCliente)
	GO

-----	|Creacion de tabla EmailClientes|	-----
	CREATE TABLE EmailClientes
	(
		CodigoEmailCliente int identity (1, 1) primary key,
		EmailCliente varchar (256) NOT NULL,
		DescripcionEmailCliente varchar (256) NOT NULL,
		CodigoCliente int,
	);
	GO

-----	|Alteración de la tabla DetalleCompras|	-----
	alter table EmailClientes add constraint FK_EmailClientes_Clientes foreign key (CodigoCliente) references Clientes (CodigoCliente)
	GO

-----	|Creacion de tabla Facturas|	-----
	CREATE TABLE Facturas 
	(
		NumeroFactura int identity (1, 1) primary key,
		FechaFactura date NOT NULL,
		NitFactura varchar (256) NOT NULL,
		EstadoFactura varchar (256) NOT NULL,
		TotalFactura decimal (10, 2) NOT NULL,
		CodigoCliente int,
	);
	GO
	

-----	|Alteración de la tabla DetalleCompras|	-----
	alter table Facturas add constraint FK_Facturas_Clientes foreign key (CodigoCliente) references Clientes (CodigoCliente)
	GO

-----	|Creacion de tabla DetalleFacturas|	-----
	CREATE TABLE DetalleFacturas
	(
		CodigoDetalleFactura int identity (1, 1) primary key,
		Precio decimal (10, 2) NOT NULL,
		Cantidad int NOT NULL,
		CodigoProducto int,
		NumeroFactura int,
	);
	GO

-----	|Alteración de la tabla DetalleCompras|	-----
	alter table DetalleFacturas add constraint FK_DetalleFacturas_Facturas foreign key (NumeroFactura) references Facturas (NumeroFactura)
	GO
	alter table DetalleFacturas add constraint FK_DetalleFacturas_Productos foreign key (CodigoProducto) references Productos (CodigoProducto)
	GO
	alter table DetalleFacturas alter column Precio decimal (10, 2)
	GO

-----	|Creación de tabla AgregarUsuario|	-----
	CREATE TABLE AgregarUsuarios
	(
		CodigoUsuario int identity (1, 1) primary key,
		NombreUsuario varchar (256) NOT NULL,
		Usuario varchar (256) NOT NULL,
		EmailUsuario varchar (256) NOT NULL,
		TipoUsuario varchar (256) NOT NULL,
		ContrasenaUsuario varchar (256) NOT NULL,
	);
	GO
