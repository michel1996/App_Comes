<?php
	$usuario=$_REQUEST["usuario"];
	$password=$_REQUEST["password"];
	
	$servidor="127.0.0.1";
	$db="comes";
	$dbUsuario="root";
	$dbPassword="";
	try
	{
		$con=new PDO("mysql:host=$servidor;dbname=$db",$dbUsuario,$dbPassword,array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES  \'UTF8\''));
		$con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		
		$datos=array();
		$res=$con->query("select * from usuario where username='$usuario' and password='$password'");
		foreach($res as $row)
		{
			$datos[]=$row;
		}
		
		echo json_encode($datos);
	}
	catch(PDOException $e)
	{
		echo "La conexion a la base de datos ha fallado con el siguiente mensaje: ".$e->getMessage();
	}
	
	
	

?>