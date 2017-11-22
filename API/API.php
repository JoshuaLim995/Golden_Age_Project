<?php

require_once 'DbConnect.php';
$response = array();

if(isset($_GET['apicall']) == "Create"){
  
  if(isTheseParametersAvailable(array('Name','IC','Gender', 'Birthyear', 'Contact','Address','regisDate','regisType'))){
    //Get registeration type
    $regisType = $_POST['regisType'];
    
    $Name = $_POST['Name'];
		$IC = $_POST['IC'];
		$Gender = $_POST['Gender'];
		$Birthyear = $_POST['Birthyear'];
		$Contact = $_POST['Contact'];
		$Address = $_POST['Address'];
		$regisDate = $_POST['regisDate'];
		$Password = md5($_POST['IC']);
    
    if($regisType == "A" || $regisType == "N" || $regisType == "D"){
      $query = "INSERT INTO users (Name, IC, Gender, Birthyear,Contact, Address, regisDate, regisType, Password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
      //Use User table name
      $tableName = "User";
      $successMsg = 'User registered successfully';
      $failMsg = 'Unable to Register User'; 
    }
    else if($regisType == "C"){
    }
    else if($regisType == "P"){
    }
    else{
      $response['error'] = true; 
      $response['message'] = 'Invalid Register Type';
    }
    
    if($tableName != null){
      $result = isExist($tableName, $check_name, $check_ic);
      if(!$result){
        switch($tableName){
          case "User":
            //Run function add User
            break;
          default:
            $response['error'] = true; 
            $response['message'] = 'Invalid Table Name'; 
            break;
        }
      }
      else
        $response = $result;
    }
    else{
      $response['error'] = true; 
      $response['message'] = 'No Table'; 
    }
  }
  else{
    $response['error'] = true; 
		$response['message'] = 'required parameters are not available';     
  }  
}
else if(isset($_GET['apicall']) == "Read"){
  
  
}
else if(isset($_GET['apicall']) == "Update"){
  
}
else if(isset($_GET['apicall']) == "Delete"){
  
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
		if(!isset($_POST[$param])){
			return false; 
		}
	}
	return true; 
}

//Function to check existing account Should return false if not exist
function isExist($tableName, $check_name, $check_ic){
  $response = array();
  switch($tableName){
    case "User":
      $query = "SELECT id FROM users WHERE IC = $check_ic";     
      break;
    default:
      $query = null;
      break;
  }
  if($query != null){
    $stmt = $conn->prepare($query);
		$stmt->execute();
		$stmt->store_result();
    
    if($stmt->num_rows > 0){
      $response['error'] = true;
      $response['message'] = 'Data Exist in Database';
      $stmt->close();
      return $response;
    }
    else {
      return false;      
    }
  }
  else{
    $response['error'] = true;
    $response['message'] = 'Query is NULL';
    return $response;
  }
}

//Function to add User
