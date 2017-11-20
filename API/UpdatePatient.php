<?php 
require_once 'DbConnect.php';
$response = array();

define('UPLOAD_PATH', 'patient_image/');

if(isset($_GET['apicall']) == "Patient"){
//NEED TO CONSIDER ABOUT THE USER ID
	if(isTheseParametersAvailable(array('Name','IC','Gender', 'Birthyear', 'Contact','Address','regisDate','regisType', 'id','BloodType','Meals','Allergic','Sickness','Margin'))){

		$Name = $_POST['Name'];
		$IC = $_POST['IC'];
		$Gender = $_POST['Gender'];
		$Birthyear = $_POST['Birthyear'];	//Birthyear maybe need to change
		$Contact = $_POST['Contact'];
		$Address = $_POST['Address'];
		$regisDate = $_POST['regisDate'];
		$regisType = $_POST['regisType'];
		$BloodType = $_POST['BloodType'];
		$Meals = $_POST['Meals'];
		$Allergic = $_POST['Allergic'];
		$Sickness = $_POST['Sickness'];
		$Margin = $_POST['Margin'];
		$id = $_POST['id'];


		if(isset($_FILES['pic']['name'])){
			$image = $_FILES['pic']['name'];
			$image_temp = $_FILES['pic']['tmp_name'];

			$query = "UPDATE patients SET Name = '$Name', IC = '$IC', Gender = '$Gender', Birthyear = $Birthyear, Contact = '$Contact', Address = '$Address', regisDate = '$regisDate', regisType = '$regisType', BloodType = '$BloodType', Meals = '$Meals' , Allergic = '$Allergic', Sickness = '$Sickness', Margin = $Margin, Image = '$image' WHERE id=$id";
		}
		else{
			$query = "UPDATE patients SET Name = '$Name', IC = '$IC', Gender = '$Gender', Birthyear = $Birthyear, Contact = '$Contact', Address = '$Address', regisDate = '$regisDate', regisType = '$regisType', BloodType = '$BloodType', Meals = '$Meals' , Allergic = '$Allergic', Sickness = '$Sickness', Margin = $Margin WHERE id=$id";
		}

		try{
			$stmt = $conn->prepare($query);

	//		$stmt = $conn->prepare("UPDATE patients SET Name = '$Name', IC = '$IC', Gender = '$Gender', Birthyear = $Birthyear, Contact = '$Contact', Address = '$Address', regisDate = '$regisDate', regisType = '$regisType', Image = '$image' WHERE id=$id");


			if($stmt->execute()){

				$response['error'] = false; 
				$response['message'] = 'Patient Update successfully'; 

				if(isset($_FILES['pic']['name'])){
					move_uploaded_file($image_temp, UPLOAD_PATH . $image);
				}
			}
			else{
				throw new Exception("Could Not Update patient");
			}
		}catch(Exception $e){
			$response['error'] = true; 
			$response['message'] = 'Could Not Update patient'; 
		}
		

		
		
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