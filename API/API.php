<?php

//require_once 'DbConnect.php';
$response = array();
$server_ip = gethostbyName(gethostName());

//Patient image location path
define('UPLOAD_PATH', 'patient_image/');

//Get registeration type
//User, Client, Patient
$type = $_POST['type'];

if(isset($_GET['apicall']) == "Create"){	
	
	//General Information
	$Name = $_POST['Name'];
	$IC = $_POST['IC'];
	$Gender = $_POST['Gender'];
	$Birthyear = $_POST['Birthyear'];
	$Contact = $_POST['Contact'];
	$Address = $_POST['Address'];
	$regisDate = $_POST['regisDate'];
	$regisType = $_POST['regisType'];	
	
	switch($type){
		case "User":
			//Check if all parameters are available
		if(isTheseParametersAvailable(array($Name,$IC,$Gender, $Birthyear, $Contact,$Address,$regisDate,$regisType))){
			//Check if User exist in the database
			$exist = isExist("Users", $IC);
			if(!$exist)
				$response = createUser($Name,$IC,$Gender, $Birthyear, $Contact,$Address,$regisDate,$regisType);
			else{
				$response['error'] = true;
				$response['message'] = 'User Exist in Database';
			}
		}
		else{
			$response['error'] = true;
			$response['message'] = 'Required Parameters not available';
		}
		break;		

		case "Client":
//Client additional information
		$Patient_IC = $_POST['Patient_IC'];
		$Patient_Name =  $_POST['Patient_Name'];

			//Check if all parameters are available
		if(isTheseParametersAvailable(array($Name,$IC,$Gender, $Birthyear, $Contact,$Address,$regisDate,$regisType,$Patient_IC,$Patient_Name))){
			//Check if Client exist in the database
			$exist = isExist("Clients", $IC);
			if(!$exist){
				$Patient_ID = getPatientID($Patient_Name, $Patient_IC);
				if($Patient_ID > 0)
					$response = createClient($Name, $IC, $Gender, $Birthyear, $Contact, $Address, $regisDate, $regisType, $Patient_ID);
				else{
					$response['error'] = true;
					$response['message'] = 'Invalid Patient Details';
				}
			}
			else{
				$response['error'] = true;
				$response['message'] = 'Client Exist in Database';
			}
		}
		else{
			$response['error'] = true;
			$response['message'] = 'Required Parameters not available';
		}
		break;
		case "Patient":
		
	//Patient additional information
		$BloodType = $_POST['BloodType'];
		$Meals = $_POST['Meals'];
		$Allergic = $_POST['Allergic'];
		$Sickness = $_POST['Sickness'];
		$Margin = $_POST['Margin'];
		//Image
		$image = $_FILES['pic']['name'];	
			//Check if all parameters are available
		if(isTheseParametersAvailable(array($Name, $IC, $Birthyear, $Gender, $BloodType, $Address, $Contact, $Meals, $Allergic, $Sickness, $regisType, $regisDate, $Margin, $image))){
			//Check if Client exist in the database
			$exist = isExist("Patients", $IC);
			if(!$exist)
				$response = createPatient($Name, $IC, $Birthyear, $Gender, $BloodType, $Address, $Contact, $Meals, $Allergic, $Sickness, $regisType, $regisDate, $Margin, $image);
			else{
				$response['error'] = true;
				$response['message'] = 'Patient Exist in Database';
			}
		}
		else{
			$response['error'] = true;
			$response['message'] = 'Required Parameters not available';
		}
		break;
		default:
		$response['error'] = true; 
		$response['message'] = 'Invalid Register Type';
		break;
	}

}
else if(isset($_GET['apicall']) == "ReadAll"){
	switch($type){
		case "User":
			$query = "SELECT ID, Name, RegisType FROM users";
		break;			
		case "Client":
			$query = "SELECT ID, Name FROM clients";
		break;
		case "Patient":
			$query = "SELECT ID, Name FROM patients";
		break;
		default:
			$response['error'] = true; 
			$response['message'] = 'Invalid Register Type';
		break;
	}
	if(isset($query))
		$response = getData($query);
}

