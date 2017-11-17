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
//first confirming that we have the image and tags in the request parameter
		if(isset($_FILES['pic']['name']) && isTheseParametersAvailable(array('name','ic','Birthyear','gender','bloodType','address','contact','meals','allergic','sickness','regType','regDate','margin'))){

			$name = $_POST['name'];
			$ic = $_POST['ic'];
			$Birthyear = $_POST['Birthyear'];
			$gender = $_POST['gender'];
			$bloodType = $_POST['bloodType'];
			$address = $_POST['address'];
			$contact = $_POST['contact'];
			$meals = $_POST['meals'];
			$allergic = $_POST['allergic'];
			$sickness = $_POST['sickness'];
			$regType = $_POST['regType'];
			$regDate = $_POST['regDate'];
			$margin = $_POST['margin'];
			
			$image = $_FILES['pic']['name'];
			$image_temp = $_FILES['pic']['tmp_name'];
			
			try{
				//Check if is not null
				if(!empty($image)){
					//uploading file 
					move_uploaded_file($image_temp, UPLOAD_PATH . $image);
				}
				else if(empty($image)){
					$image = 'NULL';
				}
				
				//Store into database
				$stmt = $conn->prepare("INSERT INTO patients (name, ic, Birthyear, gender, bloodType, address, contact, meals, allergic, sickness, regType, regDate, margin, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			$stmt->bind_param("ssssssssssssss", $name, $ic, $Birthyear, $gender, $bloodType, $address, $contact, $meals, $allergic, $sickness, $regType, $regDate, $margin, $image);

				
				if($stmt->execute()){
					$response['error'] = false;
					$response['message'] = 'Patient registered successfully';
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
