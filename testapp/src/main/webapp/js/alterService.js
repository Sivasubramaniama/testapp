
var key = 'items';

function load(){
	var items = $.localStorage(key);
	console.log(items);
	if(items != null){
		$.each(items, function(index, i) {
			$('#items .list').append('<li>'+ i.itemName+'</li>');
		});		
	}else{
		console.log('no items added in Grocery View');	
	}
}

