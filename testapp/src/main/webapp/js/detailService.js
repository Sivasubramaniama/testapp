var key = 'items';


function renderDetailView(){
	var list = $.localStorage(key);
	$.each(list, function(index, i) {

		var itemurl = '/testapp/rest/product/fetch/'+i.itemName;
			
			$.ajax( {
		        url:itemurl,
		        success:function(data) {
		        	console.log(data);
		            try{
		            	if(data.hasOwnProperty('errorCode')){
		             	  console.log("No detail found for itemName: "+ i.itemName);
		            	}else{
		             		$('#detailTable').append('<tr><td>'+i.itemName+'</td>'
		             				+'<td>'+data.productName+'</td>'
		             				+'<td>'+data.parentName+'</td>'
		             				+'<td>'+data.country+'</td></tr>');
		            	}
		            }catch(e){
		            	console.log(e);
		            }	
		        
		        },
		        error:function(e){
		        	console.log(e);
		        }
		     });
		
		
	});	
	
}