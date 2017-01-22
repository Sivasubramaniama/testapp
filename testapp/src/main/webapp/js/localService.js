



function getAllItems(){
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
