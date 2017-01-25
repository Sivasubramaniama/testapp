
function renderDetailView(){
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
function addItem(){
	//localStorage add the items
	//render in UI
}

function getAllItems(){
	//get initial list from json file
	//saveToLocalStorage
	//getFrom localStorage and return
	//return json;
	return ['item a','item b','item c','item d','item e','item f','item g'];
}

var key = 'items';

function clearLocalStorage(){
	$.localStorage.remove(key);
}

function localSaveItem(item){

var ret = $.localStorage.get(key);
if(ret!=null)
{
	ret[ret.length] = item;
}else{
	ret = new Array();
	ret[0] = item;
}	
	
$.localStorage.set(key,JSON.stringify(ret));
	
	var ret = $.localStorage.get(key);
	var instance = ret[0];
	console.log("Object in localStorage: "+instance.name);
}
