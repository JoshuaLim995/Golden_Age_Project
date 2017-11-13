<?php 

require_once 'DbConnect.php';

$response = array();

if(isset($_GET['apicall'])){
	
	switch($_GET['apicall']){
		
		case 'signup':
		
		if(isTheseParametersAvailable(array('Date','Nurse_ID','Patient_ID','Blood_Pressure','Heart_Rate','Sugar_Level'))){

      $Date = $_POST['Date'];
      $Nurse_ID = $_POST['Nurse_ID'];
			$Patient_ID = $_POST['Patient_ID'];
      $Blood_Pressure = $_POST['Blood_Pressure']; 
      $Heart_Rate = $_POST['Heart_Rate'];
      $Sugar_Level = $_POST['Sugar_Level'];
      
      
      $stmt = $conn->prepare("INSERT INTO medical (Date, Nurse_ID, Patient_ID, Blood_Pressure, Heart_Rate, Sugar_Level) VALUES (?,?,?)");

			$stmt->bind_param("ssssss", $Date, $Nurse_ID, $Patient_ID, $Blood_Pressure, $Heart_Rate, $Sugar_Level);


			if($stmt->execute()){
				$stmt->close();
				$response['error'] = false; 
				$response['message'] = 'Patient\'s medical saved'; 
			}

		}else{
			$response['error'] = true; 
			$response['message'] = 'required parameters are not available'; 
		}


		break;
		default: 
		$response['error'] = true; 
		$response['message'] = 'Invalid Operation Called';

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



?>