else if(isset($_GET['apicall']) == "ReadData"){
	//get id 
	$id = $_POST['ID'];
	if(isTheseParametersAvailable($id)){
		switch($type){
		case "User":
			$query = "SELECT ID, Name, IC, Contact, BirthYear, Address, Gender, RegisDate, RegisType FROM users WHERE id = $id";
		break;			
		case "Client":
			$query = "SELECT c.ID, c.Name, c.IC, c.Contact, c.BirthYear, c.Address, c.Gender, c.RegisDate, c.Patient_ID,  p.Name as P_Name FROM clients c, patients p WHERE c.Patient_ID = p.ID AND c.ID = $id";
		break;
		case "Patient":
			$query = "SELECT ID, Name, IC, Contact, BirthYear, Address, Gender, RegisDate, BloodType, Meals , Allergic, Sickness, Margin, Image FROM patients WHERE id = $id";
		break;
		default:
			$response['error'] = true; 
			$response['message'] = 'Invalid Register Type';
		break;
	}
	if(isset($query))
		$response = getData($query);
	}
	else{
		$response['error'] = true; 
		$response['message'] = 'Required parameter not available';	
	}
}
else if(isset($_GET['apicall']) == "Update"){
	//General Information
	$id = $_POST['ID'];
	$Name = $_POST['Name'];
	$IC = $_POST['IC'];
	$Gender = $_POST['Gender'];
	$Birthyear = $_POST['Birthyear'];
	$Contact = $_POST['Contact'];
	$Address = $_POST['Address'];
	$regisDate = $_POST['regisDate'];
	$regisType = $_POST['regisType'];
	
	switch($type){
		case "User":
			if(isTheseParametersAvailable(array($Name,$IC,$Gender, $Birthyear, $Contact,$Address,$regisDate,$regisType, $id))){
				$response = updateUser($Name,$IC,$Gender, $Birthyear, $Contact,$Address,$regisDate,$regisType, $id);
			}
			else{
				$response['error'] = true;
				$response['message'] = 'Required Parameters not available';
			}
		break;			
		case "Client":
			if(isTheseParametersAvailable(array($Name,$IC,$Gender, $Birthyear, $Contact,$Address,$regisDate,$regisType, $id))){
				$response = updateClient($Name,$IC,$Gender, $Birthyear, $Contact,$Address,$regisDate,$regisType, $id);
			}
			else{
				$response['error'] = true;
				$response['message'] = 'Required Parameters not available';
			}
		break;
		case "Patient":
			//Patient additional information
			$BloodType = $_POST['BloodType'];
			$Meals = $_POST['Meals'];
			$Allergic = $_POST['Allergic'];
			$Sickness = $_POST['Sickness'];
			$Margin = $_POST['Margin'];
			
			if(isTheseParametersAvailable(array($Name, $IC, $Birthyear, $Gender, $BloodType, $Address, $Contact, $Meals, $Allergic, $Sickness, $regisType, $regisDate, $Margin, $id))){
				$response = createPatient($Name, $IC, $Birthyear, $Gender, $BloodType, $Address, $Contact, $Meals, $Allergic, $Sickness, $regisType, $regisDate, $Margin, $id);
			}
			else{
				$response['error'] = true;
				$response['message'] = 'Required Parameters not available';
			}
		break;
		default:
		$response['error'] = true; 
		$response['message'] = 'Invalid Register Type';
		break;
	}
}

else if(isset($_GET['apicall']) == "Delete"){
	//get id 
	$id = $_POST['ID'];
	if(isTheseParametersAvailable($id)){
		
		switch($type){
			case "User":
				$query = "DELETE FROM users WHERE ID = $id";
		break;			
		case "Client":
			$query = "DELETE FROM clients WHERE id = $id";
		break;
		case "Patient":
			$query = "DELETE FROM patients WHERE id = $id";
		break;
		default:
			$response['error'] = true; 
			$response['message'] = 'Invalid Register Type';
		break;
		}
		if(isset($query))
			$response = delete($query);
	}
	else{
		$response['error'] = true; 
		$response['message'] = 'Required parameter not available';
	}
}
else {
	$response['error'] = true; 
	$response['message'] = 'Invalid API Call';
}

//Send out the Response
echo json_encode($response);


//Function to check parameter
function isTheseParametersAvailable($params){
	foreach($params as $param){
		if(!isset($param)){
			return false; 
		}
	}
	return true; 
}

//Function to check existing account Should return false if not exist
function isExist($tableName, $IC){
	include 'DbConnect.php';
	$query = "SELECT id FROM $tableName WHERE IC = ?";
	
	$stmt = $conn->prepare($query);
	$stmt->bind_param("s", $IC);
	$stmt->execute();
	$stmt->store_result();
	
	if($stmt->num_rows > 0)
		return true;
	else 
		return false;    
}

