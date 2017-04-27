var key = 'items';
var domain = '/';
function addItem(){
	console.log('addItem called');
	var newItem = $('#newItem').val();
	if(newItem.length == 0){
		console.log('Item text box is empty');
		$('#msg').text('Item text box is empty');
		return;
	}else if(validateSpecialChars(newItem)){
		$('#msg').text('No Special Characters allowed');
		return;
	}else{
		$('#msg').text('');
	}
	//localStorage add the items
	var ii = new Item(newItem);
	var saved = localSaveItem(ii);
	 $('#newItem').val("");
 	 if(!saved){
 		 addNewItem(newItem);
 	 }
	//sendtoserver
var itemurl = domain+'testapp/rest/product/fetch/'+newItem;
	
	$.ajax( {
        url:itemurl,
        success:function(data) {
        	console.log(data);
            try{
            	if(data.hasOwnProperty('errorCode')){
             	  console.log("New Item added to database");
             	  //$('#newItem').val("");
             	  //addNewItem(newItem);
             	  return;
            	}else{
            	 	//store details
                 	var d = new Detail(newItem, data.productName, data.parentName, data.country);
                 	$.localStorage(newItem, d);
                }
            }catch(e){
            	console.log(e);
            }	
        
        },
        error:function(e){
        	console.log(e);
        }
     });
	//render in UI

}

function addNewItem(newItem){
	
	try{
		var id = newItem+"1";
		$('#items .list-group').prepend('<li id="'+ id +'" class="list-group-item">'+ newItem+'</li>');
		$("#"+id).effect( "bounce", {times:3}, 300 );
	}catch(e){
		console.log(e);	
	}
}

function validateSpecialChars(value){
	 var regex = new RegExp("[^A-Za-z0-9]");
     var key = value;

     if (!regex.test(key)) {
        return false;
     }
     return true;
}

function getAllItems() {
	var retu = $.localStorage(key);
	return retu;
}


function load(){
	var items = getAllItems();
	console.log(items);
	if(items != null){
		$.each(items, function(index, i) {
			$('#items .list-group').append('<li class="list-group-item">'+ i.itemName+'</li>');
		});	
	}else{
		 $('#msg').text("please add an Item name in the text box to start");
   }

	var nat = $.localStorage('native');
	if(nat == null){
		nat = 'India';
		$.localStorage('native', nat);
	}
	
	
}


function clearLocalStorage() {
	$('#items .list-group').hide( "explode", {pieces: 16 }, 2000 );
	$('#items .list-group').empty();
	$('#items .list-group').show( "explode", {pieces: 16 }, 2000 );
	$.localStorage(key, null);
}

function localSaveItem(item) {

	//console.log(item);
	var flag = false;
	var ret = $.localStorage(key);
	if (ret != null) {
		$.each(ret, function(index, t) {
  		if(t.itemName === item.itemName){
  			flag = true;
  		}   
		});
		
		if(!flag){ //check if contains
			ret.push(item);
		}else{
			
		}
	} else {
		ret = new Array();
		ret[0] = item;
	}

	$.localStorage(key, ret); //saving
	return flag;
}

function removeItem(){
	//console.log('removeItem called');
	$("#items ul li.active").hide( "explode", {pieces: 16 }, 2000 );
	$('#items ul li.active').remove();
	
}

function makeActive($li){
	$('#alter li.active').removeClass('active');
	$li.toggleClass('active');
}

function replaceItem($this){
	$this.toggleClass('active');
	
	var alterText = $('#alter ul li.active').text();
	
	//var rem = $('#items ul li.active').text();
	//var ret = $.localStorage(key);
	//console.log(ret)
	//$.localStorage(key, ret); //saving

	$('#items ul li.active').remove();
	
	
	var rep = alterText.replace('+',' ');
	console.log(alterText+ " == replacing == "+rep);
	//var id = rep+"1";
	$('#items .list-group').prepend('<li class="list-group-item active">'+ rep +'</li>');
	$('#items .list-group').effect( "shake", {times:2}, 1000 );
	

}

function viewDetails($li){
	$('#items li.active').removeClass('active');
	$li.toggleClass('active');
	var itemName =$li.text();
	var d = $.localStorage(itemName);
	if(d == null || d.productName === 'Unknown'){
		var itemurl = domain+'testapp/rest/product/fetch/'+itemName;
		$.ajax( {
	        url:itemurl,
	        success:function(data) {
	            try{
	            //	console.log(data);
	            	displayDetails(data);
	            	var d = new Detail(itemName, data.productName, data.parentName, data.country);
                 	$.localStorage(itemName, d);
	            }catch(e){
	            	console.log(e);
	            }	
	        
	        },
	        error:function(e){
	        	console.log(e);
	        }
	     });
		
	}else{
		displayDetails(d);
	}
	
}

function displayDetails(data){
   	$('#details .list-group').empty();
	$('#details .list-group').append(
			'<a href="#" class="list-group-item active">'+
		      '<h4 class="list-group-item-heading">'+data.country+'</h4>'+
		      '<p class="list-group-item-text">'+data.parentName+'</p>'+
		    '</a>');	
	$('#details .list-group').effect( "pulsate", {times:1}, 3000 );
     
}


function alterItems($li){
	
	var naCountry = $.localStorage('native');
	
	var alterurl = domain+'testapp/rest/product/alter/'+$li.text()+'/'+naCountry;
	
	$.ajax( {
        url:alterurl,
        success:function(data) {
        	console.log(data);
            try{
            	if(data.hasOwnProperty('errorCode')){
            		$('#alter .list-group').empty();
            		$('#alter .list-group').append('<li class="list-group-item">'+ data.errorCode+'</li>');
            	}else{
            		$('#alter .list-group').empty();
		            $.each(data, function(index, t) {
	     				//console.log(t.productName);
	     				$('#alter .list-group').append('<li class="list-group-item">'+t.productName+ '<span class="badge">+</span></li>');
		            });
            	}

 					            
            }catch(e){
            	console.log(e);
            }	
        
        },
        error:function(e){
        	console.log(e);
        }
     });
	
}

function Item(name){
	this.itemName = name;
}

function Detail(iName, pName, paName, country){
	this.itemName = iName;
	this.productName = pName;
	this.parentName = paName;
	this.country = country;
}

/*Category View JS*/

function loadCat(){

	var catUrl = domain+'testapp/rest/product/cat/all';

	$.ajax( {
	    url:catUrl,
	    success:function(data) {
	    	console.log(data);
	    	$.each(data.category, function(index, i) {
	    	    $('#cat').append('<option>'+ i.categoryName +'</option>');
	    	});	
	    },
	    error:function(e){
	    	console.log(e);
	    }
	 });

}


function getProducts(cat){

		var catUrl = domain+'testapp/rest/product/cat/'+cat;

		$.ajax( {
		    url:catUrl,
		    success:function(data) {
		    	console.log(data);
		    	if(data.hasOwnProperty('errorCode')){
	        		$('#items .list-group').empty();
	        		$('#items .list-group').append('<li class="list-group-item">'+ data.errorCode+'</li>');
	        	}else{
	        		$('#items .list-group').empty();
	        		$.each(data, function(index, i) {
	    				$('#items .list-group').append('<li class="list-group-item">'+ i.productName+'</li>');
	    			});
	        		
	        	}
		    	
		    },
		    error:function(e){
		    	console.log(e);
		    }
		 });

}