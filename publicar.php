<?php
	$titulo=$_REQUEST["titulo"];
	$descripcion=$_REQUEST["descripcion"];
	$precio=$_REQUEST["precio"];
	$cantidad=$_REQUEST["cantidad"];
	
	$servidor="127.0.0.1";
	$db="comes";
	$dbUsuario="root";
	$dbPassword="";
	try
	{
		
		$con=new PDO("mysql:host=$servidor;dbname=$db",$dbUsuario,$dbPassword,array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES  \'UTF8\''));
		$con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		
		try
		{
			$res=$con->query("INSERT INTO producto (nombre, descripcion, precio,cantidad) VALUES('$titulo','$descripcion', '$precio', '$cantidad')");
			$response=array("true");
			
			echo json_encode($response);
		}
		catch(PDOException $e)
		{
			$response=array();
			echo json_encode($response);
		}
		
		
		
	}
	catch(PDOException $e)
	{
		echo "La conexion a la base de datos ha fallado con el siguiente mensaje: ".$e->getMessage();
	}
	
	
	

?>