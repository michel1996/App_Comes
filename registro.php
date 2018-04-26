<?php
	$nombre=$_REQUEST["nombre"];
	$apellido=$_REQUEST["apellido"];
	$telefono=$_REQUEST["telefono"];
	$email=$_REQUEST["email"];
	$password=$_REQUEST["password"];
	
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
			$res=$con->query("select * from usuario where email='$email'");
			$rows=$res->rowCount();
			if($rows>0)
			{
				$response=array("existente");
				echo json_encode($response);
				exit;
			}
			else
			{
				try
				{
					$res2=$con->query("INSERT INTO usuario (nombre,apellido,telefono,email,password) VALUES ('$nombre','$apellido','$telefono','$email','$password')");
					$response=array("exito");
					echo json_encode($response);
					exit;
				}
				catch(PDOException $e)
				{
					$response=array("error");
					echo json_encode($response);
					exit;
				}
				
			}				
			
			
		}
		catch(PDOException $e)
		{
			//$response=array("error");
		}
		
		
		
	}
	catch(PDOException $e)
	{
		echo "La conexion a la base de datos ha fallado con el siguiente mensaje: ".$e->getMessage();
	}
	
	
	

?>