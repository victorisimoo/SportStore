
-----	|@uthor: Victor Noé Hernández Meléndez|	-----
-----	|Creación de procesos almacenados para DBAplicacionSport2013241|	-----

-----	|Uso de la base de datos|	-----
USE DBApplicationSportStore2013241
GO

-----	|TABLA PRODUCTOS|	-----
-----	|Proceso almacenado "AGREGAR PRODUCTO"|	-----
	CREATE PROCEDURE sp_AgregarProducto @DescripcionProducto varchar (256), @ExistenciaProducto int, @CodigoCategoria int, @CodigoMarca int, @CodigoTalla int, @Imagen varchar (256)
	AS
	BEGIN
		INSERT INTO Productos (DescripcionProducto, ExistenciaProducto, CodigoCategoria, CodigoMarca, CodigoTalla, Imagen) VALUES (@DescripcionProducto, @ExistenciaProducto, @CodigoCategoria, @CodigoMarca, @CodigoTalla, @Imagen)
	END
	GO

-----	|Proceso almacenado para "ELIMINAR PRODUCTO"|	-----
	CREATE PROCEDURE sp_EliminarProducto @CodigoProducto int
	AS
	BEGIN
		DELETE FROM Productos WHERE CodigoProducto = @CodigoProducto
	END 
	GO

-----	|proceso almacenado para "MODIFICAR PRODUCTO"|	-----
	CREATE PROCEDURE sp_ModificarProducto @CodigoProducto int, @DescripcionProducto varchar (256), @ExistenciaProducto int, @CodigoCategoria int, @CodigoMarca int, @CodigoTalla int, @Imagen varchar (256)
	AS
	BEGIN
		UPDATE Productos SET DescripcionProducto = @DescripcionProducto, ExistenciaProducto = @ExistenciaProducto, CodigoCategoria = @CodigoCategoria, CodigoMarca = @CodigoMarca, CodigoTalla = @CodigoTalla, Imagen = @Imagen WHERE CodigoProducto = @CodigoProducto
	END
	GO

-----	|proceso almacenado para "LISTAR PRODUCTO"|	-----
	CREATE PROCEDURE sp_ListarProducto  
	AS
	BEGIN
		SELECT  Productos.CodigoProducto, Productos.PrecioUnitario, Productos.DescripcionProducto, Productos.PrecioDocena, Productos.PrecioPorMayor, Productos.ExistenciaProducto, Productos.CodigoCategoria, Productos.CodigoMarca, Productos.CodigoTalla, Productos.Imagen FROM Productos
	END
	GO 
	
-----	|proceso almacenado para "LISTAR PRODUCTOS"|	-----
	CREATE PROCEDURE sp_ListarProductos @CodigoProducto int 
	AS
	BEGIN
		SELECT  Productos.CodigoProducto, Productos.PrecioUnitario, Productos.DescripcionProducto, Productos.PrecioDocena, Productos.PrecioPorMayor, Productos.ExistenciaProducto, Productos.CodigoCategoria, Productos.CodigoMarca, Productos.CodigoTalla, Productos.Imagen FROM Productos WHERE Productos.CodigoProducto = @CodigoProducto
	END
	GO

-----	|proceso almacenado para "BUSCAR PRODUCTO"|	-----

	CREATE PROCEDURE sp_BuscarProducto @CodigoProducto int
	AS
	BEGIN
		SELECT CodigoProducto, PrecioUnitario, DescripcionProducto, PrecioDocena, PrecioPorMayor, ExistenciaProducto, CodigoCategoria, CodigoMarca, CodigoTalla, Imagen  FROM Productos WHERE CodigoProducto = @CodigoProducto
	END
	GO


-----------------------------------------------------------------------------------------------------------------------------------------------------
-----	|Tabla CATEGORIAS|	-----
-----	|Proceso almacenado "AGREGAR CATEGORIA"|	-----
	CREATE PROCEDURE sp_AgregarCategoria @DescripcionCategoria varchar (256)
	AS
	BEGIN
		INSERT INTO Categorias (DescripcionCategoria) VALUES (@DescripcionCategoria)
	END
	GO

-----	|Proceso almacenado para "ELIMINAR CATEGORIA"|	-----
	CREATE PROCEDURE sp_EliminarCategoria @CodigoCategoria int
	AS
	BEGIN
		DELETE FROM Categorias WHERE CodigoCategoria = @CodigoCategoria
	END 
	GO

-----	|proceso almacenado para "MODIFICAR CATEGORIA"|	-----
	CREATE PROCEDURE sp_ModificarCategoria @CodigoCategoria int, @DescripcionCategoria varchar (256)
	AS
	BEGIN
		UPDATE Categorias SET DescripcionCategoria = @DescripcionCategoria WHERE CodigoCategoria = @CodigoCategoria
	END
	GO
	
-----	|proceso almacenado para "LISTAR CATEGORIA"|	-----
	CREATE PROCEDURE sp_ListarCategorias  
	AS
	BEGIN
		SELECT Categorias.CodigoCategoria, Categorias.DescripcionCategoria FROM Categorias
	END 
	GO

-----	|proceso almacenado para "LISTAR CATEGORIAS"|	-----
	CREATE PROCEDURE sp_ListarCategoria @CodigoCategoria int 
	AS
	BEGIN
		SELECT Categorias.CodigoCategoria, Categorias.DescripcionCategoria FROM Categorias WHERE CodigoCategoria = @CodigoCategoria
	END
	GO

-----	|proceso almacenado para "BUSCAR CATEGORIA"|	-----
	CREATE PROCEDURE sp_BuscarCategoria @CodigoCategoria int
	AS
	BEGIN
		SELECT CodigoCategoria, DescripcionCategoria FROM Categorias WHERE CodigoCategoria = @CodigoCategoria
	END
	GO


-------------------------------------------------------------------------------------------------------------------------------------------------------
-----	|Tabla MARCAS|	-----
-----	|Proceso almacenado "AGREGAR MARCA"|	-----
	CREATE PROCEDURE sp_AgregarMarcas @DescripcionMarca varchar (256)
	AS
	BEGIN
		INSERT INTO Marcas (DescripcionMarca) VALUES (@DescripcionMarca)
	END
	GO

-----	|Proceso almacenado para "ELIMINAR MARCA"|	-----
	CREATE PROCEDURE sp_EliminarMarca @CodigoMarca int
	AS
	BEGIN
		DELETE FROM Marcas WHERE CodigoMarca = @CodigoMarca
	END 
	GO

