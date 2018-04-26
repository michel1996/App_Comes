<?php

include 'connection.php';

// Create connection
$conn = new mysqli($HostName, $HostUser, $HostPass, $DatabaseName);
 
 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 $DefaultId = 0;
 
 $ImageData = $_POST['image_path'];
 
 $idProducto = $_POST['idProducto'];

 /*$GetOldIdSQL ="SELECT id FROM UploadImageToServer ORDER BY id ASC";
 
 $Query = mysqli_query($conn,$GetOldIdSQL);
 
 while($row = mysqli_fetch_array($Query)){
 
 $DefaultId = $row['id'];
 }*/
 
 $ImagePath = "productos/foto.png";
 
 $ServerURL = "http://148.210.100.196/Comes/$ImagePath";
 
 $InsertSQL = "insert into imagenes (imagen,id_producto) values ('$ServerURL','$idProducto')";
 
 if(mysqli_query($conn, $InsertSQL)){

 file_put_contents($ImagePath,base64_decode($ImageData));

 echo "Your Image Has Been Uploaded.";
 }
 
 mysqli_close($conn);
 }else{
 echo "Not Uploaded";
 }

?>