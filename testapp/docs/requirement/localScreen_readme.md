A local Storage Application

DataStructure:
Item set
Detail set
Parent set

Relationship:
	Item has a Detail, Details has a Parent

Screens:
Item Screen
	A Screen should be available to Create, Read, Update and Delete operations.
	All Operation should be storing the data into localStorage of browser	
Detail Screen
	A Screen to display the Item Name, Detail Name and Parent Name
	A Webservice call to remoteService to fetch the Detail & Parent
		if Detail & Parent available
			display in a table
		else 
			call Webservice to insert the items

Admin Screen:
	A Screen to enter Detail & parent records
	List all items without Details & parent in UI
	Enter Detail & Parent of Each item
	Save to database			 
			 