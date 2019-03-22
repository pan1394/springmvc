<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>user register</title> 
</head>
<body> 
    <form action="save" method="post">
    	<div class="details">
 			<c:if test="${USERS.lst.size() > 0}">
 			 	<c:forEach items="${USERS.lst}" var="itm" varStatus="status" >
 			 		<div class="grid">
 			 		 	name:<input type="text" name="lst[${status.index}].name" value="${itm.name}"   ></input> <input type="button" class="minus" value="-"></button> <br>
    					alias:<input type="text" name="lst[${status.index}].alias" value="${itm.alias}"  ></input> <input type="button" class="add" value="+"></button> <br>			
	    			 </div> 
 			 	</c:forEach>
 			</c:if>   
 			<c:if test="${USERS.lst ==  null}">	
 				<div class="grid">
	    			name:<input type="text" name="lst[0].name" value="${USERS.lst[0].name}"   ></input> <input type="button" class="minus" value="-"></button> <br>
    				alias:<input type="text" name="lst[0].alias" value="${USERS.lst[0].alias}"  ></input> <input type="button" class="add" value="+"></button> <br>			
    			</div>
 			</c:if>	 
    		<input type="submit" value="submit"/>
    	</div> 
    </form>
    
    <div style="display:none">
    	<div class="box">
	    		name:<input type="text" name="lst[0].name" value=""   ></input> <input type="button" class="minus" value="-"></button> <br>
    			alias:<input type="text" name="lst[0].alias" value=""  ></input> <input type="button" class="add" value="+"></button> <br>					
    	</div>
    </div>
</body>

<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript">
	 $(document).ready(function(){
		 
		 $(".add").on("click", add);
		 
		 var idx = 0;
		 console.log("start");
	     function add(){
	    	 idx ++;
	    	 console.log("clone:" + $(".box").clone().html());
	    	 var obj = $(".box").clone().html().replace(/(name=)(\S+)\d+(\S+)/g, "$1$2"+idx+"$3" );
	    	 console.log(obj);
	    	 $(".details .grid:last").after(obj);
	     }
	 
	 });
</script>
</html>
 
