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
		if(isset($_FILES['pic']['name']) && isset($_POST['tags'])){
//uploading file and storing it to database as well 
			try{
				move_uploaded_file($_FILES['pic']['tmp_name'], UPLOAD_PATH . $_FILES['pic']['name']);
				$stmt = $conn->prepare("INSERT INTO images (image, tags) VALUES (?,?)");
				$stmt->bind_param("ss", $_FILES['pic']['name'],$_POST['tags']);
				if($stmt->execute()){
					$response['error'] = false;
					$response['message'] = 'File uploaded successfully';
				}else{
					throw new Exception("Could not upload file");
				}
			}catch(Exception $e){
				$response['error'] = true;
				$response['message'] = 'Could not upload file';
			}
		}else{
			$response['error'] = true;
			$response['message'] = "Required params not available";
		}
		break;
//in this call we will fetch all the images 
		case 'getpics':
//getting server ip for building image url 
		$server_ip = gethostbyname(gethostname());
//query to get images from database
		$stmt = $conn->prepare("SELECT id, image, tags FROM images");
		$stmt->execute();
		$stmt->bind_result($id, $image, $tags);
		$images = array();

//fetching all the images from database
//and pushing it to array 
		while($stmt->fetch()){
			$temp = array();
			$temp['id'] = $id; 
			$temp['image'] = 'http://' . $server_ip . '/Android/'. UPLOAD_PATH . $image; 
			$temp['tags'] = $tags; 
			array_push($images, $temp);
		}
//pushing the array in response 
		$response['error'] = false;
		$response['images'] = $images; 
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

?>