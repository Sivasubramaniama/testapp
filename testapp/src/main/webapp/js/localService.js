var key = 'items';

function renderDetailView() {
	//details object in localStorage key(item) -> detail(detail name, parent name, country)

	//getAllItemsDirectlyfromStorage()
	//iterate each items
	//take one item
	//check details object for item
	//if(true)
	//render in UI (table row)
	//else
	//callWebservice(item)
	//details object from server
	//persist in localStorage
	//render in UI (table row)

}

function itemCall(){
	console.log('itemcall');
}

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
             	   return;
            	}else{
            		console.log(data);
            	}
            }catch(e){
            	
            }	
        
        },
        error:function(e){
        	console.log(e);
        }
     });
	//render in UI

}

function getAllItems() {
	var temp = initItems();
	//saveToLocalStorage

	$.each(temp, function(index, t) {
		   console.log(t.id +':'+ t.itemName);
		   var ii = new Item(t.itemName);
		   localSaveItem(ii);
	});
	//localSaveItem("cocacola");
	//getFrom localStorage and return
	
	//return json;
	return $.localStorage(key);
}


function clearLocalStorage() {
	$.localStorage(key, null);
}

function localSaveItem(item) {

	console.log(item)
	
	var ret = $.localStorage(key);
	if (ret != null) {
		ret.push(item);
	} else {
		ret = new Array();
		ret[0] = item;
	}
//	JSON.stringify(ret)
	$.localStorage(key, ret);

	var ret = $.localStorage(key);
	
}


function initItems() {
	return [ {
		id : 1,
		itemName : 'pepsi',
		productName : 'PepsiCo',
		parentName : 'PepsiCo',
		country : 'US'
	}, {
		id : 2,
		itemName : 'pepsi1',
		productName : 'PepsiCo',
		parentName : 'PepsiCo',
		country : 'US'
	}, {
		id : 3,
		itemName : 'pepsi2',
		productName : 'PepsiCo',
		parentName : 'PepsiCo',
		country : 'US'
	}, {
		id : 4,
		itemName : 'pepsi3',
		productName : 'PepsiCo',
		parentName : 'PepsiCo',
		country : 'US'
	}, {
		id : 5,
		itemName : 'pepsi4',
		productName : 'PepsiCo',
		parentName : 'PepsiCo',
		country : 'US'
	} ]
}


function Item(name){
	this.itemName = name;
	
}