-----	|proceso almacenado para "MODIFICAR MARCA"|	-----
	CREATE PROCEDURE sp_ModificarMarca @CodigoMarca int, @DescripcionMarca varchar (256)
	AS
	BEGIN
		UPDATE Marcas SET DescripcionMarca = @DescripcionMarca WHERE CodigoMarca = @CodigoMarca
	END
	GO

-----	|proceso almacenado para "LISTAR MARCA"|	-----
	CREATE PROCEDURE sp_ListarMarca  
	AS
	BEGIN
		SELECT Marcas.CodigoMarca, Marcas.DescripcionMarca FROM Marcas
	END 
	GO

-----	|proceso almacenado para "LISTAR MARCAS"|	-----
	CREATE PROCEDURE sp_ListarMarcas  @CodigoMarca int 
	AS
	BEGIN
		SELECT Marcas.CodigoMarca, Marcas.DescripcionMarca FROM Marcas WHERE CodigoMarca = @CodigoMarca
	END 
	GO

-----	|proceso almacenado para "BUSCAR MARCA"|	-----
	CREATE PROCEDURE sp_BuscarMarca @CodigoMarca int
	AS
	BEGIN
		SELECT CodigoMarca, DescripcionMarca FROM Marcas WHERE CodigoMarca = @CodigoMarca
	END
	GO


-------------------------------------------------------------------------------------------------------------------------------------------------------
-----	|Tabla TALLAS|	-----
-----	|Proceso almacenado "AGREGAR TALLAS"|	-----
	CREATE PROCEDURE sp_AgregarTalla @DescripcionTalla varchar (256)
	AS
	BEGIN
		INSERT INTO Tallas (DescripcionTalla) VALUES (@DescripcionTalla)
	END
	GO

-----	|Proceso almacenado para "ELIMINAR TALLA"|	-----
	CREATE PROCEDURE sp_EliminarTalla @CodigoTalla int
	AS
	BEGIN
		DELETE FROM Tallas WHERE CodigoTalla = @CodigoTalla
	END 
	GO

-----	|proceso almacenado para "MODIFICAR TALLA"|	-----
	CREATE PROCEDURE sp_ModificarTalla @CodigoTalla int, @DescripcionTalla varchar (256)
	AS
	BEGIN
		UPDATE Tallas SET DescripcionTalla = @DescripcionTalla WHERE CodigoTalla = @CodigoTalla
	END
	GO

-----	|proceso almacenado para "LISTAR TALLA"|	-----
	CREATE PROCEDURE sp_ListarTalla  
	AS
	BEGIN
		SELECT Tallas.CodigoTalla, Tallas.DescripcionTalla FROM Tallas
	END 
	GO

-----	|proceso almacenado para "LISTAR TALLAS"|	-----
	CREATE PROCEDURE sp_ListarTallas @CodigoTalla  int 
	AS
	BEGIN
		SELECT Tallas.CodigoTalla, Tallas.DescripcionTalla FROM Tallas WHERE CodigoTalla = @CodigoTalla
	END 
	GO

-----	|proceso almacenado para "BUSCAR TALLA"|	-----
	CREATE PROCEDURE sp_BuscarTalla @CodigoTalla int
	AS
	BEGIN
		SELECT CodigoTalla, DescripcionTalla FROM Tallas WHERE CodigoTalla = @CodigoTalla
	END
	GO


-------------------------------------------------------------------------------------------------------------------------------------------------------
-----	|Tabla PROVEEDORES|	-----
-----	|Proceso almacenado "AGREGAR PROVEEDOR"|	-----
	CREATE PROCEDURE sp_AgregarProveedor @RazonSocial varchar (256), @NitProveedor varchar (256), @DireccionProveedor varchar (256), @PaginaWeb varchar (256), @ContactoPrincipal varchar (256)
	AS
	BEGIN
		INSERT INTO Proveedores (RazonSocial, NitProveedor, DireccionProveedor, PaginaWeb, ContactoPrincipal) VALUES (@RazonSocial, @NitProveedor, @DireccionProveedor, @PaginaWeb, @ContactoPrincipal)
	END
	GO

-----	|Proceso almacenado para "ELIMINAR PROVEEDOR"|	-----
	CREATE PROCEDURE sp_EliminarProveedor @CodigoProveedor int
	AS
	BEGIN
		DELETE FROM Proveedores WHERE CodigoProveedor = @CodigoProveedor
	END 
	GO

-----	|proceso almacenado para "MODIFICAR PROVEEDOR"|	-----
	CREATE PROCEDURE sp_ModificarProveedor @CodigoProveedor int, @RazonSocial varchar (256), @NitProveedor varchar (256), @DireccionProveedor varchar (256), @PaginaWeb varchar (256), @ContactoPrincipal varchar (256)
	AS
	BEGIN
		UPDATE Proveedores SET RazonSocial = @RazonSocial, NitProveedor = @NitProveedor, DireccionProveedor = @DireccionProveedor, PaginaWeb = @PaginaWeb, ContactoPrincipal = @ContactoPrincipal WHERE CodigoProveedor = @CodigoProveedor
	END
	GO

----	|proceso almacenado para "LISTAR PROVEEDOR"|	----
	CREATE PROCEDURE sp_ListarProveedor 
	AS
	BEGIN
		SELECT Proveedores.CodigoProveedor, Proveedores.RazonSocial, Proveedores.NitProveedor, Proveedores.DireccionProveedor, Proveedores.PaginaWeb, Proveedores.ContactoPrincipal FROM Proveedores
	END
	GO

----	|proceso almacenado para "LISTAR PROVEEDOR"|	----
	CREATE PROCEDURE sp_ListarProveedores @CodigoProveedor int
	AS
	BEGIN
		SELECT Proveedores.CodigoProveedor, Proveedores.RazonSocial, Proveedores.NitProveedor, Proveedores.DireccionProveedor, Proveedores.PaginaWeb, Proveedores.ContactoPrincipal FROM Proveedores WHERE CodigoProveedor = @CodigoProveedor
	END
	GO

-----	|proceso almacenado para "BUSCAR PROVEEDOR"|	-----
	CREATE PROCEDURE sp_BuscarProveedor @CodigoProveedor int
	AS
	BEGIN
		SELECT CodigoProveedor, RazonSocial, NitProveedor, DireccionProveedor, PaginaWeb, ContactoPrincipal FROM Proveedores WHERE CodigoProveedor = @CodigoProveedor
	END
	GO

	
