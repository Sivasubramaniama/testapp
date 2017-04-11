
var ekey = 'email';
var nkey = 'native';
var namekey = 'name';


function saveSettings(){
	$('#msg').text('');
	var email = $('#email').val();
	var name = $('#name').val();
	var native = $('#native').val();
//validate
	if(email.length == 0){
		$('#msg').text('email id is empty');
		return;
	}else if(!isValidEmailAddress(email)){
		$('#msg').text('please enter valid email id');
		return;
	}else if(native.length == 0){
		$('#msg').text('Native country is empty');
		return;
	}else if(name.length == 0){
		$('#msg').text('Name is empty');
		return;
	}

	$.localStorage(ekey, email);
	$.localStorage(nkey, native);
	$.localStorage(namekey, name);
	$('#msg').text('Saved Successfully');
	
var url = '/testapp/rest/person/create/'+name+'/'+email+'/'+native;
	$.ajax( {
        url:url,
        success:function(data) {
        	console.log(data);
            try{
            	if(data.hasOwnProperty('error')){
             	   $('#msg').text("New Item added to database");
             	  return;
            	}else{
 
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


function isValidEmailAddress(emailAddress) {
    var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
    return pattern.test(emailAddress);
};

function reset(){
	$.localStorage(ekey, null);
	$.localStorage(nkey, 'India');
	$.localStorage(namekey, null);
	$('#email').val('');
	$('#name').val('');
	$('#native').val('India');

}

function load(){
	$('#email').val($.localStorage(ekey));
	$('#name').val($.localStorage(namekey));
	var nat = $.localStorage(nkey);
	if(nat == null){
		nat = 'India';
		$('#native').val(nat);	
		$.localStorage(nkey, nat);
	}else{
		$('#native').val(nat);
	}
	
}