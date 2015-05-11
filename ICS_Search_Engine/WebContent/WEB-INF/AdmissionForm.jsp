<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  
 pageEncoding="ISO-8859-1"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">  
<script type="text/javascript"
    src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
</head>  
<body>  
 <center>  
 
 <!-- @src http://stackoverflow.com/questions/18064666/update-div-with-jquery-ajax-response-html -->
  <script>  
   function searchResult() {  
      
    var name = $('#name').val();  
    if(name=='') {
    	alert("Enter a valid search term");
    }
    else{
    $.ajax({  
     type : "Get",   
     url : "hello.html",   
     data : "name=" + name,  
     success : function(response) {  
    	 $('#results').html(response);
     },  
     error : function(e) {  
      alert('Error: ' + e);   
     }  
    });  
    }  
   } 
  </script>  
  <div id="form">  
   <form method="get">  
   
<h1 style="text-align:center; color:red">ICS Search Engine</h1>
<div style="text-align:center;">
    <input type="text" id="name" /> <br><br>
    <input type="button" value="Search" onclick="searchResult();" /><br><br> 
</div>
<div id="results"></div>
   </form>  
  </div>  
 </center>  
</body>  
</html>  

