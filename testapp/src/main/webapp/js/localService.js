var key = 'items';

function addItem(){
	console.log('addItem called');
	var newItem = $('#newItem').val();
	if(newItem.length == 0){
		console.log('Item text box is empty');
		$('#msg').text('Item text box is empty');
		return;
	}else{
		$('#msg').text('');
	}
	//localStorage add the items
	//sendtoserver
var itemurl = '/testapp/rest/product/fetch/'+newItem;
	
	$.ajax( {
        url:itemurl,
        success:function(data) {
        	console.log(data);
            try{
            	if(data.hasOwnProperty('errorCode')){
             	  console.log("New Item added to database");
             	  $('#newItem').val("");
             	  addNewItem(newItem);
             	  return;
            	}else{
            		var ii = new Item(newItem);
          		   	localSaveItem(ii);
            		 $('#newItem').val("");
                 	 addNewItem(newItem);
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
		$('#items .list-group').append('<li class="list-group-item">'+ newItem+'</li>');
	}catch(e){
		console.log(e);	
	}
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
	
}


function clearLocalStorage() {
	$('#items .list-group').empty();
	$.localStorage(key, null);
}

function localSaveItem(item) {

	//console.log(item);
	
	var ret = $.localStorage(key);
	if (ret != null) {
		var flag = false;
		$.each(ret, function(index, t) {
  		if(t.itemName === item.itemName){
  			flag = true;
  		}   
		});
		
		if(!flag){ //check if contains
			ret.push(item);
		}
	} else {
		ret = new Array();
		ret[0] = item;
	}

	$.localStorage(key, ret); //saving

//	var ret = $.localStorage(key);
//	console.log(ret);
}

function removeItem(){
	//console.log('removeItem called');
	 $('#items ul li.active').remove();
}

function makeActive($li){
	$('#alter li.active').removeClass('active');
	$li.toggleClass('active');
}

function replaceItem($this){
	$this.toggleClass('active');
	
	var alterText = $('#alter ul li.active').text();
	$('#items ul li.active').remove();
	
	var rep = alterText.replace('+',' ');
	console.log(alterText+ " == replacing == "+rep);
	$('#items .list-group').prepend('<li class="list-group-item active">'+ rep +'</li>');
}

function viewDetails($li){
	$('#items li.active').removeClass('active');
	$li.toggleClass('active');
	var itemName =$li.text();
	var d = $.localStorage(itemName);
	if(d == null || d.productName === 'Unknown'){
		var itemurl = '/testapp/rest/product/fetch/'+itemName;
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
}


function alterItems($li){
	
	var naCountry = $.localStorage('native');
	
	var alterurl = '/testapp/rest/product/alter/'+$li.text()+'/'+naCountry;
	
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