-------------------------------------------------------------------------------------------------------------------------------------------------------
-----	|Tabla TELEFONOPROVEEDORES|	-----
-----	|Proceso almacenado "AGREGAR TELEFONOPROVEEDOR"|	-----
	CREATE PROCEDURE sp_AgregarTelefonoProveedor @NumeroTelefono varchar (256), @DescripcionTelefono varchar (256), @CodigoProveedor int
	AS
	BEGIN
		INSERT INTO TelefonoProveedores (NumeroTelefono, DescripcionTelefono, CodigoProveedor) VALUES (@NumeroTelefono, @DescripcionTelefono, @CodigoProveedor)
	END
	GO

-----	|Proceso almacenado para "ELIMINAR TELEFONOPROVEEDOR"|	-----Co
	CREATE PROCEDURE sp_EliminarTelefonoProveedor @CodigoTelefonoProveedor int
	AS
	BEGIN
		DELETE FROM TelefonoProveedores WHERE CodigoTelefonoProveedor = @CodigoTelefonoProveedor
	END 
	GO

-----	|proceso almacenado para "MODIFICAR TELEFONOPROVEEDOR"|	-----
	CREATE PROCEDURE sp_ModificarTelefonoProveedor @CodigoTelefonoProveedor int, @NumeroTelefono varchar (256), @DescripcionTelefono varchar (256), @CodigoProveedor int
	AS
	BEGIN
		UPDATE TelefonoProveedores SET NumeroTelefono = @NumeroTelefono, DescripcionTelefono = @DescripcionTelefono, CodigoProveedor = @CodigoProveedor WHERE CodigoTelefonoProveedor = @CodigoTelefonoProveedor
	END
	GO

----	|proceso almacenado para "LISTAR TELEFONOPROVEEDORES"|	-----
	CREATE PROCEDURE sp_ListarTelefonoProveedor 
	AS
	BEGIN
		SELECT TelefonoProveedores.CodigoTelefonoProveedor, TelefonoProveedores.NumeroTelefono, TelefonoProveedores.DescripcionTelefono, TelefonoProveedores.CodigoProveedor, Proveedores.RazonSocial FROM TelefonoProveedores INNER JOIN Proveedores ON Proveedores.CodigoProveedor = TelefonoProveedores.CodigoProveedor
	END
	GO

----	|proceso almacenado para "LISTAR TELEFONOPROVEEDORES"|	-----
	CREATE PROCEDURE sp_ListarTelefonoProveedores @CodigoTelefonoProveedor int
	AS
	BEGIN
		SELECT TelefonoProveedores.CodigoTelefonoProveedor, TelefonoProveedores.NumeroTelefono, TelefonoProveedores.DescripcionTelefono, TelefonoProveedores.CodigoProveedor, Proveedores.RazonSocial FROM TelefonoProveedores INNER JOIN Proveedores ON Proveedores.CodigoProveedor = TelefonoProveedores.CodigoProveedor WHERE CodigoTelefonoProveedor = @CodigoTelefonoProveedor
	END
	GO

-----	|proceso almacenado para "BUSCAR TELEFONOPROVEEDOR"|	-----
	CREATE PROCEDURE sp_BuscarTelefonoProveedor @CodigoTelefonoProveedor int
	AS
	BEGIN
		SELECT TelefonoProveedores.CodigoTelefonoProveedor, TelefonoProveedores.NumeroTelefono, TelefonoProveedores.DescripcionTelefono, TelefonoProveedores.CodigoProveedor, Proveedores.RazonSocial FROM TelefonoProveedores INNER JOIN Proveedores ON Proveedores.CodigoProveedor = TelefonoProveedores.CodigoProveedor WHERE CodigoTelefonoProveedor = @CodigoTelefonoProveedor
	END
	GO


-------------------------------------------------------------------------------------------------------------------------------------------------------
-----	|Tabla EMAILPROVEEDORES|	-----
-----	|Proceso almacenado "AGREGAR EMAILPROVEEDOR"|	-----
	CREATE PROCEDURE sp_AgregarEmailProveedores @DescripcionEmail varchar (256), @EmailProveedor varchar (256), @CodigoProveedor varchar (256)
	AS
	BEGIN
		INSERT INTO EmailProveedores(DescripcionEmail, EmailProveedor, CodigoProveedor) VALUES (@DescripcionEmail, @EmailProveedor, @CodigoProveedor)
	END
	GO

-----	|Proceso almacenado para "ELIMINAR EMAILPROVEEDOR"|	-----
	CREATE PROCEDURE sp_EliminarEmailProveedor @CodigoEmailProveedor int
	AS
	BEGIN
		DELETE FROM EmailProveedores WHERE CodigoEmailProveedor = @CodigoEmailProveedor
	END 
	GO

-----	|proceso almacenado para "MODIFICAR EMAILPROVEEDOR"|	-----
	CREATE PROCEDURE sp_ModificarEmailProveedor @CodigoEmailProveedor int, @DescripcionEmail varchar (256), @EmailProveedor varchar (256), @CodigoProveeedor int
	AS
	BEGIN
		UPDATE EmailProveedores SET DescripcionEmail = @DescripcionEmail, EmailProveedor = @EmailProveedor, CodigoProveedor = @CodigoProveeedor WHERE CodigoEmailProveedor = @CodigoEmailProveedor
	END
	GO

----	|proceso almacenado para "LISTAR EMAILPROVEEDORES"|	-----
	CREATE PROCEDURE sp_ListarEmailProveedor 
	AS
	BEGIN
		SELECT EmailProveedores.CodigoEmailProveedor, EmailProveedores.DescripcionEmail, EmailProveedores.EmailProveedor, EmailProveedores.CodigoProveedor, Proveedores.RazonSocial FROM EmailProveedores INNER JOIN Proveedores ON Proveedores.CodigoProveedor = EmailProveedores.CodigoProveedor
	END
	GO

----	|proceso almacenado para "LISTAR EMAILPROVEEDORES"|	-----
	CREATE PROCEDURE sp_ListarEmailProveedores @CodigoEmailProveedor int  
	AS
	BEGIN
		SELECT EmailProveedores.CodigoEmailProveedor, EmailProveedores.DescripcionEmail, EmailProveedores.EmailProveedor, EmailProveedores.CodigoProveedor, Proveedores.RazonSocial FROM EmailProveedores INNER JOIN Proveedores ON Proveedores.CodigoProveedor = EmailProveedores.CodigoProveedor WHERE CodigoEmailProveedor = @CodigoEmailProveedor
	END
	GO

