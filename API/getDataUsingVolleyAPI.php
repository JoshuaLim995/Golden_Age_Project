<?php 
require_once 'DbConnect.php';

//Response
$response = array();

//GET USER DATA
$stmt = $conn->prepare("SELECT id, name, ic, register_type FROM users;");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($id, $name, $ic, $register_type);
 
 $stmt->execute();
 $stmt->store_result();
 if($stmt->num_rows > 0){   
 $response['users'] = array(); 
    while($stmt->fetch()){
      $user = array();
      $user['id'] = $id; 
      $user['name'] = $name; 
      $user['ic'] = $ic; 
      $user['register_type'] = $register_type; 
      array_push($users, $user);
      }
      
    $response['error'] = false; 
			$response['message'] = 'Login successfull'; 
			$response['users'] = $users; 
 }else{
    $response['error'] = true; 
			$response['message'] = 'Users table is empty';  
 }

 
 //display the result from response
 echo json_encode($response);

?>
