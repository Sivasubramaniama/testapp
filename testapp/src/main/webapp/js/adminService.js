var current_page = 1;
var records_per_page = 1;

var objJson;

function resetTextBox(){
	$('#itemName').val('');
	$('#productName').val('');
	$('#categoryName').val('');
	$('#boss').val('');
	$('#parentName').val('');
	$('#country').val('');
	
}

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
           resetTextBox();
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
       	  console.log(data);
          if(data === undefined){
        	   $('#msg').text("No record without Product info");
        	   return;
          }else{
        	  objJson = data;
        	  changePage(1);
          }
        
        },
        error:function(e){
        	console.log(e);
        }
     });
}

 // Can be obtained from another source, such as your objJson variable

function prevPage()
{
    if (current_page > 1) {
        current_page--;
        changePage(current_page);
    }
}

function nextPage()
{
    if (current_page < numPages()) {
        current_page++;
        changePage(current_page);
    }
}
    
function changePage(page)
{
    var btn_next = document.getElementById("btn_next");
    var btn_prev = document.getElementById("btn_prev");
    
    var listing_table = document.getElementById("listingTable");
    
    var page_span = document.getElementById("page");
 
    // Validate page
    if (page < 1) page = 1;
    if (page > numPages()) page = numPages();

    //listing_table.innerHTML = "";

    for (var i = (page-1) * records_per_page; i < (page * records_per_page); i++) {
//    	listing_table.innerHTML +=  + "<br>";
    try{
    	$('#itemName').val(objJson.item[i].itemName);
    }catch(e){
    	$('#itemName').val(objJson.item.itemName);    	    	
    }
    }
    page_span.innerHTML = page;

    if (page == 1) {
        btn_prev.style.visibility = "hidden";
    } else {
        btn_prev.style.visibility = "visible";
    }

    if (page == numPages()) {
        btn_next.style.visibility = "hidden";
    } else {
        btn_next.style.visibility = "visible";
    }
}

function numPages()
{
    return Math.ceil(objJson.item.length / records_per_page);
}

//window.onload = function() {
//    changePage(1);
//};

function swapPanel() {
	var swap = 'admin_swap';
	var ret = $.localStorage(swap);
	if(ret === undefined){
		$.localStorage(swap, 1);
		$("#getItem").css("display", "block");
		$("#deleteItem").css("display", "none");
		$.localStorage(swap, 1);
	}else if(ret === 1){
		$("#getItem").css("display", "none");
		$("#deleteItem").css("display", "block");
		$.localStorage(swap, 0);
	}else{
		$("#getItem").css("display", "block");
		$("#deleteItem").css("display", "none");
		$.localStorage(swap, 1);
	}
}

function getDetails(){
	
	var get = '/testapp/rest/product/fetch/'+$('#itemName_d').val();
	
	$.ajax( {
        url:get,
        success:function(data) {
           console.log(data);
           $('#productName_d').val(data.productName);
        },
        error:function(e){
        	console.log(e);
        	
        }
     });
	
}

function deleteDetails(){
	
	var deleteUrl = '/testapp/rest/product/delete/'+$('#itemName_d').val();
	
	$.ajax( {
        url:deleteUrl,
        success:function(data) {
           console.log(data.message);
           $('#msg').text(data.message);
           $('#itemName_d').val('');
           $('#productName_d').val('');
        },
        error:function(e){
        	console.log(e);
        	
        }
     });
	
}