-----	|proceso almacenado para "BUSCAR EMAILPROVEEDOR"|	-----
	CREATE PROCEDURE sp_BuscarEmailProveedor @CodigoEmailProveedor int
	AS
	BEGIN
		SELECT EmailProveedores.CodigoEmailProveedor, EmailProveedores.DescripcionEmail, EmailProveedores.EmailProveedor, EmailProveedores.CodigoProveedor, Proveedores.RazonSocial FROM EmailProveedores INNER JOIN Proveedores ON Proveedores.CodigoProveedor = EmailProveedores.CodigoProveedor WHERE CodigoEmailProveedor = @CodigoEmailProveedor
	END
	GO
	

-------------------------------------------------------------------------------------------------------------------------------------------------------
-----	|Tabla COMPRAS|	-----
-----	|Proceso almacenado "AGREGAR COMPRA"|	-----
	CREATE PROCEDURE sp_AgregarCompra @DescripcionCompra varchar (256), @FechaCompra date, @CodigoProveedor int, @TotalCompra decimal (10, 2)
	AS
	BEGIN
		INSERT INTO Compras (DescripcionCompra, FechaCompra, CodigoProveedor, TotalCompra) VALUES (@DescripcionCompra, @FechaCompra, @CodigoProveedor, @TotalCompra)
	END
	GO
	
-----	|Proceso almacenado para "ELIMINARCOMPRA"|	-----
	CREATE PROCEDURE sp_EliminarCompra @NumeroDocumento int
	AS
	BEGIN
		DELETE FROM Compras WHERE NumeroDocumento = @NumeroDocumento
	END 
	GO

-----	|proceso almacenado para "MODIFICAR COMPRA"|	-----
	CREATE PROCEDURE sp_ModificarCompra @NumeroDocumento int, @DescripcionCompra varchar (256), @FechaCompra date, @CodigoProveedor int, @TotalCompra decimal (10, 2)
	AS
	BEGIN
		UPDATE Compras SET DescripcionCompra = @DescripcionCompra, FechaCompra = @FechaCompra, CodigoProveedor = @CodigoProveedor, TotalCompra = @TotalCompra WHERE NumeroDocumento = @NumeroDocumento
	END
	GO

-----	|proceso almacenado para "LISTAR COMPRA"|	-----
	CREATE PROCEDURE sp_ListarCompra 
	AS
	BEGIN
		SELECT Compras.NumeroDocumento, Compras.DescripcionCompra,  Compras.FechaCompra, Compras.CodigoProveedor, Proveedores.RazonSocial, Compras.TotalCompra FROM Compras INNER JOIN Proveedores ON Proveedores.CodigoProveedor = Compras.CodigoProveedor
	END
	GO
	
-----	|proceso almacenado para "BUSCAR COMPRA"|	-----
	CREATE PROCEDURE sp_BuscarCompra @NumeroDocumento int
	AS
	BEGIN
		SELECT Compras.NumeroDocumento, Compras.DescripcionCompra, Compras.FechaCompra, Compras.CodigoProveedor, RazonSocial, Compras.TotalCompra FROM Compras INNER JOIN Proveedores ON Proveedores.CodigoProveedor = Compras.CodigoProveedor WHERE NumeroDocumento = @NumeroDocumento
	END
	GO

-----	|proceso almacenado para "REPORTE COMPRAS"|	-----
	CREATE PROCEDURE sp_ListarCompras @NumeroDocumento int
	AS
	BEGIN
		SELECT Compras.NumeroDocumento, Compras.FechaCompra, Compras.DescripcionCompra, Proveedores.RazonSocial, Compras.TotalCompra from Compras INNER JOIN Proveedores ON Compras.CodigoProveedor = Proveedores.CodigoProveedor WHERE Compras.NumeroDocumento = @NumeroDocumento 
	END				
	GO


-------------------------------------------------------------------------------------------------------------------------------------------------------
-----	|Tabla DETALLECOMPRAS|	-----
-----	|Proceso almacenado "AGREGAR DETALLECOMPRA"|	-----
	CREATE PROCEDURE sp_AgregarDetalleCompra @NumeroDocumento int, @CostoProducto decimal (10, 2), @CostoUnitario decimal (10, 2), @Cantidad int, @CodigoProducto int 
	AS
	BEGIN
		INSERT INTO DetalleCompras (NumeroDocumento, CostoProducto, CostoUnitario, Cantidad, CodigoProducto) VALUES (@NumeroDocumento, @CostoProducto, @CostoUnitario, @Cantidad, @CodigoProducto)
	END
	GO
	
-----	|Proceso almacenado para "ELIMINAR DETALLECOMPRA"|	-----
	CREATE PROCEDURE sp_EliminarDetalleCompra @CodigoDetalleCompra int
	AS
	BEGIN
		DELETE FROM DetalleCompras WHERE CodigoDetalleCompra = @CodigoDetalleCompra
	END 
	GO

-----	|proceso almacenado para "MODIFICAR DETALLECOMPRA"|	-----
	CREATE PROCEDURE sp_ModificarDetalleCompra @CodigoDetalleCompra int, @NumeroDocumento int, @CostoProducto decimal (10, 2), @CostoUnitario decimal (10, 2), @Cantidad int, @CodigoProducto int
	AS
	BEGIN
		UPDATE DetalleCompras SET  NumeroDocumento  = @NumeroDocumento, CostoProducto = @CostoProducto, CostoUnitario = @CostoUnitario, Cantidad = @Cantidad, CodigoProducto = @CodigoProducto WHERE CodigoDetalleCompra = @CodigoDetalleCompra
	END
	GO

-----	|proceso almacenado para "LISTAR DETALLECOMPRA"|	-----
	CREATE PROCEDURE sp_ListarDetalleCompra
	AS
	BEGIN
		SELECT DetalleCompras.CodigoDetalleCompra, DetalleCompras.NumeroDocumento, DetalleCompras.CostoProducto, DetalleCompras.CostoUnitario, DetalleCompras.Cantidad, DetalleCompras.CodigoProducto, Productos.DescripcionProducto FROM DetalleCompras INNER JOIN Productos ON Productos.CodigoProducto = DetalleCompras.CodigoProducto
	END
	GO

-----	|proceso almacenado para Listar Compra|	-----
	CREATE PROCEDURE sp_ListarDetalleCompras @NumeroDocumento int
	AS
	BEGIN
		SELECT DetalleCompras.CodigoDetalleCompra, DetalleCompras.NumeroDocumento, DetalleCompras.CostoProducto, DetalleCompras.CostoUnitario, DetalleCompras.Cantidad, DetalleCompras.CodigoProducto, Productos.DescripcionProducto FROM DetalleCompras INNER JOIN Productos ON Productos.CodigoProducto = DetalleCompras.CodigoProducto WHERE DetalleCompras.NumeroDocumento = @NumeroDocumento
	END 
	GO

