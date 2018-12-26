<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="com.VENUE.model.*"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>${venueVO.venue_name}</title>
    <!-- Basic -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>

<body>

	<div class="container">
		<%-- 錯誤表列 --%>
		<c:if test="${not empty eventTitleErrorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${eventTitleErrorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
	</div>





    	<div class="container">
        <form method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/VENUE/VenueServlet.do"> 
            
            <div class="form-group">
                <label for="venue_name">場地名稱</label>
                <input type="text" name="venue_name" id="venue_name" class="form-control" value="${venueVO.venue_name}">
            </div>
            
            <div class="form-group">
                <label for="address">地址</label>
                <input type="text" name="address" id="address" class="form-control" value="${venueVO.address}">
            </div>
                        
            <div class="row">
                <div class="col-xs-12 col-sm-6">
                    <div class="form-group">
                        <label for="latitude">緯度</label>
                        <input type="text" id="latitude" name="latitude" class="form-control" value="${venueVO.latitude}" readonly>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6">
                    <div class="form-group">
                        <label for="longitude">經度</label>
                        <input type="text" id="longitude" name="longitude" class="form-control" value="${venueVO.longitude}" readonly> 
                    </div>
                </div>
            </div>     
            
			<div class="form-group">
				<label for="venue_locationPic">場地位置圖</label>
				<input type="file" id="venue_locationPic" name="venue_locationPic" class="form-control" accept="image/*">
				<div>
					<img src="<%= request.getContextPath()%>/VENUE/VenueGifReader?scaleSize=400&venue_no=${venueVO.venue_no}"  id="venue_preview">
            	</div>
			</div>
            
            <div class="form-group">
                <label for="venue_name">場地資訊</label>
                <textarea name="venue_info" id="venue_info">${venueVO.venue_info}</textarea>
            </div>
            <span class="form-group">
				<button type="submit" class="btn btn-success" name="action" value="updateVenue" id="updateVenue">儲存</button>
			</span>
        </form>
        </div>

    <!-- Basic -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- ckEditor JS -->
    <script src="<%=request.getContextPath()%>/vendor/ckeditor_full/ckeditor.js"></script>
	<!-- JavaScript in HTML -->
    <script type="text/javascript">
    $(function() {
    	initVenueEditor();
    	
        $("#venue_locationPic").change(function() {
            imagesPreview(this);
        });
    });
    
    function imagesPreview(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $("#venue_preview").attr("src", e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }
    
    var initVenueEditor = ( function() {
    	var wysiwygareaAvailable = isWysiwygareaAvailable(),
    		isBBCodeBuiltIn = !!CKEDITOR.plugins.get( 'bbcode' );

    	return function() {
    		var editorElement = CKEDITOR.document.getById( 'venue_info' );

    		// :(((
    		if ( isBBCodeBuiltIn ) {
    			editorElement.setHtml(
    				'Hello world!\n\n' +
    				'I\'m an instance of [url=https://ckeditor.com]CKEditor[/url].'
    			);
    		}

    		// Depending on the wysiwygarea plugin availability initialize classic or inline editor.
    		if ( wysiwygareaAvailable ) {
    			CKEDITOR.replace( 'venue_info' );
    		} else {
    			editorElement.setAttribute( 'contenteditable', 'true' );
    			CKEDITOR.inline( 'venue_info' );

    			// TODO we can consider displaying some info box that
    			// without wysiwygarea the classic editor may not work.
    		}
    	};

    	function isWysiwygareaAvailable() {
    		// If in development mode, then the wysiwygarea must be available.
    		// Split REV into two strings so builder does not replace it :D.
    		if ( CKEDITOR.revision == ( '%RE' + 'V%' ) ) {
    			return true;
    		}

    		return !!CKEDITOR.plugins.get( 'wysiwygarea' );
    	}
    } )();
    </script>
</body>

</html>