<?php 

require_once 'DbConnect.php';

$response = array();
$users = array();

// $sql = null;

if(isset($_GET['apicall'])){

	$id = $_POST['id'];
//	$id = 1;

	switch ($_GET['apicall']) {
		case 'User':
		$sql = "SELECT ID, Name, IC, Contact, BirthYear, Address, Gender, RegisDate, RegisType FROM users WHERE id = $id";
		break;
		case 'Client':
		$sql = "SELECT c.ID, c.Name, c.IC, c.Contact, c.BirthYear, c.Address, c.Gender, c.RegisDate, c.Patient_ID,  p.Name as P_Name FROM clients c, patients p WHERE c.Patient_ID = p.ID AND c.ID = $id";
		break;
		case 'Patient':
		$sql = "SELECT ID, Name, IC, Contact, BirthYear, Address, Gender, RegisDate, BloodType, Meals , Allergic, Sickness, Margin, Image FROM patients WHERE id = $id";
		break;
		default:
		$sql = null;
		break;
	}
	
	
	
	if($sql != null){
		$stmt=$conn->query($sql);

		if($stmt -> num_rows > 0){
			while($e = mysqli_fetch_assoc($stmt)){
				$output[]=$e;
			}

			$response['error'] = false;
			$response['message'] = 'Success';
			$response['result'] = $output;

		}
		else{
			$response['error'] = true;
			$response['message'] = 'No Record';
		}


	}
	
	else{

		$response['error'] = true;
		$response['message'] = 'No Query';
	}



}else{
	$response['error'] = true;
	$response['message'] = 'Invalid API call';
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