-----	|proceso almacenado para "BUSCAR DETALLECOMPRA"|	----
	CREATE PROCEDURE sp_BuscarDetalleCompra @CodigoDetalleCompra int
	AS
	BEGIN
		SELECT DetalleCompras.CodigoDetalleCompra, DetalleCompras.NumeroDocumento, DetalleCompras.CostoProducto, DetalleCompras.CostoUnitario, DetalleCompras.Cantidad, DetalleCompras.CodigoProducto, Productos.DescripcionProducto FROM DetalleCompras INNER JOIN Productos ON Productos.CodigoProducto = DetalleCompras.CodigoProducto WHERE CodigoDetalleCompra = @CodigoDetalleCompra
	END
	GO

-----	|tigger "INSERTAR COMPRAS"|	-----
	CREATE TRIGGER tr_Compras_Insertar
	ON DetalleCompras
	FOR INSERT
	AS
		DECLARE @Cantidad int
		SELECT @Cantidad = ExistenciaProducto from Productos
		JOIN INSERTED ON INSERTED.CodigoProducto = Productos.CodigoProducto
		WHERE Productos.CodigoProducto = INSERTED.CodigoProducto
			BEGIN
				UPDATE Productos SET ExistenciaProducto = ExistenciaProducto + INSERTED.Cantidad from Productos
				JOIN INSERTED ON INSERTED.CodigoProducto = Productos.CodigoProducto
				WHERE Productos.CodigoProducto = INSERTED.CodigoProducto
			END
			drop trigger tr_Facturas_Insertar
	GO

-----	|trigger "INSERTAR PRECIOUNITARIO"|	-----
	CREATE TRIGGER tr_PrecioUnitario_Insertar
	ON DetalleCompras
	FOR INSERT 
	AS
		DECLARE @CostoU decimal (10, 2)
		SELECT @CostoU = PrecioUnitario from Productos
		JOIN INSERTED ON INSERTED.CodigoProducto = Productos.CodigoProducto
		WHERE Productos.CodigoProducto = INSERTED.CodigoProducto
			BEGIN
				UPDATE Productos SET PrecioUnitario = ((((INSERTED.CostoUnitario + DetalleCompras.CostoProducto) * 0.20) + DetalleCompras.CostoProducto) /DetalleCompras.Cantidad) from Productos INNER JOIN DetalleCompras ON Productos.CodigoProducto = DetalleCompras.CodigoProducto
				JOIN INSERTED ON INSERTED.CodigoProducto = Productos.CodigoProducto
				WHERE Productos.CodigoProducto = INSERTED.CodigoProducto
			END
	GO

-----	|trigger "INSERTAR PRECIODOCENA"|	-----
	CREATE TRIGGER tr_PrecioDocena_Insertar
	ON DetalleCompras
	FOR INSERT
	AS
		DECLARE @CostoD decimal (10, 2)
		SELECT @CostoD = PrecioDocena from Productos
		JOIN INSERTED ON INSERTED.CodigoProducto = Productos.CodigoProducto
		WHERE Productos.CodigoProducto = INSERTED.CodigoProducto
			BEGIN
				UPDATE Productos SET PrecioDocena = (PrecioUnitario - (PrecioUnitario * 0.05)) FROM Productos
				JOIN INSERTED ON INSERTED.CodigoProducto = Productos.CodigoProducto
				WHERE Productos.CodigoProducto = INSERTED.CodigoProducto
			END
	GO
		
-----	|trigger "INSERTAR PRECIOMAYOR"|	-----
	CREATE TRIGGER tr_PrecioMayor_Insertar
	ON DetalleCompras
	FOR INSERT
	AS
		DECLARE @CostoM decimal (10, 2)
		SELECT @CostoM = PrecioPorMayor FROM Productos
		JOIN INSERTED ON INSERTED.CodigoProducto = Productos.CodigoProducto
		WHERE Productos.CodigoProducto = INSERTED.CodigoProducto
			BEGIN
				UPDATE Productos SET PrecioPorMayor = (PrecioUnitario - (PrecioUnitario * 0.07)) FROM Productos
				JOIN INSERTED ON INSERTED.CodigoProducto = Productos.CodigoProducto
				WHERE Productos.CodigoProducto = INSERTED.CodigoProducto	
			END
	GO


-------------------------------------------------------------------------------------------------------------------------------------------------------
-----	|Tabla CLIENTES|	-----
-----	|Proceso almacenado "AGREGAR CLIENTE"|	-----
	CREATE PROCEDURE sp_AgregarCliente @NombreCliente varchar (256), @NitCliente varchar (256), @DireccionCliente varchar (256)
	AS
	BEGIN
		INSERT INTO Clientes (NombreCliente, NitCliente, DireccionCliente) VALUES (@NombreCliente, @NitCliente, @DireccionCliente)
	END
	GO

-----	|Proceso almacenado para "ELIMINAR CLIENTE"|	-----
	CREATE PROCEDURE sp_EliminarCliente @CodigoCliente int
	AS
	BEGIN
		DELETE FROM Clientes WHERE CodigoCliente = @CodigoCliente
	END 
	GO

-----	|proceso almacenado para "MODIFICAR CLIENTE"|	-----
	CREATE PROCEDURE sp_ModificarCliente @CodigoCliente int, @NombreCliente varchar (256), @NitCliente varchar (256), @DireccionCliente varchar (256) 
	AS
	BEGIN
		UPDATE Clientes SET NombreCliente = @NombreCliente, NitCliente = @NitCliente, DireccionCliente = @DireccionCliente WHERE CodigoCliente = @CodigoCliente
	END
	GO

-----	|proceso almacenado para "LISTAR CLIENTE"|	-----
	CREATE PROCEDURE sp_ListarCliente
	AS
	BEGIN
		SELECT Clientes.CodigoCliente, Clientes.NombreCliente, Clientes.NitCliente, Clientes.DireccionCliente FROM Clientes
	END
	GO

-----	|proceso almacenado para "LISTAR CLIENTE"|	-----
	CREATE PROCEDURE sp_ListarClientes @CodigoCliente int
	AS
	BEGIN
		SELECT Clientes.CodigoCliente, Clientes.NombreCliente, Clientes.NitCliente, Clientes.DireccionCliente FROM Clientes WHERE CodigoCliente = @CodigoCliente
	END
	GO

