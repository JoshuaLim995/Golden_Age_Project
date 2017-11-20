<?php 
require_once 'DbConnect.php';
$response = array();

define('UPLOAD_PATH', 'patient_image/');

//if the call is an api call 
if(isset($_GET['apicall'])){
//switching the api call 
	switch($_GET['apicall']){
//if it is an upload call we will upload the image
		case 'signup':
//first confirming that we have the image and tag in the request parameter
		if(isset($_FILES['pic']['name']) && isTheseParametersAvailable(array('Name','IC','Birthyear','Gender','BloodType','Address','Contact','Meals','Allergic','Sickness','regisType','regisDate','Margin'))){

			$server_ip = gethostbyName(gethostName());


			$Name = $_POST['Name'];
			$IC = $_POST['IC'];
			$Birthyear = $_POST['Birthyear'];
			$Gender = $_POST['Gender'];
			$BloodType = $_POST['BloodType'];
			$Address = $_POST['Address'];
			$Contact = $_POST['Contact'];
			$Meals = $_POST['Meals'];
			$Allergic = $_POST['Allergic'];
			$Sickness = $_POST['Sickness'];
			$regisType = $_POST['regisType'];
			$regisDate = $_POST['regisDate'];
			$Margin = $_POST['Margin'];
			
			$image = $_FILES['pic']['name'];
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
			}catch(Exception $e){
				$response['error'] = true;
				$response['message'] = 'Could not register patient';
			}
		}else{
			$response['error'] = true;
			$response['message'] = "Required params not available";
		}
		break;
			
		default: 
		$response['error'] = true;
		$response['message'] = 'Invalid api call';
	}
}else{
	header("HTTP/1.0 404 Not Found");
	echo "<h1>404 Not Found</h1>";
	echo "The page that you have requested could not be found.";
	exit();
}
//displaying the response in json 
header('Content-Type: application/json');

echo json_encode($response);


function isTheseParametersAvailable($params){
	foreach($params as $param){
		if(!isset($_POST[$param])){
			return false; 
		}
	}
	return true; 
}

?>
