<?php 
require_once 'DbConnect.php';
$response = array();
if(isset($_GET['apicall']) == "Client"){
//NEED TO CONSIDER ABOUT THE USER ID
	if(isTheseParametersAvailable(array('Name','IC','Gender', 'Birthyear', 'Contact','Address','regisDate','regisType', 'id'))){
		$Name = $_POST['Name'];
		$IC = $_POST['IC'];
		$Gender = $_POST['Gender'];
		$Birthyear = $_POST['Birthyear'];	//Birthyear maybe need to change
		$Contact = $_POST['Contact'];
		$Address = $_POST['Address'];
		$regisDate = $_POST['regisDate'];
		$regisType = $_POST['regisType'];
		$id = $_POST['id'];

		
	$stmt = $conn->prepare("UPDATE clients SET Name = '$Name', IC = '$IC', Gender = '$Gender', Birthyear = '$Birthyear', Contact = '$Contact', Address = '$Address', regisDate = '$regisDate', regisType = '$regisType' WHERE id=$id");

//$stmt = $conn->prepare("UPDATE users SET Name = '$Name', IC = '$IC' WHERE id=$id");

		if($stmt->execute()){

			$response['error'] = false; 
			$response['message'] = 'Client Update successfully'; 
		}
		else{
			$response['error'] = true; 
			$response['message'] = 'Could Not Update Client'; 
		}

		$stmt->close();
		
	}else{
		$response['error'] = true; 
		$response['message'] = 'required parameters are not available'; 
	}
}else{
	$response['error'] = true; 
	$response['message'] = 'Invalid API Call';
}
echo json_encode($response);

function isTheseParametersAvailable($params){
	foreach($params as $param){
		if(!isset($_POST[$param])){
			return false; 
		}
	}
	return true; 
}