-----	|proceso almacenado para "BUSCAR CLIENTE"|	----
	CREATE PROCEDURE sp_BuscarCliente @CodigoCliente int
	AS
	BEGIN
		SELECT CodigoCliente, NombreCliente, NitCliente, DireccionCliente FROM Clientes WHERE CodigoCliente = @CodigoCliente
	END
	GO

-------------------------------------------------------------------------------------------------------------------------------------------------------
-----	|Tabla TELEFONOCLIENTES|	-----
-----	|Proceso almacenado "AGREGAR TELEFONOCLIENTE"|	-----
	CREATE PROCEDURE sp_AgregarTelefonoCliente @DescripcionTelefonoCliente varchar (256), @NumeroCliente varchar (256), @CodigoCliente int
	AS
	BEGIN
		INSERT INTO TelefonoClientes (DescripcionTelefonoCLiente, NumeroCliente, CodigoCliente) VALUES (@DescripcionTelefonoCliente, @NumeroCliente, @CodigoCliente)
	END
	GO

-----	|Proceso almacenado para "ELIMINAR TELEFONOCLIENTE"|	-----
	CREATE PROCEDURE sp_EliminarTelefonoCliente @CodigoTelefonoCliente int
	AS
	BEGIN
		DELETE FROM TelefonoClientes WHERE CodigoTelefonoCliente = @CodigoTelefonoCliente
	END 
	GO

-----	|proceso almacenado para "MODIFICAR TELEFONOCLIENTE"|	-----
	CREATE PROCEDURE sp_ModificarTelefonoCliente @CodigoTelefonoCliente int, @DescripcionTelefonoCliente varchar (256), @NumeroCliente varchar (256), @CodigoCliente int
	AS
	BEGIN
		UPDATE TelefonoClientes SET  DescripcionTelefonoCliente = @DescripcionTelefonoCliente, NumeroCliente = @NumeroCliente, CodigoCliente = @CodigoCliente WHERE CodigoTelefonoCliente = @CodigoTelefonoCliente
	END
	GO

-----	|proceso almacenado para "LISTAR TELEFONOCLIENTE"|	-----
	CREATE PROCEDURE sp_ListarTelefonoCliente
	AS
	BEGIN
		SELECT TelefonoClientes.CodigoTelefonoCliente, TelefonoClientes.DescripcionTelefonoCliente, TelefonoClientes.NumeroCliente, TelefonoClientes.CodigoCliente, Clientes.NombreCliente FROM TelefonoClientes INNER JOIN Clientes ON Clientes.CodigoCliente = TelefonoClientes.CodigoCliente
	END
	GO

-----	|proceso almacenado para "LISTAR TELEFONOCLIENTE"|	-----
	CREATE PROCEDURE sp_ListarTelefonoClientes @CodigoTelefonoCliente int
	AS
	BEGIN
		SELECT TelefonoClientes.CodigoTelefonoCliente, TelefonoClientes.DescripcionTelefonoCliente, TelefonoClientes.NumeroCliente, TelefonoClientes.CodigoCliente, Clientes.NombreCliente FROM TelefonoClientes INNER JOIN Clientes ON Clientes.CodigoCliente = TelefonoClientes.CodigoCliente WHERE CodigoTelefonoCliente = @CodigoTelefonoCliente
	END
	GO
		
-----	|proceso almacenado para "BUSCAR TELEFONOCLIENTE"|	----
	CREATE PROCEDURE sp_BuscarTelefonoCliente @CodigoTelefonoCliente int
	AS
	BEGIN
		SELECT TelefonoClientes.CodigoTelefonoCliente, TelefonoClientes.DescripcionTelefonoCliente, TelefonoClientes.NumeroCliente, TelefonoClientes.CodigoCliente, Clientes.NombreCliente  FROM TelefonoClientes INNER JOIN Clientes ON Clientes.CodigoCliente = TelefonoClientes.CodigoCliente WHERE CodigoTelefonoCliente = @CodigoTelefonoCliente
	END
	GO

-------------------------------------------------------------------------------------------------------------------------------------------------------
-----	|Tabla EMAILCLIENTES|	-----
-----	|Proceso almacenado "AGREGAR EMAILCLIENTE"|	-----
	CREATE PROCEDURE sp_AgregarEmailCliente @EmailCliente varchar (256), @DescripcionEmailCliente varchar (256), @CodigoCliente int
	AS
	BEGIN
		INSERT INTO EmailClientes (EmailCliente, DescripcionEmailCliente, CodigoCliente) VALUES (@EmailCLiente, @DescripcionEmailCliente, @CodigoCliente)
	END
	GO

-----	|Proceso almacenado para "ELIMINAR EMAILCLIENTE"|	-----
	CREATE PROCEDURE sp_EliminarEmailCliente @CodigoEmailCliente int
	AS
	BEGIN
		DELETE FROM EmailClientes WHERE CodigoEmailCliente = @CodigoEmailCliente
	END 
	GO

-----	|proceso almacenado para "MODIFICAR EMAILCLIENTE"|	-----
	CREATE PROCEDURE sp_ModificarEmailCliente @CodigoEmailCliente int, @EmailCliente varchar (256), @DescripcionEmailCliente varchar (256), @CodigoCliente int
	AS
	BEGIN
		UPDATE EmailClientes SET EmailCliente = @EmailCliente, DescripcionEmailCliente = @DescripcionEmailCliente, CodigoCliente = @CodigoCliente WHERE CodigoEmailCliente = @CodigoEmailCliente
	END
	GO

-----	|proceso almacanado para "LISTAR EMAILCLIENTE"|	-----
	CREATE PROCEDURE sp_ListarEmailCliente
	AS
	BEGIN
		SELECT EmailClientes.CodigoEmailCliente, EmailClientes.EmailCliente, EmailClientes.DescripcionEmailCliente, EmailClientes.CodigoCliente, Clientes.NombreCliente FROM EmailClientes INNER JOIN Clientes ON Clientes.CodigoCliente = EmailClientes.CodigoCliente
	END
	GO

-----	|proceso almacanado para "LISTAR EMAILCLIENTE"|	-----
	CREATE PROCEDURE sp_ListarEmailClientes @CodigoEmailCliente int 
	AS
	BEGIN
		SELECT EmailClientes.CodigoEmailCliente, EmailClientes.EmailCliente, EmailClientes.DescripcionEmailCliente, EmailClientes.CodigoCliente, Clientes.NombreCliente FROM EmailClientes INNER JOIN Clientes ON Clientes.CodigoCliente = EmailClientes.CodigoCliente WHERE CodigoEmailCliente = @CodigoEmailCliente
	END
	GO