//Function to add User
function createUser($Name,$IC,$Gender, $Birthyear, $Contact,$Address,$regisDate,$regisType){
	include 'DbConnect.php';
	$response = array();
	$Password = md5($IC);
	$stmt = $conn->prepare("INSERT INTO users (Name, IC, Gender, Birthyear,Contact, Address, regisDate, regisType, Password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
	$stmt->bind_param("sssssssss", $Name, $IC, $Gender, $Birthyear, $Contact, $Address, $regisDate, $regisType, $Password);

	if($stmt->execute()){
		$response['error'] = false; 
		$response['message'] = 'User registered successfully'; 		
	}
	else{
		$response['error'] = true; 
		$response['message'] = 'Unable to Register User'; 
	}
	$stmt->close();
	return $response;
}

//Function to get Patient's ID
function getPatientID($Patient_Name, $Patient_IC){
	include 'DbConnect.php';
	$stmt = $conn->prepare("SELECT id FROM patients WHERE Name = ? AND ic = ?");
	$stmt->bind_param("ss",$Patient_Name, $Patient_IC);
	$stmt->execute();
	$stmt->store_result();
	if($stmt->num_rows > 0){
		$stmt->bind_result($Patient_ID);
		$stmt->fetch();
		return $Patient_ID;
	}
	else
		return 0;
}

//Funtion to add Client
function createClient($Name, $IC, $Gender, $Birthyear, $Contact, $Address, $regisDate, $regisType, $Patient_ID){
	include 'DbConnect.php';
	$response = array();
	$Password = md5($IC);
	$stmt = $conn->prepare("INSERT INTO clients (Name, IC, Gender, Birthyear,Contact, Address, regisDate, regisType, Password, Patient_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	$stmt->bind_param("ssssssssss", $Name, $IC, $Gender, $Birthyear, $Contact, $Address, $regisDate, $regisType, $Password, $Patient_ID);
	
	if($stmt->execute()){
		$response['error'] = false; 
		$response['message'] = 'Client registered successfully'; 		
	}
	else{
		$response['error'] = true; 
		$response['message'] = 'Unable to Register Client'; 
	}
	$stmt->close();
	return $response;
}

//Function to add Patient
function createPatient($Name, $IC, $Birthyear, $Gender, $BloodType, $Address, $Contact, $Meals, $Allergic, $Sickness, $regisType, $regisDate, $Margin, $image){
	include 'DbConnect.php';
	$response = array();
	$image_temp = $_FILES['pic']['tmp_name'];
	try{
		//Store into database
		$stmt = $conn->prepare("INSERT INTO patients (Name, IC, Birthyear, Gender, BloodType, Address, Contact, Meals, Allergic, Sickness, regisType, regisDate, Margin, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		$stmt->bind_param("ssssssssssssss", $Name, $IC, $Birthyear, $Gender, $BloodType, $Address, $Contact, $Meals, $Allergic, $Sickness, $regisType, $regisDate, $Margin, $image);
		if($stmt->execute()){
			$response['error'] = false;
			$response['message'] = 'Patient registered successfully';
			move_uploaded_file($image_temp, UPLOAD_PATH . $image);
		}else{
			throw new Exception("Could not register patient");
		}
		$stmt->close();
	}catch(Exception $e){
		$response['error'] = true;
		$response['message'] = 'Could not register patient';
		$stmt->close();
	}	
	return $response;
}

//Function to get data from using query
function getData($query){
	include 'DbConnect.php';
	$response = array();
	
	$result=$conn->query($query);
	if($result -> num_rows > 0){
		while($e = mysqli_fetch_assoc($result)){
			$output[]=$e;
		}
		$response['error'] = false;
		$response['message'] = 'Success';
		$response['result'] = $output;
	}
	else{
		$response['error'] = true;
		$response['message'] = 'No record';
	}
	return $response;
}

//Function to update User
function updateUser($Name,$IC,$Gender, $Birthyear, $Contact,$Address,$regisDate,$regisType, $id){
	include 'DbConnect.php';
	$response = array();
	$stmt = $conn->prepare("UPDATE users SET Name = '$Name', IC = '$IC', Gender = '$Gender', Birthyear = '$Birthyear', Contact = '$Contact', Address = '$Address', regisDate = '$regisDate', regisType = '$regisType' WHERE id=$id");
	if($stmt->execute()){
		$response['error'] = false; 
		$response['message'] = 'User Update successfully'; 
	}
	else{
		$response['error'] = true; 
		$response['message'] = 'Could Not Update User'; 
	}
	$stmt->close();
	return $response;
}

//Function to update Client
function updateClient($Name,$IC,$Gender, $Birthyear, $Contact,$Address,$regisDate,$regisType, $id){
	include 'DbConnect.php';
	$response = array();
	if($stmt->execute()){
			$response['error'] = false; 
			$response['message'] = 'Client Update successfully'; 
		}
		else{
			$response['error'] = true; 
			$response['message'] = 'Could Not Update Client'; 
		}
		$stmt->close();
	return $response;
}

//Function to update Patient
function updatePatient($Name, $IC, $Birthyear, $Gender, $BloodType, $Address, $Contact, $Meals, $Allergic, $Sickness, $regisType, $regisDate, $Margin, $id){
	include 'DbConnect.php';
	$response = array();
	//check if image is null
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
}

//Function to delete data
function delete($query){
	include 'DbConnect.php';
	$response = array();
	
	$stmt = $conn->prepare($query);
	if($stmt->execute()){
		$stmt->close();
		$response['error'] = false;
		$response['message'] = 'Delete Success';
	}
	else{
		$stmt->close();
		$response['error'] = true; 
		$response['message'] = 'Error Execute Query';
	}
	return $response;
}
	
	
