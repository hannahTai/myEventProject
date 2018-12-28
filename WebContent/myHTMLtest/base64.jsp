<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Title Page</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
</head>

<body>
    <img src="<%= request.getContextPath()%>/EVENT_TITLE/EventTitleGifReader?scaleSize=850&evetit_no=E0001" id="imageid">
    
    <img src="" id="src">
    
    <button id="btn">Click Me</button>
    
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    function toDataURL(url, callback) {
    	  var xhr = new XMLHttpRequest();
    	  xhr.onload = function() {
    	    var reader = new FileReader();
    	    reader.onloadend = function() {
    	      callback(reader.result);
    	    }
    	    reader.readAsDataURL(xhr.response);
    	  };
    	  xhr.open('GET', url);
    	  xhr.responseType = 'blob';
    	  xhr.send();
    	}

    
    $(document).ready(function(){
    	$("#btn").click(function(){
        	toDataURL($("#imageid").attr("src"), function(dataUrl) {
          	  console.log('RESULT:', dataUrl)
          	  $("#src").attr("src", dataUrl);
          	})
    	});
    });
    

    
    
    </script>
</body>

</html>