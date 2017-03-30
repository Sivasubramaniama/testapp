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
             	   $('#msg').text("New Item added to database");
             	  $('#newItem').val("");
             	  addNewItem(newItem);
             	  return;
            	}else{
            		var ii = new Item(newItem);
          		   	localSaveItem(ii);
            		 $('#newItem').val("");
                 	 addNewItem(newItem);
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
	$('#items .list').append('<li>'+ newItem+'</li>');
}

function getAllItems() {

var itemurl = '/testapp/rest/product/items';

//service call
//	$.ajax( {
//        url:itemurl,
//        success:function(data) {
//        	console.log(data);
//            try{
//            	$.each(data.item, function(index, t) {
//         		      var ii = new Item(t.itemName);
//             		   localSaveItem(ii);
//         		});
//           
//            }catch(e){
//            	console.log("Error occured : "+ e);
//            }	
//        
//        },
//        error:function(e){
//        	console.log(e);
//        }
//     });

	var retu = $.localStorage(key);
	return retu;

}


function load(){
	var items = getAllItems();
	console.log(items);
	if(items != null){
		$.each(items, function(index, i) {
			$('#items .list').append('<li>'+ i.itemName+'</li>');
		});	
	}else{
		 $('#msg').text("please add an Item name in the text box to start");
   }
	
}


function clearLocalStorage() {

	
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



function Item(name){
	this.itemName = name;
	
}