-----	|proceso almacenado para "BUSCAR EMAILCLIENTE"|	-----
	CREATE PROCEDURE sp_BuscarEmailCliente @CodigoEmailCliente int
	AS
	BEGIN
		SELECT EmailClientes.CodigoEmailCliente, EmailClientes.EmailCliente, EmailClientes.DescripcionEmailCliente, EmailClientes.CodigoCliente, Clientes.NombreCliente FROM EmailClientes INNER JOIN Clientes ON Clientes.CodigoCliente = EmailClientes.CodigoCliente WHERE CodigoEmailCliente = @CodigoEmailCliente
	END
	GO

-------------------------------------------------------------------------------------------------------------------------------------------------------
-----	|Tabla FACTURAS|	-----
-----	|Proceso almacenado "AGREGAR FACTURA"|	-----
	CREATE PROCEDURE sp_AgregarFactura @FechaFactura date, @NitFactura varchar (256), @EstadoFactura varchar (256), @CodigoCliente int, @TotalFactura decimal (10, 2)
	AS
	BEGIN
		INSERT INTO Facturas (FechaFactura, NitFactura, EstadoFactura, CodigoCliente, TotalFactura) VALUES (@FechaFactura, @NitFactura, @EstadoFactura, @CodigoCliente, @TotalFactura)
	END
	GO
	
-----	|Proceso almacenado para "ELIMINAR FACTURA"|	-----
	CREATE PROCEDURE sp_EliminarFactura @NumeroFactura int
	AS
	BEGIN
		DELETE FROM Facturas WHERE NumeroFactura = @NumeroFactura
	END 
	GO

-----	|proceso almacenado para "MODIFICAR FACTURA"|	-----
	CREATE PROCEDURE sp_ModificarFactura @NumeroFactura int, @FechaFactura date, @NitFactura varchar (256),  @EstadoFactura varchar (256), @CodigoCliente int, @TotalFactura decimal (10, 2)
	AS
	BEGIN
		UPDATE Facturas SET FechaFactura = @FechaFactura, NitFactura = @NitFactura, EstadoFactura = @EstadoFactura, CodigoCliente = @CodigoCliente, TotalFactura = @TotalFactura WHERE NumeroFactura = @NumeroFactura
	END
	GO

-----	|proceso almacenado para "LISTAR FACTURAS"|	-----
	CREATE PROCEDURE sp_ListarFactura 
	AS
	BEGIN
		SELECT Facturas.NumeroFactura, Facturas.FechaFactura, Facturas.NitFactura, Facturas.EstadoFactura, Facturas.CodigoCliente, Clientes.NombreCliente, Facturas.TotalFactura FROM Facturas INNER JOIN Clientes ON Clientes.CodigoCliente = Facturas.CodigoCliente
	END
	GO
		
-----	|proceso almacenado para "BUSCAR FACTURA"|	-----
	CREATE PROCEDURE sp_BuscarFactura @NumeroFactura int
	AS
	BEGIN
		SELECT Facturas.NumeroFactura, Facturas.FechaFactura, Facturas.NitFactura, Facturas.EstadoFactura, Facturas.CodigoCliente, Clientes.NombreCliente, Facturas.TotalFactura FROM Facturas INNER JOIN Clientes ON Clientes.CodigoCliente = Facturas.CodigoCliente WHERE NumeroFactura = @NumeroFactura
	END
	GO

-----	|proceso almacenado para "LISTAR FACTURAS"|	-----
	CREATE PROCEDURE sp_ListarFacturas @NumeroFactura int
	AS
	BEGIN
		SELECT Facturas.NumeroFactura, Facturas.FechaFactura, Facturas.NitFactura, Facturas.EstadoFactura, Clientes.NombreCliente, Facturas.TotalFactura FROM Facturas INNER JOIN Clientes ON Clientes.CodigoCliente = Facturas.CodigoCliente WHERE NumeroFactura = @NumeroFactura
	END
	GO

-------------------------------------------------------------------------------------------------------------------------------------------------------
-----	|Tabla DETALLEFACTURAS|	-----
-----	|Proceso almacenado "AGREGAR DETALLEFACTURA"|	-----
	CREATE PROCEDURE sp_AgregarDetalleFactura @Cantidad int, @CodigoProducto int, @NumeroFactura int
	AS
	BEGIN
		INSERT INTO DetalleFacturas (Cantidad, CodigoProducto, NumeroFactura) VALUES (@Cantidad, @CodigoProducto, @NumeroFactura)
	END
	GO

-----	|Proceso almacenado para "ELIMINAR DETALLEFACTURA"|	-----
	CREATE PROCEDURE sp_EliminarDetalleFactura @CodigoDetalleFactura int
	AS
	BEGIN
		DELETE FROM DetalleFacturas WHERE CodigoDetalleFactura = @CodigoDetalleFactura
	END 
	GO

-----	|proceso almacenado para "MODIFICAR DETALLEFACTURA"|	-----
	CREATE PROCEDURE sp_ModificarDetalleFactura @CodigoDetalleFactura int, @Cantidad int, @CodigoProducto int, @NumeroFactura int
	AS
	BEGIN
		UPDATE DetalleFacturas SET Cantidad = @Cantidad, CodigoProducto = @CodigoProducto, NumeroFactura = @NumeroFactura WHERE CodigoDetalleFactura = @CodigoDetalleFactura
	END
	GO

-----	|proceso almacenado para "LISTAR DETALLEFACTURA"|	-----
	CREATE PROCEDURE sp_ListarDetalleFactura 
	AS
	BEGIN
		SELECT DetalleFacturas.CodigoDetalleFactura, DetalleFacturas.Precio, DetalleFacturas.Cantidad, DetalleFacturas.CodigoProducto, DetalleFacturas.NumeroFactura, Productos.DescripcionProducto FROM DetalleFacturas INNER JOIN Productos ON Productos.CodigoProducto = DetalleFacturas.CodigoProducto
	END
	GO

-----	|proceso almacenado para "LISTAR DETALLEFACTURAS"|	-----
	CREATE PROCEDURE sp_ListarDetalleFacturas @NumeroFactura int 
	AS
	BEGIN
		SELECT DetalleFacturas.CodigoDetalleFactura, DetalleFacturas.Precio, DetalleFacturas.Cantidad, DetalleFacturas.CodigoProducto, DetalleFacturas.NumeroFactura, Productos.DescripcionProducto FROM DetalleFacturas INNER JOIN Productos ON Productos.CodigoProducto = DetalleFacturas.CodigoProducto WHERE DetalleFacturas.NumeroFactura = @NumeroFactura
	END
	GO

