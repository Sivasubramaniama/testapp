
function validate(v){
	if(v.length == 0)
	{
		$('#msg').text('validation error: one of the textbox is empty');
	    return true;	
	}
	return false;
}

function saveDetails(){
	
	console.log('calling saveDetails');
	var itemName = $('#itemName').val();
	var productName = $('#productName').val();
	var categoryName = $('#categoryName').val();
	var boss = $('#boss').val();
	var parentName = $('#parentName').val();
	var country = $('#country').val();
	if(validate(itemName) || validate(productName) || validate(categoryName) || validate(parentName) || validate(boss) || validate(country)){
		return;
	}
	console.log('validation success');
	///details/create/{item}/{productName}/{categoryName}/{parentName}/{boss}/{country}
	var saveurl = '/testapp/rest/product/details/create/'+itemName+'/'
	+productName+'/'+categoryName+'/'+parentName+'/'
	+boss+'/'+country;
	
	$.ajax( {
        url:saveurl,
        success:function(data) {
           console.log(data);
           $('#itemName').val();
           $('#msg').text('Successfully Saved');
           
        },
        error:function(e){
        	console.log(e);
        	
        }
     });
	
	console.log('saveDetails completed');
	
}


function getItem(){
	var geturl = '/testapp/rest/product/fetchToday';
	
	$.ajax( {
        url:geturl,
        success:function(data) {
           if(data === undefined){
        	   $('#msg').text("No record without Product info");
        	   return;
           }
        console.log(data);
        $('#itemName').val(data.itemName);
        },
        error:function(e){
        	console.log(e);
        }
     });
}
