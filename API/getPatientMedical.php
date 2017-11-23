<?php 
require_once 'DbConnect.php';

$response = array();

$id = $_POST['ID'];

$query = "SELECT Date, Nurse_ID, Patient_ID, Blood_Pressure, Heart_Rate, Sugar_Level, Temperature FROM medical WHERE id = $id";

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

echo json_encode($response);