-----	|proceso almacenado para "BUSCAR DETALLEFACTURA"|	-----
	CREATE PROCEDURE sp_BuscarDetalleFactura @CodigoDetalleFactura int
	AS
	BEGIN
		SELECT DetalleFacturas.CodigoDetalleFactura, DetalleFacturas.Precio, DetalleFacturas.Cantidad, DetalleFacturas.CodigoProducto, DetalleFacturas.NumeroFactura, Productos.DescripcionProducto FROM DetalleFacturas INNER JOIN Productos ON Productos.CodigoProducto = DetalleFacturas.CodigoProducto WHERE CodigoDetalleFactura = @CodigoDetalleFactura
	END
	GO

-----	|tigger "INSERTAR VENTAS"|	-----
	CREATE TRIGGER tr_Facturas_Insertar
	ON DetalleFacturas 
	FOR INSERT
	AS
		DECLARE @Existencia int
		SELECT @Existencia = ExistenciaProducto from Productos
		JOIN INSERTED ON INSERTED.CodigoProducto = Productos.CodigoProducto
		WHERE Productos.CodigoProducto = INSERTED.CodigoProducto
		IF(@Existencia >= (SELECT Cantidad FROM INSERTED))
			BEGIN
				UPDATE Productos SET ExistenciaProducto = ExistenciaProducto - INSERTED.Cantidad from Productos
				JOIN INSERTED ON INSERTED.CodigoProducto = Productos.CodigoProducto
				WHERE Productos.CodigoProducto = INSERTED.CodigoProducto
				IF(@Existencia <= 8)
					BEGIN
						UPDATE DetalleFacturas SET Precio = PrecioUnitario FROM Productos  
						JOIN INSERTED ON INSERTED.CodigoDetalleFactura = inserted.CodigoDetalleFactura
						WHERE DetalleFacturas.CodigoDetalleFactura = Inserted.CodigoDetalleFactura
					END
				ELSE
					IF(@Existencia >= 9)
						BEGIN
							UPDATE DetalleFacturas SET Precio = PrecioDocena FROM Productos
							JOIN INSERTED ON INSERTED.CodigoDetalleFactura = inserted.CodigoDetalleFactura
							WHERE DetalleFacturas.CodigoDetalleFactura = Inserted.CodigoDetalleFactura
						END
					ELSE
						IF(@Existencia >= 25)
							BEGIN
								UPDATE DetalleFacturas SET Precio = PrecioPorMayor FROM Productos
								JOIN INSERTED ON INSERTED.CodigoDetalleFactura = inserted.CodigoDetalleFactura
								WHERE DetalleFacturas.CodigoDetalleFactura = Inserted.CodigoDetalleFactura
							END
				END
		ELSE
			BEGIN
				RAISERROR ('No se puede vender tal cantidad', 16, 1)
				ROLLBACK TRANSACTION
			END
	GO
	
-------------------------------------------------------------------------------------------------------------------------------------------------------
-----	|Tabla AgregarUsuario|	-----
-----	|Proceso almacenado "AGREGAR USUARIO"|	-----
	CREATE PROCEDURE sp_AgregarUsuario @NombreUsuario varchar (256), @Usuario varchar (256), @EmailUsuario varchar (256), @TipoUsuario varchar (256), @ContrasenaUsuario varchar (256)  
	AS
	BEGIN
		INSERT INTO AgregarUsuarios (NombreUsuario, Usuario, EmailUsuario, TipoUsuario, ContrasenaUsuario) VALUES (@NombreUsuario, @Usuario, @EmailUsuario, @TipoUsuario, @ContrasenaUsuario)
	END
	GO
	
-----	|Proceso almacenado para "ELIMINAR DETALLEFACTURA"|	-----
	CREATE PROCEDURE sp_EliminarUsuario @CodigoUsuario int
	AS
	BEGIN
		DELETE FROM AgregarUsuarios WHERE CodigoUsuario = @CodigoUsuario
	END 
	GO

-----	|proceso almacenado para "MODIFICAR DETALLEFACTURA"|	-----
	CREATE PROCEDURE sp_ModificarUsuario @CodigoUsuario int, @NombreUsuario varchar (256), @Usuario varchar (256), @EmailUsuario varchar (256), @TipoUsuario varchar (256), @ContrasenaUsuario varchar (256)
	AS
	BEGIN
		UPDATE AgregarUsuarios SET NombreUsuario = @NombreUsuario, Usuario = @Usuario, EmailUsuario = @EmailUsuario, TipoUsuario = @TipoUsuario, ContrasenaUsuario = @ContrasenaUsuario  WHERE CodigoUsuario = @CodigoUsuario
	END
	GO

-----	|proceso almacenado para "LISTAR DETALLEFACTURA"|	-----
	CREATE PROCEDURE sp_ListarUsuario 
	AS
	BEGIN
		SELECT AgregarUsuarios.CodigoUsuario, AgregarUsuarios.NombreUsuario, AgregarUsuarios.Usuario, AgregarUsuarios.EmailUsuario, AgregarUsuarios.TipoUsuario, AgregarUsuarios.ContrasenaUsuario FROM AgregarUsuarios
	END
	GO

-----	|proceso almacenado para "LISTAR DETALLEFACTURAS"|	-----
	CREATE PROCEDURE sp_ListarUsuarios @CodigoUsuario int
	AS
	BEGIN
		SELECT AgregarUsuarios.NombreUsuario, AgregarUsuarios.Usuario, AgregarUsuarios.EmailUsuario, AgregarUsuarios.TipoUsuario, AgregarUsuarios.ContrasenaUsuario FROM AgregarUsuarios WHERE CodigoUsuario = @CodigoUsuario
	END
	GO

-----	|proceso almacenado para "BUSCAR DETALLEFACTURA"|	-----
	CREATE PROCEDURE sp_BuscarUsuario @CodigoUsuario int
	AS
	BEGIN
		SELECT AgregarUsuarios.CodigoUsuario, AgregarUsuarios.NombreUsuario, AgregarUsuarios.Usuario, AgregarUsuarios.EmailUsuario, AgregarUsuarios.TipoUsuario, AgregarUsuarios.ContrasenaUsuario FROM AgregarUsuarios WHERE CodigoUsuario = @CodigoUsuario
	END
	GO


	