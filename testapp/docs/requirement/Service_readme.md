#A local Storage Application

##Service

##Service gives you the product name, parent name and country
 * http://localhost:8080/testapp/rest/product/fetch/{item}
 	
 	eg. http://localhost:8080/testapp/rest/product/fetch/pepsi		
 		
##Service gives you the item which dont have product & parent name 		
 * http://localhost:8080/testapp/rest/product/fetchToday	
 
 	eg. http://localhost:8080/testapp/rest/product/fetchToday
 	
##Service gives you all items 		
 * http://localhost:8080/testapp/rest/product/items
 
 
##Service gives you List of alternate product for the given product name  		
 * http://localhost:8080/testapp/rest/product/alter/{productName}	
 
 	eg. http://localhost:8080/testapp/rest/product/alter/pepsi	

##Service gives you List of alternate product for the given product name and native country 		
 * http://localhost:8080/testapp/rest/product/alter/{productName}/{country}	
 
 	eg. http://localhost:8080/testapp/rest/product/alter/pepsi/India
 	This api call will return all alternate product of pepsi. i.e. Indian brands from the database	