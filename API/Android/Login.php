<?php 

require_once 'DbConnect.php';

$response = array();

if(isset($_GET['apicall']) == "login"){

//	if(isTheseParametersAvailable(array('Name', 'Password'))){

		$Name = $_POST['Name'];
		$Password = md5($_POST['Password']); 

//		$Name = 'maggie';
//		$Password = md5('123123'); 

		//LATER WILL HAVE TO ADD MORE

		$query = "SELECT id, Name, regisType FROM users WHERE Name = '$Name' AND Password = '$Password'";
		$result = login($query);
		if(!$result){
			$query = "SELECT id, Name, regisType FROM clients WHERE Name = '$Name' AND Password = '$Password'";

			$result = login($query);
			if($result){
				$response = $result;
				
			}
			else{
				$response['error'] = true; 
				$response['message'] = 'Invalid username or password';
			}
		}
		else{
			$response = $result;
		}





		
/*
	}
	else{
		$response['error'] = true; 
		$response['message'] = 'Required parameters not available';
	}
*/
}else{
	$response['error'] = true; 
	$response['message'] = 'Invalid API Call';
}

echo json_encode($response);

function login($query){
	include 'DbConnect.php';
	$response = array();
	$stmt = $conn->prepare($query);

	$stmt->execute();

	$stmt->store_result();

	if($stmt->num_rows > 0){

		$stmt->bind_result($id, $username, $regisType);
		$stmt->fetch();

		$user = array(
			'id'=>$id, 
			'Name'=>$username, 
			'regisType'=>$regisType
			);

		$response['error'] = false; 
		$response['message'] = 'Login successfull'; 
		$response['user'] = $user; 
		return $response;
	}else{
		return false;
	}
}

function isTheseParametersAvailable($params){

	foreach($params as $param){
		if(!isset($_POST[$param])){
			return false; 
		}
	}
	return true; 
}