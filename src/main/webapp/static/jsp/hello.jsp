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
						<input type="text" name="no" value="${status.index}"> <br>
 			 		 	name:     <input type="text" name="lst[${status.index}].name" value="${itm.name}"   />  <br>
    					alias:    <input type="text" name="lst[${status.index}].alias" value="${itm.alias}"  />
						<input type="button" class="add" value="+">  <input type="button" class="minus" value="-" > <br>
	    			 </div> 
 			 	</c:forEach>
 			</c:if>   
 			<c:if test="${USERS.lst ==  null}">	
 				<div class="grid">
					<input type="text" name="no" value="0"> <br>
	    			name:     <input type="text" name="lst[0].name" value="${USERS.lst[0].name}"   />    <br>
    				alias:    <input type="text" name="lst[0].alias" value="${USERS.lst[0].alias}"  />
					<input type="button" class="add" value="+"  >  <input type="button" class="minus" value="-" >  <br>
    			</div>
 			</c:if>	 
    		<input type="submit" value="submit"/>
    	</div> 
    </form>
    
    <div style="display:none">
    	<div class="box">
				<input type="text" name="no" value="0"> <br>
	    		name:     <input type="text" name="lst[0].name" value=""   /> <br>
    			alias:    <input type="text" name="lst[0].alias" value=""  />
			<input type="button" class="add" value="+">  <input type="button" class="minus" value="-" >  <br>
    	</div>
    </div>
</body>
<style>
	.done{
		display: none;
	}

</style>

<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript">
	 $(document).ready(function(){
		 
		 $(".add").on("click", add);
		 $(".minus").on("click", minus);
		 console.log("start");

		 function minus(){
			 //console.log($(this));
			 $(this).closest(".grid").remove();
		 }

	     function add(){
	    	 let idx = parseInt( $(".details .grid:last [name='no']").val()) + 1;

			 console.log("idx:" +idx);
			 //$(".box [name='no']").val(idx);
			 let obj = $(".box").clone().html().replace(/(name=)(\S+)\d+(\S+)/g, "$1$2"+idx+"$3" )
					 .replace(/(value=)(\S+)\d+(\S+)/, "$1$2"+idx+"$3");
			 let newobj = $("<div class=\"grid\"></div>").append(obj);
			 console.log("clone:" + newobj.html());
			 $(".details .grid:last").after(newobj);
			 //console.log($(".details .grid").length);
			 $(".details .grid:last").find(".add").on("click", add);
			 $(".details .grid:last").find(".minus").on("click", minus);
		 }

	 
	 });
</script>
</html>
 
